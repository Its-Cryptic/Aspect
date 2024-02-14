package dev.cryptic.aspect.api.client.shader;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import dev.cryptic.aspect.api.client.synceddata.SyncedForgeCapData;
import dev.cryptic.aspect.api.flux.AspectColor;
import dev.cryptic.aspect.api.flux.AspectType;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

import java.util.function.Consumer;

public class ShaderHandler {
    public static class ClientOnly {

        public static void renderDarknessVignette(PoseStack poseStack) {
            if (true) return;
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            AspectType aspectType = SyncedForgeCapData.getAspectType();
            AspectColor colors = aspectType.getColors();

            float alpha = 0.6f; // 0.4
            float zoom = 0.55f; // 0.55
            float intensity = 1f;

            ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) ShaderRegistry.TOUCH_OF_DARKNESS.getInstance().get();
            shaderInstance.safeGetUniform("Speed").set(2000f);
            Consumer<Float> setZoom = f -> shaderInstance.safeGetUniform("Zoom").set(f);
            Consumer<Float> setIntensity = f -> shaderInstance.safeGetUniform("Intensity").set(f);
            VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                    .setPosColorTexLightmapDefaultFormat()
                    .setPositionWithWidth(0, 0, minecraft.getWindow().getGuiScaledWidth(), minecraft.getWindow().getGuiScaledHeight())
                    .setShader(ShaderRegistry.TOUCH_OF_DARKNESS.getInstance());


            poseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            // Render first vignette
            builder.setColor(colors.primary());
            builder.setAlpha(alpha);
            setZoom.accept(zoom);
            setIntensity.accept(intensity);
            builder.draw(poseStack);

            // Render second vignette
            setZoom.accept(zoom*0.9f);
            setIntensity.accept(intensity);
            builder.setColor(colors.secondary());
            builder.setAlpha(alpha);
            builder.draw(poseStack);

            // Render third vignette
            setZoom.accept(zoom*0.8f);
            setIntensity.accept(intensity*1.5f);
            builder.setColor(colors.tertiary());
            builder.setAlpha(alpha);
            builder.draw(poseStack);

            RenderSystem.disableBlend();
            poseStack.popPose();

            shaderInstance.setUniformDefaults();
        }
    }
}
