package dev.cryptic.aspect.api.client;

import dev.cryptic.aspect.api.util.AspectSavedData;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class SyncedGolemData {
    public static Map<UUID, ArrayList<AspectSavedData.GolemData>> playerGolemMap;

    public SyncedGolemData(Map<UUID, ArrayList<AspectSavedData.GolemData>> playerGolemMap) {
        SyncedGolemData.playerGolemMap = playerGolemMap;
    }

    public static void set(Map<UUID, ArrayList<AspectSavedData.GolemData>> playerGolemMap) {
        SyncedGolemData.playerGolemMap = playerGolemMap;
    }

    public Map<UUID, ArrayList<AspectSavedData.GolemData>> getPlayerGolemMap() {
        return playerGolemMap;
    }


}