package dev.cryptic.aspects.api.networking;

import dev.cryptic.aspects.Aspect;
//import dev.cryptic.aspects.api.networking.packet.DrinkWaterC2SPacket;
import dev.cryptic.aspects.api.networking.packet.ExampleC2SPacket;
//import dev.cryptic.aspects.api.networking.packet.ThirstDataSyncS2CPacket;
import dev.cryptic.aspects.api.networking.packet.UseRawFluxC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }


    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Aspect.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Registers custom packet ExampleC2SPacket
        net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();

//        net.messageBuilder(DrinkWaterC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
//                .decoder(DrinkWaterC2SPacket::new)
//                .encoder(DrinkWaterC2SPacket::toBytes)
//                .consumerMainThread(DrinkWaterC2SPacket::handle)
//                .add();
//
//        net.messageBuilder(ThirstDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(ThirstDataSyncS2CPacket::new)
//                .encoder(ThirstDataSyncS2CPacket::toBytes)
//                .consumerMainThread(ThirstDataSyncS2CPacket::handle)
//                .add();

        net.messageBuilder(UseRawFluxC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseRawFluxC2SPacket::new)
                .encoder(UseRawFluxC2SPacket::toBytes)
                .consumerMainThread(UseRawFluxC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
