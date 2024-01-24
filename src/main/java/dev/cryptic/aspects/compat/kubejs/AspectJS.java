package dev.cryptic.aspects.compat.kubejs;

import dev.cryptic.aspects.api.flux.AspectType;
import dev.cryptic.aspects.api.util.FluxUtil;
import dev.cryptic.aspects.block.ModBlocks;
import dev.cryptic.aspects.entity.ModEntityTypes;
import dev.cryptic.aspects.item.ModItems;

public class AspectJS {
    public static final FluxUtil fluxUtil = new FluxUtil();
    public static final Class<AspectType> ASPECT_TYPES = AspectType.class;
    public static final ModItems items = new ModItems();
    public static final ModBlocks blocks = new ModBlocks();
    public static final ModEntityTypes entities = new ModEntityTypes();
}
