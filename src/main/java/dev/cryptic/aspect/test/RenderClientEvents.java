package dev.cryptic.aspect.test;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.AspectRenderType;
import dev.cryptic.aspect.client.shader.ShaderRegistry;
import dev.cryptic.aspect.client.shader.RenderHelper;
import dev.cryptic.aspect.common.misc.obj.IcoSphereHDModel;
import dev.cryptic.aspect.mixin.ducks.IRenderTargetMixin;
import dev.cryptic.encryptedapi.api.vfx.model.Face;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

import java.util.List;

import static com.mojang.blaze3d.platform.GlConst.GL_DRAW_FRAMEBUFFER;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
public class RenderClientEvents {

    private static final Minecraft minecraft = Minecraft.getInstance();

    private static RenderTarget tempRenderTarget = new TextureTarget(minecraft.getMainRenderTarget().width, minecraft.getMainRenderTarget().height, true, Minecraft.ON_OSX);

    @SubscribeEvent
    public static void onLevelRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            Camera camera = event.getCamera();
            PoseStack poseStack = event.getPoseStack();
            Vec3 playerPos = player.position();
            Vec3 playerPosOld = new Vec3(player.xOld, player.yOld, player.zOld);
            Vec3 renderPos = new Vec3(0, 15, 0);
            //renderPos = playerPosOld.lerp(playerPos, event.getPartialTick()).add(0, 1.5, 0);
            Vec3 cameraPos = camera.getPosition();
            Vec3 relativePos = renderPos.subtract(cameraPos);

            float radius = 1.0f;


            Matrix4f ModelViewMat = new Matrix4f(RenderHelper.getModelViewStack().last().pose());

            ExtendedShaderInstance shader = (ExtendedShaderInstance) ShaderRegistry.SHIELD.getInstance().get();
            shader.safeGetUniform("lookVector").set(renderPos.subtract(cameraPos).normalize().toVector3f());
            //shader.safeGetUniform("lookVector").set(minecraft.options.getCameraType().isMirrored() ? camera.getLookVector().normalize() : camera.getLookVector().normalize().negate());

            shader.safeGetUniform("CameraPosition").set(cameraPos.toVector3f());
            shader.safeGetUniform("ObjectPosition").set(renderPos.toVector3f());
            shader.safeGetUniform("SphereRadius").set(radius*2);

            shader.safeGetUniform("RenderTime").set(RenderHelper.getRenderTime());
            shader.safeGetUniform("ModelViewMatrix").set(ModelViewMat);
            shader.safeGetUniform("InvViewMat").set(new Matrix4f(RenderHelper.modelViewStack.last().pose()).invert());

            copyBuffers();
            shader.setSampler("DepthBuffer", tempRenderTarget.getDepthTextureId());
            shader.setSampler("ColorBuffer", tempRenderTarget.getColorTextureId());

            poseStack.pushPose();
            poseStack.translate(relativePos.x, relativePos.y, relativePos.z);
            poseStack.scale(radius, radius, radius);
            for (Face face : IcoSphereHDModel.INSTANCE.faces) {
                VertexConsumer vertexConsumer = RenderHelper.getBufferSource().getBuffer(AspectRenderType.TESTING);

                renderTriangle(face, poseStack, vertexConsumer, OverlayTexture.NO_OVERLAY);
            }
            poseStack.popPose();
        }
    }



    public static void renderTriangle(Face face, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight) {
        List<Vector3f> vertices = face.vertices();
        List<Vector3f> normals = face.normals();
        normals.forEach(Vector3f::normalize);

        List<Vec2> uvs = face.uvs();

        for (int i = 0; i < 3; ++i) {
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            vertexConsumer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
    }

    public static void renderWireframeTriangle(Face face, PoseStack poseStack, int packedLight) {
        VertexConsumer buffer = RenderHelper.getBufferSource().getBuffer(AspectRenderType.LINES);
        List<Vector3f> vertices = face.vertices();
        List<Vector3f> normals = face.normals();
        normals.forEach(Vector3f::normalize);

        List<Vec2> uvs = face.uvs();

        poseStack.pushPose();
        poseStack.scale(1.05f, 1.05f, 1.05f);
        for (int i = 0; i < 2; ++i) {
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            buffer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
        for (int i = 1; i < 3; ++i) {
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            buffer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
        for (int i = 0; i < 2; ++i) {
            i += i;
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            buffer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
        poseStack.popPose();
    }

    public static void mixinPoseStackTransformations(PoseStack poseStack) {
        poseStack.translate(0.2, 2.5, 0.8);
        poseStack.mulPose(Axis.ZP.rotationDegrees(5.0f*RenderHelper.getRenderTime()));
        poseStack.mulPose(Axis.XP.rotationDegrees(4.0f*RenderHelper.getRenderTime()));
        //poseStack.mulPose(Axis.YP.rotationDegrees(RenderHelper.getRenderTime()));
        poseStack.scale(0.45f, 0.45f, 0.45f);
    }
    public static void mixinPoseStackTransformations2(PoseStack poseStack) {
        float f = 0.05f*(-1+Mth.sin(0.1f*RenderHelper.getRenderTime()));
        poseStack.translate(0, f, 0);
    }

    public static void copyBuffers() {
        if (tempRenderTarget == null) return;
        RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();
        tempRenderTarget.copyDepthFrom(mainRenderTarget);
        ((IRenderTargetMixin) tempRenderTarget).aspects$copyColorFrom(mainRenderTarget);

        GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, mainRenderTarget.frameBufferId);
    }

    public static final ResourceLocation UV_GRID = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");


//    @SubscribeEvent
//    public static void renderLevelStageEvent(RenderLevelStageEvent event) {
//        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
//        Minecraft minecraft = Minecraft.getInstance();
//        LocalPlayer player = minecraft.player;
//        if (player != null) {
//            Camera camera = event.getCamera();
//            PoseStack poseStack = event.getPoseStack();
//            Vec3 renderPos = new Vec3(0, 25, 0);
//            Vec3 cameraPos = camera.getPosition();
//            Vec3 relativePos = renderPos.subtract(cameraPos);
//
//            float radius = 3.0f;
//
//            poseStack.pushPose();
//            poseStack.translate(relativePos.x, relativePos.y, relativePos.z);
//
//            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld();
//            VertexConsumer vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(UV_GRID));
//            builder.renderSphere(vertexConsumer, poseStack, radius, 20, 20);
//            poseStack.popPose();
//        }
//    }
}
