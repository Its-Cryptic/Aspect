package dev.cryptic.aspect.api.capabilities.aspect;

import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class AspectCapability implements IAspectCapability {

    private final LivingEntity livingEntity;
    private AspectType aspectType = AspectRegistry.NONE.get();

    public AspectCapability(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public AspectType getAspectType() {
        return aspectType;
    }

    @Override
    public void setAspectType(AspectType aspectType) {
        this.aspectType = aspectType;
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("aspectType", aspectType.getId());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        int typeID = tag.getInt("aspectType");
        setAspectType(AspectRegistry.getAspectFromId(typeID));
    }
}
