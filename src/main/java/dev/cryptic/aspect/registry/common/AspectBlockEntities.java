package dev.cryptic.aspect.registry.common;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.blockentities.fluxcore.FluxCoreBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AspectBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Aspect.MODID);

    public static final RegistryObject<BlockEntityType<FluxCoreBlockEntity>> FLUX_CORE =
            BLOCK_ENTITIES.register("flux_core", () ->
                    BlockEntityType.Builder.of(FluxCoreBlockEntity::new,
                            AspectBlocks.FLUX_CORE_BLOCK.get()).build(null));
    public static void init(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
