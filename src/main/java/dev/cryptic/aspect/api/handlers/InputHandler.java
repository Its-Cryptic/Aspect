package dev.cryptic.aspect.api.handlers;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.registry.KeyBinding;
import dev.cryptic.aspect.client.shader.RenderHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
public class InputHandler {
    public static Minecraft minecraft = Minecraft.getInstance();

    public static void singlePress() {

    }

    public static void doublePress() {
    }


    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        KeyMapping key = KeyBinding.AbilityKeyBinding.PRIMARY_SKILL_1;
        boolean consumeClick = key.consumeClick();
        boolean isDown = key.isDown();
        Long time = System.currentTimeMillis();

        Aspect.LOGGER.info("Key: {}, consumeClick: {}, isDown: {}, time: {}", key, consumeClick, isDown, time);
    }



}
