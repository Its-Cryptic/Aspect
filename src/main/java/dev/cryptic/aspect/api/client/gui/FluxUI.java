package dev.cryptic.aspect.api.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.synceddata.SyncedForgeCapData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FluxUI extends GuiComponent {
    public static final IGuiOverlay OVERLAY = FluxUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final LocalPlayer player = minecraft.player;

    public static void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight) {
        Player player = minecraft.player;
        if (player == null) return;

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
        fill(poseStack, posX, posY - 1, posX + barWidth, posY + barHeight + 1, 0xFF555555);

        // Flux Bar
        fill(poseStack, posX+1, (int) (posY + barHeight - (lerpedFluxPercentage * barHeight)), posX + barWidth - 1, posY + barHeight, 0xFF00FF00);

        drawString(poseStack, minecraft.font, fluxString, posX + barWidth + 2, posY + barHeight - (int)(lerpedFluxPercentage * barHeight) - 5, 0xFFFFFFFF);
    }
}
