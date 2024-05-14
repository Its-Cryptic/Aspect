package dev.cryptic.aspect.registry.client;

import com.mojang.datafixers.util.Pair;
import dev.cryptic.aspect.client.worldevent.LavaWorldEventRenderer;
import dev.cryptic.aspect.registry.common.AspectWorldEventTypes;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.lodestar.lodestone.registry.client.LodestoneWorldEventRendererRegistry;
import team.lodestar.lodestone.systems.worldevent.WorldEventRenderer;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

import java.util.ArrayList;
import java.util.List;

public class AspectWorldEventRenderers {
    private static final List<Pair<WorldEventType, WorldEventRenderer<?>>> renderers = new ArrayList<>();
    public static final Pair<WorldEventType, WorldEventRenderer<?>> LAVA_FISSURE_RENDERER = register(AspectWorldEventTypes.LAVA, new LavaWorldEventRenderer());


    private static Pair<WorldEventType, WorldEventRenderer<?>> register(WorldEventType type, WorldEventRenderer<?> renderer) {
        Pair<WorldEventType, WorldEventRenderer<?>> pair = new Pair<>(type, renderer);
        renderers.add(pair);
        return pair;
    }
    public static void init(FMLClientSetupEvent event) {
        renderers.forEach(pair -> LodestoneWorldEventRendererRegistry.registerRenderer(pair.getFirst(), pair.getSecond()));
    }
}
