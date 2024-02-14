package dev.cryptic.aspect.api.client.shader;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MagmaShaderRenderer {
    private static final MagmaShaderRenderer INSTANCE = new MagmaShaderRenderer();
    private Vector3f entityPos;
    private boolean shouldRender;
    private int startTime;
    private int duration;
    private final List<Entity> entitiesToGlow = new ArrayList<>();

    private MagmaShaderRenderer() {
    }

    public static MagmaShaderRenderer getInstance() {
        return INSTANCE;
    }

    public void setup(Entity entity, int duration) {
        this.entityPos = new Vector3f(entity.position());
        this.shouldRender = true;
        this.startTime = -1;
        this.duration = duration;
        entitiesToGlow.clear();
        entitiesToGlow.addAll(entity.getLevel().getEntities(entity, entity.getBoundingBox().inflate(30)));
    }

    public void stop() {
        this.shouldRender = false;
    }

    public void renderMagmaShader(PoseStack poseStack, int renderTick, float partialTick, Camera camera, RenderTarget target) {
        if(!shouldRender) return;

        if(startTime == -1) startTime = renderTick;
        int time = renderTick - startTime;
        if(time > duration) {
            stop();
            return;
        }
        ShaderInstance shader = AspectCoreShaders.getMagmaShader();
        Aspect.LOGGER.info("Shader: " + shader);
        if (shader == null) return;

        int width = target.width;
        int height = target.height;

        Matrix4f invViewMat = new Matrix4f(poseStack.last().pose());
        invViewMat.invert();
        Matrix4f invProjMat = new Matrix4f(RenderSystem.getProjectionMatrix());
        invProjMat.invert();
        Vec3 cameraPos = camera.getPosition();

        shader.setSampler("DepthBuffer", target.getDepthTextureId());
        shader.safeGetUniform("InvViewMat").set(invViewMat);
        shader.safeGetUniform("InvProjMat").set(invProjMat);
        shader.safeGetUniform("CameraPos").set(new Vector3f(cameraPos));
        shader.safeGetUniform("Center").set(entityPos);
        shader.safeGetUniform("Radius").set((time + partialTick) * 1.5f);
        Aspect.LOGGER.info("Center: " + entityPos);
        Aspect.LOGGER.info("Radius: " + ((time + partialTick) * 1.5f));

        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(() -> shader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.backupProjectionMatrix();

        RenderSystem.setProjectionMatrix(Matrix4f.orthographic(0, width, 0, height, 1, 100 ));

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(width, 0, -50).uv(1, 0).endVertex();
        buffer.vertex(width, height, -50).uv(1, 1).endVertex();
        buffer.vertex(0, height, -50).uv(0, 1).endVertex();
        buffer.vertex(0, 0, -50).uv(0, 0).endVertex();
        tesselator.end();

        //reset
        RenderSystem.restoreProjectionMatrix();
        RenderSystem.setShader(() -> oldShader);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();

    }

    public boolean shouldRenderGlowingEntiy(Entity entity) {
        if(!(entity instanceof LivingEntity)) return false;
        return shouldRender && entitiesToGlow.contains(entity);
    }



}
