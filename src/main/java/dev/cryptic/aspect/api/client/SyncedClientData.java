package dev.cryptic.aspect.api.client;

import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class SyncedClientData {
    public static Map<UUID, Float> playerFluxMap;
    public static Map<UUID, Integer> playerMaxFluxMap;
    public static Map<UUID, ArrayList<AbstractGolem>> playerGolemMap;

    public static void set(Map<UUID, Float> playerFluxMap, Map<UUID, Integer> playerMaxFluxMap, Map<UUID, ArrayList<AbstractGolem>> playerGolemMap) {
        SyncedClientData.playerFluxMap = playerFluxMap;
        SyncedClientData.playerMaxFluxMap = playerMaxFluxMap;
        SyncedClientData.playerGolemMap = playerGolemMap;
    }

    public static Map<UUID, Float> getPlayerFluxMap() {
        return playerFluxMap;
    }

    public static Map<UUID, Integer> getPlayerMaxFluxMap() {
        return playerMaxFluxMap;
    }

    public static float getPlayerFlux(UUID uuid) {
        return playerFluxMap.get(uuid);
    }

    public static int getPlayerMaxFlux(UUID uuid) {
        return playerMaxFluxMap.get(uuid);
    }

    public static Map<UUID, ArrayList<AbstractGolem>> getPlayerGolemMap() {
        return playerGolemMap;
    }

    public static ArrayList<AbstractGolem> getPlayerGolems(UUID uuid) {
        return playerGolemMap.get(uuid);
    }








}
