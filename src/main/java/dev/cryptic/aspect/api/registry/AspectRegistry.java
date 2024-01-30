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

    public static int id = 0;

    public static AspectType getAspectFromId(int id) {
        for (RegistryObject<AspectType> aspectType : ASPECTS.getEntries()) {
            if (aspectType.get().getId() == id) {
                return aspectType.get();
            }
        }
        return null;
    }

    public static final RegistryObject<AspectType> NONE = registerAspect(new AspectType(
            id++,
            "none",
            new AspectColor(0xFFFFFF, 0xFFFFFF, 0xFFFFFF),
            new AspectColor(0xFFFFFF, 0xFFFFFF, 0xFFFFFF),
            new FluxProperties(0, 0),
            null
    ));

    public static final RegistryObject<AspectType> FLAME = registerAspect(new AspectType(
            id++,
            "flame",
            new AspectColor(0xFF0000, 0xFF0000, 0xFF0000),
            new AspectColor(0xFF0000, 0xFF0000, 0xFF0000),
            new FluxProperties(0, 0),
            () -> {
                ArrayList<AbstractAbility> abilities = new ArrayList<>();
                abilities.add(AbilityRegistry.FIREBALL_ABILITY.get());
                abilities.add(AbilityRegistry.FLAME_WHIP_ABILITY.get());

                return abilities;
            }
    ));

    public static final RegistryObject<AspectType> LIGHTNING = registerAspect(new AspectType(
            id++,
            "lightning",
            new AspectColor(0xFF0000, 0xFF0000, 0xFF0000),
            new AspectColor(0xFF0000, 0xFF0000, 0xFF0000),
            new FluxProperties(0, 0),
            () -> {
                ArrayList<AbstractAbility> abilities = new ArrayList<>();
                abilities.add(AbilityRegistry.SHOCK_ABILITY.get());

                return abilities;
            }
    ));

    public static void register(IEventBus eventBus) {
        ASPECTS.register(eventBus);
        //eventBus.register(AspectRegistry::clientSetup);
    }

    private static RegistryObject<AspectType> registerAspect(AspectType aspect) {
        return ASPECTS.register(aspect.getName(), () -> aspect);
    }

    public static ArrayList<AspectType> getAspects() {
        ArrayList<AspectType> aspects = new ArrayList<>();
        for (RegistryObject<AspectType> aspectType : ASPECTS.getEntries()) {
            aspects.add(aspectType.get());
        }
        return aspects;
    }
}
