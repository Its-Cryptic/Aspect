package dev.cryptic.aspect.test;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.AspectRenderType;
import dev.cryptic.aspect.client.shader.ShaderRegistry;
import dev.cryptic.aspect.client.shader.lodestone.RenderHelper;
import dev.cryptic.aspect.common.misc.obj.SphereShieldModel;
import dev.cryptic.encryptedapi.api.vfx.model.Face;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.function.Consumer;

import static dev.cryptic.aspect.common.event.RenderClientEvents.UV_TEST;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
public class RenderClientEvents {


    @SubscribeEvent
    public static void onLevelRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            Camera camera = event.getCamera();
            PoseStack poseStack = event.getPoseStack();
            Vec3 renderPos = new Vec3(0, 10, 0);
            Vec3 cameraPos = camera.getPosition();
            Vec3 relativePos = renderPos.subtract(cameraPos);

            //  VertexConsumer vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(AspectRenderType.SHIELD);

            poseStack.pushPose();
            poseStack.translate(relativePos.x, relativePos.y, relativePos.z);
            poseStack.scale(2, 2, 2);
            for (Face face : SphereShieldModel.INSTANCE.faces) {
                VertexConsumer vertexConsumer = RenderHelper.getBufferSource().getBuffer(AspectRenderType.TESTING);
                //vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TEXTURE.applyAndCache(UV_TEST));
                renderTriangle(face, poseStack, vertexConsumer, 255);
                //face.renderFace(poseStack, vertexConsumer, 255);
            }
            poseStack.popPose();
        }
    }



    public static void renderTriangle(Face face, PoseStack poseStack, VertexConsumer buffer, int packedLight) {
        Matrix4f matrix4f = poseStack.last().pose();
        Matrix3f normalMatrix = poseStack.last().normal();
        try (MemoryStack memorystack = MemoryStack.stackPush()) {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());

            List<Vector3f> vertices = face.vertices();
            Vector3f normal = new Vector3f(0.0f, 1.0f, 0.0f);
            List<Vec2> uvs = face.uvs();

            for (int i = 0; i < 3; ++i) {
                //buffer.applyBakedNormals(normal, bytebuffer, normalMatrix);
                //face.addVertex(buffer, matrix4f, vertices.get(i), uvs.get(i), normalMatrix, new Vector3f(0.0f, 1.0f, 0.0f), packedLight);
                Vector3f vertex = vertices.get(i);
                Vec2 uv = uvs.get(i);

                buffer.vertex(matrix4f, vertex.x(), vertex.y(), vertex.z())
                        .uv(uv.x, -uv.y)
                        .color(255, 255, 255, 255)
                        .normal(normalMatrix, normal.x(), normal.y(), normal.z())
                        .overlayCoords(OverlayTexture.NO_OVERLAY)
                        .uv2(packedLight)
                        .endVertex();
            }
            //face.addVertex(buffer, matrix4f, vertices.get(0), uvs.get(0), normalMatrix, new Vector3f(0.0f, 1.0f, 0.0f), packedLight);
            Vector3f vertex = vertices.get(0);
            Vec2 uv = uvs.get(0);

//            buffer.vertex(matrix4f, vertex.x(), vertex.y(), vertex.z())
//                    .uv(uv.x, -uv.y)
//                    .color(255, 255, 255, 255)
//                    .normal(normalMatrix, normal.x(), normal.y(), normal.z())
//                    .overlayCoords(OverlayTexture.NO_OVERLAY)
//                    .uv2(packedLight)
//                    .endVertex();
        }
    }
}
