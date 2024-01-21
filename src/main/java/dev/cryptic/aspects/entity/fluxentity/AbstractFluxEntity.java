package dev.cryptic.aspects.entity.fluxentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;

public abstract class AbstractFluxEntity extends PathfinderMob implements IAnimatable {

    protected AbstractFluxEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }
}
