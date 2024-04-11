package dev.cryptic.aspect.common.event;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.util.ClassUtil;
import dev.cryptic.aspect.api.util.KeyBinding;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Aspect.MODID)
public class AbilityCommonEvents {

//    @SubscribeEvent
//    public void onPlayerLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
//        Player player = event.getEntity();
//    }

        /*
    Ability Cast System Below
    Need:
    - System for players to register keybinds for abilities
    - System for players to select which ability is assigned to which keybind.
    Some abilities have different casting behaivors where they require you to hold down the key or double tap it, Some may have multiple.
    - System for determining which casting method a player is using (Single Tap, Double Tap, Hold)
     */

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        // Filter for fields of type KeyMapping
        Arrays.stream(ClassUtil.getDeclaredFieldsOfType(KeyBinding.AbilityKeyBinding.class, KeyMapping.class)).forEach(field -> {
            try {
                KeyMapping keyMapping = (KeyMapping) field.get(null);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

}
