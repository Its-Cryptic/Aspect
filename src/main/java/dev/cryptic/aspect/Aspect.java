package dev.cryptic.aspect;

import com.mojang.logging.LogUtils;
import dev.cryptic.aspect.registry.client.AspectParticles;
import dev.cryptic.aspect.registry.client.AspectWorldEventRenderers;
import dev.cryptic.aspect.registry.common.*;
import dev.cryptic.aspect.registry.client.AspectPostProcessors;
import dev.cryptic.aspect.api.networking.ModMessages;
import dev.cryptic.aspect.api.registry.AbilityRegistry;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.registry.client.AspectObjModels;
import dev.cryptic.aspect.common.blockentities.fluxcore.FluxCoreRenderer;
import dev.cryptic.aspect.common.config.AspectClientConfig;
import dev.cryptic.aspect.common.config.AspectCommonConfig;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Aspect.MODID)
public class Aspect {
    public static final String MODID = "aspect";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Aspect() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::sendImc);

        AspectItemRegistry.init(modEventBus);
        AspectBlocks.init(modEventBus);
        AspectCreativeTabs.init(modEventBus);
        AspectBlockEntities.init(modEventBus);
        AspectEntities.init(modEventBus);
        AspectAttributes.init(modEventBus);

        AbilityRegistry.register(modEventBus);
        AspectRegistry.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AspectCommonConfig.SPEC, "aspect-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AspectClientConfig.SPEC, "aspect-client.toml");

        AspectArgumentTypes.init(modEventBus);

        GeckoLib.initialize();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            AspectObjModels.init();
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
        AspectGamerules.setup();
        event.enqueueWork(AspectArgumentTypes::registerArgumentTypes);

        MinecraftForge.EVENT_BUS.register(AspectCommands.class);
    }

    public void sendImc(InterModEnqueueEvent evt) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockEntityRenderers.register(AspectBlockEntities.FLUX_CORE.get(), FluxCoreRenderer::new);
            AspectWorldEventRenderers.init(event);

            AspectPostProcessors.init();
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            AspectParticles.registerParticleFactory(event);
        }
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }


}
