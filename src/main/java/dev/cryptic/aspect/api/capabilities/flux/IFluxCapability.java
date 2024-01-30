package dev.cryptic.aspect.api.capabilities.flux;

import dev.cryptic.aspect.api.flux.AspectTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IFluxCapability extends INBTSerializable<CompoundTag> {
    float getCurrentFlux();

    int getMaxFlux();

    void setMaxFlux(int maxFlux);

    float setFlux(final float flux);

    float addFlux(final float flux);
    
    float removeFlux(final float flux);

    int addMaxFlux(final int flux);

    int removeMaxFlux(final int flux);
}
