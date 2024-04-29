package dev.cryptic.aspect.api.aspect.abilities;

import dev.cryptic.aspect.api.aspect.AspectType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public abstract class AbstractAbility implements IAbilityInteractEvents {
    protected ResourceLocation resourceLocation;
    protected Supplier<AspectType> aspectTypeSupplier;

    public AbstractAbility() {
    }

    public abstract boolean isToggleable();
    public abstract boolean isClickable();
    public abstract boolean isDoubleClickable();

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public String getName() {
        return resourceLocation.getPath();
    }

    public String getLangKey() {
        return String.format("%s.ability.%s", resourceLocation.getNamespace(), resourceLocation.getPath());
    }

    public Supplier<AspectType> getAspectTypeSupplier() {
        return aspectTypeSupplier;
    }

    public AspectType getAspectType() {
        return aspectTypeSupplier.get();
    }





}
