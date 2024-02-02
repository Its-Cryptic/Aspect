package dev.cryptic.aspect.api.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.SyncedGolemData;
import dev.cryptic.aspect.api.util.AspectSavedData;
import dev.cryptic.aspect.misc.SyncedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;

public class SoulUI extends GuiComponent {
    public static final IGuiOverlay OVERLAY = SoulUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final ResourceLocation SOUL_MAIN = new ResourceLocation(Aspect.MODID, "textures/gui/notballin.png");

    public static void renderOverlay(ForgeGui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        if (!shouldRender(player)) return;

        Vec2 center = new Vec2((float) screenWidth / 2, (float) screenHeight / 2);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SOUL_MAIN);
        blit(poseStack, (int) center.x - 9, screenHeight - 49, 0, 0, 18, 18, 18, 18);

        // Draw golem icons
        renderGolemIcons(gui, poseStack, partialTicks, screenWidth, screenHeight);
    }

    public static void renderGolemIcons(ForgeGui gui, PoseStack poseStack, float partialTicks, int screenWidth, int screenHeight) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        ArrayList<AspectSavedData.GolemData> golemData = SyncedGolemData.playerGolemMap.get(player.getUUID());
        int golemCount = golemData.size();


    }

    public static ArrayList<Vec2> generateIconPositions(int golemCount, Vec2 center) {
        ArrayList<Vec2> positions = new ArrayList<>();
        for (int i = 0; i < golemCount; i++) {
            float x = center.x - 9 + (i * 18);
            float y = center.y - 9;
            positions.add(new Vec2(x, y));
        }
        return positions;
    }

    public static boolean shouldRender(Player player) {
        return !player.isCreative();
    }

}
