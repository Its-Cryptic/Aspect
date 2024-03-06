package dev.cryptic.aspect.api.capabilities.flux;

import dev.cryptic.aspect.common.config.AspectCommonConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class FluxCapability implements IFluxCapability {

    private final LivingEntity livingEntity;
    private float flux;
    private int maxFlux = AspectCommonConfig.BASE_MAX_FLUX.get();
    private final int roundPlaces = 3;

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
        flux = round(flux);
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
        return setFlux(this.getCurrentFlux() + flux);
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
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("flux", getCurrentFlux());
        tag.putInt("maxFlux", getMaxFlux());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setFlux(tag.getFloat("flux"));
        setMaxFlux(tag.getInt("maxFlux"));

    }

    private float round(float value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(roundPlaces, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
