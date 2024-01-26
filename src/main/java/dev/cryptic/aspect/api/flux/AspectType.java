package dev.cryptic.aspect.api.flux;

public enum AspectType {
    BASE(0, "None", new FluxProperties(0,0)),
    LIGHTNING(1, "Lightning", new FluxProperties(0.2,0.5)),
    FLAME(2, "Flame", new FluxProperties(0.2,0.5)),
    BLOOD(3, "Blood", new FluxProperties(0.2,0.5)),
    SOLAR(4, "Solar", new FluxProperties(0.2,0.5));



    private final int id;
    private final String name;
    private final FluxProperties properties;

    AspectType(int id, String name, FluxProperties properties) {
        this.id = id;
        this.name = name;
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
