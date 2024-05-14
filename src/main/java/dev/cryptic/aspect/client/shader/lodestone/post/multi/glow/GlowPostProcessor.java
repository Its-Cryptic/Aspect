package dev.cryptic.aspect.client.shader.lodestone.post.multi.glow;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.RenderHelper;
import dev.cryptic.aspect.client.shader.lodestone.post.multi.ExtendedMultiInstancePostProcessor;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;

public class GlowPostProcessor extends ExtendedMultiInstancePostProcessor<LightingFx> {
    private EffectInstance effectGlow;
    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("glow");
    }

    @Override
    protected int getMaxInstances() {
        return 64;
    }

    // Number of uniforms in fx instance. Ex: Writer integers 0 -> 6 would be 7 uniforms
    @Override
    protected int getDataSizePerInstance() {
        return 11;
    }

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectGlow = effects[0];
        }
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        super.beforeProcess(viewModelStack);
        setDataBufferUniform(effectGlow, "DataBuffer", "InstanceCount");
        RenderHelper.applyExtraUniforms(this.postChain);
        this.postChain.passes.forEach(pass -> pass.getEffect().safeGetUniform("DataSize").set(getDataSizePerInstance()));
    }
}
