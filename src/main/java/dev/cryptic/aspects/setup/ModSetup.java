package dev.cryptic.aspects.setup;

import dev.cryptic.aspects.api.attribute.AttributeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModSetup {
    public static void registers(IEventBus modEventBus) {
        AttributeRegistry.ATTRIBUTES.register(modEventBus);
    }
}
