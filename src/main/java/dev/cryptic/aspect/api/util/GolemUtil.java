package dev.cryptic.aspect.api.util;

import dev.cryptic.aspect.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspect.api.capabilities.golem.ISoulCapability;
import dev.cryptic.aspect.common.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class GolemUtil {
    public static LazyOptional<ISoulCapability> getGolemCap(Player player) {
        return CapabilityRegistry.getSoul(player);
    }

    public static int getMaxSoul(ServerPlayer player) {
        final int[] soul = new int[1];
        getGolemCap(player).ifPresent(golemCap -> soul[0] = golemCap.getMaxSoul());
        return soul[0];
    }

    public static int getRemainingSoul(ServerPlayer player) {
        final int[] soul = new int[1];
        getGolemCap(player).ifPresent(golemCap -> soul[0] = golemCap.getRemainingSoul());
        return soul[0];
    }

    public static int getTotalImbuedSoul(ServerPlayer player) {
        final int[] soul = new int[1];
        getGolemCap(player).ifPresent(golemCap -> soul[0] = golemCap.getTotalImbuedSoul());
        return soul[0];
    }

    public static void addGolem(Player player, AbstractGolem golem, int imbuedSoul) {
        // Adds golem to player's golem list
        getGolemCap(player).ifPresent(golemCap -> golemCap.addGolem(golem, imbuedSoul));

        // Adds player to golem's owner tag
        golem.setOwnerUUID(player.getUUID());
        // Sets golem's imbued soul (Most likely will be 1)
        golem.setImbuedSoul(imbuedSoul);

        // Adds golem to SavedData
        //GolemManager.INSTANCE.addGolem((ServerPlayer) player, golem, imbuedSoul);
        AspectSavedData savedData = AspectSavedData.get(player.level());
        if (savedData != null) savedData.addGolem((ServerLevel) player.level(), player, golem, imbuedSoul);
    }

    public static void removeGolem(Player player, AbstractGolem golem) {
        // Removes golem from player's golem list
        getGolemCap(player).ifPresent(golemCap -> golemCap.removeGolem(golem));

        // Removes player from golem's owner tag
        golem.setOwnerUUID(null);
        // Sets golem's imbued soul to 0
        golem.setImbuedSoul(0);
    }

    public static int getImbuedSoul(Player player, AbstractGolem golem) {
        final int[] soul = new int[1];
        getGolemCap(player).ifPresent(golemCap -> soul[0] = golemCap.getImbuedSoul(golem));
        return soul[0];
    }

    public static boolean hasGolem(Player player, AbstractGolem golem) {
        final boolean[] hasGolem = new boolean[1];
        getGolemCap(player).ifPresent(golemCap -> hasGolem[0] = golemCap.hasGolem(golem));
        return hasGolem[0];
    }
}
