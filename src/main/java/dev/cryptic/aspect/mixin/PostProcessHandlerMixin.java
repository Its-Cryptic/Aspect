package dev.cryptic.aspect.mixin;

import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

import java.util.ArrayList;
import java.util.List;

import static team.lodestar.lodestone.systems.postprocess.PostProcessHandler.copyDepthBuffer;

@Mixin(PostProcessHandler.class)
public class PostProcessHandlerMixin {

    @Final
    @Shadow(remap = false)
    private static final List<PostProcessor> instances = new ArrayList<>();
    @Shadow(remap = false)
    private static boolean didCopyDepth = false;

    /**
     * @author ItsMeCryptic
     * @reason Fixing post-processing & adding
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @Overwrite(remap = false)
    public static void onWorldRenderLast(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;

        copyDepthBuffer();
        PostProcessor.viewModelStack = event.getPoseStack();
        instances.forEach(PostProcessor::applyPostProcess);
        didCopyDepth = false;
    }

}
