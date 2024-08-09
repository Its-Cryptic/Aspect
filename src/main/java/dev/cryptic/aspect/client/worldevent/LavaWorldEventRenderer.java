package dev.cryptic.aspect.client.worldevent;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.RenderHelper;
import dev.cryptic.aspect.common.worldevent.LavaWorldEvent;
import dev.cryptic.aspect.registry.client.AspectCoreShaders;
import dev.cryptic.aspect.registry.client.AspectObjModels;
import dev.cryptic.aspect.registry.client.AspectRenderType;
import dev.cryptic.encryptedapi.api.vfx.model.Face;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventRenderer;

import java.util.List;

public class LavaWorldEventRenderer extends WorldEventRenderer<LavaWorldEvent> {

    public LavaWorldEventRenderer() {
    }

    @Override
    public void render(LavaWorldEvent instance, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
        //Aspect.LOGGER.info("Rendering LavaWorldEvent");
        float renderTime = instance.tickCount + partialTicks;
        int duration = instance.duration;
        float f = 0.5f*(1.0f - Mth.cos((2.0f*Mth.PI/duration)*renderTime));
        //Aspect.LOGGER.info(""+renderTime/duration);

        poseStack.pushPose();
        poseStack.translate(instance.position.x, instance.position.y, instance.position.z);
        poseStack.scale(f,f,f);

        ExtendedShaderInstance shader = (ExtendedShaderInstance) AspectCoreShaders.FRESNEL.getInstance().get();
        shader.safeGetUniform("Center").set(instance.position.toVector3f());
        shader.safeGetUniform("InvViewMat").set(new Matrix4f(poseStack.last().pose()).invert());
        shader.safeGetUniform("CameraPos").set(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().toVector3f());
        shader.safeGetUniform("Progress") .set(renderTime/duration);

        AspectObjModels.IcoSphereHDModel.faces.forEach(face -> {
            renderTriangle(face, poseStack, bufferSource.getBuffer(AspectRenderType.fresnel(VertexFormat.Mode.TRIANGLES)), LightTexture.FULL_BRIGHT);
        });


        poseStack.popPose();
    }

    @Override
    public boolean canRender(LavaWorldEvent instance) {
        return true;
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
                    .normal(poseStack.last().normal(), normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
    }
}
