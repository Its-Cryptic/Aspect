package dev.cryptic.aspects.event;

import dev.cryptic.aspects.Aspect;
//import dev.cryptic.aspects.api.capabilities.PlayerFlux;
//import dev.cryptic.aspects.api.capabilities.PlayerFluxProvider;
import dev.cryptic.aspects.api.flux.FluxUtil;
//import dev.cryptic.aspects.api.networking.packet.ThirstDataSyncS2CPacket;
import dev.cryptic.aspects.entity.ModEntityTypes;
import dev.cryptic.aspects.entity.threewisemonkeys.Mizaru;
import dev.cryptic.aspects.misc.SyncedData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Aspect.MODID)
    public static class ForgeEvents {


//        @SubscribeEvent
//        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
//            if (event.getObject() instanceof Player) {
//                if (!event.getObject().getCapability(PlayerFluxProvider.PLAYER_FLUX).isPresent()) {
//                    event.addCapability(new ResourceLocation(Aspects.MODID, "properties"), new PlayerFluxProvider());
//                }
//            }
//        }
//
//        @SubscribeEvent
//        public static void onPlayerCloned(PlayerEvent.Clone event) {
//            if (event.isWasDeath()) {
//                event.getOriginal().getCapability(PlayerFluxProvider.PLAYER_FLUX).ifPresent(oldStore -> {
//                    event.getOriginal().getCapability(PlayerFluxProvider.PLAYER_FLUX).ifPresent(newStore -> {
//                        newStore.copyFrom(oldStore);
//                    });
//                });
//            }
//        }
//
//        @SubscribeEvent
//        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
//            event.register(PlayerFlux.class);
//        }
//
//        @SubscribeEvent
//        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
//            if (event.side == LogicalSide.SERVER) {
//                if (!event.player.isCreative()) {
//                    event.player.getCapability(PlayerFluxProvider.PLAYER_FLUX).ifPresent(energy -> {
//                        Aspects.LOGGER.info("Flux: "+ energy.getFlux());
//                        Aspects.LOGGER.info("Max Flux: "+ energy.getMaxFlux());
//                        if (energy.getFlux() < energy.getMaxFlux() && event.player.tickCount % 20 == 0 && event.phase == TickEvent.Phase.START) {
//                            energy.addFlux(1);
//                            ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(energy.getFlux()), ((ServerPlayer) event.player));
//                            //event.player.sendSystemMessage(Component.literal("Subtracted Energy"));
//                        }
//                    });
//                }
//            }
//        }
//
//        @SubscribeEvent
//        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
//            if (!event.getLevel().isClientSide()) {
//                if (event.getEntity() instanceof ServerPlayer player) {
//                    player.getCapability(PlayerFluxProvider.PLAYER_FLUX).ifPresent(energy -> {
//                        ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(energy.getFlux()), player);
//                    });
//                }
//            }
//        }

        @SubscribeEvent
        public static void updateSyncedDataServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
                MinecraftServer server = event.getServer();
                Level level = server.getLevel(Level.OVERWORLD);

                SyncedData.setServerTime(server.getTickCount());
                SyncedData.setLevelTime(level.getGameTime());
                SyncedData.setDayTime(level.getDayTime());
                server.getPlayerList().getPlayers().forEach(player -> {
                    int flux = FluxUtil.getCurrentFlux(player);
                    SyncedData.setPlayerFlux(player.getUUID(), flux);
                });
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
