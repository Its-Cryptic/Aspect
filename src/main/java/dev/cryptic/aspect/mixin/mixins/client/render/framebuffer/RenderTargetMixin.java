package dev.cryptic.aspect.mixin.mixins.client.render.framebuffer;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.mixin.ducks.IRenderTargetMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30C.GL_RGBA16F;

@Mixin(RenderTarget.class)
public abstract class RenderTargetMixin implements IRenderTargetMixin {

    /**
     * Allows copying the color buffer from another render target to this render target using a duck interface
     * @param renderTarget the render target to copy the color buffer from
     */
    @Override
    public void aspect$copyColorFrom(RenderTarget renderTarget) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, renderTarget.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, this.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, renderTarget.width, renderTarget.height, 0, 0, this.width, this.height, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, 0);
    }

//    /**
//     * @author Cryptic
//     * @reason Use GL_RGBA16F instead of GL_RGBA8 for the color texture REPLACE ASAP
//     */
//    @Overwrite
//    public void createBuffers(int p_83951_, int p_83952_, boolean p_83953_) {
//        Aspect.LOGGER.info("Creating buffers");
//        RenderSystem.assertOnRenderThreadOrInit();
//        int i = RenderSystem.maxSupportedTextureSize();
//        if (p_83951_ > 0 && p_83951_ <= i && p_83952_ > 0 && p_83952_ <= i) {
//            this.viewWidth = p_83951_;
//            this.viewHeight = p_83952_;
//            this.width = p_83951_;
//            this.height = p_83952_;
//            this.frameBufferId = GlStateManager.glGenFramebuffers();
//            this.colorTextureId = TextureUtil.generateTextureId();
//            if (this.useDepth) {
//                this.depthBufferId = TextureUtil.generateTextureId();
//                GlStateManager._bindTexture(this.depthBufferId);
//                GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_FILTER, GlConst.GL_NEAREST);
//                GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAG_FILTER, GlConst.GL_NEAREST);
//                GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_COMPARE_MODE, 0);
//                GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_WRAP_S, GlConst.GL_CLAMP_TO_EDGE);
//                GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_WRAP_T, GlConst.GL_CLAMP_TO_EDGE);
//                if (!stencilEnabled)
//                    GlStateManager._texImage2D(GlConst.GL_TEXTURE_2D, 0, GlConst.GL_DEPTH_COMPONENT, this.width, this.height, 0, GlConst.GL_DEPTH_COMPONENT, GlConst.GL_FLOAT, (IntBuffer)null);
//                else
//                    GlStateManager._texImage2D(GlConst.GL_TEXTURE_2D, 0, org.lwjgl.opengl.GL30.GL_DEPTH32F_STENCIL8, this.width, this.height, 0, org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL, org.lwjgl.opengl.GL30.GL_FLOAT_32_UNSIGNED_INT_24_8_REV, null);
//            }
//
//            this.setFilterMode(GlConst.GL_NEAREST);
//            GlStateManager._bindTexture(this.colorTextureId);
//            GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_WRAP_S, GlConst.GL_CLAMP_TO_EDGE);
//            GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_WRAP_T, GlConst.GL_CLAMP_TO_EDGE);
//            //GlStateManager._texImage2D(GlConst.GL_TEXTURE_2D, 0, GlConst.GL_RGBA8, this.width, this.height, 0, GlConst.GL_RGBA, GlConst.GL_UNSIGNED_BYTE, (IntBuffer)null);
//            GlStateManager._texImage2D(GlConst.GL_TEXTURE_2D, 0, GL_RGBA16F, this.width, this.height, 0, GlConst.GL_RGBA, GlConst.GL_FLOAT, (IntBuffer)null);
//            GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, this.frameBufferId);
//            GlStateManager._glFramebufferTexture2D(GlConst.GL_FRAMEBUFFER, GlConst.GL_COLOR_ATTACHMENT0, GlConst.GL_TEXTURE_2D, this.colorTextureId, 0);
//            if (this.useDepth) {
//                if(!stencilEnabled)
//                    GlStateManager._glFramebufferTexture2D(GlConst.GL_FRAMEBUFFER, GlConst.GL_DEPTH_ATTACHMENT, GlConst.GL_TEXTURE_2D, this.depthBufferId, 0);
//                else if(net.minecraftforge.common.ForgeConfig.CLIENT.useCombinedDepthStencilAttachment.get()) {
//                    GlStateManager._glFramebufferTexture2D(org.lwjgl.opengl.GL30.GL_FRAMEBUFFER, org.lwjgl.opengl.GL30.GL_DEPTH_STENCIL_ATTACHMENT, GlConst.GL_TEXTURE_2D, this.depthBufferId, 0);
//                } else {
//                    GlStateManager._glFramebufferTexture2D(org.lwjgl.opengl.GL30.GL_FRAMEBUFFER, org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT, GlConst.GL_TEXTURE_2D, this.depthBufferId, 0);
//                    GlStateManager._glFramebufferTexture2D(org.lwjgl.opengl.GL30.GL_FRAMEBUFFER, org.lwjgl.opengl.GL30.GL_STENCIL_ATTACHMENT, GlConst.GL_TEXTURE_2D, this.depthBufferId, 0);
//                }
//            }
//
//            this.checkStatus();
//            this.clear(p_83953_);
//            this.unbindRead();
//        } else {
//            throw new IllegalArgumentException("Window " + p_83951_ + "x" + p_83952_ + " size out of bounds (max. size: " + i + ")");
//        }
//    }

    @Redirect(method = "createBuffers",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/platform/GlStateManager;_texImage2D(IIIIIIIILjava/nio/IntBuffer;)V",
                    ordinal = 2
            )
    )
    private void onCreateBuffers(int p_84210_, int p_84211_, int p_84212_, int p_84213_, int p_84214_, int p_84215_, int p_84216_, int p_84217_, IntBuffer p_84218_) {
        //GlStateManager._texImage2D(GlConst.GL_TEXTURE_2D, 0, GlConst.GL_RGBA8, this.width, this.height, 0, GlConst.GL_RGBA, GlConst.GL_UNSIGNED_BYTE, (IntBuffer)null);
        GlStateManager._texImage2D(GlConst.GL_TEXTURE_2D, 0, GL_RGBA16F, this.width, this.height, 0, GlConst.GL_RGBA, GlConst.GL_FLOAT, (IntBuffer)null);
    }

    @Shadow public int width;
    @Shadow public int height;
    @Shadow public int viewWidth;
    @Shadow public int viewHeight;
    @Shadow @Final public boolean useDepth;
    @Shadow public int frameBufferId;
    @Shadow protected int colorTextureId;
    @Shadow protected int depthBufferId;
    @Shadow private boolean stencilEnabled;

    @Shadow public abstract void setFilterMode(int p_83937_);
    @Shadow public abstract void checkStatus();
    @Shadow public abstract void clear(boolean p_83939_);
    @Shadow public abstract void unbindRead();
}
