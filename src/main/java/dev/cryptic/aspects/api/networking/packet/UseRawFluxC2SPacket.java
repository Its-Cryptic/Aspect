package dev.cryptic.aspects.api.networking.packet;

import dev.cryptic.aspects.api.capabilities.PlayerFluxProvider;
import dev.cryptic.aspects.api.networking.ModMessages;
import dev.cryptic.aspects.item.custom.AbstractFluxItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
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
                }
            }
        });
        context.setPacketHandled(true);
    }


}
