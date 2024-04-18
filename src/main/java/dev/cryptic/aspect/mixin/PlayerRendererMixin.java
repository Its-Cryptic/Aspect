package dev.cryptic.aspect.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cryptic.aspect.client.shader.AspectRenderType;
import dev.cryptic.aspect.client.shader.ShaderRegistry;
import dev.cryptic.aspect.common.misc.obj.IcoSphereModel;
import dev.cryptic.aspect.common.misc.obj.SphereShieldModel;
import dev.cryptic.aspect.test.RenderClientEvents;
import dev.cryptic.encryptedapi.api.vfx.model.Face;
import dev.cryptic.encryptedapi.api.vfx.model.models.MonkeyModel;
import dev.cryptic.encryptedapi.api.vfx.sprite.VFXSpriteLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

import java.util.List;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {


    public PlayerRendererMixin(EntityRendererProvider.Context p_174289_, PlayerModel<AbstractClientPlayer> p_174290_, float p_174291_) {
        super(p_174289_, p_174290_, p_174291_);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void renderHand(PoseStack poseStack, MultiBufferSource bufferSource, int f, AbstractClientPlayer player, ModelPart armModel, ModelPart sleeveModel) {
        PlayerModel<AbstractClientPlayer> playermodel = this.getModel();
        this.setModelProperties(player);
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        armModel.xRot = 0.0F;
        armModel.render(poseStack, bufferSource.getBuffer(RenderType.endPortal()), f, OverlayTexture.NO_OVERLAY);
        poseStack.pushPose();
        RenderClientEvents.mixinPoseStackTransformations2(poseStack);
        sleeveModel.render(poseStack, bufferSource.getBuffer(AspectRenderType.LINES), f, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        poseStack.pushPose();
        RenderClientEvents.mixinPoseStackTransformations(poseStack);
        ExtendedShaderInstance shader = (ExtendedShaderInstance) ShaderRegistry.SHIELD.getInstance().get();
        shader.safeGetUniform("lookVector").set(Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector());
        IcoSphereModel.INSTANCE.faces.forEach(face -> aspects$renderTriangleLines(face, poseStack, bufferSource.getBuffer(AspectRenderType.LINES), f));
        poseStack.scale(0.6f, 0.6f, 0.6f);
        //MonkeyModel.INSTANCE.faces.forEach(face -> aspects$renderTriangle(face, poseStack, bufferSource.getBuffer(AspectRenderType.TRIANGLE_SPHERE_RENDERTYPE.applyAndCache(VFXSpriteLibrary.Misc.UV_GRID)), f));
        poseStack.popPose();

        sleeveModel.xRot = 0.0F;
        sleeveModel.render(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(player.getSkinTextureLocation())), f, OverlayTexture.NO_OVERLAY);
    }

    @Shadow
    protected abstract void setModelProperties(AbstractClientPlayer p117779);

    @Unique
    private static void aspects$renderTriangleLines(Face face, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight) {
        List<Vector3f> vertices = face.vertices();
        List<Vector3f> normals = face.normals();
        normals.forEach(Vector3f::normalize);

        List<Vec2> uvs = face.uvs();

        for (int i = 0; i < 2; ++i) {
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            vertexConsumer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
        for (int i = 1; i < 3; ++i) {
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            vertexConsumer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
        for (int i = 0; i < 2; ++i) {
            i += i;
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            vertexConsumer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
    }

    @Unique
    private static void aspects$renderTriangle(Face face, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight) {
        List<Vector3f> vertices = face.vertices();
        List<Vector3f> normals = face.normals();
        normals.forEach(Vector3f::normalize);

        List<Vec2> uvs = face.uvs();

        for (int i = 0; i < 3; ++i) {
            Vector3f vertex = vertices.get(i);
            Vec2 uv = uvs.get(i);
            Vector3f normal = normals.get(i);

            vertexConsumer.vertex(poseStack.last().pose(), vertex.x(), vertex.y(), vertex.z())
                    .color(255, 255, 255, 255)
                    .uv(uv.x, -uv.y)
                    .uv2(packedLight)
                    .normal(normal.x(), normal.y(), normal.z())
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .endVertex();
        }
    }

}
