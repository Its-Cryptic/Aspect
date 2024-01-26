package dev.cryptic.aspect.api.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.misc.SyncedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FluxUI extends GuiComponent {
    public static final IGuiOverlay OVERLAY = FluxUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight) {
        Player player = minecraft.player;
        if (player == null) return;

        float flux = SyncedData.getPlayerData(player).getFlux();
        int maxFlux = SyncedData.getPlayerData(player).getMaxFlux();
        float fluxPercentage = flux / maxFlux;
        String fluxString = flux + "/" + maxFlux;


        int barWidth = 10;
        int barHeight = 100;
        int posX = 15;
        int posY = screenHeight/2 - barHeight/2;

        // Background Bar
        fill(poseStack, posX, posY - 1, posX + barWidth, posY + barHeight + 1, 0xFF555555);

        // Flux Bar
        fill(poseStack, posX+1, posY + barHeight - (int)(fluxPercentage * barHeight), posX + barWidth - 1, posY + barHeight, 0xFF00FF00);

        drawString(poseStack, minecraft.font, fluxString, posX + barWidth + 2, posY + barHeight - (int)(fluxPercentage * barHeight) - 5, 0xFFFFFFFF);
    }
}
