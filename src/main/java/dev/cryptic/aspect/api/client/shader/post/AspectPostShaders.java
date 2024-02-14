package dev.cryptic.aspect.api.client.shader.post;

import dev.cryptic.aspect.Aspect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AspectPostShaders implements ResourceManagerReloadListener {
    private static final AspectPostShaders INSTANCE = new AspectPostShaders();
    private final List<PostChain> shaders = new ArrayList<>();
    private AspectPostShaders() {
    }
    public static AspectPostShaders getInstance() {
        return INSTANCE;
    }

    private PostChain magmaShader;
    public PostChain getMagmaShader() {
        return magmaShader;
    }

    public void init() {
        Minecraft.getInstance().submitAsync(() -> {
            final ResourceManager manager = Minecraft.getInstance().getResourceManager();
            this.onResourceManagerReload(manager);
            if (manager instanceof ReloadableResourceManager reloadableManager) {
                reloadableManager.registerReloadListener(this);
            }
        });
    }
    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        clear();
        try {
            magmaShader = createPostChain(new ResourceLocation(Aspect.MODID, "berserk"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        shaders.forEach(PostChain::close);
        shaders.clear();
    }

    public PostChain createPostChain(ResourceLocation location) throws IOException {
        return new PostChain(Minecraft.getInstance().textureManager,
                Minecraft.getInstance().getResourceManager(),
                Minecraft.getInstance().getMainRenderTarget(),
                new ResourceLocation(location.getNamespace(), "shaders/post/" + location.getPath() + ".json"));
    }
}
