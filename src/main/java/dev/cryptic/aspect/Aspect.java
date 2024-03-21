package dev.cryptic.aspect;

import com.mojang.logging.LogUtils;
import dev.cryptic.aspect.client.shader.lodestone.post.DepthWorldPostProcessor;
import dev.cryptic.aspect.client.shader.lodestone.post.SobelPostProcessor;
import dev.cryptic.aspect.client.shader.lodestone.post.TestMultiInstancePostProcessor;
import dev.cryptic.aspect.client.shader.lodestone.post.VoronoiPostProcessor;
import dev.cryptic.aspect.client.shader.post.AspectPostShaders;
import dev.cryptic.aspect.api.registry.GameruleRegistry;
import dev.cryptic.aspect.api.networking.ModMessages;
import dev.cryptic.aspect.api.registry.AbilityRegistry;
import dev.cryptic.aspect.api.registry.AspectRegistry;
import dev.cryptic.aspect.common.blockentities.ModBlockEntities;
import dev.cryptic.aspect.common.block.BlockRegistry;
import dev.cryptic.aspect.common.blockentities.fluxcore.FluxCoreRenderer;
import dev.cryptic.aspect.common.config.AspectClientConfig;
import dev.cryptic.aspect.common.config.AspectCommonConfig;
import dev.cryptic.aspect.common.entity.ModEntityTypes;
import dev.cryptic.aspect.common.entity.ability.flame.fireblast.FireBlastRenderer;
import dev.cryptic.aspect.common.entity.client.mizaru.MizaruRenderer;
import dev.cryptic.aspect.common.item.CreativeTabRegistry;
import dev.cryptic.aspect.common.item.ItemRegistry;
//import dev.cryptic.aspect.misc.obj.MonkeyModel;
import dev.cryptic.aspect.common.misc.obj.IcoSphereModel;
import dev.cryptic.aspect.common.misc.obj.MonkeyModel;
import dev.cryptic.aspect.common.misc.obj.SphereShieldModel;
import dev.cryptic.aspect.common.setup.ModSetup;
//import dev.cryptic.encryptedapi.registries.ObjModelRegistry;
import dev.cryptic.encryptedapi.registries.ObjModelRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
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
import software.bernie.geckolib.GeckoLib;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Aspect.MODID)
public class Aspect {
    public static final String MODID = "aspect";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Aspect() {
        ObjModelRegistry.registerModel(MonkeyModel.INSTANCE);
        ObjModelRegistry.registerModel(IcoSphereModel.INSTANCE);
        ObjModelRegistry.registerModel(SphereShieldModel.INSTANCE);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::sendImc);

        ModSetup.registers(modEventBus);
        ItemRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        AbilityRegistry.register(modEventBus);
        AspectRegistry.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AspectCommonConfig.SPEC, "aspect-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AspectClientConfig.SPEC, "aspect-client.toml");

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
        GameruleRegistry.setup();
    }

    public void sendImc(InterModEnqueueEvent evt) {
        ModSetup.sendIntercoms();
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.MIZARU.get(), MizaruRenderer::new);
            EntityRenderers.register(ModEntityTypes.FIRE_BLAST.get(), FireBlastRenderer::new);

            BlockEntityRenderers.register(ModBlockEntities.FLUX_CORE.get(), FluxCoreRenderer::new);

            AspectPostShaders.getInstance().init();

            //PostProcessHandler.addInstance(new TestPostProcessor());
            PostProcessHandler.addInstance(SobelPostProcessor.getInstance());
            PostProcessHandler.addInstance(DepthWorldPostProcessor.INSTANCE);
            PostProcessHandler.addInstance(VoronoiPostProcessor.INSTANCE);

            PostProcessHandler.addInstance(TestMultiInstancePostProcessor.INSTANCE);
        }
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }


}
