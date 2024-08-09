package dev.cryptic.aspect.registry.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;

import java.util.OptionalDouble;

public class AspectRenderType extends RenderType {
    public AspectRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_SHIELD = AspectCoreShaders.SHIELD.getShard();
    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_DISSOLVE = AspectCoreShaders.DISSOLVE.getShard();
    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_FRESNEL = AspectCoreShaders.FRESNEL.getShard();

    protected static final RenderStateShard.TransparencyStateShard TEST_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("test_transparency", () -> {
        RenderSystem.depthMask(false);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    });


    public static RenderType SHIELD = create("shield", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 2097152, true, true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_SHIELD)
                    .setLightmapState(LIGHTMAP)
                    .setTextureState(new TextureStateShard(new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png"), false, false))
                    .setTransparencyState(TEST_TRANSPARENCY)
                    //.setOutputState(MAIN_TARGET)
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .createCompositeState(false)
    );

    //DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL
    //DefaultVertexFormat.BLOCK
    public static RenderType TESTING = create("testing", DefaultVertexFormat.BLOCK, VertexFormat.Mode.TRIANGLES, 2097152, true, true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_SHIELD)
                    .setLightmapState(LIGHTMAP)
                    //.setTransparencyState(TEST_TRANSPARENCY)
                    .setTextureState(new TextureStateShard(new ResourceLocation(Aspect.MODID, "textures/vfx/shield01.png"), false, false))
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .createCompositeState(true)
    );

    public static final RenderType.CompositeRenderType LINES = create("lines", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES, 256, false, false,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LINES_SHADER)
                    .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setCullState(NO_CULL)
                    .createCompositeState(false)
    );

    public static RenderType DISSOLVE = create("dissolve", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.TRIANGLES, 2097152, true, true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_DISSOLVE)
                    .setLightmapState(LightmapStateShard.LIGHTMAP)
                    //.setTransparencyState(TEST_TRANSPARENCY)
                    .setTextureState(new TextureStateShard(new ResourceLocation(Aspect.MODID, "textures/vfx/shield01.png"), false, false))
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .createCompositeState(true)
    );

    public static RenderType dissolve(VertexFormat.Mode vMode) {
        return create("dissolve", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, vMode, 2097152, true, true,
                RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_DISSOLVE)
                        .setLightmapState(LightmapStateShard.LIGHTMAP)
                        //.setTransparencyState(TEST_TRANSPARENCY)
                        .setTextureState(new TextureStateShard(new ResourceLocation(Aspect.MODID, "textures/vfx/shield01.png"), false, false))
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .setCullState(NO_CULL)
                        .createCompositeState(true)
        );
    }

    public static RenderType fresnel(VertexFormat.Mode vMode) {
        return create("fresnel", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, vMode, 2097152, true, true,
                RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_FRESNEL)
                        .setLightmapState(LightmapStateShard.LIGHTMAP)
                        //.setTransparencyState(TEST_TRANSPARENCY)
                        .setTextureState(new TextureStateShard(new ResourceLocation(Aspect.MODID, "textures/vfx/shield01.png"), false, false))
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .setCullState(NO_CULL)
                        .createCompositeState(true)
        );
    }

    public static final RenderTypeProvider TRIANGLE_SPHERE_RENDERTYPE = new RenderTypeProvider((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType("aspect:triangle_sphere_rendertype", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.TRIANGLES, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorTexLightmapShader))
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(texture.get())
                    .setCullState(RenderStateShard.NO_CULL)
            )
    );
}
