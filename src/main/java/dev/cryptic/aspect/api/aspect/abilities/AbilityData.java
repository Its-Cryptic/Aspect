package dev.cryptic.aspect.api.aspect.abilities;

import dev.cryptic.aspect.api.aspect.AspectType;
import dev.cryptic.aspect.api.registry.AbilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public class AbilityData implements INBTSerializable<CompoundTag> {
    private AbstractAbility ability;
    private Player player;
    public Cooldown cooldown;
    public AbilityData(AbstractAbility ability, Player player) {
        this.ability = ability;
        this.player = player;
    }

    public AspectType getAspect() {
        return ability.getAspectType();
    }

    public Player getPlayer() {
        return player;
    }

    public void tick() {
        cooldown.tick();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("ability", ability.getResourceLocation().toString());
        tag.putUUID("caster", player.getUUID());
        tag.put("cooldown", cooldown.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ability = AbilityRegistry.getAbility(nbt.getString("ability"));
        player = Minecraft.getInstance().level.getPlayerByUUID(nbt.getUUID("caster"));
        cooldown.deserializeNBT(nbt.getCompound("cooldown"));
    }

    public class Cooldown implements INBTSerializable<CompoundTag> {
        public int duration;
        public int time;
        public boolean discarded;

        public Cooldown(int duration) {
            this.duration = duration;
        }

        public void tick() {
            time++;
            if (time >= duration) {
                discarded = true;
            }
        }


        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putInt("duration", duration);
            tag.putInt("time", time);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            duration = nbt.getInt("duration");
            time = nbt.getInt("time");
        }
    }
}
