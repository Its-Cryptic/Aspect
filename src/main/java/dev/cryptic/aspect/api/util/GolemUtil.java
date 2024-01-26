package dev.cryptic.aspect.api.util;

import dev.cryptic.aspect.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspect.api.capabilities.golem.IGolemCapability;
import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GolemUtil {
    public static LazyOptional<IGolemCapability> getGolemCap(Player player) {
        return CapabilityRegistry.getGolem(player);
    }

    @Info("Get a player's maximum soul")
    public static int getMaxSoul(ServerPlayer player) {
        final int[] soul = new int[1];
        getGolemCap(player).ifPresent(golemCap -> {
            soul[0] = golemCap.getMaxSoul();
        });
        return soul[0];
    }

    @Info("Set a player's maximum soul")
    public static void setMaxSoul(ServerPlayer player, int soul) {
        getGolemCap(player).ifPresent(golemCap -> {
            golemCap.setMaxSoul(soul);
        });
    }

    public static void addGolem(Player player, AbstractGolem golem, int imbuedSoul) {
        getGolemCap(player).ifPresent(golemCap -> {
            golemCap.addGolem(golem, imbuedSoul);
        });
    }

    public static boolean hasGolem(Player player, AbstractGolem golem) {
        final boolean[] hasGolem = new boolean[1];
        getGolemCap(player).ifPresent(golemCap -> {
            hasGolem[0] = golemCap.hasGolem(golem);
        });
        return hasGolem[0];
    }

    public static int getImbuedSoul(Player player, AbstractGolem golem) {
        final int[] imbuedSoul = new int[1];
        getGolemCap(player).ifPresent(golemCap -> {
            imbuedSoul[0] = golemCap.getImbuedSoul(golem);
        });
        return imbuedSoul[0];
    }

    public static List<AbstractGolem> getGolems(Player player, ServerLevel level) {
        final List<UUID> golemUUIDs = getGolemCap(player).map(IGolemCapability::getAllGolemUUIDs).orElse(null);
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
