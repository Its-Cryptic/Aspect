package dev.cryptic.aspects.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractFluxItem extends Item {

    private static final String FLUX_KEY = "Flux";
    private static final int MAX_FLUX = 100;

    public AbstractFluxItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            CompoundTag tag = itemStack.getOrCreateTag();
            int currentFlux = tag.getInt(FLUX_KEY);
            if (currentFlux < MAX_FLUX) {
                currentFlux++;
                tag.putInt(FLUX_KEY, currentFlux + 1);
            }
            itemStack.setTag(tag);
        }

        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        int currentFlux = tag != null ? tag.getInt(FLUX_KEY) : 0;
        components.add(Component.literal("Charge: " + currentFlux + " / " + MAX_FLUX).withStyle(ChatFormatting.GREEN));

        super.appendHoverText(stack, level, components, flag);
    }
}
