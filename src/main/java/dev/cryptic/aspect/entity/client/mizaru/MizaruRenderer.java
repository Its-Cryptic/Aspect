package dev.cryptic.aspect.entity.client.mizaru;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.util.GolemUtil;
import dev.cryptic.aspect.entity.fluxentity.golem.AbstractGolem;
import dev.cryptic.aspect.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
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

    @Override
    public ResourceLocation getTextureLocation(Mizaru instance) {
        return new ResourceLocation(Aspect.MODID, "textures/entity/chomper_texture.png");
    }

    @Override
    public RenderType getRenderType(Mizaru animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }

    @Override
    public void render(GeoModel model, Mizaru animatable, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        LocalPlayer clientPlayer = Minecraft.getInstance().player;
        UUID clientUUID = clientPlayer.getUUID();
        UUID golemUUID = animatable.getUUID();
        UUID ownerUUID = animatable.getOwnerUUID();
        int imbuedSoul = animatable.getImbuedSoul();
        boolean isOwner = clientUUID.equals(ownerUUID);
        Aspect.LOGGER.info("Client UUID: " + clientUUID);
        Aspect.LOGGER.info("Owner UUID: " + ownerUUID);
        Aspect.LOGGER.info(isOwner ? "Client is owner" : "Client is not owner");
        Aspect.LOGGER.info("Imbued soul: " + imbuedSoul);
        if (clientUUID == ownerUUID) {
            poseStack.scale(1.0f + imbuedSoul / 10.0f, 1.0f + imbuedSoul / 10.0f, 1.0f + imbuedSoul / 10.0f);
        }



        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.renderQuad(poseStack, partialTick);
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

        // Update the accumulated rotation based on the partialTicks
        this.accumulatedRotation += 1 * partialTicks; // Adjust this value for speed
        this.accumulatedRotation %= 360; // Keep the rotation in the range [0, 360)

        // Apply rotation
        poseStack.mulPose(Vector3f.YP.rotationDegrees(this.accumulatedRotation));

        poseStack.translate(0f, 0.001f, 0f);
        builder.renderQuad(textureConsumer, poseStack, positions, 1f);
        builder.setPosColorLightmapDefaultFormat();

        poseStack.popPose();
    }
}
