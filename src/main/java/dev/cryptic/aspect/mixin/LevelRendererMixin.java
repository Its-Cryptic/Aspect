package dev.cryptic.aspect.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.client.shader.lodestone.RenderHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.*;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "renderLevel", at = @At(value = "HEAD"))
    private void aspects$getModelViewStack(PoseStack modelViewStack, float p_109601_, long p_109602_, boolean p_109603_, Camera p_109604_, GameRenderer p_109605_, LightTexture p_109606_, Matrix4f p_254120_, CallbackInfo ci) {
        RenderHelper.updateModelViewStack(modelViewStack);
    }

}
