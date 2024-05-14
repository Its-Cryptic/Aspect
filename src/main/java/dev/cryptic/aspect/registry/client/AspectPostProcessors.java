package dev.cryptic.aspect.registry.client;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.post.multi.glow.GlowPostProcessor;
import dev.cryptic.aspect.client.shader.lodestone.post.single.*;
import team.lodestar.lodestone.systems.postprocess.MultiInstancePostProcessor;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

import java.util.ArrayList;
import java.util.List;

public class AspectPostProcessors {
    private static final List<PostProcessor> POST_PROCESSORS = new ArrayList<>();
    private static final List<MultiInstancePostProcessor<?>> MULTI_INSTANCE_POST_PROCESSORS = new ArrayList<>();

    // Single instance post processors
    public static final DepthWorldPostProcessor DEPTH_WORLD = register(new DepthWorldPostProcessor());
    public static final SobelPostProcessor SOBEL = register(new SobelPostProcessor());
    public static final VoronoiPostProcessor VORONOI = register(new VoronoiPostProcessor());
    public static final GlitchPostProcessor GLITCH = register(new GlitchPostProcessor());

    // Multi instance post processors
    public static final GlowPostProcessor GLOW = register(new GlowPostProcessor());

    private static <T extends PostProcessor> T register(T postProcessor) {
        POST_PROCESSORS.add(postProcessor);
        return postProcessor;
    }

    private static <T extends MultiInstancePostProcessor<?>> T register(T postProcessor) {
        MULTI_INSTANCE_POST_PROCESSORS.add(postProcessor);
        return postProcessor;
    }

    public static void init() {
        POST_PROCESSORS.forEach(postProcessor -> postProcessor.setActive(false));
        POST_PROCESSORS.forEach(PostProcessHandler::addInstance);
        MULTI_INSTANCE_POST_PROCESSORS.forEach(PostProcessHandler::addInstance);

        int totalPostProcessors = POST_PROCESSORS.size() + MULTI_INSTANCE_POST_PROCESSORS.size();
        Aspect.LOGGER.info("Registered " + totalPostProcessors + " post processors");
    }

}
