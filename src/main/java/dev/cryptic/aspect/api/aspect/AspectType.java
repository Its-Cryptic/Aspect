package dev.cryptic.aspect.api.aspect;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;

import java.util.ArrayList;
import java.util.function.Supplier;

public class AspectType {
    int id;
    String name;
    String langKey;
    AspectColor colors;
    AspectColor empoweredColors;
    Supplier<ArrayList<AbstractAbility>> abilitySupplier;

    public AspectType(int id, String name, AspectColor colors, AspectColor empoweredColors, Supplier<ArrayList<AbstractAbility>> abilities) {
        this.id = id;
        this.name = name;
        this.langKey = String.format("aspect.aspect.%s", name.toLowerCase());
        this.colors = colors;
        this.empoweredColors = empoweredColors;
        this.abilitySupplier = abilities;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLangKey() {
        return langKey;
    }

    public AspectColor getColors() {
        return colors;
    }

    public AspectColor getEmpoweredColors() {
        return empoweredColors;
    }

    public Supplier<ArrayList<AbstractAbility>> getAbilitySupplier() {
        return abilitySupplier;
    }

    public ArrayList<AbstractAbility> getAbilities() {
        return abilitySupplier.get();
    }



    public String toString() {
        return Aspect.MODID + ":" + name;
    }

}
