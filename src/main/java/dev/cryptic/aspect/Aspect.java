package dev.cryptic.aspect;

import com.mojang.logging.LogUtils;
import dev.cryptic.aspect.api.gamerule.GameruleRegistry;
import dev.cryptic.aspect.api.networking.ModMessages;
import dev.cryptic.aspect.api.registry.AbilityRegistry;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.blockentities.ModBlockEntities;
import dev.cryptic.aspect.block.ModBlocks;
import dev.cryptic.aspect.config.AspectClientConfig;
import dev.cryptic.aspect.config.AspectCommonConfig;
import dev.cryptic.aspect.entity.ModEntityTypes;
import dev.cryptic.aspect.entity.ability.flame.fireblast.FireBlastRenderer;
import dev.cryptic.aspect.entity.client.mizaru.MizaruRenderer;
import dev.cryptic.aspect.item.ModItems;
import dev.cryptic.aspect.setup.ModSetup;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
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
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Aspect.MODID)
public class Aspect {
    public static final String MODID = "aspect";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Aspect() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::sendImc);

        ModSetup.registers(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        AbilityRegistry.register(modEventBus);
        AspectRegistry.register(modEventBus);

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

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.MIZARU.get(), MizaruRenderer::new);
            EntityRenderers.register(ModEntityTypes.FIRE_BLAST.get(), FireBlastRenderer::new);
        }
    }

    public ResourceLocation resourceLocation(String path) {
        return new ResourceLocation(MODID, path);
    }


}
