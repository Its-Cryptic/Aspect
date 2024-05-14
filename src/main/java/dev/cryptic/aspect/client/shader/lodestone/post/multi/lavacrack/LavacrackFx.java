package dev.cryptic.aspect.client.shader.lodestone.post.multi.lavacrack;

import org.joml.Vector3f;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.function.BiConsumer;

public class LavacrackFx extends DynamicShaderFxInstance {
    public Vector3f center;
    public Vector3f color;
    public float radius;


    public LavacrackFx(Vector3f center, Vector3f color, float radius) {
        this.center = center;
        this.color = color;
        this.radius = radius;
    }
    @Override
    public void writeDataToBuffer(BiConsumer<Integer, Float> writer) {
        writer.accept(0, center.x());
        writer.accept(1, center.y());
        writer.accept(2, center.z());
        writer.accept(3, color.x());
        writer.accept(4, color.y());
        writer.accept(5, color.z());
        writer.accept(6, radius);
    }

}
