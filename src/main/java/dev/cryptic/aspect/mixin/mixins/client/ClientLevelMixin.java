package dev.cryptic.aspect.mixin.mixins.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelTimeAccess;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin implements LevelTimeAccess {

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
