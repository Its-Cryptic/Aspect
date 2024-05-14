package dev.cryptic.aspect.client.shader.lodestone.post.single;

import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;

public class DepthWorldPostProcessor extends ExtendedPostProcessor {
    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("depth_world");
    }

    @Override
    public void afterProcess() {

    }
}
