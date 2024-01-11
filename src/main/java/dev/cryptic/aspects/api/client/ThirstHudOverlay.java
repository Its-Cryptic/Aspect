package dev.cryptic.aspects.api.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cryptic.aspects.Aspect;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ThirstHudOverlay {
    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(Aspect.MODID,
            "textures/thirst/filled_thirst.png");
    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(Aspect.MODID,
            "textures/thirst/empty_thirst.png");

    public static final IGuiOverlay HUD_THIRST = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_THIRST);

        if (gui.shouldDrawSurvivalElements()) {
            for (int i = 0; i < 10; i++) {
                GuiComponent.blit(poseStack, x - 93 + (i * 8), y - 53, 0, 0, 12, 12,
                        12, 12);
            }

            RenderSystem.setShaderTexture(0, FILLED_THIRST);
            for(int i = 0; i < 10; i++) {
                if (ClientThirstData.getPlayerThirst() > i) {
                    GuiComponent.blit(poseStack,x - 93 + (i * 8),y - 53,0,0,12,12,
                            12,12);
                } else {
                    break;
                }
            }
        }
    });

}
