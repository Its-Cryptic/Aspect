package dev.cryptic.aspect.api.networking.packet;

import dev.cryptic.aspect.api.client.SyncedGolemData;
import dev.cryptic.aspect.api.util.AspectSavedData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class GolemDataS2CPacket extends AbstractPacket {
    private Map<UUID, ArrayList<AspectSavedData.GolemData>> playerGolemMap;
    public GolemDataS2CPacket(Map<UUID, ArrayList<AspectSavedData.GolemData>> playerGolemMap) {
        this.playerGolemMap = playerGolemMap;
    }

    public GolemDataS2CPacket(FriendlyByteBuf buf) {
        this.playerGolemMap = buf.readMap(FriendlyByteBuf::readUUID, (buffer) -> {
            int size = buffer.readInt();
            ArrayList<AspectSavedData.GolemData> golemDataList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                UUID golemUUID = buffer.readUUID();
                int imbuedSoul = buffer.readInt();
                golemDataList.add(new AspectSavedData.GolemData(golemUUID, imbuedSoul));
            }
            return golemDataList;
        });
    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(playerGolemMap, FriendlyByteBuf::writeUUID, (buffer, golemDataList) -> {
            buffer.writeInt(golemDataList.size());
            for (AspectSavedData.GolemData golemData : golemDataList) {
                buffer.writeUUID(golemData.golemUUID());
                buffer.writeInt(golemData.imbuedSoul());
            }
        });
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            SyncedGolemData.set(playerGolemMap);
        });
        return true;
    }
}