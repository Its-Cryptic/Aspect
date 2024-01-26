package dev.cryptic.aspect.entity.fluxentity.golem;

import dev.cryptic.aspect.entity.fluxentity.AbstractFluxEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractGolem extends AbstractFluxEntity implements OwnableEntity {
    protected static final EntityDataAccessor<Optional<UUID>> OWNER_UUID_DATA = SynchedEntityData.defineId(AbstractGolem.class, EntityDataSerializers.OPTIONAL_UUID);

    protected AbstractGolem(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID_DATA, Optional.empty());
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.getOwnerUUID() != null) {
            nbt.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    public boolean isOwnedBy(LivingEntity p_21831_) {
        return p_21831_ == this.getOwner();
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID_DATA).orElse((UUID)null);
    }

    public void setOwnerUUID(@Nullable UUID p_21817_) {
        this.entityData.set(OWNER_UUID_DATA, Optional.ofNullable(p_21817_));
    }
}
