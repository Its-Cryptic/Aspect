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

    private static int THRESHOLD_MIN_FLUX;
    private static int SAFE_MAX_FLUX;
    private static int MAX_FLUX;

    private static int FLUX;

    public AbstractFluxItem(int threshholdMin, int safeMax, int max, Properties properties) {
        super(properties);
        THRESHOLD_MIN_FLUX = Math.max(threshholdMin, 0);
        SAFE_MAX_FLUX = Math.min(safeMax, max);
        MAX_FLUX = Math.max(0, max);

    }

//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        ItemStack itemStack = player.getItemInHand(hand);
//
//        if (!level.isClientSide()) {
//            CompoundTag tag = itemStack.getOrCreateTag();
//            int currentFlux = tag.getInt(FLUX_KEY);
//            if (currentFlux < MAX_FLUX) {
//                currentFlux++;
//                tag.putInt(FLUX_KEY, currentFlux + 1);
//            }
//            itemStack.setTag(tag);
//        }
//
//        return InteractionResultHolder.success(itemStack);
//    }

    public void increaseFlux(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        int currentFlux = tag.getInt(FLUX_KEY);
        if (currentFlux < MAX_FLUX) {
            currentFlux++;
            tag.putInt(FLUX_KEY, currentFlux);
        }
        itemStack.setTag(tag);
    }

    public void decreaseFlux(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        int currentFlux = tag.getInt(FLUX_KEY);
        if (currentFlux > 0) {
            currentFlux--;
            tag.putInt(FLUX_KEY, currentFlux);
        }
        itemStack.setTag(tag);
    }

    public int getFlux(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        return tag.getInt(FLUX_KEY);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        int currentFlux = tag != null ? tag.getInt(FLUX_KEY) : 0;
        components.add(Component.literal("Charge: " + currentFlux + " / " + MAX_FLUX).withStyle(ChatFormatting.GREEN));

        super.appendHoverText(stack, level, components, flag);
    }
}
