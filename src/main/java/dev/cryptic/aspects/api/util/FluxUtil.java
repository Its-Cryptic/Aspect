package dev.cryptic.aspects.api.util;

import dev.cryptic.aspects.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspects.api.capabilities.flux.IFluxCapability;
import dev.cryptic.aspects.api.capabilities.golem.IGolemCapability;
import dev.cryptic.aspects.api.flux.AspectType;
import dev.cryptic.aspects.entity.fluxentity.golem.AbstractGolem;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FluxUtil {

    public static LazyOptional<IFluxCapability> getFluxCap(Player player) {
        return CapabilityRegistry.getFlux(player);
    }

    @Info("Get a player's current flux")
    public static float getCurrentFlux(Player player) {
        final float[] flux = new float[1];
        getFluxCap(player).ifPresent(fluxCap -> {
            flux[0] = fluxCap.getCurrentFlux();
        });
        return flux[0];
    }

    @Info("Get a player's maximum flux")
    public static int getMaxFlux(Player player) {
        final int[] flux = new int[1];
        getFluxCap(player).ifPresent(fluxCap -> {
            flux[0] = fluxCap.getMaxFlux();
        });
        return flux[0];
    }

    @Info("Set a player's current flux")
    public static void setFlux(Player player, float flux) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.setFlux(flux);
        });
    }

    @Info("Set a player's maximum flux")
    public static void setMaxFlux(Player player, int flux) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.setMaxFlux(flux);
        });
    }

    @Info("Add to a player's current flux")
    public static void addFlux(Player player, float flux) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.addFlux(flux);
        });
    }

    @Info("Add to a player's maximum flux")
    public static void addMaxFlux(Player player, int flux) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.addMaxFlux(flux);
        });
    }

    @Info("Subtract from a player's current flux")
    public static void removeFlux(Player player, float flux) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.removeFlux(flux);
        });
    }

    @Info("Subtract from a player's maximum flux")
    public static void removeMaxFlux(Player player, int flux) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.removeMaxFlux(flux);
        });
    }

    @Info("Set a player's aspect level")
    public static void setAspectLevel(Player player, int level) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.setAspectLevel(level);
        });
    }

    @Info("Set a player's aspect type")
    public static void setAspectType(Player player, AspectType type) {
        getFluxCap(player).ifPresent(fluxCap -> {
            fluxCap.setAspectType(type);
        });
    }

    @Info("Get a player's aspect type")
    public static AspectType getAspectType(Player player) {
        final AspectType[] type = new AspectType[1];
        getFluxCap(player).ifPresent(fluxCap -> {
            type[0] = fluxCap.getAspectType();
        });
        return type[0];
    }

    @Info("Get a player's aspect level")
    public static int getAspectLevel(ServerPlayer player) {
        final int[] level = new int[1];
        getFluxCap(player).ifPresent(fluxCap -> {
            level[0] = fluxCap.getAspectLevel();
        });
        return level[0];
    }
}
