package dev.cryptic.aspect.api.item;

import dev.cryptic.aspect.item.ModCreativeModeTab;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class AspectCurio extends Item implements ICurioItem {

    public AspectCurio() {
        this(new Properties().stacksTo(1).tab(ModCreativeModeTab.ASPECTS));
    }

    public AspectCurio(Properties properties) {
        super(properties);
    }
}
