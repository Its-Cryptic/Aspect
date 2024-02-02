package dev.cryptic.aspect.entity;

import dev.cryptic.aspect.Aspect;
import dev.cryptic.aspect.entity.ability.flame.fireblast.FireBlastProjectile;
import dev.cryptic.aspect.entity.fluxentity.golem.threewisemonkeys.Mizaru;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Aspect.MODID);

    public static final RegistryObject<EntityType<Mizaru>> MIZARU =
            ENTITY_TYPES.register("mizaru", () -> EntityType.Builder.of(Mizaru::new, MobCategory.MONSTER)
            .sized(0.7F, 2.0F)
            .build(new ResourceLocation(Aspect.MODID, "mizaru").toString()));

    public static final RegistryObject<EntityType<FireBlastProjectile>> FIRE_BLAST =
            ENTITY_TYPES.register("fire_blast", () -> EntityType.Builder.of(FireBlastProjectile::create, MobCategory.MISC)
                    .sized(0.7F, 2.0F)
                    .build(new ResourceLocation(Aspect.MODID, "fire_blast").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }




}
