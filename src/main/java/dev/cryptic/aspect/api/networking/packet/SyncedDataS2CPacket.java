package dev.cryptic.aspect.api.networking.packet;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.SyncedClientData;
import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import dev.cryptic.aspect.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import dev.cryptic.aspect.misc.SyncedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class SyncedDataS2CPacket extends AbstractPacket {
    private Map<UUID, Float> playerFluxMap;
    private Map<UUID, Integer> playerMaxFluxMap;
    private Map<UUID, ArrayList<UUID>> playerGolemUUIDMap;

    public SyncedDataS2CPacket(Map<UUID, Float> playerFluxMap, Map<UUID, Integer> playerMaxFluxMap, Map<UUID, ArrayList<UUID>> playerGolemUUIDMap) {
        this.playerFluxMap = playerFluxMap;
        this.playerMaxFluxMap = playerMaxFluxMap;
        this.playerGolemUUIDMap = playerGolemUUIDMap;
    }

    public SyncedDataS2CPacket(FriendlyByteBuf buf) {
        this.playerFluxMap = buf.readMap(FriendlyByteBuf::readUUID, FriendlyByteBuf::readFloat);
        this.playerMaxFluxMap = buf.readMap(FriendlyByteBuf::readUUID, FriendlyByteBuf::readInt);

        // Read the map of UUID lists
        int mapSize = buf.readInt();
        this.playerGolemUUIDMap = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            UUID key = buf.readUUID();
            int listSize = buf.readInt();
            ArrayList<UUID> uuidList = new ArrayList<>();
            for (int j = 0; j < listSize; j++) {
                int[] uuidArray = buf.readVarIntArray();
                UUID uuid = UUIDUtil.uuidFromIntArray(uuidArray);
                uuidList.add(uuid);
            }
            playerGolemUUIDMap.put(key, uuidList);
        }
    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(playerFluxMap, FriendlyByteBuf::writeUUID, FriendlyByteBuf::writeFloat);
        buf.writeMap(playerMaxFluxMap, FriendlyByteBuf::writeUUID, FriendlyByteBuf::writeInt);

        // Write the map of UUID lists
        buf.writeInt(playerGolemUUIDMap.size());
        for (Map.Entry<UUID, ArrayList<UUID>> entry : playerGolemUUIDMap.entrySet()) {
            buf.writeUUID(entry.getKey());
            ArrayList<UUID> uuidList = entry.getValue();
            buf.writeInt(uuidList.size());
            for (UUID uuid : uuidList) {
                int[] uuidArray = UUIDUtil.uuidToIntArray(uuid);
                buf.writeVarIntArray(uuidArray);
            }
        }


    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON CLIENT
            LocalPlayer player = Minecraft.getInstance().player;
            ClientLevel level = Minecraft.getInstance().level;

            Map<UUID, ArrayList<AbstractGolem>> playerGolemMap = new HashMap<>();
            level.entitiesForRendering().forEach(entity -> {
                if (entity instanceof AbstractGolem golem) {
                    if (playerGolemUUIDMap.get(player.getUUID()).contains(golem.getUUID())) {
                        ArrayList<AbstractGolem> golemList = new ArrayList<>();
                        golemList.add(golem);
                        playerGolemMap.put(player.getUUID(), golemList);
                    }
                }
            });


            SyncedClientData.set(playerFluxMap, playerMaxFluxMap, playerGolemMap);

        });
        return true;
    }
}
