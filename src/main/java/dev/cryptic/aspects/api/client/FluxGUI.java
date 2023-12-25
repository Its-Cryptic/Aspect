package dev.cryptic.aspects.api.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspects.Aspects;
import dev.cryptic.aspects.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspects.api.capabilities.IFluxCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FluxGUI extends GuiComponent {
    public static final IGuiOverlay OVERLAY = FluxGUI::render;

    public static final Minecraft minecraft = Minecraft.getInstance();

    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspects.MODID, "textures/vfx/uv_test.png");

    public static boolean shouldDisplayBar() {

        return true;
    }

    private static void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
        if (!shouldDisplayBar()) {
            return;
        }

        IFluxCapability fluxCapability = CapabilityRegistry.getFlux(minecraft.player).orElse(null);
        if (fluxCapability == null) return;

        int maxFlux = fluxCapability.getMaxFlux();
        if (maxFlux == 0) return;

        int xOffset = width / 2;
        int yOffset = height / 2;
        int barLength = 100;

        barLength *= ( (double) fluxCapability.getCurrentFlux() / (double) (maxFlux));

        RenderSystem.setShaderTexture(0, UV_TEST);
        blit(poseStack, xOffset - (barLength / 2), yOffset, 0, 0, barLength, 10, 10, 10);
    }
}
