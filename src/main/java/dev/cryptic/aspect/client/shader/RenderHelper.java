package dev.cryptic.aspect.client.shader;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RenderHelper {
    public static PoseStack viewStack;

    public static void updateViewStack(PoseStack poseStack) {
        RenderHelper.viewStack = poseStack;
    }
    public static PoseStack getViewStack() {
        return viewStack;
    }

    public static void applyExtraUniforms(PostChain postChain) {
        Matrix4f ViewMat = new Matrix4f(RenderHelper.getViewStack().last().pose());
        Matrix4f InvViewMat = (new Matrix4f(RenderHelper.getViewStack().last().pose())).invert();
        Vector3f playerPos = Minecraft.getInstance().player.position().toVector3f();
        float time = RenderHelper.getRenderTime();
        for (PostPass pass : postChain.passes) {
            EffectInstance shader = pass.getEffect();
            shader.safeGetUniform("ModelViewMat").set(ViewMat);
            shader.safeGetUniform("InvModelViewMat").set(InvViewMat);
            shader.safeGetUniform("playerPos").set(playerPos);
            shader.safeGetUniform("time").set(time);
        }
    }

    public static void applyExtraUniforms(EffectInstance shader) {
        Matrix4f ViewMat = new Matrix4f(RenderHelper.getViewStack().last().pose());
        Matrix4f InvViewMat = (new Matrix4f(RenderHelper.getViewStack().last().pose())).invert();
        Vector3f playerPos = Minecraft.getInstance().player.position().toVector3f();
        float time = RenderHelper.getRenderTime();
        shader.safeGetUniform("ModelViewMat").set(ViewMat);
        shader.safeGetUniform("InvModelViewMat").set(InvViewMat);
        shader.safeGetUniform("playerPos").set(playerPos);
        shader.safeGetUniform("time").set(time);
    }

    public static float getRenderTime() {
        if (Minecraft.getInstance().level == null) {
            return 0;
        }
        return Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getFrameTime();
    }

    public static MultiBufferSource getBufferSource() {
        return Minecraft.getInstance().renderBuffers().bufferSource();
    }
}
