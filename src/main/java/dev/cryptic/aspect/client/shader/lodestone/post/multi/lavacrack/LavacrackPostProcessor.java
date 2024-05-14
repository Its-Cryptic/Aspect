package dev.cryptic.aspect.client.shader.lodestone.post.multi.lavacrack;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.post.multi.ExtendedMultiInstancePostProcessor;
import net.minecraft.resources.ResourceLocation;

public class LavacrackPostProcessor extends ExtendedMultiInstancePostProcessor<LavacrackFx> {
    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("multi_lavacrack");
    }

    @Override
    protected int getMaxInstances() {
        return 4;
    }

    @Override
    protected int getDataSizePerInstance() {
        return 7;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        super.beforeProcess(viewModelStack);
        setDataBufferUniform(this.effectInstance, "DataBuffer", "InstanceCount");
    }
}
