package dev.cryptic.aspect.api.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SoulUI extends GuiComponent {
    public static final IGuiOverlay OVERLAY = SoulUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight) {
        if (!shouldRender()) return;
        Player player = minecraft.player;
        if (player == null) return;


    }

    public static boolean shouldRender() {
        return true;
    }

}
