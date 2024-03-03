package dev.cryptic.aspect.api.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.synceddata.SyncedGolemData;
import dev.cryptic.aspect.api.util.AspectSavedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;

public class SoulUI {
    public static final IGuiOverlay OVERLAY = SoulUI::renderOverlay;
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final ResourceLocation SOUL_MAIN = new ResourceLocation(Aspect.MODID, "textures/gui/notballin.png");

    public static void renderOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        if (!shouldRender(player)) return;

        Vec2 center = new Vec2((float) screenWidth / 2, (float) screenHeight / 2);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SOUL_MAIN);
        //guiGraphics.blit(guiGraphics.pose(), (int) center.x - 9, screenHeight - 49, 0, 0, 18, 18, 18, 18);

        // Draw golem icons
        renderGolemIcons(gui, guiGraphics, partialTicks, screenWidth, screenHeight);
    }

    public static void renderGolemIcons(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        if (SyncedGolemData.playerGolemMap == null) return;
        ArrayList<AspectSavedData.GolemData> golemData = SyncedGolemData.playerGolemMap.get(player.getUUID());
        Aspect.LOGGER.info("Golem Data: " + golemData);
        int golemCount = golemData.size();
        golemData.forEach(golem -> {
            int index = golemData.indexOf(golem);
            Vec2 position = generateIconPositions(golemCount, new Vec2((float) screenWidth / 2, (float) screenHeight / 2)).get(index);
            guiGraphics.drawString(minecraft.font, String.valueOf(golem.imbuedSoul()), (int) position.x, (int) position.y, 0xFFFFFFFF);
            Aspect.LOGGER.info("HIIII");
        });

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
