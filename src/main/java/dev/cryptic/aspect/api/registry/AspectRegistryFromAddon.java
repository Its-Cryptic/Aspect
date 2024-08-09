package dev.cryptic.aspect.api.registry;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.AspectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

import static dev.cryptic.aspect.api.registry.AspectRegistry.ASPECT_REGISTRY_KEY;

public class AspectRegistryFromAddon {
    public static final DeferredRegister<AspectType> ASPECTS = DeferredRegister.create(ASPECT_REGISTRY_KEY, "myaspectaddon");
    public static final Supplier<IForgeRegistry<AspectType>> REGISTRY = ASPECTS.makeRegistry(() -> new RegistryBuilder<AspectType>().disableSaving());

    public static int id = 0;

    public static final RegistryObject<AspectType> TEST = registerAspect(new AspectType(
            id++,
            "test",
            null,
            null,
            ArrayList::new
    ));

    private static RegistryObject<AspectType> registerAspect(AspectType aspect) {
        return ASPECTS.register(aspect.getName(), () -> aspect);
    }

    public static void register(IEventBus eventBus) {
        ASPECTS.register(eventBus);
    }
}
