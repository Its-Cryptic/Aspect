package dev.cryptic.aspects.api.capabilities.golem;

import dev.cryptic.aspects.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.UUID;

public interface IGolemCapability extends INBTSerializable<CompoundTag> {
    int getMaxSoul();
    int addMaxSoul(final int soul);
    void setMaxSoul(final int soul);
    int removeMaxSoul(final int soul);
    void addGolem(AbstractGolem golem, int imbuedSoul);
    void removeGolem(AbstractGolem golem);
    boolean hasGolem(AbstractGolem golem);
    int getImbuedSoul(AbstractGolem golem);
    List<UUID> getAllGolemUUIDs();
    void setAllGolemUUIDs(List<UUID> uuids);
}
