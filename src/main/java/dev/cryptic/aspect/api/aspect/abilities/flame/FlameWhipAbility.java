package dev.cryptic.aspect.api.aspect.abilities.flame;

import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;
import dev.cryptic.aspect.api.aspect.AspectType;

import java.util.function.Supplier;

public class FlameWhipAbility extends AbstractAbility {

    public FlameWhipAbility(String id, Supplier<AspectType> aspectType) {
        super(id, aspectType);
    }

    @Override
    public boolean isToggleable() {
        return false;
    }
}
