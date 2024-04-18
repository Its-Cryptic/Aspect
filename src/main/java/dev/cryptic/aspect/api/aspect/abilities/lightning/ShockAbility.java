package dev.cryptic.aspect.api.aspect.abilities.lightning;

import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;

import java.util.function.Supplier;

public class ShockAbility extends AbstractAbility {

    public ShockAbility(String id, Supplier<AspectType> aspectType) {
        super(id, aspectType);
    }

    @Override
    public boolean isToggleable() {
        return true;
    }
}
