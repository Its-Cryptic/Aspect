package dev.cryptic.aspect.aspect.abilities.lightning;

import dev.cryptic.aspect.api.flux.AspectType;
import dev.cryptic.aspect.aspect.abilities.AbstractAbility;

import java.util.function.Supplier;

public class ShockAbility extends AbstractAbility {

    public ShockAbility(String id, Supplier<AspectType> aspectType) {
        super(id, aspectType);
    }
}
