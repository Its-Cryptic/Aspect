package dev.cryptic.aspect.api.handlers;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

public class InputHandler {
    public static Minecraft minecraft = Minecraft.getInstance();
    public static void singlePress() {

    }

    public static HashMap<KeyMapping, Long> keyPressLog = new HashMap<>();
    public static void doublePress() {
    }


}
