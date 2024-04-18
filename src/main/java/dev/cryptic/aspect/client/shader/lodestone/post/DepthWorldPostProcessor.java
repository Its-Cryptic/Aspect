package dev.cryptic.aspect.client.shader.lodestone.post;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.RenderHelper;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class DepthWorldPostProcessor extends PostProcessor {
    public static final DepthWorldPostProcessor INSTANCE = new DepthWorldPostProcessor();
    static {
        INSTANCE.setActive(false);
    }
    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("depth_world");
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        RenderHelper.applyExtraUniforms(INSTANCE.postChain);
    }

    @Override
    public void afterProcess() {

    }
}
