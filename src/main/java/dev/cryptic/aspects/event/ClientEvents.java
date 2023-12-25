package dev.cryptic.aspects.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.cryptic.aspects.Aspects;
import dev.cryptic.aspects.api.client.ThirstHudOverlay;
import dev.cryptic.aspects.api.networking.ModMessages;
import dev.cryptic.aspects.api.networking.packet.DrinkWaterC2SPacket;
import dev.cryptic.aspects.api.networking.packet.UseRawFluxC2SPacket;
import dev.cryptic.aspects.api.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Aspects.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.DRINKING_KEY.consumeClick()) {
                //Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed a Key!"));
                ModMessages.sendToServer(new DrinkWaterC2SPacket());
                Minecraft.getInstance().player.swing(InteractionHand.MAIN_HAND);
            }
//            if (KeyBinding.USE_RAW_FLUX_KEY.isDown()) {
//                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed Raw Flux Use Key!"));
//                ModMessages.sendToServer(new UseRawFluxC2SPacket());
//            } else {
//                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Not Pressed Raw Flux Use Key!"));
//            }
        }

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                Minecraft minecraft = Minecraft.getInstance();
                if (minecraft.player != null) {
                    if (KeyBinding.USE_RAW_FLUX_KEY.isDown()) {
                        minecraft.player.sendSystemMessage(Component.literal("Pressed Raw Flux Use Key!"));
                        ModMessages.sendToServer(new UseRawFluxC2SPacket());
                    } else {
                        minecraft.player.sendSystemMessage(Component.literal("Not Pressed Raw Flux Use Key!"));
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Client setup code here
        }

        @SubscribeEvent
        public static void onRenderPlayer(RenderPlayerEvent event) {
            renderArmOverlay(event);
        }

        private static void renderArmOverlay(RenderPlayerEvent event) {
            Minecraft mc = Minecraft.getInstance();
            ResourceLocation overlayTexture = new ResourceLocation(Aspects.MODID, "textures/arm_overlay.png");
            PoseStack matrixStack = event.getPoseStack();

            RenderSystem.setShaderTexture(0, overlayTexture);

            // You will need to adjust the rendering code to position and scale the texture correctly.
            // This is a complex task and requires understanding of the player model and rendering system.

            // Example rendering code (this is very basic and likely needs adjustment):
            matrixStack.pushPose();
            // Position and scale the texture
            // Render the texture
            matrixStack.popPose();
        }
    }



    @Mod.EventBusSubscriber(modid = Aspects.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.USE_RAW_FLUX_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
        }

    }
}