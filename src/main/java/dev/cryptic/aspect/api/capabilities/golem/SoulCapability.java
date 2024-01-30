package dev.cryptic.aspect.api.capabilities.golem;

import dev.cryptic.aspect.config.AspectCommonConfig;
import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SoulCapability implements ISoulCapability {

    private final LivingEntity livingEntity;
    private int maxSoul = AspectCommonConfig.BASE_MAX_SOUL.get();
    private final Map<UUID, Integer> golems = new HashMap<>();

    public SoulCapability(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }
    @Override
    public int getMaxSoul() {
        return maxSoul;
    }

    @Override
    public void setMaxSoul(int maxSoul) {
        this.maxSoul = maxSoul;
    }
    @Override
    public int addMaxSoul(int soul) {
        if (soul < 0) soul = 0;
        this.maxSoul += soul;
        return this.maxSoul;
    }
    @Override
    public int removeMaxSoul(int soul) {
        if (soul < 0) soul = 0;
        this.maxSoul -= soul;
        return this.maxSoul;
    }
    @Override
    public void addGolem(AbstractGolem golem, int imbuedSoul) {
        golems.put(golem.getUUID(), imbuedSoul);
    }
    @Override
    public void removeGolem(AbstractGolem golem) {
        golems.remove(golem.getUUID());
    }
    @Override
    public int getImbuedSoul(AbstractGolem golem) {
        return golems.getOrDefault(golem.getUUID(), 0);
    }
    @Override
    public boolean hasGolem(AbstractGolem golem) {
        return golems.containsKey(golem.getUUID());
    }

    @Override
    public List<UUID> getAllGolemUUIDs() {
        return new ArrayList<>(golems.keySet());
    }

    @Override
    public void setAllGolemUUIDs(List<UUID> uuids) {

    }

    @Override
    public Map<UUID, Integer> getGolemMap() {
        return golems;
    }

    @Override
    public int getTotalImbuedSoul() {
        int total = 0;
        for (Map.Entry<UUID, Integer> entry : golems.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    @Override
    public int getRemainingSoul() {
        return getMaxSoul() - getTotalImbuedSoul();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("maxSoul", getMaxSoul());

        ListTag golemsList = new ListTag();
        for (Map.Entry<UUID, Integer> entry : golems.entrySet()) {
            CompoundTag golemTag = new CompoundTag();
            golemTag.putUUID("UUID", entry.getKey());
            golemTag.putInt("soul", entry.getValue());
            golemsList.add(golemTag);
        }
        tag.put("golems", golemsList);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setMaxSoul(tag.getInt("maxSoul"));

        ListTag golemsList = tag.getList("golems", 10);
        golems.clear();
        for (int i = 0; i < golemsList.size(); i++) {
            CompoundTag golemTag = golemsList.getCompound(i);
            UUID uuid = golemTag.getUUID("UUID");
            int soul = golemTag.getInt("soul");
            golems.put(uuid, soul);
        }
    }

}
