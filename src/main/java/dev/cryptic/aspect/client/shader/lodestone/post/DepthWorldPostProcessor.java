package dev.cryptic.aspect.client.shader.lodestone.post;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
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
