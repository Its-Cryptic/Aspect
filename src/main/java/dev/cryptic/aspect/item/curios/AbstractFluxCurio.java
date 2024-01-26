package dev.cryptic.aspect.item.curios;

import com.google.common.collect.Multimap;
import dev.cryptic.aspect.api.registry.AttributeRegistry;
import dev.cryptic.aspect.api.item.AspectCurio;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public abstract class AbstractFluxCurio extends AspectCurio {
    public AbstractFluxCurio() {
        super();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributeModifiers = super.getAttributeModifiers(slotContext, uuid, stack);
        //attributeModifiers.put(AttributeRegistry.MAX_FLUX.get(), new AttributeModifier(uuid, "max_flux_modifier_curio", 100, AttributeModifier.Operation.ADDITION));
        attributeModifiers.put(AttributeRegistry.FLUX_REGEN.get(), new AttributeModifier(uuid, "max_flux_modifier_curio", 10, AttributeModifier.Operation.ADDITION));
        return attributeModifiers;
    }
}
