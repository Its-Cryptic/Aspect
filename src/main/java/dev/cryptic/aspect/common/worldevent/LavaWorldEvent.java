package dev.cryptic.aspect.common.worldevent;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.post.multi.glow.LightingFx;
import dev.cryptic.aspect.registry.client.AspectPostProcessors;
import dev.cryptic.aspect.registry.common.AspectWorldEventTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class LavaWorldEvent extends WorldEventInstance {

    public Vec3 position;
    public int radius;
    public int duration;
    public int tickCount;

    private LightingFx lightingFx;

    public LavaWorldEvent(Vec3 position, int radius, int duration) {
        super(AspectWorldEventTypes.LAVA);
        this.position = position;
        this.radius = radius;
        this.duration = duration;
    }

    public LavaWorldEvent() {
        super(AspectWorldEventTypes.LAVA);
    }


    @Override
    public CompoundTag serializeNBT(CompoundTag tag) {
        tag.putDouble("x", position.x);
        tag.putDouble("y", position.y);
        tag.putDouble("z", position.z);
        tag.putInt("radius", radius);
        tag.putInt("duration", duration);
        tag.putInt("tickCount", tickCount);
        return super.serializeNBT(tag);
    }

    @Override
    public WorldEventInstance deserializeNBT(CompoundTag tag) {
        LavaWorldEvent lavaWorldEvent = new LavaWorldEvent(
                new Vec3(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z")),
                tag.getInt("radius"),
                tag.getInt("duration")
        );
        lavaWorldEvent.tickCount = tag.getInt("tickCount");
        return lavaWorldEvent;
    }

    @Override
    public void tick(Level level) {

        if (level instanceof ClientLevel clientLevel) {
            if (lightingFx == null) {
                Vector3f color = new Vector3f(0.4f, 1.0f, 0.2f);
                lightingFx = new LightingFx(position.toVector3f(), color, radius, 10.0f, true, true, false);
                AspectPostProcessors.GLOW.addFxInstance(lightingFx);
                Aspect.LOGGER.info("Added LightingFx instance");
            }
        }
        tickCount++; // Tick on both sides
        //Aspect.LOGGER.info("Ticking LavaWorldEvent on {}! Tick Count: {}", level.isClientSide ? "client" : "server", tickCount);

        if (tickCount >= duration) {
            end(level);
            Aspect.LOGGER.info("Ended LavaWorldEvent");
        }
    }

    @Override
    public void end(Level level) {

        if (level instanceof ClientLevel clientLevel) {
            lightingFx.remove();
            lightingFx = null;
            Aspect.LOGGER.info("Removed LightingFx instance");
        }
        super.end(level);
    }

    @Override
    public boolean isClientSynced() {
        return true;
    }

}
