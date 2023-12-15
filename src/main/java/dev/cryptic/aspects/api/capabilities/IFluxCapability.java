package dev.cryptic.aspects.api.capabilities;

import dev.cryptic.aspects.kubejs.bindings.AspectType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IFluxCapability extends INBTSerializable<CompoundTag> {
    int getCurrentFlux();

    int getMaxFlux();

    void setMaxFlux(int maxFlux);

    int setFlux(final int flux);

    int addFlux(final int flux);
    
    int removeFlux(final int flux);

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
