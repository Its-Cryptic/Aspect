package dev.cryptic.aspect.api.registry;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.AspectColor;
import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;

public class AspectRegistry {
    public static final ResourceKey<Registry<AspectType>> ASPECT_REGISTRY_KEY = ResourceKey.createRegistryKey(Aspect.id("aspects"));
    public static final DeferredRegister<AspectType> ASPECTS = DeferredRegister.create(ASPECT_REGISTRY_KEY, Aspect.MODID);
    public static final Supplier<IForgeRegistry<AspectType>> REGISTRY = ASPECTS.makeRegistry(() -> new RegistryBuilder<AspectType>()
                    .disableSaving()
                    .disableOverrides()
                    .setName(ASPECT_REGISTRY_KEY.location())
    );

    public static int id = 0;

    public static AspectType getAspectFromId(int id) {
        return ASPECTS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(aspectType -> aspectType.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static final RegistryObject<AspectType> NONE = registerAspect(new AspectType(
            id++,
            "none",
            new AspectColor(new Color(242, 0, 255), new Color(255, 0, 128), new Color(255, 119, 0)),
            new AspectColor(new Color(242, 0, 255), new Color(255, 0, 128), new Color(255, 119, 0)),
            ArrayList::new
    ));

    public static final RegistryObject<AspectType> FLAME = registerAspect(new AspectType(
            id++,
            "flame",
            new AspectColor(new Color(242, 0, 255), new Color(255, 0, 128), new Color(255, 119, 0)),
            new AspectColor(new Color(242, 0, 255), new Color(255, 0, 128), new Color(255, 119, 0)),
            () -> {
                ArrayList<AbstractAbility> abilities = new ArrayList<>();
                abilities.add(AbilityRegistry.FIREBALL_ABILITY.get());

                return abilities;
            }
    ));

    public static final RegistryObject<AspectType> LIGHTNING = registerAspect(new AspectType(
            id++,
            "lightning",
            new AspectColor(new Color(242, 0, 255), new Color(255, 0, 128), new Color(255, 119, 0)),
            new AspectColor(new Color(242, 0, 255), new Color(255, 0, 128), new Color(255, 119, 0)),
            () -> {
                ArrayList<AbstractAbility> abilities = new ArrayList<>();
                abilities.add(AbilityRegistry.SHOCK_ABILITY.get());

                return abilities;
            }
    ));

    private static RegistryObject<AspectType> registerAspect(AspectType aspect) {
        return ASPECTS.register(aspect.getName(), () -> aspect);
    }

    public static void register(IEventBus eventBus) {
        ASPECTS.register(eventBus);
        //eventBus.register(AspectRegistry::clientSetup);
    }

    public static ArrayList<AspectType> getAspects() {
        ArrayList<AspectType> aspects = new ArrayList<>();
        for (RegistryObject<AspectType> aspectType : ASPECTS.getEntries()) {
            aspects.add(aspectType.get());
        }
        return aspects;
    }
}
