package dev.cryptic.aspect.test;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.client.shader.lodestone.post.multi.glow.LightingFx;
import dev.cryptic.aspect.registry.client.AspectKeybinds;
import dev.cryptic.aspect.registry.client.AspectPostProcessors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
public class ClientEvents {
    private static final List<DynamicShaderFxInstance> instances = new ArrayList<>();
    @SubscribeEvent
    public static void inputEvent(InputEvent.Key event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        // Sends packet when held down
        if (player != null) {
            if (AspectKeybinds.POWERUP_SKILL_1.consumeClick()) {
                Aspect.LOGGER.info("Added Instance!");
                Vector3f playerPos = player.position().toVector3f();
                Vector3f color = (new Vec3(Math.random(), Math.random(), Math.random())).toVector3f();
                color = new Vector3f(0.4f, 1.0f, 0.2f);
                LightingFx lightingFx = new LightingFx(playerPos, color, 8f, 2.0f, true, true, true);
                AspectPostProcessors.GLOW.addFxInstance(lightingFx);
                instances.add(lightingFx);
                instances.forEach(instance -> ((LightingFx) instance).isDirectional = true);
                instances.forEach(instance -> ((LightingFx) instance).useNoise = true);
            }
            if (AspectKeybinds.ULTIMATE_SKILL_1.consumeClick()) {
                Aspect.LOGGER.info("Removed All Instances!");
                instances.forEach(DynamicShaderFxInstance::remove);
                instances.clear();
            }

        }
    }

}
