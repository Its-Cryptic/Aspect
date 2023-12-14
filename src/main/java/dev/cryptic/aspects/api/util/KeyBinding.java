package dev.cryptic.aspects.api.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_ASPECTS = "key.aspects.riftrealms.aspects";
    public static final String KEY_DRINK_WATER = "key.aspects.drink_water";
    public static final String KEY_USE_RAW_FLUX = "key.aspects.use_raw_flux";

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_ASPECTS);

    public static final KeyMapping USE_RAW_FLUX_KEY = new KeyMapping(KEY_USE_RAW_FLUX , KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_ASPECTS);


}
