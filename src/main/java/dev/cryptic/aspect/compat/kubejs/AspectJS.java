package dev.cryptic.aspect.compat.kubejs;

import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.api.util.FluxUtil;
import dev.cryptic.aspect.common.block.BlockRegistry;
import dev.cryptic.aspect.common.entity.ModEntityTypes;
import dev.cryptic.aspect.common.item.ItemRegistry;

public class
AspectJS {
    public static final FluxUtil fluxUtil = new FluxUtil();
    public static final AspectRegistry aspectsRegistry = new AspectRegistry();
    public static final ItemRegistry items = new ItemRegistry();
    public static final BlockRegistry blocks = new BlockRegistry();
    public static final ModEntityTypes entities = new ModEntityTypes();
}
