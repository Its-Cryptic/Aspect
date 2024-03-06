package dev.cryptic.aspect.common.entity.goal.threewisemonkeys;

import dev.cryptic.aspect.common.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MonkeyKickGoal extends Goal {
    Mizaru boss;
    boolean finished;
    boolean isKicking;
    int ticks;
    BlockPos kickPos;
    BlockPos startPos;

    public MonkeyKickGoal(Mizaru boss) {
        this.boss = boss;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }


    @Override
    public boolean canUse() {
        return boss.canKick() && boss.getTarget() != null;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        super.start();
    }
}
