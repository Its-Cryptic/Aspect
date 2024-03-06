package dev.cryptic.aspect.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.cryptic.aspect.Aspect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

import java.io.IOException;

import static team.lodestar.lodestone.registry.client.LodestoneShaderRegistry.registerShader;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Aspect.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {

    public static ShaderHolder TOUCH_OF_DARKNESS = new ShaderHolder(new ResourceLocation(Aspect.MODID, "touch_of_darkness"),
            DefaultVertexFormat.POSITION_COLOR_TEX,
            "Speed", "Zoom", "Distortion", "Intensity", "Wibble"
    );

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();

        registerShader(event, TOUCH_OF_DARKNESS.createInstance(resourceProvider));
    }
}
