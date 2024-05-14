package dev.cryptic.aspect.client.shader.lodestone.post.multi;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.mixin.ducks.IRenderTargetMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;
import team.lodestar.lodestone.systems.postprocess.MultiInstancePostProcessor;

public abstract class ExtendedMultiInstancePostProcessor<I extends DynamicShaderFxInstance> extends MultiInstancePostProcessor<I> {
    protected EffectInstance effectInstance;
    protected RenderTarget cutoutRenderTarget, blockEntityRenderTarget, translucentRenderTarget;
    @Override
    public abstract ResourceLocation getPostChainLocation();

    @Override
    protected abstract int getMaxInstances();

    @Override
    protected abstract int getDataSizePerInstance();


    public final void copyCutoutDepth() {
        if (this.isActive()) {
            if (this.postChain == null || this.cutoutRenderTarget == null) return;

            this.cutoutRenderTarget.copyDepthFrom(MC.getMainRenderTarget());
            GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, MC.getMainRenderTarget().frameBufferId);
        }
    }

    public final void copyBlockEntityDepth() {
        if (this.isActive()) {
            if (this.postChain == null || this.blockEntityRenderTarget == null) return;

            this.blockEntityRenderTarget.copyDepthFrom(MC.getMainRenderTarget());
            ((IRenderTargetMixin) (Object) blockEntityRenderTarget).aspect$copyColorFrom(MC.getMainRenderTarget());
            GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, MC.getMainRenderTarget().frameBufferId);
        }
    }

    public final void copyTransparent() {
        if (this.isActive()) {
            if (this.postChain == null || this.translucentRenderTarget == null) return;

            this.translucentRenderTarget.copyDepthFrom(MC.getMainRenderTarget());
            ((IRenderTargetMixin) (Object) translucentRenderTarget).aspect$copyColorFrom(MC.getMainRenderTarget());
            GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, MC.getMainRenderTarget().frameBufferId);
        }
    }


    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];

            cutoutRenderTarget = this.postChain.getTempTarget("depthCutout");
            blockEntityRenderTarget = this.postChain.getTempTarget("depthBlockEntity");
            translucentRenderTarget = this.postChain.getTempTarget("depthTranslucent");
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (this.postChain != null) {
            this.postChain.resize(width, height);
            if (this.cutoutRenderTarget != null) this.cutoutRenderTarget.resize(width, height, Minecraft.ON_OSX);
            if (this.blockEntityRenderTarget != null) this.blockEntityRenderTarget.resize(width, height, Minecraft.ON_OSX);
            if (this.translucentRenderTarget != null) this.translucentRenderTarget.resize(width, height, Minecraft.ON_OSX);
        }
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        postChain.passes.forEach(pass -> pass.getEffect().setSampler("BEDiffuseSampler", () -> blockEntityRenderTarget.getColorTextureId()));
        postChain.passes.forEach(pass -> pass.getEffect().setSampler("TDiffuseSampler", () -> translucentRenderTarget.getColorTextureId()));
        super.beforeProcess(viewModelStack);
    }

    @Override
    public void afterProcess() {

    }
}
