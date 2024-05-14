package dev.cryptic.aspect.client.shader.lodestone.post.single;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.client.shader.RenderHelper;
import net.minecraft.client.Minecraft;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public abstract class ExtendedPostProcessor extends PostProcessor {
    //private RenderTarget tempRenderTarget = new TextureTarget(MC.getMainRenderTarget().width, MC.getMainRenderTarget().height, true, Minecraft.ON_OSX);
    private RenderTarget cutoutDepthBuffer;

    public final void copyExtraDepth() {
        if (this.isActive()) {
            if (this.postChain == null || this.cutoutDepthBuffer == null) {
                return;
            }

            this.cutoutDepthBuffer.copyDepthFrom(MC.getMainRenderTarget());
            GlStateManager._glBindFramebuffer(36009, MC.getMainRenderTarget().frameBufferId);
        }
    }

    @Override
    public void init() {
        super.init();
        if (this.postChain != null) {
            this.cutoutDepthBuffer = this.postChain.getTempTarget("depthCutout");
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (this.postChain != null) {
            this.postChain.resize(width, height);
            if (this.cutoutDepthBuffer != null) {
                this.cutoutDepthBuffer.resize(width, height, Minecraft.ON_OSX);
            }
        }
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        RenderHelper.applyExtraUniforms(this.postChain);
    }
}
