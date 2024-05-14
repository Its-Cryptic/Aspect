package dev.cryptic.aspect.common.entity.fluxentity.golem.threewisemonkeys;

import dev.cryptic.aspect.registry.common.AspectAttributes;
import dev.cryptic.aspect.common.entity.fluxentity.AbstractFluxEntity;
import dev.cryptic.aspect.common.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Mizaru extends AbstractGolem {
    //protected static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("move.fly");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(
            this.getDisplayName(), ServerBossEvent.BossBarColor.GREEN, ServerBossEvent.BossBarOverlay.NOTCHED_6))
            .setDarkenScreen(true)
            .setCreateWorldFog(true);

    public static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(Mizaru.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IS_KICKING = SynchedEntityData.defineId(Mizaru.class, EntityDataSerializers.BOOLEAN);

    public Mizaru(EntityType<? extends Mizaru> type, Level level) {
        super(type, level);
        setPersistenceRequired();
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    public static AttributeSupplier setAttributes() {
        return AbstractFluxEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)

                .add(AspectAttributes.FLUX_REGEN.get(), 2.0f)
                .build();
    }

    @Override
    protected void registerGoals() {
        //this.goalSelector.addGoal(1, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.5D, 5));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
//        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
//        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
//
//        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
//        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
//        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Creeper.class, true));
    }


    protected void playStepSound(BlockPos Pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAT_STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }


    public boolean canKick() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //controllers.add(new AnimationController<>(this, "Flying", 20, this::flyAnimController));
    }

    protected <E extends Mizaru> PlayState flyAnimController(final AnimationState<E> event) {
        //if (event.isMoving()) return event.setAndContinue(FLY_ANIM);

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
