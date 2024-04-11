package dev.cryptic.aspect.api.aspect.abilities.flame;

import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;
import dev.cryptic.aspect.api.aspect.AspectType;

import java.util.function.Supplier;

public class FireballAbility extends AbstractAbility {

    public FireballAbility(String id, Supplier<AspectType> aspectType) {
        super(id, aspectType);
    }

}
