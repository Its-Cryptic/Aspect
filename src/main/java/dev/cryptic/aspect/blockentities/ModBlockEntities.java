package dev.cryptic.aspect.blockentities;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.block.BlockRegistry;
import dev.cryptic.aspect.blockentities.fluxcore.FluxCoreBlockEntity;
import dev.cryptic.aspect.blockentities.fluxcore.FluxCoreRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Aspect.MODID);

    public static final RegistryObject<BlockEntityType<FluxCoreBlockEntity>> FLUX_CORE = BLOCK_ENTITIES.register("flux_core",
            () -> BlockEntityType.Builder.of(FluxCoreBlockEntity::new, BlockRegistry.FLUX_CORE_BLOCK.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = Aspect.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientOnly {

        @SubscribeEvent
        public static void registerBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(FLUX_CORE.get(), FluxCoreRenderer::new);
        }
    }
}
