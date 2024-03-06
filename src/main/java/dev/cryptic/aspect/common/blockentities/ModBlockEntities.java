package dev.cryptic.aspect.common.blockentities;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.block.BlockRegistry;
import dev.cryptic.aspect.common.blockentities.fluxcore.FluxCoreBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Aspect.MODID);

    public static final RegistryObject<BlockEntityType<FluxCoreBlockEntity>> FLUX_CORE =
            BLOCK_ENTITIES.register("flux_core", () ->
                    BlockEntityType.Builder.of(FluxCoreBlockEntity::new,
                            BlockRegistry.FLUX_CORE_BLOCK.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
