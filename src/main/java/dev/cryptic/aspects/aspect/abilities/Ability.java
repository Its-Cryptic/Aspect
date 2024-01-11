package dev.cryptic.aspects.aspect.abilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class Ability implements INBTSerializable<CompoundTag> {
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
//        ResourceLocation id = Aspects.ABILITY_TYPE_REGISTRY.get().getKey(type);
//        tag.putString("id", id.toString());
//        tag.putBoolean("enabled", enabled);
//        tag.putInt("cooldown", cooldown);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
