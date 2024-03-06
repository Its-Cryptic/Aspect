package dev.cryptic.aspect.common.blockentities.fluxcore;

import dev.cryptic.aspect.Aspect;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

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

