package dev.cryptic.aspect.common.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.registry.client.AspectObjModels;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.util.List;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
public class RenderClientEvents {
    public static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");
    private static final ResourceLocation SHIELD = new ResourceLocation(Aspect.MODID, "textures/vfx/shield01.png");

    @SubscribeEvent
    public static void playerQuadRender(RenderLevelStageEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            Camera camera = event.getCamera();
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick();
            int renderTick = event.getRenderTick();
            float cumulativeTicks = renderTick + partialTick;
            Vec3 cameraPos = camera.getPosition();
            Vec3 playerPos = player.position();
            Vec3 playerPosO = new Vec3(player.xOld, player.yOld, player.zOld);
            Vec3 lerpPos = playerPosO.lerp(playerPos, partialTick);
            Vec3 relativePos = lerpPos.subtract(cameraPos);

            float radius = 4.0f;
            float speed = 0.1f;
            Vec3 pathPos = new Vec3(
                    Mth.sin(cumulativeTicks*speed) * radius,
                    0.0F,
                    Mth.cos(cumulativeTicks*speed) * radius
            );

            poseStack.pushPose();
            poseStack.translate(relativePos.x, relativePos.y, relativePos.z);
            poseStack.translate(pathPos.x, pathPos.y, pathPos.z);
            //renderQuad(poseStack, partialTick, 1);
            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void onLevelRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER) return;
        event.getProjectionMatrix();
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            Camera camera = event.getCamera();
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick();
            Vec3 renderPos = new Vec3(0, 10, 0);
            Vec3 cameraPos = camera.getPosition();
            Vec3 relativePos = renderPos.subtract(cameraPos);
            poseStack.pushPose();
            poseStack.translate(relativePos.x, relativePos.y, relativePos.z);
            VertexConsumer vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(SHIELD));
            //renderShield(poseStack, vertexConsumer, event.getRenderTick() + partialTick);
            poseStack.popPose();
        }

    }
    public static void renderQuad(PoseStack poseStack, float partialTicks, float size) {
        float height = 0.0f;
        float width = 1.5f;
        VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(UV_TEST));
        Vector3f[] positions = new Vector3f[] {
                new Vector3f(-width, height, width),
                new Vector3f(width, height, width),
                new Vector3f(width, height, -width),
                new Vector3f(-width, height, -width)
        };
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();

        poseStack.pushPose();

        poseStack.translate(0f, 0.001f, 0f);
        builder.renderQuad(textureConsumer, poseStack, positions, size);
        builder.setPosColorLightmapDefaultFormat();

        poseStack.popPose();
    }

    @SubscribeEvent
    public static void newLevelRendererEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        PoseStack poseStack = event.getPoseStack();
        VertexConsumer vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(UV_TEST));

    }

    public static void renderShield(PoseStack poseStack, VertexConsumer vertexConsumer, float time) {
        //SphereShieldModel.INSTANCE.renderModel(poseStack, vertexConsumer, 255, false);
        poseStack.pushPose();
        float totalScale = (float) ((Math.cos(time*0.1))*0.4);
        poseStack.scale(totalScale, totalScale, totalScale);
        AspectObjModels.SphereShieldModel.faces.forEach(face -> {
            poseStack.pushPose();
            List<Vector3f> normals = face.normals();
            double scale = (1-Math.cos(time*0.1))*0.4;
            //poseStack.translate(normal.x() * scale, normal.y() * scale, normal.z() * scale);
            face.renderTriangle(poseStack, vertexConsumer, 255);
            poseStack.popPose();
        });
        poseStack.popPose();
    }

}
