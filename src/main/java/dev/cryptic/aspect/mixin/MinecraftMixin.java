package dev.cryptic.aspect.mixin;

import dev.cryptic.aspect.compat.JoiningWorldBridgeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow
    @Nullable
    public abstract ClientPacketListener getConnection();

    @ModifyVariable(at = @At("HEAD"), method = "setScreen", ordinal = 0, argsOnly = true)
    public Screen setScreen(Screen screen) {
        if (screen instanceof ReceivingLevelScreen) {
            return null;
        }
        return screen;
    }

    @ModifyArg(method = "setLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateScreenAndTick(Lnet/minecraft/client/gui/screens/Screen;)V", opcode = Opcodes.INVOKEVIRTUAL), index = 0)
    private Screen setLevelUpdateScreenAndTick(Screen screen) {
        // Make sure we clean up what needs cleaning up, just that we don't set a new screen on server switches within a proxy
        // Can't just set it to null during reconfiguration, so set an empty screen
        return new JoiningWorldBridgeScreen();
    }
}