package dev.cryptic.aspect.api.util;

import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AspectSavedData extends SavedData {
    private static final String IDENTIFIER = "aspect_data";
    private Map<UUID, ArrayList<GolemData>> playerGolemMap = new HashMap<>();
    private record GolemData(UUID golemUUID, int imbuedSoul) {}
    private static final String GOLEM_TAG = "GolemMap";

    private AspectSavedData() {
        super();
    }

    public static AspectSavedData get(Level level) {
        if (level instanceof ServerLevel) {
            ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);
            DimensionDataStorage overworldData = overworld.getDataStorage();
            AspectSavedData data = overworldData.computeIfAbsent(AspectSavedData::load, AspectSavedData::new, IDENTIFIER);
            if (data != null) {
                data.setDirty();
            }
            return data;
        }
        return null;
    }

    public ArrayList<GolemData> getGolemData(Player player) {
        AspectSavedData data = get(player.level);
        if (data != null) {
            return data.playerGolemMap.getOrDefault(player.getUUID(), new ArrayList<>());
        }
        return new ArrayList<>();
    }

    public GolemData getGolemData(Player player, AbstractGolem golem) {
        AspectSavedData data = get(player.level);
        if (data != null) {
            ArrayList<GolemData> golemDataList = data.playerGolemMap.getOrDefault(player.getUUID(), new ArrayList<>());
            for (GolemData golemData : golemDataList) {
                if (golemData.golemUUID().equals(golem.getUUID())) {
                    return golemData;
                }
            }
        }
        return null;
    }

    public void addGolem(ServerLevel level, Player player, AbstractGolem golem, int imbuedSoul) {
        AspectSavedData data = get(level);
        if (data != null) {
            ArrayList<GolemData> golemDataList = getGolemData(player);
            golemDataList.add(new GolemData(golem.getUUID(), imbuedSoul));
            data.playerGolemMap.put(player.getUUID(), golemDataList);
            data.setDirty();
        }
    }

    public void removeGolem(ServerLevel level, Player player, AbstractGolem golem) {
        AspectSavedData data = get(level);
        if (data != null) {
            ArrayList<GolemData> golemDataList = getGolemData(player);
            golemDataList.removeIf(golemData -> golemData.golemUUID().equals(golem.getUUID()));
            data.playerGolemMap.put(player.getUUID(), golemDataList);
            data.setDirty();
        }
    }

    public static AspectSavedData load(CompoundTag nbt) {
        AspectSavedData data = new AspectSavedData();
        if (nbt.contains(GOLEM_TAG)) {
            CompoundTag golemTag = nbt.getCompound(GOLEM_TAG);
            for (String playerUUID : golemTag.getAllKeys()) {
                ArrayList<GolemData> golemDataList = new ArrayList<>();
                CompoundTag playerTag = golemTag.getCompound(playerUUID);
                for (String golemUUID : playerTag.getAllKeys()) {
                    golemDataList.add(new GolemData(UUID.fromString(golemUUID), playerTag.getInt(golemUUID)));
                }
                data.playerGolemMap.put(UUID.fromString(playerUUID), golemDataList);
            }
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        CompoundTag golemTag = new CompoundTag();
        for (Map.Entry<UUID, ArrayList<GolemData>> entry : playerGolemMap.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            for (GolemData golemData : entry.getValue()) {
                playerTag.putInt(golemData.golemUUID().toString(), golemData.imbuedSoul());
            }
            golemTag.put(entry.getKey().toString(), playerTag);
        }
        nbt.put(GOLEM_TAG, golemTag);
        return nbt;
    }
}
