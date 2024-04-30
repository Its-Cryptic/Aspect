package dev.cryptic.aspect.mixin.ducks;

import com.mojang.blaze3d.pipeline.RenderTarget;

public interface IRenderTargetMixin {

    /**
     * Allows copying the color buffer from another render target to this render target using a duck interface
     * @param renderTarget the render target to copy the color buffer from
     */
    public void aspects$copyColorFrom(RenderTarget renderTarget);
}
