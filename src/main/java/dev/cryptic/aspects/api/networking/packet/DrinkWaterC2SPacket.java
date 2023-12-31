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

public class DrinkWaterC2SPacket {
    //C2S (Client 2 Server)
    public static final String MESSAGE_DRINK_WATER = "message.aspects.drink_water";
    public static final String MESSAGE_CANT_DRINK_WATER = "message.aspects.cant_drink_water";
    public DrinkWaterC2SPacket() {

    }

    public DrinkWaterC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (hasWaterAroundThem(player, level, 1)) {
                // Notify player that water has been drunk
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER)
                        .withStyle(ChatFormatting.DARK_AQUA));
                // Play drinking sound
                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5f, level.random.nextFloat() * 0.1f + 0.9f);
                // Increase thirst level of player
                // Output current thirst level
                player.getCapability(PlayerFluxProvider.PLAYER_FLUX).ifPresent(energy -> {
                    energy.addFlux(1);
                    player.sendSystemMessage(Component.literal("Current Thirst: " + energy.getFlux())
                            .withStyle(ChatFormatting.AQUA));
                    ModMessages.sendToPlayer(new ThirstDataSyncS2CPacket(energy.getFlux()), player);
                });
            } else {
                // Notify player that there is no water around!
                player.sendSystemMessage(Component.translatable(MESSAGE_CANT_DRINK_WATER)
                        .withStyle(ChatFormatting.DARK_RED));
                // Output current thirst level
                player.getCapability(PlayerFluxProvider.PLAYER_FLUX).ifPresent(energy -> {
                    player.sendSystemMessage(Component.literal("Current Thirst: " + energy.getFlux())
                            .withStyle(ChatFormatting.RED));
                });

            }

//            if (MathUtility.aspectChargeCheck(player)) {
//
//            }

        });
        return true;
    }

    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.WATER)).toArray().length > 0;
    }
}
