package dev.cryptic.aspect.blockentities.fluxcore;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.api.client.shader.AspectRenderType;
import dev.cryptic.aspect.misc.obj.IcoSphereModel;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.registry.client.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry.createGenericRenderType;

public class FluxCoreRenderer extends GeoBlockRenderer<FluxCoreBlockEntity> {
    private static final ResourceLocation UV_TEST = new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png");
    private static final ResourceLocation EMOJI = new ResourceLocation(Aspect.MODID, "textures/vfx/wh.png");
    private static Vec3 lastPosVector;
    //public static final RenderTypeProvider ADDITIVE_TEXTURE_TRIANGLE = new RenderTypeProvider((texture) -> createGenericRenderType(Aspect.MODID, "additive_texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, LodestoneShaderRegistry.LODESTONE_TEXTURE.getShard(), StateShards.NORMAL_TRANSPARENCY, texture));


    public FluxCoreRenderer(BlockEntityRendererProvider.Context context) {
        super(new FluxCoreModel());
    }


}

