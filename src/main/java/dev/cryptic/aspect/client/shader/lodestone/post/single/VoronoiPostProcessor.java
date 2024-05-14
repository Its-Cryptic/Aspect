package dev.cryptic.aspect.client.shader.lodestone.post.single;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.RenderHelper;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class VoronoiPostProcessor extends ExtendedPostProcessor {


    @Override
    public ResourceLocation getPostChainLocation() {
        return Aspect.id("voronoi");
        //return Aspect.id("depth_world");
        //return Aspect.id("fisheye");
        //return Aspect.id("world_space_test");
    }

    @Override
    public void beforeProcess(PoseStack poseStack) {
        RenderHelper.applyExtraUniforms(this.postChain);
    }

    @Override
    public void afterProcess() {

    }
}
