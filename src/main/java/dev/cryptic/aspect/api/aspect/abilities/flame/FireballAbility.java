package dev.cryptic.aspect.api.aspect.abilities.flame;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;
import dev.cryptic.aspect.api.registry.AspectRegistry;

public class FireballAbility extends AbstractAbility {

    public FireballAbility() {
        this.resourceLocation = Aspect.id("fireball");
        this.aspectTypeSupplier = AspectRegistry.FLAME;
    }

    @Override
    public boolean isToggleable() {
        return false;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public boolean isDoubleClickable() {
        return false;
    }

}
