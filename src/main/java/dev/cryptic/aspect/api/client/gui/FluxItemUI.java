package dev.cryptic.aspect.api.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.item.custom.AbstractFluxItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FluxItemUI {
    public static final IGuiOverlay OVERLAY = FluxItemUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        if (!shouldDisplayBar()) {
            return;
        }

        Player player = minecraft.player;
        if (player == null) return;

        PoseStack poseStack = new PoseStack();

        ItemStack fluxItemStack = player.getMainHandItem().getItem() instanceof AbstractFluxItem ? player.getMainHandItem() : player.getOffhandItem();
        AbstractFluxItem fluxItem = (AbstractFluxItem) fluxItemStack.getItem();
        int currentFlux = fluxItem.getFlux(fluxItemStack);


        int barWidth = 100; // 182
        int barHeight = 5;
        int posX = (screenWidth - barWidth) / 2;
        int posY = screenHeight - 50;


        int filledWidth = (int)((currentFlux / (float) fluxItem.MAX_FLUX) * barWidth);

        // Background Bar
        guiGraphics.fill(posX, posY, posX + barWidth, posY + barHeight, 0xFF555555);

        // Flux Bar
        guiGraphics.fill(posX, posY, posX + filledWidth, posY + barHeight, 0xFF00FF00);

        // Threshold Markers
        int thresholdMinX = posX + (int)((fluxItem.THRESHOLD_MIN_FLUX / (float)fluxItem.MAX_FLUX) * barWidth);
        int safeMaxX = posX + (int)((fluxItem.SAFE_MAX_FLUX / (float)fluxItem.MAX_FLUX) * barWidth);

        guiGraphics.fill(thresholdMinX, posY, thresholdMinX + 1, posY + barHeight, 0xFFFFFF00);
        guiGraphics.fill(safeMaxX, posY, safeMaxX + 1, posY + barHeight, 0xFFFF0000);
    }

    public static boolean shouldDisplayBar() {
        Player player = minecraft.player;
        if (player == null) {
            return false;
        }

        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        return isFluxItem(mainHandItem) || isFluxItem(offHandItem);
    }

    private static boolean isFluxItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof AbstractFluxItem;
    }

}
