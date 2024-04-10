package dev.cryptic.aspect.mixin;

import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

@Mixin(PostProcessHandler.class)
public class PostProcessHandlerMixin {

    @Inject(method = "onWorldRenderLast", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void onWorldRenderLastInject(RenderLevelStageEvent event, CallbackInfo ci) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ci.cancel();
            return;
        }

    }

}
