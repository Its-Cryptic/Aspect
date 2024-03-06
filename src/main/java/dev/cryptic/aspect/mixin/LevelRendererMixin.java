package dev.cryptic.aspect.mixin;

import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

//    @Shadow
//    @Final
//    private RenderBuffers renderBuffers;
//    @Shadow @Final private Minecraft minecraft;
//    @Shadow private PostChain entityEffect;
//    @Shadow private int ticks;
//
//    @Shadow @Nullable
//    private PostChain transparencyChain;
//    @Unique
//    private RenderTarget depthRenderTarget;

//    @Inject(method = "renderLevel", at = @At("TAIL"))
//    private void aspects$renderLevelPost(PoseStack pPoseStack, float pPartialTick, long pFinishNanoTime, boolean pRenderBlockOutline, Camera pCamera, GameRenderer pGameRenderer, LightTexture pLightTexture, Matrix4f pProjectionMatrix, CallbackInfo ci) {
//        //copies *back* the depth buffer
//        if(transparencyChain != null) {
//            minecraft.getMainRenderTarget().copyDepthFrom(depthRenderTarget);
//        }
//
//        //renders all post shaders
//        BerserkRenderer.getInstance().render(pPoseStack, ticks, pPartialTick);
//        minecraft.getMainRenderTarget().bindWrite(false);
//    }

}
