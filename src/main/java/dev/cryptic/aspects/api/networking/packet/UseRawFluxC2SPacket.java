package dev.cryptic.aspects.api.networking.packet;

import dev.cryptic.aspects.api.util.FluxUtil;
import dev.cryptic.aspects.blockentities.FluxCoreBlockEntity;
import dev.cryptic.aspects.item.custom.AbstractFluxItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UseRawFluxC2SPacket {
    //C2S (Client 2 Server)
    public UseRawFluxC2SPacket() {

    }

    public UseRawFluxC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player != null) {
                ItemStack itemStack = player.getMainHandItem(); // Or check offhand as well
                if (itemStack.getItem() instanceof AbstractFluxItem) {
                    AbstractFluxItem fluxItem = (AbstractFluxItem) itemStack.getItem();
                    fluxItem.increaseFlux(itemStack); // Method to increase flux
                    FluxUtil.removeFlux(player, 1); // Remove flux from player
                } else {
                    ClipContext raytrace = new ClipContext(player.getEyePosition(1.0F), player.getEyePosition(1.0F).add(player.getViewVector(1.0F).scale(3.0D)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
                    BlockHitResult result = player.level.clip(raytrace);
                    BlockPos blockPos = result.getBlockPos();
                    BlockEntity blockEntity = player.level.getBlockEntity(blockPos);
                    if (result.getType() == BlockHitResult.Type.BLOCK) {
                        // Check if the block is an instance of FluxCoreBlockEntity
                        if (blockEntity instanceof FluxCoreBlockEntity) {
                            FluxCoreBlockEntity fluxBlockEntity = (FluxCoreBlockEntity) blockEntity;
                            fluxBlockEntity.increaseFlux(1); // Increase flux

                            BlockPos blockPosAbove = result.getBlockPos().above(); // Position above the block
                            Cow cow = new Cow(EntityType.COW, player.level);
                            cow.moveTo(blockPosAbove.getX() + 0.5, blockPosAbove.getY(), blockPosAbove.getZ() + 0.5, 0, 0);
                            player.level.addFreshEntity(cow);
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }


}
