package dev.cryptic.aspects.api.client.shader;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.render.RenderHelper;

import java.util.ArrayList;
import java.util.List;

public class ScanRenderer {
    private static final ScanRenderer INSTANCE = new ScanRenderer();
    private Vector3f entityPos;
    private boolean shouldRender;
    private int startTime;
    private int duration;
    private final List<Entity> entitiesToGlow = new ArrayList<>();

    private ScanRenderer() {
    }

    public static ScanRenderer getInstance() {
        return INSTANCE;
    }

    public void setup(Entity entity, int duration) {
        entityPos = new Vector3f(entity.position());
        shouldRender = true;
        startTime = -1;
        this.duration = duration;
        entitiesToGlow.clear();
        entitiesToGlow.addAll(entity.level.getEntities(entity, entity.getBoundingBox().inflate(30)));
    }

    public void stop() {
        shouldRender = false;
    }

    public void renderScan(PoseStack poseStack, int renderTick, float partialTicks, Camera camera, RenderTarget target) {
        if (!shouldRender) return;

        if (startTime == -1) startTime = renderTick;
        int time = renderTick - startTime;
        if(time > duration) {
            stop();
            return;
        }

        ShaderInstance shader = AspectShaders.getScanShader();
        if (shader == null) return;

        int width = target.width;
        int height = target.height;

        Matrix4f invViewMatrix = new Matrix4f(poseStack.last().pose());
        invViewMatrix.invert();

        Matrix4f invProjectionMatrix = new Matrix4f(poseStack.last().pose());
        invProjectionMatrix.invert();

        Vec3 cameraPos = camera.getPosition();

        shader.setSampler("DepthBuffer", target::getDepthTextureId);
        shader.safeGetUniform("InvViewMatrix").set(invViewMatrix);
        shader.safeGetUniform("InvProjectionMatrix").set(invProjectionMatrix);
        shader.safeGetUniform("CameraPos").set(new Vector3f(cameraPos));
        shader.safeGetUniform("Center").set(entityPos);
        shader.safeGetUniform("Radius").set((time + partialTicks) * 1.5f);

        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(() -> shader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.backupProjectionMatrix();
        //sets the current projection matrix so that screen coords go from (0,0) to (width, height)
        RenderSystem.setProjectionMatrix(Matrix4f.orthographic(0, width, 0, height, 1, 100));
    }

    public boolean shouldRenderGlowingEntiy(Entity entity) {
        if(!(entity instanceof LivingEntity)) return false;
        return shouldRender && entitiesToGlow.contains(entity);
    }
}
