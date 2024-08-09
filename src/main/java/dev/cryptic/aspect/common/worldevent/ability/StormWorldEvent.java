package dev.cryptic.aspect.common.worldevent.ability;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.post.multi.glow.LightingFx;
import dev.cryptic.aspect.registry.client.AspectPostProcessors;
import dev.cryptic.aspect.registry.common.AspectWorldEventTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class StormWorldEvent extends WorldEventInstance {
    public StormWorldEvent() {
        super(AspectWorldEventTypes.STORM);
    }

    @Override
    public void tick(Level level) {
        if (!level.isClientSide()) {

        }
    }
}
