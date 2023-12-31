package dev.cryptic.aspects.entity.ability.flame.fireblast;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class FireBlastRenderer extends EntityRenderer<FireBlastProjectile> {

    public static final ResourceLocation[] TEXTURES = {
            new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png"),
            new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png"),
            new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png"),
            new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png"),
            new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png"),
    };

    public FireBlastRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FireBlastProjectile entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();


        Vec3 motion = entity.getDeltaMovement();
        float xRot = -((float) (Mth.atan2(motion.horizontalDistance(), motion.y) * (double) (180F / (float) Math.PI)) - 90.0F);
        float yRot = -((float) (Mth.atan2(motion.z, motion.x) * (double) (180F / (float) Math.PI)) + 90.0F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(yRot));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(xRot));
        renderModel(poseStack, bufferSource, entity.getAge());
        poseStack.popPose();

        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    public static void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int animOffset) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.energySwirl(getTextureLocation(animOffset), 0, 0));

        float halfWidth = 2;
        float halfHeight = 1;
        float angleCorrection = 55;
        //Vertical plane
        poseStack.mulPose(Vector3f.XP.rotationDegrees(angleCorrection));
        consumer.vertex(poseMatrix, 0, -halfWidth, -halfHeight).color(255, 255, 255, 255).uv(0f, 1f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, 0, halfWidth, -halfHeight).color(255, 255, 255, 255).uv(0f, 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, 0, halfWidth, halfHeight).color(255, 255, 255, 255).uv(1f, 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, 0, -halfWidth, halfHeight).color(255, 255, 255, 255).uv(1f, 1f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-angleCorrection));

        //Horizontal plane
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-angleCorrection));
        consumer.vertex(poseMatrix, -halfWidth, 0, -halfHeight).color(255, 255, 255, 255).uv(0f, 1f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, halfWidth, 0, -halfHeight).color(255, 255, 255, 255).uv(0f, 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, halfWidth, 0, halfHeight).color(255, 255, 255, 255).uv(1f, 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, -halfWidth, 0, halfHeight).color(255, 255, 255, 255).uv(1f, 1f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(angleCorrection));
    }

    @Override
    public ResourceLocation getTextureLocation(FireBlastProjectile projectile) {
        return getTextureLocation(projectile.getAge());
    }

    public static ResourceLocation getTextureLocation(int offset) {
        float ticksPerFrame = 1f;
        return TEXTURES[(int) (offset / ticksPerFrame) % TEXTURES.length];
    }
}
