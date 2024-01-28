package dev.cryptic.aspect.api.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.misc.SyncedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SoulUI extends GuiComponent {
    public static final IGuiOverlay OVERLAY = SoulUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();

    private static final ResourceLocation SOUL_MAIN = new ResourceLocation(Aspect.MODID, "textures/gui/notballin.png");

    public static void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight) {
        Player player = minecraft.player;
        if (player == null) return;
        if (!shouldRender(player)) return;

        Vec2 center = new Vec2((float) screenWidth / 2, (float) screenHeight / 2);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SOUL_MAIN);
        blit(poseStack, (int) center.x - 9, screenHeight - 49, 0, 0, 18, 18, 18, 18);

    }

    public static boolean shouldRender(Player player) {
        return !player.isCreative();
    }

}
