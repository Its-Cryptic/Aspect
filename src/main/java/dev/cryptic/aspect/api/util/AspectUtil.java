package dev.cryptic.aspect.api.util;

import dev.cryptic.aspect.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspect.api.capabilities.aspect.IAspectCapability;
import dev.cryptic.aspect.api.flux.AspectType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class AspectUtil {
    public static LazyOptional<IAspectCapability> getAspectCap(Player player) {
        return CapabilityRegistry.getAspect(player);
    }


    public static void setAspect(Player player, AspectType aspect) {
        getAspectCap(player).ifPresent(aspectCap -> {
            aspectCap.setAspectType(aspect);
        });
    }

    public static AspectType getAspect(Player player) {
        final AspectType[] aspect = new AspectType[1];
        getAspectCap(player).ifPresent(aspectCap -> {
            aspect[0] = aspectCap.getAspectType();
        });
        return aspect[0];
    }



}
