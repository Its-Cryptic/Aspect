package dev.cryptic.aspect.api.capabilities.flux;

import dev.cryptic.aspect.api.flux.AspectType;
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

    default int getAspectLevel() {
        return 0;
    }

    default AspectType getAspectType() {
        return AspectType.BASE;
    }

    default void setAspectLevel(int level) {
    }

    default void setAspectType(AspectType aspectType) {
    }
}
