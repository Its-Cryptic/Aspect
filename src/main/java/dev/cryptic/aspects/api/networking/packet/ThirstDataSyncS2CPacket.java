//package dev.cryptic.aspects.api.networking.packet;
//
//import dev.cryptic.aspects.api.client.ClientThirstData;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraftforge.network.NetworkEvent;
//
//import java.util.function.Supplier;
//
//public class ThirstDataSyncS2CPacket {
//    //S2C (Server to Client)
//    private final int flux;
//    public ThirstDataSyncS2CPacket(int flux) {
//        this.flux = flux;
//    }
//
//    public ThirstDataSyncS2CPacket(FriendlyByteBuf buf) {
//        this.flux = buf.readInt();
//    }
//
//    public void toBytes(FriendlyByteBuf buf) {
//        buf.writeInt(flux);
//    }
//
//    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
//        NetworkEvent.Context context = supplier.get();
//        context.enqueueWork(() -> {
//            // HERE WE ARE ON CLIENT
//            ClientThirstData.set(flux);
//
//        });
//        return true;
//    }
//}
