package dev.cryptic.aspect.api.capabilities.flux;

import dev.cryptic.aspect.api.flux.AspectTypes;
import dev.cryptic.aspect.config.AspectCommonConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class FluxCapability implements IFluxCapability {

    private final LivingEntity livingEntity;
    private float flux;
    private int maxFlux = AspectCommonConfig.BASE_MAX_FLUX.get();
    private int aspectLevel;
    private AspectTypes aspectType = AspectTypes.BASE;

    public FluxCapability(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public float getCurrentFlux() {
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
    public float setFlux(float flux) {
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
    public float addFlux(float flux) {
        this.setFlux(this.getCurrentFlux() + flux);
        return this.getCurrentFlux();
    }

    @Override
    public float removeFlux(float flux) {
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
    public AspectTypes getAspectType() {
        return aspectType;
    }

    @Override
    public void setAspectLevel(int aspectLevel) {
        this.aspectLevel = aspectLevel;
    }

    @Override
    public void setAspectType(AspectTypes type) {
        this.aspectType = type;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("flux", getCurrentFlux());
        tag.putInt("maxFlux", getMaxFlux());
        tag.putInt("aspectLevel", getAspectLevel());
        tag.putInt("aspectType", getAspectType().getId());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setFlux(tag.getFloat("flux"));
        setMaxFlux(tag.getInt("maxFlux"));
        setAspectLevel(tag.getInt("aspectLevel"));
        setAspectType(AspectTypes.getById(tag.getInt("aspectType")));
    }

}
