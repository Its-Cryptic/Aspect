package dev.cryptic.aspect.api.capabilities.aspect;

import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.aspect.abilities.AbstractAbility;
import dev.cryptic.aspect.api.registry.AbilityRegistry;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AspectCapability implements IAspectCapability {

    private final LivingEntity livingEntity;
    private AspectType aspectType = AspectRegistry.NONE.get();
    private final Map<AbstractAbility, AbilityData> abilityDataMap = new HashMap<>();

    public AspectCapability(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
        AbilityRegistry.REGISTRY.get().getValues().forEach(ability -> {
            abilityDataMap.put(ability, new AbilityData());
        });
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
        tag.putString("aspectType", aspectType.getName());

            CompoundTag aspectsTag = new CompoundTag();
            for (AspectType aspectType : AspectRegistry.REGISTRY.get().getValues() ) {
                CompoundTag abilitiesTag = new CompoundTag();
                for (AbstractAbility ability : aspectType.getAbilities()) {
                    CompoundTag abilityTag = new CompoundTag();
                    if (ability.isToggleable()) abilityTag.putBoolean("enabled", Math.random() > 0.5);
                    abilityTag.putInt("level", 0);
                    abilityTag.putInt("cooldown", 0);

                    abilitiesTag.put(ability.getName(), abilityTag);
                }

                aspectsTag.put(aspectType.getName(), abilitiesTag);
            }
        tag.put("aspectData", aspectsTag);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        int typeID = tag.getInt("aspectType");
        setAspectType(AspectRegistry.getAspectFromId(typeID));
    }


    public class AbilityData {
        private boolean level;

    }
}
