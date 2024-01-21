package dev.cryptic.aspects.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;

public class SomethingData {

    Vec3 position;
    PoseStack poseStack;

    public SomethingData(Vec3 position, PoseStack poseStack) {
        this.position = position;
        this.poseStack = poseStack;
    }

    public Vec3 getPosition() {
        return position;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }
}
