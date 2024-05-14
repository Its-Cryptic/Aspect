package dev.cryptic.aspect.client.shader.lodestone.post.multi.glow;

import dev.cryptic.aspect.client.shader.RenderHelper;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.postprocess.DynamicShaderFxInstance;

import java.util.function.BiConsumer;

public class LightingFx extends DynamicShaderFxInstance {
    public Vector3f center;
    public Vector3f color;
    public float radius;
    public float intensity;
    public boolean isDirectional;
    public boolean useNoise;
    public boolean glassGlare;

    public LightingFx(Vector3f center, Vector3f color, float radius, float intensity, boolean isDirectional, boolean useNoise, boolean glassGlare) {
        this.center = center;
        this.color = color;
        this.radius = radius;
        this.intensity = intensity;
        this.isDirectional = isDirectional;
        this.useNoise = useNoise;
        this.glassGlare = glassGlare;
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
        writer.accept(7, intensity);
        writer.accept(8, isDirectional ? 1f : 0f);
        writer.accept(9, useNoise ? 1f : 0f);
        writer.accept(10, glassGlare ? 1f : 0f);
    }

}
