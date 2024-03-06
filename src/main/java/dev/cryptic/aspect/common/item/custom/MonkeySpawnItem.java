package dev.cryptic.aspect.common.item.custom;

import dev.cryptic.aspect.common.misc.Constants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MonkeySpawnItem extends ForgeSpawnEggItem {

    private final Supplier<? extends EntityType<? extends Mob>> entityTypeSupplier;
    private final String note;
    private final String spawnNote;

    public MonkeySpawnItem(Supplier<? extends EntityType<? extends Mob>> type, String note, String spawnNote, Item.Properties properties) {
        super(type, 0x4c170e, 0x6f391a, properties);
        this.entityTypeSupplier = type;
        this.note = note;
        this.spawnNote = spawnNote;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable(note).withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
            components.add(Component.translatable(spawnNote).withStyle(ChatFormatting.DARK_GREEN));
        } else {
            components.add(Component.translatable(Constants.MESSAGE_HOLD_SHIFT).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!player.level().isClientSide && entity instanceof IronGolem) {
            EntityType<? extends Mob> mobType = this.entityTypeSupplier.get();

            // Create and spawn your custom entity
            Mob customEntity = mobType.create(player.level());
            if (customEntity != null) {
                customEntity.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                player.level().addFreshEntity(customEntity);
            }

            // Remove the Iron Golem
            entity.remove(Entity.RemovalReason.DISCARDED);

            // Consume the spawn egg (if needed)
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            return true; // Indicate that the action was successful
        }

        return super.onLeftClickEntity(stack, player, entity);
    }
}
