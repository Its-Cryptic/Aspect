package dev.cryptic.aspects.api.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspects.api.capabilities.CapabilityRegistry;
import dev.cryptic.aspects.api.capabilities.IFluxCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class FluxGUI extends GuiComponent {
    public static final IGuiOverlay OVERLAY = FluxGUI::render;

    public static final Minecraft minecraft = Minecraft.getInstance();

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


    }
}
