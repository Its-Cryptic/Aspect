package dev.cryptic.aspect.blockentities.fluxcore;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.shader.AspectRenderType;
import dev.cryptic.aspect.misc.obj.IcoSphereModel;
import dev.cryptic.aspect.misc.obj.MonkeyModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.registry.client.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry.createGenericRenderType;

public class FluxCoreRenderer extends GeoBlockRenderer<FluxCoreBlockEntity> {
    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");
    private static final ResourceLocation EMOJI = new ResourceLocation(Aspect.MODID, "textures/vfx/wh.png");
    private static Vec3 lastPosVector;
    public static final RenderTypeProvider ADDITIVE_TEXTURE_TRIANGLE = new RenderTypeProvider((texture) -> createGenericRenderType(Aspect.MODID, "additive_texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, LodestoneShaderRegistry.LODESTONE_TEXTURE.getShard(), StateShards.NORMAL_TRANSPARENCY, texture));

    public FluxCoreRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new FluxCoreModel());
    }


    @Override
    public void render(GeoModel model, FluxCoreBlockEntity animatable, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        if (bufferSource != null) {
            VertexConsumer vertexConsumer = bufferSource.getBuffer(LodestoneRenderTypeRegistry.TEXTURE.applyAndCache(UV_TEST));

            float time = animatable.getLevel().getGameTime() + partialTick;
            Vec3 renderPos = new Vec3(0.0, 2.5, 0.0);
            float scale1 = 0.5f;
            poseStack.pushPose();
            poseStack.translate(renderPos.x, renderPos.y, renderPos.z);
            poseStack.scale(scale1, scale1, scale1);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(time * 2.0f));

            //MonkeyModel.INSTANCE.renderModel(poseStack, vertexConsumer);
//            MonkeyModel.INSTANCE.faces.forEach(face -> {
//                poseStack.pushPose();
//                Vector3f normal = face.normal();
//                //Vector3f orginToFaceCenter = Vector3f.ZERO.copy().add(face.getCenter());
//                double scale = (1-Math.cos(time*0.1))*0.4;
//                poseStack.translate(normal.x() * scale, normal.y() * scale, normal.z() * scale);
//                face.renderTriangle(poseStack, vertexConsumer, 255, false);
//                poseStack.popPose();
//            });
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
    }


    public void renderSphere(PoseStack poseStack, MultiBufferSource bufferSource) {
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        //VertexConsumer textureConsumer = RenderHandler.DELAYED_RENDER.getBuffer(ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(UV_TEST));
        VertexConsumer textureConsumer = bufferSource.getBuffer(renderType());
        poseStack.pushPose();
        builder.renderSphere(textureConsumer, poseStack, 2, 15, 15);
        poseStack.popPose();
    }


    protected RenderType renderType() {
        return AspectRenderType.aspectTest();
    }
}

