package dev.cryptic.aspect.mixin.mixins.client.render;

import dev.cryptic.aspect.client.shader.lodestone.post.multi.ExtendedMultiInstancePostProcessor;
import dev.cryptic.aspect.client.shader.lodestone.post.single.ExtendedPostProcessor;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.spongepowered.asm.mixin.*;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

import java.util.ArrayList;
import java.util.List;

import static team.lodestar.lodestone.systems.postprocess.PostProcessHandler.copyDepthBuffer;

@Mixin(PostProcessHandler.class)
public class PostProcessHandlerMixin {

    /**
     * @author
     * @reason
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public static void onWorldRenderLast(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            // Copy Extra Depth Buffer after cutout blocks for each extended post processor
            instances.stream()
                    .filter(ExtendedPostProcessor.class::isInstance)
                    .map(ExtendedPostProcessor.class::cast)
                    .forEach(ExtendedPostProcessor::copyExtraDepth);
            instances.stream()
                    .filter(ExtendedMultiInstancePostProcessor.class::isInstance)
                    .map(ExtendedMultiInstancePostProcessor.class::cast)
                    .forEach(ExtendedMultiInstancePostProcessor::copyCutoutDepth);
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            // Copy Extra Depth Buffer after block entities for each extended post processor
            instances.stream()
                    .filter(ExtendedMultiInstancePostProcessor.class::isInstance)
                    .map(ExtendedMultiInstancePostProcessor.class::cast)
                    .forEach(ExtendedMultiInstancePostProcessor::copyBlockEntityDepth);
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            // Copy Extra Depth Buffer after translucent blocks for each extended post processor
            instances.stream()
                    .filter(ExtendedMultiInstancePostProcessor.class::isInstance)
                    .map(ExtendedMultiInstancePostProcessor.class::cast)
                    .forEach(ExtendedMultiInstancePostProcessor::copyTransparent);
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            // Copies Correct PoseStack for each post processor
            PostProcessor.viewModelStack = event.getPoseStack();
        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            copyDepthBuffer();
            //PostProcessor.viewModelStack = event.getPoseStack();
            instances.forEach(PostProcessor::applyPostProcess);
            didCopyDepth = false;
        }
    }

    @Shadow(remap = false)
    @Final
    private static final List<PostProcessor> instances = new ArrayList();
    @Shadow(remap = false)
    private static boolean didCopyDepth = false;


}
