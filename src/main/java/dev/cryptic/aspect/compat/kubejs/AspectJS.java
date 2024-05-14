package dev.cryptic.aspect.compat.kubejs;

import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.api.util.FluxUtil;
import dev.cryptic.aspect.registry.common.AspectBlocks;
import dev.cryptic.aspect.registry.common.AspectEntities;
import dev.cryptic.aspect.registry.common.AspectItemRegistry;

public class
AspectJS {
    public static final FluxUtil fluxUtil = new FluxUtil();
    public static final AspectRegistry aspectsRegistry = new AspectRegistry();
    public static final AspectItemRegistry items = new AspectItemRegistry();
    public static final AspectBlocks blocks = new AspectBlocks();
    public static final AspectEntities entities = new AspectEntities();
}
