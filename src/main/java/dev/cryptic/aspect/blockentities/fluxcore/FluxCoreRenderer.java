package dev.cryptic.aspect.blockentities.fluxcore;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.shader.AspectRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry.createGenericRenderType;

public class FluxCoreRenderer extends GeoBlockRenderer<FluxCoreBlockEntity> {
    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/9.png");
    private static Vec3 lastPosVector;
    public static final RenderTypeProvider ADDITIVE_TEXTURE_TRIANGLE = new RenderTypeProvider((texture) -> createGenericRenderType(Aspect.MODID, "additive_texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.TRIANGLES, LodestoneShaderRegistry.TRIANGLE_TEXTURE.getShard(), StateShards.NORMAL_TRANSPARENCY, texture));

    public FluxCoreRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new FluxCoreModel());
    }

    private float scale(float time) {
        return Mth.sin(time * 0.1f) * 2.0f+2.5f;
    }

    @Override
    public void render(GeoModel model, FluxCoreBlockEntity animatable, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //renderSphere(poseStack, bufferSource);
        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.pushPose();
        poseStack.scale(2,2,2);
        renderCube(animatable, poseStack, bufferSource.getBuffer(renderType()));
        poseStack.popPose();
    }


    public void renderSphere(PoseStack poseStack, MultiBufferSource bufferSource) {
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(UV_TEST));
        poseStack.pushPose();
        builder.renderSphere(textureConsumer, poseStack, 2, 15, 15);
        poseStack.popPose();
    }

    public void renderCube(FluxCoreBlockEntity animatable, PoseStack poseStack, VertexConsumer vertexConsumer) {
        poseStack.pushPose();
        poseStack.translate(-0.5, 2, -0.5);
        Matrix4f matrix4f = poseStack.last().pose();
        float f = this.getOffsetDown();
        float f1 = this.getOffsetUp();

        this.renderFace(animatable, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
        this.renderFace(animatable, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        this.renderFace(animatable, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
        this.renderFace(animatable, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
        this.renderFace(animatable, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderFace(animatable, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
        poseStack.popPose();
    }

    private void renderFace(FluxCoreBlockEntity p_173695_, Matrix4f matrix4f, VertexConsumer buffer, float p_173698_, float p_173699_, float p_173700_, float p_173701_, float p_173702_, float p_173703_, float p_173704_, float p_173705_, Direction direction) {
        buffer.vertex(matrix4f, p_173698_, p_173700_, p_173702_).endVertex();
        buffer.vertex(matrix4f, p_173699_, p_173700_, p_173703_).endVertex();
        buffer.vertex(matrix4f, p_173699_, p_173701_, p_173704_).endVertex();
        buffer.vertex(matrix4f, p_173698_, p_173701_, p_173705_).endVertex();
    }

    protected float getOffsetUp() {
        return 0.75F;
    }

    protected float getOffsetDown() {
        return 0.375F;
    }

    protected RenderType renderType() {
        return AspectRenderType.aspectTest();
    }

}
