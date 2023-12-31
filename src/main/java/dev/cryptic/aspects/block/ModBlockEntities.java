package dev.cryptic.aspects.block;

import dev.cryptic.aspects.Aspects;
import dev.cryptic.aspects.blockentities.FluxCoreBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Aspects.MODID);

    public static final RegistryObject<BlockEntityType<FluxCoreBlockEntity>> FLUX_CORE = BLOCK_ENTITIES.register("flux_core",
            () -> BlockEntityType.Builder.of(FluxCoreBlockEntity::new, ModBlocks.FLUX_CORE_BLOCK.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
