package dev.cryptic.aspect.api.capabilities.aspect;

import dev.cryptic.aspect.api.flux.AspectType;
import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.UUID;

public interface IAspectCapability extends INBTSerializable<CompoundTag> {

    AspectType getAspectType();
    void setAspectType(AspectType aspectType);
}
