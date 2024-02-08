package dev.cryptic.aspect.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.synceddata.SyncedGolemData;
import dev.cryptic.aspect.api.client.gui.FluxItemUI;
import dev.cryptic.aspect.api.client.gui.FluxUI;
import dev.cryptic.aspect.api.client.gui.SoulUI;
import dev.cryptic.aspect.api.networking.ModMessages;
//import dev.cryptic.aspects.api.networking.packet.DrinkWaterC2SPacket;
import dev.cryptic.aspect.api.networking.packet.UseRawFluxC2SPacket;
import dev.cryptic.aspect.api.util.KeyBinding;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.DRINKING_KEY.consumeClick()) {
                //Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed a Key!"));
                //ModMessages.sendToServer(new DrinkWaterC2SPacket());
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
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            // Sends packet when held down
            if (event.phase == TickEvent.Phase.START) {
                if (player != null) {
                    if (KeyBinding.USE_RAW_FLUX_KEY.isDown()) {
                        ModMessages.sendToServer(new UseRawFluxC2SPacket());
                    }
                }
            }
            // Sends packet when double tapped within 5 ticks
//            if (event.phase == TickEvent.Phase.START) {
//                Minecraft minecraft = Minecraft.getInstance();
//                if (minecraft.player != null) {
//                    if (KeyBinding.USE_RAW_FLUX_KEY.isDown()) {
//                        ModMessages.sendToServer(new UseRawFluxC2SPacket());
//                    }
//                }
//            }


            if (player != null) {
                if (event.phase == TickEvent.Phase.START) {
                    if (SyncedGolemData.playerGolemMap != null) {
                        SyncedGolemData.playerGolemMap.forEach((uuid, golemDataList) -> {
                            golemDataList.forEach(golemData -> {
                                //Aspect.LOGGER.info("Golem UUID: " + golemData.golemUUID() + " Imbued Soul: " + golemData.imbuedSoul());
                            });
                        });
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Client setup code here
        }
    }



    @Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRINKING_KEY);
            event.register(KeyBinding.USE_RAW_FLUX_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("flux_item_ui", FluxItemUI.OVERLAY);
            event.registerAboveAll("flux_ui", FluxUI.OVERLAY);
            event.registerAboveAll("soul_ui", SoulUI.OVERLAY);
        }

    }
}