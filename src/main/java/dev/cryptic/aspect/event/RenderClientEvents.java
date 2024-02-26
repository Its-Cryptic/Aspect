package dev.cryptic.aspect.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
public class RenderClientEvents {
    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            event.getRenderer().getTextureLocation(player);
        }
    }

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

            renderQuad(poseStack, partialTick, 1);
            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void onLevelRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) return;
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            Camera camera = event.getCamera();
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick();
            Vec3 renderPos = new Vec3(0, 100,0);
            Vec3 relativePos = renderPos.subtract(camera.getPosition());

            poseStack.pushPose();
            poseStack.translate(relativePos.x, relativePos.y, relativePos.z);
            renderQuad(poseStack, partialTick, 1);
            poseStack.popPose();
        }
    }
    public static void renderQuad(PoseStack poseStack, float partialTicks, float size) {
        float height = 0.0f;
        float width = 1.5f;
        VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(UV_TEST));
        Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();

        poseStack.pushPose();

        poseStack.translate(0f, 0.001f, 0f);
        builder.renderQuad(textureConsumer, poseStack, positions, size);
        builder.setPosColorLightmapDefaultFormat();

        poseStack.popPose();
    }
}
