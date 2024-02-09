package dev.cryptic.aspect.api.client.synceddata;

import dev.cryptic.aspect.api.flux.AspectType;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.api.util.AspectSavedData;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class SyncedForgeCapData {
    public static float playerFlux;
    public static int playerMaxFlux;
    public static double fluxRegen;
    public static AspectType aspectType;
    public static int maxSoul;

    public static void set(float playerFlux, int playerMaxFlux, double fluxRegen, int aspectID, int maxSoul) {
        SyncedForgeCapData.playerFlux = playerFlux;
        SyncedForgeCapData.playerMaxFlux = playerMaxFlux;
        SyncedForgeCapData.fluxRegen = fluxRegen;

        SyncedForgeCapData.aspectType = AspectRegistry.getAspectFromId(aspectID);

        SyncedForgeCapData.maxSoul = maxSoul;
    }

    public static float getPlayerFlux() {
        return playerFlux;
    }

    public static int getPlayerMaxFlux() {
        return playerMaxFlux;
    }

    public static AspectType getAspectType() {
        return aspectType;
    }

    public static int getMaxSoul() {
        return maxSoul;
    }

    public static double getPlayerFluxRegen() {
        return fluxRegen;
    }
}