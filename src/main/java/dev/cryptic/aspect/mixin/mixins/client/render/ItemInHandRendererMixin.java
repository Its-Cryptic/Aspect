package dev.cryptic.aspect.mixin.mixins.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Final
    @Shadow private Minecraft minecraft;

    @Final
    @Shadow private EntityRenderDispatcher entityRenderDispatcher;

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void renderPlayerArm(PoseStack p_109347_, MultiBufferSource p_109348_, int p_109349_, float p_109350_, float p_109351_, HumanoidArm p_109352_) {
        boolean flag = p_109352_ != HumanoidArm.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = Mth.sqrt(p_109351_);
        float f2 = -0.3F * Mth.sin(f1 * (float)Math.PI);
        float f3 = 0.4F * Mth.sin(f1 * ((float)Math.PI * 2F));
        float f4 = -0.4F * Mth.sin(p_109351_ * (float)Math.PI);
        p_109347_.translate(f * (f2 + 0.64000005F), f3 + -0.6F + p_109350_ * -0.6F, f4 + -0.71999997F);
        p_109347_.mulPose(Axis.YP.rotationDegrees(f * 45.0F));
        float f5 = Mth.sin(p_109351_ * p_109351_ * (float)Math.PI);
        float f6 = Mth.sin(f1 * (float)Math.PI);
        p_109347_.mulPose(Axis.YP.rotationDegrees(f * f6 * 70.0F));
        p_109347_.mulPose(Axis.ZP.rotationDegrees(f * f5 * -20.0F));
        AbstractClientPlayer abstractclientplayer = this.minecraft.player;
        RenderSystem.setShaderTexture(0, abstractclientplayer.getSkinTextureLocation());
        p_109347_.translate(f * -1.0F, 3.6F, 3.5F);
        p_109347_.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
        p_109347_.mulPose(Axis.XP.rotationDegrees(200.0F));
        p_109347_.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
        p_109347_.translate(f * 5.6F, 0.0F,  0.0F);
        PlayerRenderer playerrenderer = (PlayerRenderer)this.entityRenderDispatcher.<AbstractClientPlayer>getRenderer(abstractclientplayer);
        if (flag) {
            playerrenderer.renderRightHand(p_109347_, p_109348_, p_109349_, abstractclientplayer);
        } else {
            playerrenderer.renderLeftHand(p_109347_, p_109348_, p_109349_, abstractclientplayer);
        }

    }

}
