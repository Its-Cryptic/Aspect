package dev.cryptic.aspect.api.capabilities.golem;

import dev.cryptic.aspect.common.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ISoulCapability extends INBTSerializable<CompoundTag> {
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

    Map<UUID, Integer> getGolemMap();

    int getTotalImbuedSoul();

    int getRemainingSoul();
}
