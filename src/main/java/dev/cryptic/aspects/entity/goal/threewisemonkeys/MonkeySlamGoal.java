package dev.cryptic.aspects.entity.goal.threewisemonkeys;

import dev.cryptic.aspects.entity.threewisemonkeys.Mizaru;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MonkeySlamGoal extends Goal {
    Mizaru boss;
    boolean finished;
    boolean isSlamming;
    BlockPos slamPos;
    BlockPos startPos;
    int ticks;

    public MonkeySlamGoal(Mizaru boss) {
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
        finished = false;
        slamPos = null;
        ticks = 0;
        isSlamming = false;

        startPos = boss.blockPosition();
        //boss.setFlying(true);
    }
}
