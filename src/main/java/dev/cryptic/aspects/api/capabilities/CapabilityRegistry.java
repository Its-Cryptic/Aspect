package dev.cryptic.aspects.api.capabilities;

import dev.cryptic.aspects.Aspect;
//import dev.cryptic.aspects.api.networking.packet.ThirstDataSyncS2CPacket;
import dev.cryptic.aspects.api.attribute.AttributeRegistry;
import dev.cryptic.aspects.api.capabilities.flux.FluxCapabilityAttacher;
import dev.cryptic.aspects.api.capabilities.flux.IFluxCapability;
import dev.cryptic.aspects.api.capabilities.golem.GolemCapabilityAttacher;
import dev.cryptic.aspects.api.capabilities.golem.IGolemCapability;
import dev.cryptic.aspects.entity.fluxentity.AbstractFluxEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

public class CapabilityRegistry {

    public static final Integer UPDATE_INTERVAL = 5;

    public static final Capability<IFluxCapability> FLUX_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IGolemCapability> GOLEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static LazyOptional<IFluxCapability> getFlux(final LivingEntity entity) {
        if (entity == null) {
            return LazyOptional.empty();
        }
        return entity.getCapability(FLUX_CAPABILITY);
    }

    public static LazyOptional<IGolemCapability> getGolem(final LivingEntity entity) {
        if (entity == null) {
            return LazyOptional.empty();
        }
        return entity.getCapability(GOLEM_CAPABILITY);
    }

    @Mod.EventBusSubscriber(modid = Aspect.MODID)
    public static class EventHandler {

        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player || event.getObject() instanceof AbstractFluxEntity) {
                FluxCapabilityAttacher.attach(event);
            }
            if (event.getObject() instanceof Player) {
                GolemCapabilityAttacher.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(IFluxCapability.class);
            event.register(IGolemCapability.class);
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            Player original = event.getOriginal();
            original.revive();

            getFlux(original).ifPresent(oldStore -> getFlux(event.getEntity()).ifPresent(newStore -> {
                newStore.setMaxFlux(oldStore.getMaxFlux());
                newStore.setFlux(oldStore.getCurrentFlux());
                newStore.setAspectLevel(oldStore.getAspectLevel());
                newStore.setAspectType(oldStore.getAspectType());
            }));
            getGolem(original).ifPresent(oldStore -> getGolem(event.getEntity()).ifPresent(newStore -> {
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
                        float fluxRegen = (float) event.player.getAttribute(AttributeRegistry.FLUX_REGEN.get()).getValue();
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
                                float fluxRegen = (float) livingEntity.getAttribute(AttributeRegistry.FLUX_REGEN.get()).getValue();
                                flux.addFlux(fluxRegen);
                            });
                        }
                    });
                }
            }

        }
    }
}
