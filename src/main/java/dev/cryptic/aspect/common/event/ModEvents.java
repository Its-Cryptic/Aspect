package dev.cryptic.aspect.common.event;

import com.mojang.brigadier.Command;
import dev.cryptic.aspect.Aspect;
//import dev.cryptic.aspects.api.capabilities.PlayerFlux;
//import dev.cryptic.aspects.api.capabilities.PlayerFluxProvider;
//import dev.cryptic.aspects.api.networking.packet.ThirstDataSyncS2CPacket;
import dev.cryptic.aspect.api.aspect.AspectColor;
import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.networking.ModMessages;
import dev.cryptic.aspect.api.networking.packet.ForgeCapDataS2CPacket;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.api.util.AspectUtil;
import dev.cryptic.aspect.api.util.GolemUtil;
import dev.cryptic.aspect.common.commands.SetAspectCommand;
import dev.cryptic.aspect.common.worldevent.LavaWorldEvent;
import dev.cryptic.aspect.registry.common.AspectEntities;
import dev.cryptic.aspect.common.entity.fluxentity.golem.AbstractGolem;
import dev.cryptic.aspect.common.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.lodestone.handlers.WorldEventHandler;

import java.awt.*;

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
                    //WorldEventHandler.addWorldEvent(event.getEntity().level(), new LavaWorldEvent(event.getEntity().position(), 10, 200));


                }
            }
        }

        @SubscribeEvent
        public static void punchCowEvent(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                if (event.getEntity() instanceof Cow) {
                    Vec3 pos = event.getEntity().position().add(new Vec3(0, 1, 0));
                    WorldEventHandler.addWorldEvent(event.getEntity().level(), new LavaWorldEvent(pos, 100, 60));
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Aspect.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(AspectEntities.MIZARU.get(), Mizaru.setAttributes());
        }
    }

}
