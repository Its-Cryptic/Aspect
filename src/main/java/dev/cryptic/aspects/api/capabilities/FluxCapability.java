package dev.cryptic.aspects.api.capabilities;

import dev.cryptic.aspects.api.flux.AspectType;
import dev.cryptic.aspects.config.AspectCommonConfig;
import dev.cryptic.aspects.entity.fluxentity.golem.AbstractGolem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FluxCapability implements IFluxCapability {

    private final LivingEntity livingEntity;

    private float flux;

    private int maxFlux = AspectCommonConfig.BASE_MAX_FLUX.get();

    private int aspectLevel;

    private AspectType aspectType = AspectType.BASE;

    private int maxSoul = AspectCommonConfig.BASE_MAX_SOUL.get();

    private final Map<UUID, Integer> golems = new HashMap<>();


    public FluxCapability(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public float getCurrentFlux() {
        return flux;
    }

    @Override
    public int getMaxFlux() {
        return maxFlux;
    }

    @Override
    public void setMaxFlux(int maxFlux) {
        this.maxFlux = maxFlux;
    }

    @Override
    public float setFlux(float flux) {
        if (flux > getMaxFlux()) {
            this.flux = getMaxFlux();
        } else if (flux < 0) {
            this.flux = 0;
        } else {
            this.flux = flux;
        }
        return this.getCurrentFlux();
    }

    @Override
    public float addFlux(float flux) {
        this.setFlux(this.getCurrentFlux() + flux);
        return this.getCurrentFlux();
    }

    @Override
    public float removeFlux(float flux) {
        if (flux < 0) flux = 0;
        this.setFlux(this.getCurrentFlux()- flux);
        return this.getCurrentFlux();
    }

    @Override
    public int addMaxFlux(int flux) {
        this.setMaxFlux(this.getMaxFlux() + flux);
        return this.getMaxFlux();
    }

    @Override
    public int removeMaxFlux(int flux) {
        if (flux < 0) flux = 0;
        this.setMaxFlux(this.getMaxFlux()- flux);
        return this.getMaxFlux();
    }

    @Override
    public int getAspectLevel() {
        return aspectLevel;
    }

    @Override
    public AspectType getAspectType() {
        return aspectType;
    }

    @Override
    public void setAspectLevel(int aspectLevel) {
        this.aspectLevel = aspectLevel;
    }

    @Override
    public void setAspectType(AspectType type) {
        this.aspectType = type;
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
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("flux", getCurrentFlux());
        tag.putInt("maxFlux", getMaxFlux());
        tag.putInt("aspectLevel", getAspectLevel());
        tag.putInt("aspectType", getAspectType().getId());
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
        setFlux(tag.getFloat("flux"));
        setMaxFlux(tag.getInt("maxFlux"));
        setAspectLevel(tag.getInt("aspectLevel"));
        setAspectType(AspectType.getById(tag.getInt("aspectType")));
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
