package dev.cryptic.aspect.client.shader.lodestone.post;

import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.postprocess.MultiInstancePostProcessor;

public class TestMultiInstancePostProcessor extends MultiInstancePostProcessor<TestDynamicShaderFxInstance> {
    public static final TestMultiInstancePostProcessor INSTANCE = new TestMultiInstancePostProcessor();
    private TestMultiInstancePostProcessor() {
    }
    @Override
    protected int getMaxInstances() {
        return 5;
    }

    @Override
    protected int getDataSizePerInstance() {
        return 3;
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("lode_test");
    }

    @Override
    public void afterProcess() {

    }
}
