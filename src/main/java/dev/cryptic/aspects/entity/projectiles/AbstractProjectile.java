package dev.cryptic.aspects.entity.projectiles;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Optional;
import java.util.UUID;

public abstract class AbstractProjectile extends Projectile {

    private static final EntityDataAccessor<Optional<UUID>> TARGET_UUID = SynchedEntityData.defineId(AbstractProjectile.class, EntityDataSerializers.OPTIONAL_UUID);

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;
    private double lerpXDelta;
    private double lerpYDelta;
    private double lerpZDelta;

    private int dieIn = -1;
    public AbstractProjectile(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    public void tick() {
        super.tick();

        // Homing Logic
        if (!level.isClientSide) {
            Entity target = getTarget();
            if (target != null && tickCount > 3 && dieIn == -1 && this.distanceTo(target) > 1.5F && tickCount < 20) {
                Vec3 arcVec = target.position().add(0, 0.3 * target.getBbHeight(), 0).subtract(this.position()).normalize();
                this.setDeltaMovement(this.getDeltaMovement().add(arcVec.scale(0.3F)));
            }
        }

        // Movement and Collision Logic
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        this.setPos(d0, d1, d2);

        // Hit Detection
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        // Lifetime Management
        if (dieIn > 0) {
            dieIn--;
            if (dieIn == 0) {
                this.discard();
            }
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps, boolean b) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yr;
        this.lerpXRot = xr;
        this.lerpSteps = steps;
        this.setDeltaMovement(this.lerpXDelta, this.lerpYDelta, this.lerpZDelta);
    }

    @Override
    public void lerpMotion(double x, double y, double z) {
        this.lerpXDelta = x;
        this.lerpYDelta = y;
        this.lerpZDelta = z;
        this.setDeltaMovement(this.lerpXDelta, this.lerpYDelta, this.lerpZDelta);
    }


    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
    }

    public Entity getTarget() {
        UUID id = this.entityData.get(TARGET_UUID).orElse(null);
        return id == null ? null : ((ServerLevel) level).getEntity(id);
    }

    public void setTarget(@javax.annotation.Nullable UUID targetUUID) {
        this.entityData.set(TARGET_UUID, Optional.ofNullable(targetUUID));
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(TARGET_UUID, Optional.empty());
    }
}
