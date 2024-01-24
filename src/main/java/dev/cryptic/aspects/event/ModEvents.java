package dev.cryptic.aspects.event;

import dev.cryptic.aspects.Aspect;
//import dev.cryptic.aspects.api.capabilities.PlayerFlux;
//import dev.cryptic.aspects.api.capabilities.PlayerFluxProvider;
//import dev.cryptic.aspects.api.networking.packet.ThirstDataSyncS2CPacket;
import dev.cryptic.aspects.api.util.FluxUtil;
import dev.cryptic.aspects.api.util.GolemUtil;
import dev.cryptic.aspects.api.util.MathUtility;
import dev.cryptic.aspects.entity.ModEntityTypes;
import dev.cryptic.aspects.entity.fluxentity.golem.AbstractGolem;
import dev.cryptic.aspects.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import dev.cryptic.aspects.misc.SyncedData;
import net.minecraft.network.chat.Component;
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

import java.util.List;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Aspect.MODID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void updateSyncedDataServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
                MinecraftServer server = event.getServer();
                ServerLevel level = server.getLevel(Level.OVERWORLD);

                SyncedData.setServerTime(server.getTickCount());
                SyncedData.setLevelTime(level.getGameTime());
                SyncedData.setDayTime(level.getDayTime());
                server.getPlayerList().getPlayers().forEach(player -> {
                    SyncedData.PlayerData playerData = SyncedData.getPlayerData(player);

                    // Update Player Flux :3
                    playerData.setFlux(FluxUtil.getCurrentFlux(player));
                    playerData.setMaxFlux(FluxUtil.getMaxFlux(player));
                    playerData.setAspectLevel(FluxUtil.getAspectLevel(player));
                    playerData.setAspectType(FluxUtil.getAspectType(player));
                    playerData.setMaxSoul(GolemUtil.getMaxSoul(player));

                    // Update Golem Data :3
                    List<AbstractGolem> golems = GolemUtil.getGolems(player, level);
                    if (golems != null) {
                        for (AbstractGolem golem : golems) {
                            int imbuedSoul = GolemUtil.getImbuedSoul(player, golem);
                            playerData.addGolem(golem, imbuedSoul);
                        }
                    }

                    // Set all data :3
                    SyncedData.setPlayerData(player, playerData);
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
                        player.sendSystemMessage(Component.literal("Golem soul: " + newSoul));
                    } else {
                        GolemUtil.addGolem(player, golem, 1);
                        player.sendSystemMessage(Component.literal("New Golem Registered!"));
                        player.sendSystemMessage(Component.literal("Golem soul: " + 1));
                    }
                    Aspect.LOGGER.info(MathUtility.slerp());
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
