package dev.cryptic.aspects.aspect;

import dev.cryptic.aspects.api.capabilities.FluxProperties;
import dev.cryptic.aspects.api.flux.AspectType;
import dev.cryptic.aspects.aspect.abilities.Ability;

public abstract class AbstractAspect {
    private String name;
    private FluxProperties fluxProperties;
    private Ability[] abilities;

    public AbstractAspect(String name, FluxProperties fluxProperties, Ability[] abilities) {
        this.name = name;
        this.fluxProperties = fluxProperties;
    }

    public String getName() {
        return name;
    }

    public FluxProperties getFluxProperties() {
        return fluxProperties;
    }
}
