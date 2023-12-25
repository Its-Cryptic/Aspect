package dev.cryptic.aspects;

import com.mojang.logging.LogUtils;
import dev.cryptic.aspects.api.gamerule.GameruleRegistry;
import dev.cryptic.aspects.api.networking.ModMessages;
import dev.cryptic.aspects.block.ModBlocks;
import dev.cryptic.aspects.config.AspectClientConfig;
import dev.cryptic.aspects.config.AspectCommonConfig;
import dev.cryptic.aspects.entity.ModEntityTypes;
import dev.cryptic.aspects.entity.client.MizaruRenderer;
import dev.cryptic.aspects.item.ModItems;
import dev.cryptic.aspects.setup.ModSetup;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Aspects.MODID)
public class Aspects {
    public static final String MODID = "aspects";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Aspects() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::sendImc);

        ModSetup.registers(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AspectCommonConfig.SPEC, "aspect-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AspectClientConfig.SPEC, "aspect-client.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
        GameruleRegistry.setup();
    }

    public void sendImc(InterModEnqueueEvent evt) {
        ModSetup.sendIntercoms();
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.MIZARU.get(), MizaruRenderer::new);
        }
    }
}
