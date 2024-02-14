package dev.cryptic.aspect.event;

import dev.cryptic.aspect.Aspect;
//import dev.cryptic.aspects.api.capabilities.PlayerFlux;
//import dev.cryptic.aspects.api.capabilities.PlayerFluxProvider;
//import dev.cryptic.aspects.api.networking.packet.ThirstDataSyncS2CPacket;
import dev.cryptic.aspect.api.flux.AspectColor;
import dev.cryptic.aspect.api.flux.AspectType;
import dev.cryptic.aspect.api.networking.ModMessages;
import dev.cryptic.aspect.api.networking.packet.ForgeCapDataS2CPacket;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.api.util.AspectUtil;
import dev.cryptic.aspect.api.util.FluxUtil;
import dev.cryptic.aspect.api.util.GolemUtil;
import dev.cryptic.aspect.entity.ModEntityTypes;
import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import dev.cryptic.aspect.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.*;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Aspect.MODID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void updateSyncedDataServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
                MinecraftServer server = event.getServer();
                ServerLevel level = server.getLevel(Level.OVERWORLD);

                // Send every player their forgecaps to client
                server.getPlayerList().getPlayers().forEach(player -> {
                    ModMessages.sendToPlayer(new ForgeCapDataS2CPacket(player), player);
                });

            }
        }

        @SubscribeEvent
        public static void punchGolemEvent(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (event.getEntity() instanceof AbstractGolem golem) {
                    if (GolemUtil.hasGolem(player, golem)) {
                        int soul = GolemUtil.getImbuedSoul(player, golem);
                        int newSoul = soul + 1;
                        GolemUtil.addGolem(player, golem, newSoul);
                        //player.sendSystemMessage(Component.literal("Golem soul: " + newSoul));
                    } else {
                        GolemUtil.addGolem(player, golem, 1);
                        //player.sendSystemMessage(Component.literal("New Golem Registered!"));
                        //player.sendSystemMessage(Component.literal("Golem soul: " + 1));
                    }
                    AspectType aspectType = AspectUtil.getAspect(player);

                    AspectColor colors = aspectType.getColors();
                    AspectColor empoweredColors = aspectType.getEmpoweredColors();
                    Color primaryColor = colors.primary();
                    Color secondaryColor = colors.secondary();
                    Color empoweredPrimaryColor = empoweredColors.primary();
                    Color empoweredSecondaryColor = empoweredColors.secondary();
                    Aspect.LOGGER.info("Primary Color: R{} G{} B{}", primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue());
                    Aspect.LOGGER.info("Secondary Color: R{} G{} B{}", secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue());
                    Aspect.LOGGER.info("Empowered Primary Color: R{} G{} B{}", empoweredPrimaryColor.getRed(), empoweredPrimaryColor.getGreen(), empoweredPrimaryColor.getBlue());
                    Aspect.LOGGER.info("Empowered Secondary Color: R{} G{} B{}", empoweredSecondaryColor.getRed(), empoweredSecondaryColor.getGreen(), empoweredSecondaryColor.getBlue());



                }
            }
        }

    }

    @Mod.EventBusSubscriber(modid = Aspect.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.MIZARU.get(), Mizaru.setAttributes());
        }
    }

}
