package dev.cryptic.aspects.api.networking.packet;

import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacket {
    public AbstractPacket() { }
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
