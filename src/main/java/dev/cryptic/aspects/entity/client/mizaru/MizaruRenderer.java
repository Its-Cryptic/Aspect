package dev.cryptic.aspects.entity.client.mizaru;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.cryptic.aspects.Aspect;
import dev.cryptic.aspects.entity.client.RenderData;
import dev.cryptic.aspects.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import dev.cryptic.aspects.misc.CircularBufferTracker;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

public class MizaruRenderer extends GeoEntityRenderer<Mizaru> {
    Logger LOGGER = Aspect.LOGGER;
    Vec3 lastTickPos;
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
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }

    @Override
    public void render(GeoModel model, Mizaru animatable, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        Vec3 currentPos = animatable.position();

        // Initialize lastTickPos in the first render call or after the entity respawns
        if (this.lastTickPos == null) this.lastTickPos = currentPos;

        Vec3 interpolatedPos = this.lastTickPos.lerp(currentPos, partialTick);
        Vec3 ghostPos = new Vec3(140, 120, -59);
        Vec3 relativeGhostPos = ghostPos.subtract(interpolatedPos);

        poseStack.pushPose();
        poseStack.translate(relativeGhostPos.x(), relativeGhostPos.y(), relativeGhostPos.z());

        // Render Ghost Entity
        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();

        // Render Main Entity using the original poseStack
        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        // Update lastTickPos after rendering is done
        this.lastTickPos = currentPos;
    }

    @Override
    public boolean shouldRender(Mizaru p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }
}
