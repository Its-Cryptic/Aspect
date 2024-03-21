package dev.cryptic.aspect.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class CubeParticle extends TextureSheetParticle {

    protected CubeParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    protected CubeParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        super(level, x, y, z, dx, dy, dz);
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xFFA500;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        float x = (float) (Mth.lerp(partialTick, this.xo, this.x) - camera.getPosition().x());
        float y = (float) (Mth.lerp(partialTick, this.yo, this.y) - camera.getPosition().y());
        float z = (float) (Mth.lerp(partialTick, this.zo, this.z) - camera.getPosition().z());

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();

        int light = this.getLightColor(partialTick);
        float size = 1.0f;
        renderCube(vertexConsumer, x, y, z, size, u0, u1, v0, v1, light);


    }

    @Override
    public ParticleRenderType getRenderType() {
        return null;
    }

    private void renderCube(VertexConsumer vertexConsumer, float x, float y, float z, float size, float u0, float u1, float v0, float v1, int light) {
        float halfSize = size / 2f;

        // Top Face
        renderFace(vertexConsumer, x - halfSize, x + halfSize, y + halfSize, y + halfSize, z - halfSize, z + halfSize, u0, u1, v0, v1, light);

        // Bottom Face
        renderFace(vertexConsumer, x - halfSize, x + halfSize, y - halfSize, y - halfSize, z - halfSize, z + halfSize, u0, u1, v0, v1, light);

        // North Face
        renderFace(vertexConsumer, x - halfSize, x + halfSize, y - halfSize, y + halfSize, z - halfSize, z - halfSize, u0, u1, v0, v1, light);

        // South Face
        renderFace(vertexConsumer, x - halfSize, x + halfSize, y - halfSize, y + halfSize, z + halfSize, z + halfSize, u0, u1, v0, v1, light);

        // East Face
        renderFace(vertexConsumer, x + halfSize, x + halfSize, y - halfSize, y + halfSize, z - halfSize, z + halfSize, u0, u1, v0, v1, light);

        // West Face
        renderFace(vertexConsumer, x - halfSize, x - halfSize, y - halfSize, y + halfSize, z - halfSize, z + halfSize, u0, u1, v0, v1, light);
    }

    private void renderFace(VertexConsumer vertexConsumer, float x1, float x2, float y1, float y2, float z1, float z2, float u0, float u1, float v0, float v1, int light) {
        if (y1 == y2) {
            // Horizontal Face (Top or Bottom)
            vertexConsumer.vertex(x1, y1, z2).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u0, v1).uv2(light).endVertex();
            vertexConsumer.vertex(x2, y2, z2).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u1, v1).uv2(light).endVertex();
            vertexConsumer.vertex(x2, y2, z1).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u1, v0).uv2(light).endVertex();
            vertexConsumer.vertex(x1, y1, z1).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u0, v0).uv2(light).endVertex();
        } else if (z1 == z2) {
            // Front or Back Face
            vertexConsumer.vertex(x1, y2, z1).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u0, v1).uv2(light).endVertex();
            vertexConsumer.vertex(x2, y2, z2).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u1, v1).uv2(light).endVertex();
            vertexConsumer.vertex(x2, y1, z2).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u1, v0).uv2(light).endVertex();
            vertexConsumer.vertex(x1, y1, z1).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u0, v0).uv2(light).endVertex();
        } else {
            // Side Faces (East or West)
            vertexConsumer.vertex(x2, y1, z1).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u0, v1).uv2(light).endVertex();
            vertexConsumer.vertex(x2, y1, z2).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u1, v1).uv2(light).endVertex();
            vertexConsumer.vertex(x1, y2, z2).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u1, v0).uv2(light).endVertex();
            vertexConsumer.vertex(x1, y2, z1).color(1.0F, 1.0F, 1.0F, 1.0F).uv(u0, v0).uv2(light).endVertex();
        }
    }

}
