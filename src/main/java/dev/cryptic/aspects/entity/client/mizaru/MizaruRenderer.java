package dev.cryptic.aspects.entity.client.mizaru;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.cryptic.aspects.Aspect;
import dev.cryptic.aspects.entity.client.SomethingData;
import dev.cryptic.aspects.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import dev.cryptic.aspects.misc.CircularBufferTracker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

public class MizaruRenderer extends GeoEntityRenderer<Mizaru> {

    CircularBufferTracker<SomethingData> positionHistory = new CircularBufferTracker<>(20*20);
    public MizaruRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MizaruModel());
        this.shadowRadius = 0.6f;
    }

    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");

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


    @Override
    public ResourceLocation getTextureLocation(Mizaru instance) {
        return new ResourceLocation(Aspect.MODID, "textures/entity/chomper_texture.png");
    }

    @Override
    public RenderType getRenderType(Mizaru animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {

        poseStack.scale(1.0f, 1.0f, 1.0f);
        renderQuad(poseStack, partialTick);

        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }

    @Override
    public void render(Mizaru animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Vec3 entityPos = animatable.position();
        SomethingData data = new SomethingData(entityPos, poseStack);

        positionHistory.recordValue(data);
        if (positionHistory.getValue(20*15) == null) return;

        Vec3 ghostPos = positionHistory.getValue(20*15).getPosition();
        if (ghostPos == null) ghostPos = entityPos;

        Vec3 relativeGhostPos = ghostPos.subtract(entityPos);

        poseStack.pushPose();
        poseStack.translate(relativeGhostPos.x(), relativeGhostPos.y(), relativeGhostPos.z());

        super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        poseStack.popPose();

        // Render Original using the original poseStack
        super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
