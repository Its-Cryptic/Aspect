package dev.cryptic.aspects.entity;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractNPCTypeOne extends AgeableMob {
    protected AbstractNPCTypeOne(EntityType<? extends AgeableMob> type, Level level) {
        super(type, level);
    }
}
