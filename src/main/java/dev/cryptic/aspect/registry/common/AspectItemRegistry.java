package dev.cryptic.aspect.registry.common;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.item.custom.MonkeySpawnItem;
import dev.cryptic.aspect.common.item.custom.fluxitems.Whistle;
import dev.cryptic.aspect.common.misc.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.cryptic.aspect.registry.common.AspectCreativeTabs.addToTab;

public class AspectItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Aspect.MODID);

    public static final RegistryObject<Item> ZIRCON = addToTab(ITEMS.register("zircon",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
            )),
            AspectCreativeTabs.ASPECT_MAIN_ITEMS
    );

    public static final RegistryObject<Item> RAW_ZIRCON = addToTab(ITEMS.register("raw_zircon",
            () -> new Item(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
            )),
            AspectCreativeTabs.ASPECT_MAIN_ITEMS
    );

    public static final RegistryObject<Item> MIZARU_SPAWN_EGG = addToTab(ITEMS.register("mizaru_spawn_egg",
            () -> new MonkeySpawnItem(AspectEntities.MIZARU, Constants.MIZARU_NOTE, Constants.MIZARU_SPAWN_NOTE, new Item.Properties()
                    .rarity(Rarity.EPIC)
            )),
            AspectCreativeTabs.ASPECT_MAIN_ITEMS
    );

    public static final RegistryObject<Item> KIKAZARU_SPAWN_EGG = addToTab(ITEMS.register("kikazaru_spawn_egg",
            () -> new MonkeySpawnItem(AspectEntities.MIZARU, Constants.KIKAZARU_NOTE, Constants.KIKAZARU_SPAWN_NOTE, new Item.Properties()
                    .rarity(Rarity.EPIC)
            )),
            AspectCreativeTabs.ASPECT_MAIN_ITEMS
    );

    public static final RegistryObject<Item> IWAZARU_SPAWN_EGG = addToTab(ITEMS.register("iwazaru_spawn_egg",
            () -> new MonkeySpawnItem(AspectEntities.MIZARU, Constants.IWAZARU_NOTE, Constants.IWAZARU_SPAWN_NOTE, new Item.Properties()
                    .rarity(Rarity.EPIC)
            )),
            AspectCreativeTabs.ASPECT_MAIN_ITEMS
    );

    public static final RegistryObject<Item> FLUX_WHISTLE = addToTab(ITEMS.register("whistle",
            () -> new Whistle(20, 80, 100, new Item.Properties()
                    .rarity(Rarity.EPIC)
            )),
            AspectCreativeTabs.ASPECT_MAIN_ITEMS

    );

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
