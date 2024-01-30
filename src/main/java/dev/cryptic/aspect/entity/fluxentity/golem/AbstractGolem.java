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
    public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID_DATA = SynchedEntityData.defineId(AbstractGolem.class, EntityDataSerializers.OPTIONAL_UUID);
    public static final EntityDataAccessor<Integer> IMBUED_SOUL_DATA = SynchedEntityData.defineId(AbstractGolem.class, EntityDataSerializers.INT);

    protected AbstractGolem(EntityType<? extends AbstractGolem> type, Level level) {
        super(type, level);
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID_DATA, Optional.empty());
        this.entityData.define(IMBUED_SOUL_DATA, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        // Save the imbued soul
        nbt.putInt("ImbuedSoul", this.getImbuedSoul());

        // Save the owner UUID, if present
//        Optional<UUID> ownerUuid = Optional.ofNullable(this.getOwnerUUID());
//        ownerUuid.ifPresent(uuid -> nbt.putUUID("OwnerUUID", uuid));
        if (getOwnerUUID() != null) {
            nbt.putUUID("OwnerUUID", getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        // Load the imbued soul
        if (nbt.contains("ImbuedSoul", 3)) { // 3 is the NBT tag type for integers
            this.setImbuedSoul(nbt.getInt("ImbuedSoul"));
        }

        // Load the owner UUID, if it exists
        if (nbt.hasUUID("OwnerUUID")) {
            setOwnerUUID(nbt.getUUID("OwnerUUID"));
        } else {
            setOwnerUUID(null);
        }
    }

    public int getImbuedSoul() {
        return this.entityData.get(IMBUED_SOUL_DATA);
    }

    public void setImbuedSoul(int imbuedSoul) {
        this.entityData.set(IMBUED_SOUL_DATA, imbuedSoul);
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

    public @Nullable UUID getOwnerUUID() {
        return this.getEntityData().get(OWNER_UUID_DATA).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID_DATA, Optional.ofNullable(uuid));
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }
}
