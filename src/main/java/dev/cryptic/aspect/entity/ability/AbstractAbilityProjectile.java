package dev.cryptic.aspect.entity.ability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public abstract class AbstractAbilityProjectile extends Projectile {
    protected int maxAge = 20*20;
    protected float damage;
    public abstract float getSpeed();
    public AbstractAbilityProjectile(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    public void shoot(Vec3 rotation) {
        setDeltaMovement(rotation.scale(getSpeed()));
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public boolean hasGravity() {
        return false;
    }

    @Override
    public void tick() {
        if (tickCount > maxAge) {
            discard();
            return;
        }

        HitResult hitResult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
            onHit(hitResult);
        }

        setPos(position().add(getDeltaMovement()));
        ProjectileUtil.rotateTowardsMovement(this, 1.0F);
        if (!this.isNoGravity() && hasGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }
        super.tick();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        Vec3 pos = new Vec3(xOld, yOld, zOld);

        Chicken chicken = new Chicken(EntityType.CHICKEN, level);
        chicken.setPos(pos);
        level.addFreshEntity(chicken);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", this.damage);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.damage = pCompound.getFloat("Damage");
    }
}
