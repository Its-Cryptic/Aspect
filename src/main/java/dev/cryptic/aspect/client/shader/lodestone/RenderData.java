package dev.cryptic.aspect.client.shader.lodestone;

import com.mojang.blaze3d.vertex.PoseStack;

public class RenderData {
    public static PoseStack modelViewStack;
    public static float partialTick;
    public static int renderTick;

    public static void updateModelViewStack(PoseStack poseStack) {
        RenderData.modelViewStack = poseStack;
    }

    public static PoseStack getModelViewStack() {
        return modelViewStack;
    }

    public static void updatePartialTick(float partialTick) {
        RenderData.partialTick = partialTick;
    }

    public static float getPartialTick() {
        return partialTick;
    }

    public static void updateRenderTick(int renderTick) {
        RenderData.renderTick = renderTick;
    }

    public static int getRenderTick() {
        return renderTick;
    }
}
