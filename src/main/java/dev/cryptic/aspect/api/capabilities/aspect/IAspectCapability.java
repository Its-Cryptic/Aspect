package dev.cryptic.aspect.api.capabilities.aspect;

import dev.cryptic.aspect.api.aspect.AspectType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAspectCapability extends INBTSerializable<CompoundTag> {

    AspectType getAspectType();
    void setAspectType(AspectType aspectType);




}
