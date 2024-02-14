package dev.cryptic.aspect.api.client.shader.lodestone.post;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class MyLodestonePostProcessor extends PostProcessor {
    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.resourceLocation("lode_test");
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {

    }

    @Override
    public void afterProcess() {

    }
}
