package dev.cryptic.aspect.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Unique
    private void aspect$renderExtras(PoseStack poseStack, Camera camera, float v) {

    }
}
