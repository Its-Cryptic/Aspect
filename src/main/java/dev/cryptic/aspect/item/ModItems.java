package dev.cryptic.aspect.item;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.entity.ModEntityTypes;
import dev.cryptic.aspect.item.custom.MonkeySpawnItem;
import dev.cryptic.aspect.item.custom.fluxitems.Whistle;
import dev.cryptic.aspect.misc.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Aspect.MODID);

    public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ASPECTS)));

    public static final RegistryObject<Item> RAW_ZIRCON = ITEMS.register("raw_zircon",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ASPECTS)));

    public static final RegistryObject<Item> MIZARU_SPAWN_EGG = ITEMS.register("mizaru_spawn_egg",
            () -> new MonkeySpawnItem(ModEntityTypes.MIZARU, Constants.MIZARU_NOTE, Constants.MIZARU_SPAWN_NOTE,
                    new Item.Properties().tab(ModCreativeModeTab.ASPECTS_BLOCKS).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> KIKAZARU_SPAWN_EGG = ITEMS.register("kikazaru_spawn_egg",
            () -> new MonkeySpawnItem(ModEntityTypes.MIZARU, Constants.KIKAZARU_NOTE, Constants.KIKAZARU_SPAWN_NOTE,
                    new Item.Properties().tab(ModCreativeModeTab.ASPECTS_BLOCKS).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> IWAZARU_SPAWN_EGG = ITEMS.register("iwazaru_spawn_egg",
            () -> new MonkeySpawnItem(ModEntityTypes.MIZARU, Constants.IWAZARU_NOTE, Constants.IWAZARU_SPAWN_NOTE,
                    new Item.Properties().tab(ModCreativeModeTab.ASPECTS_BLOCKS).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> FLUX_WHISTLE = ITEMS.register("whistle",
            () -> new Whistle(20, 80, 100,
                    new Item.Properties().tab(ModCreativeModeTab.ASPECTS_BLOCKS).rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
