package dev.cryptic.aspect.api.networking.packet;

import dev.cryptic.aspect.client.synceddata.SyncedForgeCapData;
import dev.cryptic.aspect.api.util.AspectUtil;
import dev.cryptic.aspect.api.util.FluxUtil;
import dev.cryptic.aspect.api.util.GolemUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForgeCapDataS2CPacket extends AbstractPacket {
    private float playerFlux;
    private int playerMaxFlux;
    private double playerFluxRegen;

    private int aspectID;

    private int maxSoul;

    public ForgeCapDataS2CPacket(ServerPlayer player) {
        this.playerFlux = FluxUtil.getCurrentFlux(player);
        this.playerMaxFlux = FluxUtil.getMaxFlux(player);
        this.playerFluxRegen = FluxUtil.getFluxRegen(player);

        this.aspectID = AspectUtil.getAspect(player).getId();

        this.maxSoul = GolemUtil.getMaxSoul(player);
    }

    public ForgeCapDataS2CPacket(FriendlyByteBuf buf) {
        this.playerFlux = buf.readFloat();
        this.playerMaxFlux = buf.readInt();
        this.playerFluxRegen = buf.readDouble();

        this.aspectID = buf.readInt();

        this.maxSoul = buf.readInt();
    }


    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(playerFlux);
        buf.writeInt(playerMaxFlux);
        buf.writeDouble(playerFluxRegen);

        buf.writeInt(aspectID);

        buf.writeInt(maxSoul);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            SyncedForgeCapData.set(playerFlux, playerMaxFlux, playerFluxRegen, aspectID, maxSoul);
        });
        return true;
    }
}
