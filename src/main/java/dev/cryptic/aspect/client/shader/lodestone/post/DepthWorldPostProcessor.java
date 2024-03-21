package dev.cryptic.aspect.client.shader.lodestone.post;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
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
        Matrix4f invViewMat = new Matrix4f(viewModelStack.last().pose());
        invViewMat.invert();
        Matrix4f invProjMat = new Matrix4f(RenderSystem.getProjectionMatrix());
        invProjMat.invert();
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        ResourceLocation UV_TEST = Aspect.id("textures/vfx/uv_test.png");
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.bindForSetup(UV_TEST);
        int textureId = textureManager.getTexture(UV_TEST).getId();

        PostChain postChain = INSTANCE.postChain;
        for (PostPass pass : postChain.passes) {
            EffectInstance shader = pass.getEffect();
            shader.safeGetUniform("Color").set(new Vector3f(1.0f, 1.0f, 1.0f));
            shader.safeGetUniform("InvViewMat").set(invViewMat);
            shader.safeGetUniform("InvProjMat").set(invProjMat);
            shader.safeGetUniform("CameraPos").set(cameraPos.toVector3f());
            shader.setSampler("ImageSampler", () -> textureId);
        }
    }

    @Override
    public void afterProcess() {

    }
}
