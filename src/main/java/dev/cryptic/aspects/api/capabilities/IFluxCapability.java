package dev.cryptic.aspects.api.capabilities;

import dev.cryptic.aspects.api.flux.AspectType;
import dev.cryptic.aspects.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.UUID;

public interface IFluxCapability extends INBTSerializable<CompoundTag> {
    float getCurrentFlux();

    int getMaxFlux();

    void setMaxFlux(int maxFlux);

    float setFlux(final float flux);

    float addFlux(final float flux);
    
    float removeFlux(final float flux);

    int addMaxFlux(final int flux);

    int removeMaxFlux(final int flux);

    int getMaxSoul();
    int addMaxSoul(final int soul);
    void setMaxSoul(final int soul);
    int removeMaxSoul(final int soul);

    void addGolem(AbstractGolem golem, int imbuedSoul);

    void removeGolem(AbstractGolem golem);

    boolean hasGolem(AbstractGolem golem);

    int getImbuedSoul(AbstractGolem golem);

    List<UUID> getAllGolemUUIDs();


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
