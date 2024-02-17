package dev.cryptic.aspect.api.client.shader.lodestone.post;

import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.function.BiConsumer;

public class TestDynamicShaderFxInstance extends DynamicShaderFxInstance {
    public Vector3f center;
    public Camera camera;

    public TestDynamicShaderFxInstance(Vector3f center, Camera camera) {
        this.center = center;
        this.camera = camera;
    }
    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, center.x());
        writer.accept(1, center.y());
        writer.accept(2, center.z());
        writer.accept(3, (float) camera.getPosition().x);
        writer.accept(4, (float) camera.getPosition().y);
        writer.accept(5, (float) camera.getPosition().z);
        writer.accept(6, camera.getXRot());
        writer.accept(7, camera.getYRot());
    }

}
