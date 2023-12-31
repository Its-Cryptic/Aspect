package dev.cryptic.aspects.api.capabilities;

import dev.cryptic.aspects.api.flux.AspectType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class FluxCapability implements IFluxCapability {

    private final LivingEntity livingEntity;

    private int flux;

    private int maxFlux = 100;

    private int aspectLevel;

    private AspectType aspectType = AspectType.BASE;


    public FluxCapability(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public int getCurrentFlux() {
        return flux;
    }

    @Override
    public int getMaxFlux() {
        return maxFlux;
    }

    @Override
    public void setMaxFlux(int maxFlux) {
        this.maxFlux = maxFlux;
    }

    @Override
    public int setFlux(int flux) {
        if (flux > getMaxFlux()) {
            this.flux = getMaxFlux();
        } else if (flux < 0) {
            this.flux = 0;
        } else {
            this.flux = flux;
        }
        return this.getCurrentFlux();
    }

    @Override
    public int addFlux(int flux) {
        this.setFlux(this.getCurrentFlux() + flux);
        return this.getCurrentFlux();
    }

    @Override
    public int removeFlux(int flux) {
        if (flux < 0) flux = 0;
        this.setFlux(this.getCurrentFlux()- flux);
        return this.getCurrentFlux();
    }

    @Override
    public int addMaxFlux(int flux) {
        this.setMaxFlux(this.getMaxFlux() + flux);
        return this.getMaxFlux();
    }

    @Override
    public int removeMaxFlux(int flux) {
        if (flux < 0) flux = 0;
        this.setMaxFlux(this.getMaxFlux()- flux);
        return this.getMaxFlux();
    }

    @Override
    public int getAspectLevel() {
        return aspectLevel;
    }

    @Override
    public AspectType getAspectType() {
        return aspectType;
    }

    @Override
    public void setAspectLevel(int aspectLevel) {
        this.aspectLevel = aspectLevel;
    }

    @Override
    public void setAspectType(AspectType type) {
        this.aspectType = type;
    }

    public boolean isAspectActive() {
        return this.aspectLevel > 0;
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("flux", getCurrentFlux());
        tag.putInt("maxFlux", getMaxFlux());
        tag.putInt("aspectLevel", getAspectLevel());
        tag.putInt("aspectType", getAspectType().getId());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setFlux(tag.getInt("flux"));
        setMaxFlux(tag.getInt("maxFlux"));
        setAspectLevel(tag.getInt("aspectLevel"));
        setAspectType(AspectType.getById(tag.getInt("aspectType")));
    }
}
