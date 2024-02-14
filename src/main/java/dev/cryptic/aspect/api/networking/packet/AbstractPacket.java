package dev.cryptic.aspect.api.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacket {

    public AbstractPacket() {

    }
    public AbstractPacket(FriendlyByteBuf buf) { }

    public abstract void toBytes(FriendlyByteBuf buf);

    /**
     * Example:
     * <pre>
     * {@code
     * public boolean handle(Supplier<NetworkEvent.Context> supplier) {
     *     NetworkEvent.Context context = supplier.get();
     *     context.enqueueWork(() -> {
     *         ServerPlayer player = context.getSender();
     *         ServerLevel level = player.getLevel();
     *         BlockPos pos = player.blockPosition();
     *         player.teleportTo(level, pos.getX(), pos.getY(), pos.getZ(), 0,0);
     *     });
     *     return true;
     * }
     * }
     * </pre>
     */
    public abstract boolean handle(Supplier<NetworkEvent.Context> supplier);
}
