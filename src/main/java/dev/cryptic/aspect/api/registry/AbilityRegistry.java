package dev.cryptic.aspect.api.registry;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.aspect.abilities.AbstractAbility;
import dev.cryptic.aspect.aspect.abilities.flame.FireballAbility;
import dev.cryptic.aspect.aspect.abilities.flame.FlameWhipAbility;
import dev.cryptic.aspect.aspect.abilities.lightning.ShockAbility;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AbilityRegistry {
    public static final ResourceKey<Registry<AbstractAbility>> ABILITY_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Aspect.MODID, "abilities"));
    private static final DeferredRegister<AbstractAbility> ABILITIES = DeferredRegister.create(ABILITY_REGISTRY_KEY, Aspect.MODID);
    public static final Supplier<IForgeRegistry<AbstractAbility>> REGISTRY = ABILITIES.makeRegistry(() -> new RegistryBuilder<AbstractAbility>().disableSaving().disableOverrides());

    public static void register(IEventBus eventBus) {
        ABILITIES.register(eventBus);
    }

    public static RegistryObject<AbstractAbility> registerAbility(AbstractAbility ability) {
        return ABILITIES.register(ability.getName(), () -> ability);
    }

    public static AbstractAbility getAbility(String id) {
        return getAbility(new ResourceLocation(id));
    }

    public static AbstractAbility getAbility(ResourceLocation resourceLocation) {
        var ability = REGISTRY.get().getValue(resourceLocation);
        if (ability == null) {
            //return noneSpell;
        }
        return ability;
    }

    // Lightning
    public static final RegistryObject<AbstractAbility> SHOCK_ABILITY = registerAbility(new ShockAbility(
            "shock"
    ));

    public static final RegistryObject<AbstractAbility> FIREBALL_ABILITY = registerAbility(new FireballAbility(
            "fireball"
    ));

    public static final RegistryObject<AbstractAbility> FLAME_WHIP_ABILITY = registerAbility(new FlameWhipAbility(
            "flame_whip"
    ));
}
