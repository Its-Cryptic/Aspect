package dev.cryptic.aspect.mixin.mixins.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import dev.cryptic.aspect.mixin.ducks.IGameRendererMixin;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements IGameRendererMixin {

    @Unique
    @Nullable
    private static ShaderInstance aspect$rendertypeShieldShader;

//    @Inject(method = "reloadShaders(Lnet/minecraft/server/packs/resources/ResourceProvider;)V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
//    private void onReloadShaders(ResourceProvider resourceProvider, CallbackInfo ci, List<Pair<ShaderInstance, Consumer<ShaderInstance>>> list1) {
//        // Example: Load a custom shader for shield rendering
//        try {
//            list1.add(Pair.of(new ShaderInstance(resourceProvider, "aspect:shield", DefaultVertexFormat.BLOCK), (shaderInstance) -> {
//                aspect$rendertypeShieldShader = shaderInstance;
//            }));
//        } catch (IOException ioexception) {
//            list1.forEach((shader) -> {
//                shader.getFirst().close();
//            });
//            throw new RuntimeException("could not reload ASPECT shaders", ioexception);
//        }
//    }

    @Unique
    @Nullable
    @Override
    public ShaderInstance aspect$getRendertypeShieldShader() {
        return aspect$rendertypeShieldShader;
    }


}
