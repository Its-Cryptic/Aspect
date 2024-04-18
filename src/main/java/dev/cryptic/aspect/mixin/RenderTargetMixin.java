package dev.cryptic.aspect.mixin;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.cryptic.aspect.test.IRenderTargetMixin;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderTarget.class)
public class RenderTargetMixin implements IRenderTargetMixin {

    @Shadow
    public int frameBufferId;
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Override
    public void aspects$copyColorFrom(RenderTarget renderTarget) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, renderTarget.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, this.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, renderTarget.width, renderTarget.height, 0, 0, this.width, this.height, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, 0);
    }

}
