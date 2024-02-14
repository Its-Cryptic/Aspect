package dev.cryptic.aspect.api.client.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AspectCoreShaders implements ResourceManagerReloadListener {
    private static final AspectCoreShaders INSTANCE = new AspectCoreShaders();
    private AspectCoreShaders() {}
    public static AspectCoreShaders getInstance() {
        return INSTANCE;
    }

    private final List<ShaderReference> shaders = new ArrayList<>();

    private static ShaderInstance magmaShader;

    public static ShaderInstance getMagmaShader() {
        if (magmaShader == null) {
            Aspect.LOGGER.error("MagmaShader is null, make sure it's loaded correctly.");
        }
        return magmaShader;
    }

    public void init() {
        shaders.add(new ShaderReference("magma", DefaultVertexFormat.POSITION_TEX, shader -> magmaShader = shader));

        Minecraft.getInstance().submitAsync(() -> {
            final ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
            this.onResourceManagerReload(resourceManager);
            if (resourceManager instanceof ReloadableResourceManager reloadableResourceManager) {
                reloadableResourceManager.registerReloadListener(this);
            }
        });
    }


    public void onResourceManagerReload(ResourceManager resourceManager) {
        RenderSystem.assertOnRenderThread();
        shaders.forEach(shader -> shader.reload(resourceManager));
    }

    private static final class ShaderReference {
        private final String name;
        private final VertexFormat format;
        private final Consumer<ShaderInstance> reloadAction;
        private ShaderInstance shader;

        public ShaderReference(final String name, final VertexFormat format, final Consumer<ShaderInstance> reloadAction) {
            this.name = name;
            this.format = format;
            this.reloadAction = reloadAction;
        }

        public void reload(final ResourceProvider provider) {
            if (shader != null) {
                shader.close();
                shader = null;
            }

            try {
                shader = new ShaderInstance(location -> provider.getResource(new ResourceLocation(Aspect.MODID, location.getPath())).or(() -> provider.getResource(location)), name, format);
            } catch (final Exception e) {
                Aspect.LOGGER.error(String.valueOf(e));
            }

            reloadAction.accept(shader);
        }
    }
}
