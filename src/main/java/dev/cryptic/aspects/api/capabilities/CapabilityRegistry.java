package dev.cryptic.aspects.api.capabilities;

import dev.cryptic.aspects.Aspects;
import dev.cryptic.aspects.api.networking.ModMessages;
import dev.cryptic.aspects.api.networking.packet.ThirstDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

public class CapabilityRegistry {

    public static final Capability<IFluxCapability> FLUX_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static LazyOptional<IFluxCapability> getFlux(final LivingEntity entity) {
        if (entity == null) {
            return LazyOptional.empty();
        }
        return entity.getCapability(FLUX_CAPABILITY);
    }

    @Mod.EventBusSubscriber(modid = Aspects.MODID)
    public static class EventHandler {

        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                FluxCapabilityAttacher.attach(event);
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(IFluxCapability.class);
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
            event.getOriginal().invalidateCaps();
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.player.tickCount % 5 == 0) {
                if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
                    Aspects.LOGGER.info("Tick: " + event.player.tickCount + ", LogicalSide:" + event.side + ", Phase:" + event.phase);
                    Aspects.LOGGER.info(getFlux(event.player).toString());
                    getFlux(event.player).ifPresent(flux -> {
                        Aspects.LOGGER.info(String.valueOf(flux.getCurrentFlux()));
                        flux.addFlux(1);
                    });
                }
            }
        }


    }
}
