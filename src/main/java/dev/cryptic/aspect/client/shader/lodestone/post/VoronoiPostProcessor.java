package dev.cryptic.aspect.client.shader.lodestone.post;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.RenderData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class VoronoiPostProcessor extends PostProcessor {
    public static final VoronoiPostProcessor INSTANCE = new VoronoiPostProcessor();

    static {
        INSTANCE.setActive(false);
    }

    public static VoronoiPostProcessor getInstance() {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("fisheye");
        //return Aspect.id("world_space_test");
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        Matrix4f invModelViewMat = (new Matrix4f(RenderData.getModelViewStack().last().pose())).invert();
        float time = Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getFrameTime();

        PostChain postChain = INSTANCE.postChain;
        for (PostPass pass : postChain.passes) {
            EffectInstance shader = pass.getEffect();
            shader.safeGetUniform("InvModelViewMat").set(invModelViewMat);
            shader.safeGetUniform("time").set(time);
        }
    }

    @Override
    public void afterProcess() {

    }
}
