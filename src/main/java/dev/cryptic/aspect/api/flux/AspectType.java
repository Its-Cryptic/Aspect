package dev.cryptic.aspect.api.flux;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.aspect.abilities.AbstractAbility;

import java.util.ArrayList;
import java.util.function.Supplier;

public class AspectType {
    int id;
    String name;
    String langKey;
    AspectColor colors;
    AspectColor empoweredColors;
    FluxProperties properties;
    ArrayList<Supplier<AbstractAbility>> abilities;

    public AspectType(int id, String name, AspectColor colors, AspectColor empoweredColors, FluxProperties properties, ArrayList<Supplier<AbstractAbility>> abilities) {
        this.id = id;
        this.name = name;
        this.langKey = "aspect.aspect." + name.toLowerCase();
        this.colors = colors;
        this.empoweredColors = empoweredColors;
        this.properties = properties;
        this.abilities = abilities;
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

    public FluxProperties getProperties() {
        return properties;
    }

    public ArrayList<Supplier<AbstractAbility>> getAbilities() {
        return abilities;
    }



    public String toString() {
        return Aspect.MODID + ":" + name;
    }

}
