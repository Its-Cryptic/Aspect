package dev.cryptic.aspect.aspect.abilities.flame;

import dev.cryptic.aspect.api.flux.AspectType;
import dev.cryptic.aspect.aspect.abilities.AbstractAbility;

import java.util.function.Supplier;

public class FireballAbility extends AbstractAbility {

    public FireballAbility(String id, Supplier<AspectType> aspectType) {
        super(id, aspectType);
    }
}
