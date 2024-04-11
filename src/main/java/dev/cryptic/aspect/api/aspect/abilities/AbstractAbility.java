package dev.cryptic.aspect.api.aspect.abilities;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.aspect.AspectType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public abstract class AbstractAbility implements CommonAbilityEvents, IAbilityInteractEvents {
    private ResourceLocation id;
    private String langKey;

    private Supplier<AspectType> aspectType;



    public AbstractAbility(String id, Supplier<AspectType> aspectType) {
        this.id = new ResourceLocation(Aspect.MODID, id);
        this.langKey = "ability." + Aspect.MODID + "." + id;
        this.aspectType = aspectType;
    }

    public ResourceLocation getId() {
        return id;
    }

    public String getName() {
        return id.getPath();
    }

    public String getLangKey() {
        return langKey;
    }

    public Supplier<AspectType> getAspectTypeSupplier() {
        return aspectType;
    }

    public AspectType getAspectType() {
        return aspectType.get();
    }


}
