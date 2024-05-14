package dev.cryptic.aspect.registry.common;

import dev.cryptic.aspect.common.worldevent.LavaWorldEvent;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypeRegistry;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AspectWorldEventTypes {
    public static final List<WorldEventType> EVENT_TYPES = new ArrayList<>();

    public static final WorldEventType LAVA = LodestoneWorldEventTypeRegistry.registerEventType(new WorldEventType("lava", LavaWorldEvent::new));


    public static WorldEventType register(WorldEventType eventType) {
        WorldEventType event = LodestoneWorldEventTypeRegistry.registerEventType(eventType);
        EVENT_TYPES.add(event);
        return event;
    }


}
