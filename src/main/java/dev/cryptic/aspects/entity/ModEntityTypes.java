package dev.cryptic.aspects.entity;

import dev.cryptic.aspects.Aspects;
import dev.cryptic.aspects.entity.threewisemonkeys.Mizaru;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Aspects.MODID);

    public static final RegistryObject<EntityType<Mizaru>> MIZARU =
            ENTITY_TYPES.register("mizaru", () -> EntityType.Builder.of(Mizaru::new, MobCategory.MONSTER)
            .sized(0.7F, 2.0F)
            .build(new ResourceLocation(Aspects.MODID, "mizaru").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
