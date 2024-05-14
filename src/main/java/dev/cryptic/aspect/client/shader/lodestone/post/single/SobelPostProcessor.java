package dev.cryptic.aspect.client.shader.lodestone.post.single;

import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;

public class SobelPostProcessor extends ExtendedPostProcessor {
    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("sobel");
    }

    @Override
    public void afterProcess() {

    }


}
