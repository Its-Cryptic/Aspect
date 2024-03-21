package dev.cryptic.aspect.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Final
    @Shadow
    private ClientLevel.ClientLevelData clientLevelData;

//    /**
//     * @reason() Sky
//     * @author :urmmods:
//     */
//    @Overwrite
//    public Vec3 getSkyColor(Vec3 vec3, float v) {
//        long f = this.clientLevelData.getGameTime();
//        double g = Math.sin((float)f / 24) + 1;
//        return new Vec3(g, g, g);
//    }


}
