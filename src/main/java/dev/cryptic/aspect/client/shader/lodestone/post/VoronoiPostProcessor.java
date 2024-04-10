package dev.cryptic.aspect.client.shader.lodestone.post;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class VoronoiPostProcessor extends PostProcessor {
    public static final VoronoiPostProcessor INSTANCE = new VoronoiPostProcessor();

    static {
        INSTANCE.setActive(false);
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("voronoi");
        //return Aspect.id("fisheye");
        //return Aspect.id("world_space_test");
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        RenderHelper.applyExtraUniforms(INSTANCE.postChain);
    }

    @Override
    public void afterProcess() {

    }
}
