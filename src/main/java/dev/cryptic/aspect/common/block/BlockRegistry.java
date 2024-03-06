package dev.cryptic.aspect.common.block;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.block.custom.FluxCoreBlock;
import dev.cryptic.aspect.common.item.CreativeTabRegistry;
import dev.cryptic.aspect.common.item.ItemRegistry;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

import static dev.cryptic.aspect.common.item.CreativeTabRegistry.addToTab;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Aspect.MODID);

    public static final RegistryObject<Block> ZIRCON_BLOCK = registerBlock("zircon_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)),
            CreativeTabRegistry.ASPECT_MAIN_ITEMS
    );

    public static final RegistryObject<Block> ZIRCON_ORE = registerBlock("zircon_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3,7)),
            CreativeTabRegistry.ASPECT_MAIN_ITEMS);

    // Registerd only block for animated item later
//    public static final RegistryObject<Block> FLUX_CORE_BLOCK = BLOCKS.register("flux_core",
//            () -> new FluxCoreBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)
//                    .strength(6f)
//                    .requiresCorrectToolForDrops()
//            )
//    );

    public static final RegistryObject<Block> FLUX_CORE_BLOCK = registerBlock("flux_core",
            () -> new FluxCoreBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)
                    .strength(6f)
                    .requiresCorrectToolForDrops()
            ), CreativeTabRegistry.ASPECT_MAIN_ITEMS
    );




    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, List<Supplier<? extends ItemLike>> tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, List<Supplier<? extends ItemLike>> creativeTabList) {

        return addToTab(ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()
        )), creativeTabList);
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
