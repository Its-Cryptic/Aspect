package dev.cryptic.aspect.aspect.abilities;

public abstract class AbstractAbility {
    private String id;
    private String name;

    public AbstractAbility(String name) {
        this.id = name;
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
