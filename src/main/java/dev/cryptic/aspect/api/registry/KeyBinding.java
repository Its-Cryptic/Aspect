package dev.cryptic.aspect.api.registry;

import com.mojang.blaze3d.platform.InputConstants;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_ASPECTS = String.format("key.category.%s", Aspect.MODID);
    public static final String KEY_DRINK_WATER = String.format("key.%s.%s", Aspect.MODID, "drink_water");
    public static final String KEY_USE_RAW_FLUX = String.format("key.%s.%s", Aspect.MODID, "use_raw_flux");

    public static final KeyMapping DRINKING_KEY = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_ASPECTS);

    public static final KeyMapping USE_RAW_FLUX_KEY = new KeyMapping(KEY_USE_RAW_FLUX , KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_ASPECTS);

    public static final KeyMapping SCROLL_ABILITY_BUTTON = KeyBinding.register("scroll_ability_button", GLFW.GLFW_KEY_LEFT_ALT);

    public static class AbilityKeyBinding {
        public static final KeyMapping PRIMARY_SKILL_1 = KeyBinding.register("primary_skill_1", GLFW.GLFW_KEY_Z);
        public static final KeyMapping PRIMARY_SKILL_2 = KeyBinding.register("primary_skill_2", GLFW.GLFW_KEY_X);
        public static final KeyMapping POWERUP_SKILL_1 = KeyBinding.register("powerup_skill_1", GLFW.GLFW_KEY_C);
        public static final KeyMapping SPECIAL_SKILL_1 = KeyBinding.register("powerup_skill_1", GLFW.GLFW_KEY_V);
        public static final KeyMapping ULTIMATE_SKILL_1 = KeyBinding.register("ultimate_skill_1", GLFW.GLFW_KEY_B);
    }

    public static class UNREGISTERED {
        public static final KeyMapping LEFT_ALT = KeyBinding.register("left_alt", GLFW.GLFW_KEY_LEFT_ALT);
    }

    protected static KeyMapping register(String keyName, int key) {
        return new KeyMapping("key.aspect." + keyName, KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM, key, KEY_CATEGORY_ASPECTS);
    }
}