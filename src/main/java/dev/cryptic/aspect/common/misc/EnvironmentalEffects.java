package dev.cryptic.aspect.common.misc;

import dev.cryptic.aspect.Aspect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aspect.MODID, value = Dist.CLIENT)
public class EnvironmentalEffects {
    private static final RandomSource randomSource = RandomSource.create();
    public static float windSpeed = 0.0F;
    public static float windAngle = 0.0F;

    public static final float rainingSpeedMultiplier = 1.25F;
    public static final float thunderingSpeedMultiplier = 1.75F;


    public static final int windUpdateInterval = 20*120;
    public static final int rainingUpdateInterval = 20*60;
    public static final int thunderingUpdateInterval = 20*15;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level == null) return;
            ClientLevel.ClientLevelData clientLevelData = minecraft.level.getLevelData();
            long gameTime = clientLevelData.getGameTime();

            if (clientLevelData.isThundering()) {
                if (gameTime % thunderingUpdateInterval == 0) {
                    windSpeed = updateWindSpeed() * thunderingSpeedMultiplier;
                    windAngle = updateWindAngle();
                }
            } else if (clientLevelData.isRaining()) {
                if (gameTime % rainingUpdateInterval == 0) {
                    windSpeed = updateWindSpeed() * rainingSpeedMultiplier;
                    windAngle = updateWindAngle();
                }
            } else {
                if (gameTime % windUpdateInterval == 0) {
                    windSpeed = updateWindSpeed();
                    windAngle = updateWindAngle();
                }
            }
        }
    }

    private static float updateWindSpeed() {
        return Mth.randomBetween(randomSource, 0.0F, 0.4F);
    }

    private static float updateWindAngle() {
        return Mth.randomBetween(randomSource, 0.0F, 360.0F);
    }

    // Create methods that prevent the wind from working in underground areas
    public static float getGlobalWindSpeed() {
        return windSpeed;
    }

    public static float getGlobalWindAngle() {
        return windAngle;
    }

    public static Vec3 getGlobalWindVector() {
        float angle = (float) Math.toRadians(windAngle);
        return new Vec3(Math.cos(angle), 0, Math.sin(angle)).scale(windSpeed);
    }



}
