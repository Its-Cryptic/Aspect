package dev.cryptic.aspect.item;

import dev.cryptic.aspect.Aspect;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Aspect.MODID);

    public static final List<Supplier<? extends ItemLike>> ASPECT_MAIN_ITEMS = new ArrayList<>();
    public static final RegistryObject<CreativeModeTab> ASPECT_MAIN = CREATIVE_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.aspect"))
                    .icon(ItemRegistry.ZIRCON.get()::getDefaultInstance)
                    .displayItems((displayParams, output) -> ASPECT_MAIN_ITEMS.forEach(item -> output.accept(item.get())))
                    .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike, List<Supplier<? extends ItemLike>> creativeModeTabList) {
        creativeModeTabList.add(itemLike);
        return itemLike;
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
