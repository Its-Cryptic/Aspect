package dev.cryptic.aspects.api.networking.packet;

import dev.cryptic.aspects.api.capabilities.PlayerFluxProvider;
import dev.cryptic.aspects.api.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            player.getCapability(PlayerFluxProvider.PLAYER_FLUX).ifPresent(energy -> {
                if (energy.getFlux() > 0) {
                    energy.removeFlux(1);
                }
            });
        });
        return true;
    }

}
