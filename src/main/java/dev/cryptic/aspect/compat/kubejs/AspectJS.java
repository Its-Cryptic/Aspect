package dev.cryptic.aspect.compat.kubejs;

import dev.cryptic.aspect.api.util.FluxUtil;
import dev.cryptic.aspect.block.ModBlocks;
import dev.cryptic.aspect.entity.ModEntityTypes;
import dev.cryptic.aspect.item.ModItems;

public class AspectJS {
    public static final FluxUtil fluxUtil = new FluxUtil();
    public static final Class<AspectTypes> ASPECT_TYPES = AspectTypes.class;
    public static final ModItems items = new ModItems();
    public static final ModBlocks blocks = new ModBlocks();
    public static final ModEntityTypes entities = new ModEntityTypes();
}
