package dev.cryptic.aspects.entity.ability.flame.fireblast;

import dev.cryptic.aspects.entity.ModEntityTypes;
import dev.cryptic.aspects.entity.ability.AbstractAbilityProjectile;
import dev.cryptic.aspects.misc.DamageSources;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FireBlastProjectile extends AbstractAbilityProjectile {
    public FireBlastProjectile(EntityType<? extends FireBlastProjectile> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public FireBlastProjectile(LivingEntity shooter, Level level) {
        super(ModEntityTypes.FIRE_BLAST.get(), level);
        setOwner(shooter);
    }

    public static FireBlastProjectile create(EntityType<FireBlastProjectile> type, Level level) {
        return new FireBlastProjectile(type, level);
    }

    @Override
    public float getSpeed() {
        return 1f;
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {

    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        if (hitResult.getEntity() instanceof LivingEntity entity) {
            entity.hurt(DamageSources.ASPECT_FLAME, 5f);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if (!level.isClientSide) {
            this.playSound(SoundEvents.FIRECHARGE_USE, 1f, 1f);
        }

        super.onHit(hitResult);
        this.discard();
    }

    public int getAge(){
        return tickCount;
    }

}
