package dev.cryptic.aspects.api.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class FluxCapability implements IFluxCapability {

    private final LivingEntity livingEntity;

    private int flux;

    private int maxFlux = 100;

    private int aspectLevel;

    private int aspectType;


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
    public int getAspectLevel() {
        return aspectLevel;
    }

    @Override
    public int getAspectType() {
        return aspectType;
    }

    @Override
    public void setAspectLevel(int aspectLevel) {
        this.aspectLevel = aspectLevel;
    }

    @Override
    public void setAspectType(int aspectType) {
        this.aspectType = aspectType;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("flux", getCurrentFlux());
        tag.putInt("maxFlux", getMaxFlux());
        tag.putInt("aspectLevel", getAspectLevel());
        tag.putInt("aspectType", getAspectType());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setFlux(tag.getInt("flux"));
        setMaxFlux(tag.getInt("maxFlux"));
        setAspectLevel(tag.getInt("aspectLevel"));
        setAspectType(tag.getInt("aspectType"));
    }
}
