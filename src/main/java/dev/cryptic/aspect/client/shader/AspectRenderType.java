package dev.cryptic.aspect.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.compat.IGameRendererMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class AspectRenderType extends RenderType {
    public AspectRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    protected static final RenderStateShard.ShaderStateShard RENDERTYPE_SHIELD = new RenderStateShard.ShaderStateShard(ShaderRegistry.SHIELD.getInstance());

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

    public static RenderType TESTING = create("testing", DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL, VertexFormat.Mode.TRIANGLES, 2097152, true, true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_SHIELD)
                    .setTextureState(new TextureStateShard(new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png"), false, false))
                    .createCompositeState(true)
    );
}
