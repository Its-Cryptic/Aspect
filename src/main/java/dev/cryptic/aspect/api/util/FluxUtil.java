package dev.cryptic.aspect.api.util;

import dev.cryptic.aspect.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspect.api.capabilities.flux.IFluxCapability;
import dev.cryptic.aspect.api.registry.AttributeRegistry;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Objects;

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

    public static double getFluxRegen(ServerPlayer player) {
        return Objects.requireNonNull(player.getAttribute(AttributeRegistry.FLUX_REGEN.get())).getValue();
    }
}
