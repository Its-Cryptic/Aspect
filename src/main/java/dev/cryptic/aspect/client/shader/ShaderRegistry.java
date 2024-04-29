package dev.cryptic.aspect.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.cryptic.aspect.Aspect;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static team.lodestar.lodestone.registry.client.LodestoneShaderRegistry.registerShader;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Aspect.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {
    public static List<ShaderHolder> SHADERS = new ArrayList<>();

    public static ShaderHolder SHIELD = createShader("shield", DefaultVertexFormat.BLOCK);
    public static ShaderHolder DISSOLVE = createShader("dissolve", DefaultVertexFormat.BLOCK);






    public static ShaderHolder createShader(String name, VertexFormat shaderFormat, String... uniformsToCache) {
        ShaderHolder shaderHolder = new ShaderHolder(Aspect.id(name), shaderFormat, uniformsToCache);
        SHADERS.add(shaderHolder);
        return shaderHolder;
    }

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();

        for (ShaderHolder shader : SHADERS) {
            registerShader(event, shader.createInstance(resourceProvider));
        }

    }
}
