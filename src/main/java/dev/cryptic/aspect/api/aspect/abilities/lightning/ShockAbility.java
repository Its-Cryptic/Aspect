package dev.cryptic.aspect.api.aspect.abilities.lightning;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;

public class ShockAbility extends AbstractAbility {

    public ShockAbility() {
        this.resourceLocation = Aspect.id("shock");
    }

    @Override
    public boolean isToggleable() {
        return true;
    }

    @Override
    public boolean isClickable() {
        return false;
    }

    @Override
    public boolean isDoubleClickable() {
        return false;
    }
}
