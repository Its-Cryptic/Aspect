package dev.cryptic.aspect.api.capabilities;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.capabilities.aspect.AspectCapabilityAttacher;
import dev.cryptic.aspect.api.capabilities.aspect.IAspectCapability;
import dev.cryptic.aspect.registry.common.AspectAttributes;
import dev.cryptic.aspect.api.capabilities.flux.FluxCapabilityAttacher;
import dev.cryptic.aspect.api.capabilities.flux.IFluxCapability;
import dev.cryptic.aspect.api.capabilities.golem.SoulCapabilityAttacher;
import dev.cryptic.aspect.api.capabilities.golem.ISoulCapability;
import dev.cryptic.aspect.common.entity.fluxentity.AbstractFluxEntity;
import dev.cryptic.aspect.common.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.io.File;
import java.util.UUID;

public class CapabilityRegistry {

    public static final Integer UPDATE_INTERVAL = 1;

    public static final Capability<IFluxCapability> FLUX_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IAspectCapability> ASPECT_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<ISoulCapability> SOUL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static LazyOptional<IFluxCapability> getFlux(final LivingEntity entity) {
        if (entity == null) {
            return LazyOptional.empty();
        }
        return entity.getCapability(FLUX_CAPABILITY);
    }

    public static LazyOptional<IAspectCapability> getAspect(final LivingEntity entity) {
        if (entity == null) {
            return LazyOptional.empty();
        }
        return entity.getCapability(ASPECT_CAPABILITY);
    }

    public static LazyOptional<ISoulCapability> getSoul(final LivingEntity entity) {
        if (entity == null) {
            return LazyOptional.empty();
        }
        return entity.getCapability(SOUL_CAPABILITY);
    }
    @Mod.EventBusSubscriber(modid = Aspect.MODID)
    public static class EventHandler {

        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                FluxCapabilityAttacher.attach(event);
                AspectCapabilityAttacher.attach(event);
                SoulCapabilityAttacher.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(IFluxCapability.class);
            event.register(IAspectCapability.class);
            event.register(ISoulCapability.class);
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            Player original = event.getOriginal();
            original.revive();

            getFlux(original).ifPresent(oldStore -> getFlux(event.getEntity()).ifPresent(newStore -> {
                newStore.setMaxFlux(oldStore.getMaxFlux());
                newStore.setFlux(oldStore.getCurrentFlux());
            }));

            getAspect(original).ifPresent(oldStore -> getAspect(event.getEntity()).ifPresent(newStore -> {
                newStore.setAspectType(oldStore.getAspectType());
            }));

            getSoul(original).ifPresent(oldStore -> getSoul(event.getEntity()).ifPresent(newStore -> {
                newStore.setMaxSoul(oldStore.getMaxSoul());
                newStore.setAllGolemUUIDs(oldStore.getAllGolemUUIDs());
            }));

            event.getOriginal().invalidateCaps();
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            /*
            Every UPDATE_INTERVAL ticks, update the flux of all
            players based on their flux regen attribute value
             */
            if (event.phase == TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
                if (event.player.tickCount % UPDATE_INTERVAL == 0) {
                    getFlux(event.player).ifPresent(flux -> {
                        float fluxRegen = (float) event.player.getAttribute(AspectAttributes.FLUX_REGEN.get()).getValue();
                        flux.addFlux(fluxRegen);
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onLevelTick(TickEvent.LevelTickEvent event) {
            /*
            Every UPDATE_INTERVAL ticks, update the flux of all
            entities based on their flux regen attribute value
             */
            if (event.phase == TickEvent.Phase.START && event.level instanceof ServerLevel) {
                ServerLevel serverLevel = (ServerLevel) event.level;
                if (serverLevel.getServer().getTickCount() % UPDATE_INTERVAL == 0) {
                    serverLevel.getAllEntities().forEach(entity -> {
                        if (entity instanceof AbstractFluxEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            getFlux(livingEntity).ifPresent(flux -> {
                                float fluxRegen = (float) livingEntity.getAttribute(AspectAttributes.FLUX_REGEN.get()).getValue();
                                flux.addFlux(fluxRegen);
                            });
                        }
                    });
                }
            }
        }

//        @SubscribeEvent
//        public static void onGolemDeath(LivingDeathEvent event) {
//            if (event.getEntity() instanceof AbstractGolem golem) {
//                golem.revive();
//                UUID ownerUUID = golem.getOwnerUUID();
//                if (ownerUUID != null) {
//                    Player player = golem.getServer().getPlayerList().getPlayer(ownerUUID);
//                    if (player != null) {
//                        GolemUtil.removeGolem(player, golem);
//                    } else {
//                        removeGolemOffline(ownerUUID, golem);
//                    }
//                }
//            }
//        }

        @SubscribeEvent
        public static void onGolemLethalDamage(LivingHurtEvent event) {
            if (event.getEntity() instanceof AbstractGolem golem) {
                Logger logger = Aspect.LOGGER;
                logger.info("Test Frog 2");
                if (golem.getHealth() - event.getAmount() <= 0) {
                    //event.setCanceled(true);
                }

            }
        }

        private static void removeGolemOffline(UUID playerUUID, AbstractGolem golem) {
            UUID golemUUID = golem.getUUID();
            LevelResource LevelR = LevelResource.PLAYER_DATA_DIR;
            File playerDir = golem.getServer().getWorldPath(LevelR).toFile();



        }
    }
}
