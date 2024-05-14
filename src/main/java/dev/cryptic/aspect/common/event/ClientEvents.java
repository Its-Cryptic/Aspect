package dev.cryptic.aspect.common.event;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.gui.FluxItemUI;
import dev.cryptic.aspect.client.gui.FluxUI;
import dev.cryptic.aspect.client.gui.SoulUI;
import dev.cryptic.aspect.client.layer.EnergyLayer;
import dev.cryptic.aspect.api.networking.ModMessages;
//import dev.cryptic.aspects.api.networking.packet.DrinkWaterC2SPacket;
import dev.cryptic.aspect.api.networking.packet.UseRawFluxC2SPacket;
import dev.cryptic.aspect.registry.client.AspectKeybinds;
import dev.cryptic.aspect.registry.client.AspectPostProcessors;
import dev.cryptic.encryptedapi.api.vfx.sprite.VFXSpriteLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            // Sends packet when held down
            if (event.phase == TickEvent.Phase.START) {
                if (player != null) {
                    if (AspectKeybinds.PRIMARY_SKILL_1.isDown()) {
                        ModMessages.sendToServer(new UseRawFluxC2SPacket());
                    }
                    if (AspectKeybinds.PRIMARY_SKILL_1.consumeClick()) {
                        Aspect.LOGGER.info("Raw Flux Key Pressed!");
                        //BerserkRenderer.getInstance().start(100, Easing.EXPO_OUT);
                        //Aspect.LOGGER.info("Voronoi Active: " + AspectPostVFX.VORONOI.isActive());
                        //AspectPostVFX.VORONOI.setActive(true);
                        Aspect.LOGGER.info("Voronoi Active: " + AspectPostProcessors.VORONOI.isActive());
                        AspectPostProcessors.VORONOI.setActive(!AspectPostProcessors.VORONOI.isActive());


                        //AspectPostVFX.VORONOI.setActive(!AspectPostVFX.VORONOI.isActive());
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


//            if (player != null) {
//                if (event.phase == TickEvent.Phase.START) {
//                    if (SyncedGolemData.playerGolemMap != null) {
//                        SyncedGolemData.playerGolemMap.forEach((uuid, golemDataList) -> {
//                            golemDataList.forEach(golemData -> {
//                                //Aspect.LOGGER.info("Golem UUID: " + golemData.golemUUID() + " Imbued Soul: " + golemData.imbuedSoul());
//                            });
//                        });
//                    }
//                }
//            }
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Client setup code here

        }
    }



    @Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("flux_item_ui", FluxItemUI.OVERLAY);
            event.registerAboveAll("flux_ui", FluxUI.OVERLAY);
            event.registerAboveAll("soul_ui", SoulUI.OVERLAY);

//            event.registerAboveAll("touch_of_darkness", (gui, guiGraphics, partialTick, width, height) ->
//                    ShaderHandler.ClientOnly.renderDarknessVignette(guiGraphics.pose()));
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            LayerDefinition energyOverlayLayer = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 64);

            event.registerLayerDefinition(EnergyLayer.Vanilla.ENERGY_LAYER, () -> energyOverlayLayer);

        }

        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.AddLayers event) {
            addLayerToPlayerSkin(event, "default");
            addLayerToPlayerSkin(event, "slim");
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName) {
            EntityRenderer<? extends Player> renderer = event.getSkin(skinName);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new EnergyLayer.Vanilla(livingRenderer, VFXSpriteLibrary.Misc.UV_GRID, 0L));

            }
        }

    }
}