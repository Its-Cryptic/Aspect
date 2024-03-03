package dev.cryptic.aspect.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.api.client.shader.post.BerserkRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
