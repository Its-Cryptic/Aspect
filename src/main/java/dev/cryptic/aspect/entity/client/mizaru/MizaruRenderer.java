package dev.cryptic.aspect.entity.client.mizaru;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.shader.AspectRenderType;
import dev.cryptic.aspect.api.flux.AspectType;
import dev.cryptic.aspect.api.util.GolemUtil;
import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import dev.cryptic.aspect.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import dev.cryptic.aspect.misc.obj.IcoSphereModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.slf4j.Logger;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.util.UUID;

public class MizaruRenderer extends GeoEntityRenderer<Mizaru> {
    Logger LOGGER = Aspect.LOGGER;
    Vec3 lastTickPos;
    public MizaruRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MizaruModel());
        this.shadowRadius = 0.6f;
    }

    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");
    private static final ResourceLocation EMOJI = new ResourceLocation(Aspect.MODID, "textures/vfx/wh.png");

    @Override
    public ResourceLocation getTextureLocation(Mizaru instance) {
        return new ResourceLocation(Aspect.MODID, "textures/entity/chomper_texture.png");
    }

    @Override
    public RenderType getRenderType(Mizaru animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        if (!isLost(animatable)) return AspectRenderType.aspectTest();
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void render(Mizaru entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (entity == null) return;
        LocalPlayer clientPlayer = Minecraft.getInstance().player;
        UUID clientUUID = clientPlayer.getUUID();
        UUID golemUUID = entity.getUUID();
        UUID ownerUUID = entity.getOwnerUUID();
        int imbuedSoul = entity.getImbuedSoul();
        boolean isOwner = clientUUID.equals(ownerUUID);

        if (clientUUID == ownerUUID) {
            poseStack.scale(1.0f + imbuedSoul / 10.0f, 1.0f + imbuedSoul / 10.0f, 1.0f + imbuedSoul / 10.0f);
        }



        // Max packed light is 255
        //this.renderQuad(poseStack, partialTick);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(LodestoneRenderTypeRegistry.TEXTURE.applyAndCache(EMOJI));
        //this.renderIsoSphere(poseStack, entity.level().getGameTime() + partialTick, vertexConsumer);
        poseStack.pushPose();
        poseStack.translate(0.0, 3, 0.0);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        IcoSphereModel.INSTANCE.renderModel(poseStack, vertexConsumer, 255, false);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public boolean shouldRender(Mizaru p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    private float accumulatedRotation = 0.0f;
    public void renderQuad(PoseStack poseStack, float partialTicks) {
        float height = 0.0f;
        float width = 1.5f;
        VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(UV_TEST));
        Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();

        poseStack.pushPose();

        this.accumulatedRotation += 1 * partialTicks;
        this.accumulatedRotation %= 360;

        Vector3f rotationAxis = new Vector3f(1.0F, 0.0F, 1.0F);
        rotationAxis = new Vector3f(rotationAxis.z(), rotationAxis.y(), -rotationAxis.x());
        rotationAxis.normalize();

        Quaternionf rotationQuaternion = new Quaternionf();
        rotationQuaternion.rotateAxis((float) Math.toRadians(accumulatedRotation), rotationAxis.x(), rotationAxis.y(), rotationAxis.z());

        poseStack.mulPose(rotationQuaternion);
        poseStack.translate(0f, 0.001f, 0f);
        builder.renderQuad(textureConsumer, poseStack, positions, 1f);

        // Render again but 180 degrees rotated to fix backface culling now broken :(
//        Quaternion additionalRotation = new Quaternion(rotationAxis, 180.0f, true);
//        poseStack.mulPose(additionalRotation);
//        builder.renderQuad(textureConsumer, poseStack, positions, 1f);

        builder.setPosColorLightmapDefaultFormat();

        poseStack.popPose();
    }

    public void renderIsoSphere(PoseStack poseStack, float time, VertexConsumer vertexConsumer) {
        Vec3 renderPos = new Vec3(0.0, 2.5, 0.0);
        float scale1 = 0.5f;
        poseStack.pushPose();
        poseStack.translate(renderPos.x, renderPos.y, renderPos.z);
        poseStack.scale(scale1, scale1, scale1);
        poseStack.mulPose(Axis.YP.rotationDegrees(time * 2.0f));

        IcoSphereModel.INSTANCE.faces.forEach(face -> {
            poseStack.pushPose();
            Vector3f normal = face.normal();
            //Vector3f orginToFaceCenter = Vector3f.ZERO.copy().add(face.getCenter());
            double scale = (1-Math.cos(time*0.1))*0.4;
            poseStack.translate(normal.x() * scale, normal.y() * scale, normal.z() * scale);
            face.renderTriangle(poseStack, vertexConsumer, 255, false);
            poseStack.popPose();
        });
        poseStack.popPose();
    }

    private boolean isLost(Mizaru animatable) {
        return true;
    }
}
