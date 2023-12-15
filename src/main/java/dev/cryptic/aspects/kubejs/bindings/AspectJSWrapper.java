package dev.cryptic.aspects.kubejs.bindings;

import dev.cryptic.aspects.api.capabilities.CapabilityRegistry;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.entity.player.Player;

public class AspectJSWrapper {

    @Info("Get a player's current flux")
    public static int getCurrentFlux(Player player) {
        final int[] flux = new int[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            flux[0] = fluxCap.getCurrentFlux();
        });
        return flux[0];
    }

    @Info("Get a player's maximum flux")
    public static int getMaxFlux(Player player) {
        final int[] flux = new int[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            flux[0] = fluxCap.getMaxFlux();
        });
        return flux[0];
    }

    @Info("Set a player's current flux")
    public static void setFlux(Player player, int flux) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.setFlux(flux);
        });
    }

    @Info("Set a player's maximum flux")
    public static void setMaxFlux(Player player, int flux) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.setMaxFlux(flux);
        });
    }

    @Info("Add to a player's current flux")
    public static void addFlux(Player player, int flux) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.addFlux(flux);
        });
    }

    @Info("Add to a player's maximum flux")
    public static void addMaxFlux(Player player, int flux) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.addMaxFlux(flux);
        });
    }

    @Info("Subtract from a player's current flux")
    public static void removeFlux(Player player, int flux) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.removeFlux(flux);
        });
    }

    @Info("Subtract from a player's maximum flux")
    public static void removeMaxFlux(Player player, int flux) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.removeMaxFlux(flux);
        });
    }

    @Info("Set a player's aspect level")
    public static void setAspectLevel(Player player, int level) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.setAspectLevel(level);
        });
    }

    @Info("Set a player's aspect type")
    public static void setAspectType(Player player, AspectType type) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.setAspectType(type);
        });
    }

    @Info("Set a player's aspect type")
    public static AspectType getAspectType(Player player) {
        final AspectType[] type = new AspectType[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            type[0] = fluxCap.getAspectType();
        });
        return type[0];
    }
}
