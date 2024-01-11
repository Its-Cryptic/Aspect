package dev.cryptic.aspects.misc;

import java.util.HashMap;
import java.util.UUID;

public class SyncedData {
    private SyncedData() {
    }
    public static long serverTime;
    public static long levelTime;
    public static long dayTime;
    public static HashMap<UUID, Integer> playerFlux = new HashMap<>();

    public static void setServerTime(long serverTime) {
        SyncedData.serverTime = serverTime;
    }

    public static void setLevelTime(long levelTime) {
        SyncedData.levelTime = levelTime;
    }

    public static void setDayTime(long dayTime) {
        SyncedData.dayTime = dayTime;
    }

    public static void setPlayerFlux(UUID playerUUID, int flux) {
        SyncedData.playerFlux.put(playerUUID, flux);
    }

    public static Integer getPlayerFlux(UUID playerUUID) {
        return SyncedData.playerFlux.get(playerUUID);
    }
}
