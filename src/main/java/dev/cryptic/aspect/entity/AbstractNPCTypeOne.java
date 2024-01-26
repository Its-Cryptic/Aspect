package dev.cryptic.aspect.entity;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;

public abstract class AbstractNPCTypeOne extends AgeableMob implements IAnimatable {
    protected AbstractNPCTypeOne(EntityType<? extends AgeableMob> type, Level level) {
        super(type, level);
    }
}
