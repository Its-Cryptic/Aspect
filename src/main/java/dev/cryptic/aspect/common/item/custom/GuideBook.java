package dev.cryptic.aspect.common.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GuideBook extends Item {
    public GuideBook(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide()) {

            return InteractionResultHolder.success(player.getItemInHand(interactionHand));
        }
        return super.use(level, player, interactionHand);
    }
}
