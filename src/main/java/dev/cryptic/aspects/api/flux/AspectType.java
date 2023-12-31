package dev.cryptic.aspects.api.flux;

import dev.cryptic.aspects.api.capabilities.FluxProperties;

public enum AspectType {
    BASE(0, new FluxProperties(0,0)),
    LIGHTNING(1, new FluxProperties(0.2,0.5)),
    FLAME(2, new FluxProperties(0.2,0.5)),
    BLOOD(3, new FluxProperties(0.2,0.5)),
    SOLAR(4, new FluxProperties(0.2,0.5));



    private final int id;
    private final FluxProperties properties;

    AspectType(int id, FluxProperties properties) {
        this.id = id;
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public FluxProperties getProperties() {
        return properties;
    }

    public static AspectType getById(int id) {
        for (AspectType type : AspectType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null; // or throw an exception if an invalid id is provided
    }
}
