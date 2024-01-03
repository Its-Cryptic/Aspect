package dev.cryptic.aspects.api.client.shader;

import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspects.Aspects;
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

public class AspectShaders implements ResourceManagerReloadListener {
    private static final AspectShaders INSTANCE = new AspectShaders();
    private AspectShaders() {

    }
    public static AspectShaders getInstance() {
        return INSTANCE;
    }

    private final List<ShaderReference> shaders = new ArrayList<>();

    private ShaderInstance scanShader;
    private ShaderInstance circleProgressShader;

    public static ShaderInstance getScanShader() {
        return INSTANCE.scanShader;
    }
    public static ShaderInstance getCircleProgressShader() {
        return INSTANCE.circleProgressShader;
    }

    public void init() {
        shaders.add(new ShaderReference("scan", DefaultVertexFormat.POSITION_TEX, shader -> scanShader = shader));
        shaders.add(new ShaderReference("circle_progress", DefaultVertexFormat.POSITION_TEX, shader -> circleProgressShader = shader));

        Minecraft.getInstance().submitAsync(() -> {
            final ResourceManager manager = Minecraft.getInstance().getResourceManager();
            this.onResourceManagerReload(manager);
            if (manager instanceof ReloadableResourceManager reloadableManager) {
                reloadableManager.registerReloadListener(this);
            }
        });
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        RenderSystem.assertOnRenderThread();

    }

    private static class ShaderReference {
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
                shader = new ShaderInstance(location -> provider.getResource(new ResourceLocation(Aspects.MODID, location.getPath())).or(() -> provider.getResource(location)), name, format);
            } catch (final Exception e) {
                Aspects.LOGGER.error("Failed to reload shader {}", name, e);
            }

            reloadAction.accept(shader);
        }
    }
}
