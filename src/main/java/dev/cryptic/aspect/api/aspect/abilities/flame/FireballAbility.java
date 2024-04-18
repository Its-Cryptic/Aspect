package dev.cryptic.aspect.api.aspect.abilities.flame;

import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;
import dev.cryptic.aspect.api.aspect.AspectType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class FireballAbility extends AbstractAbility {

    public FireballAbility(ResourceLocation id, Supplier<AspectType> aspectType) {
        super(id, aspectType);
    }

    @Override
    public boolean isToggleable() {
        return false;
    }

}
