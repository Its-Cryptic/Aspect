package dev.cryptic.aspects.api.flux;

import dev.cryptic.aspects.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspects.api.capabilities.flux.IFluxCapability;
import dev.cryptic.aspects.entity.fluxentity.golem.AbstractGolem;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FluxUtil {
    @Info("Get a player's current flux")
    public static float getCurrentFlux(Player player) {
        final float[] flux = new float[1];
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
    public static void setFlux(Player player, float flux) {
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
    public static void addFlux(Player player, float flux) {
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
    public static void removeFlux(Player player, float flux) {
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

    @Info("Get a player's aspect type")
    public static AspectType getAspectType(Player player) {
        final AspectType[] type = new AspectType[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            type[0] = fluxCap.getAspectType();
        });
        return type[0];
    }

    @Info("Get a player's aspect level")
    public static int getAspectLevel(ServerPlayer player) {
        final int[] level = new int[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            level[0] = fluxCap.getAspectLevel();
        });
        return level[0];
    }

    @Info("Get a player's maximum soul")
    public static int getMaxSoul(ServerPlayer player) {
        final int[] soul = new int[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            soul[0] = fluxCap.getMaxSoul();
        });
        return soul[0];
    }

    @Info("Set a player's maximum soul")
    public static void setMaxSoul(ServerPlayer player, int soul) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.setMaxSoul(soul);
        });
    }

    public static void addGolem(Player player, AbstractGolem golem, int imbuedSoul) {
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            fluxCap.addGolem(golem, imbuedSoul);
        });
    }

    public static boolean hasGolem(Player player, AbstractGolem golem) {
        final boolean[] hasGolem = new boolean[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            hasGolem[0] = fluxCap.hasGolem(golem);
        });
        return hasGolem[0];
    }

    public static int getImbuedSoul(Player player, AbstractGolem golem) {
        final int[] imbuedSoul = new int[1];
        CapabilityRegistry.getFlux(player).ifPresent(fluxCap -> {
            imbuedSoul[0] = fluxCap.getImbuedSoul(golem);
        });
        return imbuedSoul[0];
    }

    public static List<AbstractGolem> getGolems(Player player, ServerLevel level) {
        final List<UUID> golemUUIDs = CapabilityRegistry.getFlux(player).map(IFluxCapability::getAllGolemUUIDs).orElse(null);
        if (golemUUIDs == null) {
            return null;
        }
        final List<AbstractGolem> golems = new ArrayList<>();
        level.getAllEntities().forEach(entity -> {
            if (entity instanceof AbstractGolem golem) {
                if (golemUUIDs.contains(golem.getUUID())) {
                    golems.add(golem);
                }
            }
        });
        return golems;
    }
}
