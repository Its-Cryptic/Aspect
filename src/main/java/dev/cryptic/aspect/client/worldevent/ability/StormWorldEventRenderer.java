package dev.cryptic.aspect.client.worldevent.ability;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspect.common.worldevent.ability.StormWorldEvent;
import dev.cryptic.aspect.registry.client.AspectCoreShaders;
import dev.cryptic.aspect.registry.client.AspectObjModels;
import dev.cryptic.aspect.registry.client.AspectRenderType;
import dev.cryptic.encryptedapi.api.vfx.model.Face;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventRenderer;

import java.util.List;

public class StormWorldEventRenderer extends WorldEventRenderer<StormWorldEvent> {

    public StormWorldEventRenderer() {
    }

    @Override
    public void render(StormWorldEvent instance, PoseStack poseStack, MultiBufferSource bufferSource, float partialTicks) {
    }

    @Override
    public boolean canRender(StormWorldEvent instance) {
        return true;
    }
}