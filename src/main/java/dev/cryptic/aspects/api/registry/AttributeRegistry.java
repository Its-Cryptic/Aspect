package dev.cryptic.aspects.api.registry;

import dev.cryptic.aspects.Aspect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Aspect.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    public static final HashMap<RegistryObject<Attribute>, UUID> UUIDS = new HashMap<>();
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Aspect.MODID);

    public static final RegistryObject<Attribute> ASPECT_PROFICIENCY = registerAttribute("aspect_proficiency",(id) -> new RangedAttribute(id, 0.0D, 0, 10).setSyncable(true), "cd455036-75b4-4013-b9ea-ecccfe08f917");
    public static final RegistryObject<Attribute> ASPECT_POWER = registerAttribute("aspect_power",(id) -> new RangedAttribute(id, 0.0D, 0, 10).setSyncable(true), "53dd12dd-383d-41fa-b5e9-a0334ea07ffb");
    public static final RegistryObject<Attribute> FLUX_REGEN = registerAttribute("flux_regen",(id) -> new RangedAttribute(id, 2.0D, 0, 10000).setSyncable(true), "007d37b4-9aec-45ec-9ea5-df9d383143f6");
    public static final RegistryObject<Attribute> FLAT_MAX_FLUX = registerAttribute("max_flux_flat_bonus",(id) -> new RangedAttribute(id, 0.0D, 0, 10000).setSyncable(true), "59c5c348-ff7b-4419-801b-7f55654dc095");


    public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, String uuid) {
        return registerAttribute(name, attribute, UUID.fromString(uuid));
    }

    public static RegistryObject<Attribute> registerAttribute(String name, Function<String, Attribute> attribute, UUID uuid) {
        RegistryObject<Attribute> registryObject = ATTRIBUTES.register(name, () -> attribute.apply(name));
        UUIDS.put(registryObject, uuid);
        return registryObject;
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ASPECT_PROFICIENCY.get());
        event.add(EntityType.PLAYER, ASPECT_POWER.get());
        event.add(EntityType.PLAYER, FLUX_REGEN.get());
        event.add(EntityType.PLAYER, FLAT_MAX_FLUX.get());
    }
}