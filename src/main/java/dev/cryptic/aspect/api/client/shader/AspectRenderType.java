package dev.cryptic.aspect.api.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;

public class AspectRenderType extends RenderType {
    public AspectRenderType(String name, VertexFormat p_173179_, VertexFormat.Mode mode, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(name, p_173179_, mode, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }


    private static final RenderType ASPECT_TEST = create("aspect_test", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_END_GATEWAY_SHADER)
                    .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
                            .add(new ResourceLocation(Aspect.MODID, "textures/vfx/uv_test.png"), false, false)
                            .add(new ResourceLocation(Aspect.MODID, "textures/vfx/9.png"), false, false)
                            .build())
                    .createCompositeState(false));
    public static RenderType aspectTest() {
        return ASPECT_TEST;
    }
}
