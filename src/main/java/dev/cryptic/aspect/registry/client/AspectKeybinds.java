package dev.cryptic.aspect.registry.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AspectKeybinds {
    public static final List<KeyMapping> KEYBINDS = new ArrayList<>();
    public static final String KEY_CATEGORY_ASPECTS = String.format("key.category.%s", Aspect.MODID);


    public static final KeyMapping PRIMARY_SKILL_1 = register("primary_skill_1", GLFW.GLFW_KEY_Z);
    public static final KeyMapping PRIMARY_SKILL_2 = register("primary_skill_2", GLFW.GLFW_KEY_X);
    public static final KeyMapping POWERUP_SKILL_1 = register("powerup_skill_1", GLFW.GLFW_KEY_C);
    public static final KeyMapping SPECIAL_SKILL_1 = register("special_skill_1", GLFW.GLFW_KEY_V);
    public static final KeyMapping ULTIMATE_SKILL_1 = register("ultimate_skill_1", GLFW.GLFW_KEY_B);

    protected static KeyMapping register(String keyName, int key) {
        KeyMapping keyMapping = new KeyMapping("key.aspect." + keyName, KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM, key, KEY_CATEGORY_ASPECTS);
        KEYBINDS.add(keyMapping);
        return keyMapping;
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        KEYBINDS.forEach(event::register);
    }
}
