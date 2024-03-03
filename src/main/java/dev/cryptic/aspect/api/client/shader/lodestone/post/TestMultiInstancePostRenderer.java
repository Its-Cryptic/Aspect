package dev.cryptic.aspect.api.client.shader.lodestone.post;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.joml.Vector3f;

public class TestMultiInstancePostRenderer {

    public void render(Vector3f center) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        TestDynamicShaderFxInstance instance = new TestDynamicShaderFxInstance(center, camera);
        TestMultiInstancePostProcessor.INSTANCE.addFxInstance(instance);
    }

    public void stop() {

    }
}
