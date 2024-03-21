package dev.cryptic.aspect.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.synceddata.SyncedForgeCapData;
import dev.cryptic.aspect.common.misc.obj.MonkeyModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;


public class FluxUI {
    public static final IGuiOverlay OVERLAY = FluxUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final LocalPlayer player = minecraft.player;

    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");

    public static void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        Player player = minecraft.player;
        if (player == null) return;
        //if (true) return;

        float flux = SyncedForgeCapData.getPlayerFlux();
        int maxFlux = SyncedForgeCapData.getPlayerMaxFlux();
        double fluxRegen = SyncedForgeCapData.getPlayerFluxRegen();
        double lerpedFlux = Math.min(flux + fluxRegen * partialTicks, maxFlux);

        double lerpedFluxPercentage = lerpedFlux / maxFlux;
        String fluxString = flux + "/" + maxFlux;
        String lerpedFluxString = lerpedFlux + "/" + maxFlux;
        
        int barWidth = 10;
        int barHeight = 200;
        int posX = 15;
        int posY = (screenHeight/2 - barHeight/2);

        // Background Bar
        guiGraphics.fill(posX, posY - 1, posX + barWidth, posY + barHeight + 1, 0xFF555555);

        // Flux Bar
        guiGraphics.fill(posX+1, (int) (posY + barHeight - (lerpedFluxPercentage * barHeight)), posX + barWidth - 1, posY + barHeight, 0xFF00FF00);

        guiGraphics.drawString(minecraft.font, fluxString, posX + barWidth + 2, posY + barHeight - (int)(lerpedFluxPercentage * barHeight) - 5, 0xFFFFFFFF);


        PoseStack poseStack = guiGraphics.pose();
        MultiBufferSource.BufferSource bufferSource = guiGraphics.bufferSource();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(UV_TEST));
        poseStack.pushPose();
        poseStack.translate(0, 0, 0);
        MonkeyModel.INSTANCE.renderModel(poseStack, vertexConsumer, 255, false);
        poseStack.popPose();

    }
}
