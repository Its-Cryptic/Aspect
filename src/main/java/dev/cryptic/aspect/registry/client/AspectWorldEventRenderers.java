package dev.cryptic.aspect.registry.client;

import com.mojang.datafixers.util.Pair;
import dev.cryptic.aspect.client.worldevent.LavaWorldEventRenderer;
import dev.cryptic.aspect.client.worldevent.ability.StormWorldEventRenderer;
import dev.cryptic.aspect.registry.common.AspectWorldEventTypes;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.lodestar.lodestone.registry.client.LodestoneWorldEventRendererRegistry;
import team.lodestar.lodestone.systems.worldevent.WorldEventRenderer;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

import java.util.ArrayList;
import java.util.List;

public class AspectWorldEventRenderers {
    private static final List<WorldEventRenderHolder> renderers = new ArrayList<>();
    public static final WorldEventRenderHolder LAVA_FISSURE_RENDERER = register(AspectWorldEventTypes.LAVA, new LavaWorldEventRenderer());
    public static final WorldEventRenderHolder STORM_RENDERER = register(AspectWorldEventTypes.STORM, new StormWorldEventRenderer());

    private static WorldEventRenderHolder register(WorldEventType type, WorldEventRenderer<?> renderer) {
        WorldEventRenderHolder pair = new WorldEventRenderHolder(type, renderer);
        renderers.add(pair);
        return pair;
    }
    public static void init(FMLClientSetupEvent event) {
        renderers.forEach(pair -> LodestoneWorldEventRendererRegistry.registerRenderer(pair.type, pair.renderer));
    }

    public record WorldEventRenderHolder(WorldEventType type, WorldEventRenderer<?> renderer) {
    }
}
