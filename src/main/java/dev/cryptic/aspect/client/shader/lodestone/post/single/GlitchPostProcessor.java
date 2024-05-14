package dev.cryptic.aspect.client.shader.lodestone.post.single;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.RenderHelper;
import net.minecraft.resources.ResourceLocation;

public class GlitchPostProcessor extends ExtendedPostProcessor {


    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("glitch");
    }

    @Override
    public void beforeProcess(PoseStack poseStack) {
        RenderHelper.applyExtraUniforms(this.postChain);
    }

    @Override
    public void afterProcess() {

    }
}
