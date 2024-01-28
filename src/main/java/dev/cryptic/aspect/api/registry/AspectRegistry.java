package dev.cryptic.aspect.api.registry;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.flux.AspectColor;
import dev.cryptic.aspect.api.flux.AspectType;
import dev.cryptic.aspect.api.flux.FluxProperties;
import dev.cryptic.aspect.aspect.abilities.AbstractAbility;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

public class AspectRegistry {
    public static final ResourceKey<Registry<AspectType>> ASPECT_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aspect.MODID, "aspects"));
    private static final DeferredRegister<AspectType> ASPECTS = DeferredRegister.create(ASPECT_REGISTRY_KEY, Aspect.MODID);
    public static final Supplier<IForgeRegistry<AspectType>> REGISTRY = ASPECTS.makeRegistry(() -> new RegistryBuilder<AspectType>().disableSaving().disableOverrides());

    public static int id = -1;

    public static void register(IEventBus eventBus) {
        ASPECTS.register(eventBus);
        //eventBus.register(AspectRegistry::clientSetup);
    }

    private static RegistryObject<AspectType> registerAspect(AspectType aspect) {
        return ASPECTS.register(aspect.getName(), () -> aspect);
    }

    public static final RegistryObject<AspectType> NONE = registerAspect(new AspectType(
            id++,
            "none",
            new AspectColor(0xFFFFFF, 0xFFFFFF, 0xFFFFFF),
            new AspectColor(0xFFFFFF, 0xFFFFFF, 0xFFFFFF),
            new FluxProperties(0, 0),
            null
    ));

    public static final RegistryObject<AspectType> FIRE = registerAspect(new AspectType(
            id++,
            "fire",
            new AspectColor(0xFF0000, 0xFF0000, 0xFF0000),
            new AspectColor(0xFF0000, 0xFF0000, 0xFF0000),
            new FluxProperties(0, 0),
            new ArrayList<Supplier<AbstractAbility>>(){{
                add(AbilityRegistry.FIREBALL_ABILITY);
                add(AbilityRegistry.FLAME_WHIP_ABILITY);
            }}
    ));

}
