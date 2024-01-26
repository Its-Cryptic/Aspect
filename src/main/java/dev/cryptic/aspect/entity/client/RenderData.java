package dev.cryptic.aspect.entity.client;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class RenderData<T extends LivingEntity> {
    public Vec3 position;
    public Vec3 positionOld;
    public float yBodyRot;
    public float yBodyRotO;
    public float yaw;

    public RenderData(T entity, float yaw) {
        this.yaw = yaw;
        position = entity.position();
        positionOld = new Vec3(entity.xOld, entity.yOld, entity.zOld);
        yBodyRot = entity.yBodyRot;
        yBodyRotO = entity.yBodyRotO;

    }

    public Vec3 getPosition() {
        return position;
    }
    public float getYaw() {
        return yaw;
    }
}
