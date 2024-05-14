package dev.cryptic.aspect.registry.common;

import com.mojang.datafixers.util.Pair;
import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.common.entity.ability.flame.fireblast.FireBlastProjectile;
import dev.cryptic.aspect.common.entity.ability.flame.fireblast.FireBlastRenderer;
import dev.cryptic.aspect.common.entity.client.mizaru.MizaruRenderer;
import dev.cryptic.aspect.common.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AspectEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Aspect.MODID);
    public static final List<Pair<RegistryObject<EntityType<? extends Entity>>, EntityRendererProvider<? extends Entity>>> ENTITY_TYPE_RENDERER_PAIRS = new ArrayList<>();

    public static final RegistryObject<EntityType<Mizaru>> MIZARU = ENTITY_TYPES.register("mizaru", () ->
            EntityType.Builder.of(Mizaru::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.0F)
                    .build(Aspect.id("mizaru").toString())
    );

    public static final RegistryObject<EntityType<FireBlastProjectile>> FIRE_BLAST = ENTITY_TYPES.register("fire_blast", () ->
            EntityType.Builder.of(FireBlastProjectile::create, MobCategory.MISC)
                    .sized(0.7F, 2.0F)
                    .build(Aspect.id("fire_blast").toString())
    );

    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(AspectEntities.MIZARU.get(), MizaruRenderer::new);
            event.registerEntityRenderer(AspectEntities.FIRE_BLAST.get(), FireBlastRenderer::new);
        }
    }


}
