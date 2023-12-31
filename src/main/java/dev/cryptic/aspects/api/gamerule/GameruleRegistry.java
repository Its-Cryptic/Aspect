package dev.cryptic.aspects.api.gamerule;

import dev.cryptic.aspects.api.util.GameruleFactory;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Category;

public class GameruleRegistry {
    public static GameRules.Key<GameRules.BooleanValue> BRAIN_DAMAGE;
    public static GameRules.Key<GameRules.BooleanValue> MILITARY_RAIDS;
    public static GameRules.Key<GameRules.BooleanValue> ASPECT_PVP;
    public static GameRules.Key<GameRules.BooleanValue> FLUX_CYCLE;


    public GameruleRegistry() {
    }

    public static void setup() {
        BRAIN_DAMAGE = GameruleFactory.createBoolean("allowBrainDamage", Category.PLAYER, true);
        MILITARY_RAIDS = GameruleFactory.createBoolean("allowMilitaryRaids", Category.SPAWNING, true);
        ASPECT_PVP = GameruleFactory.createBoolean("allowAspectPVP", Category.PLAYER, true);
        FLUX_CYCLE = GameruleFactory.createBoolean("doFluxCycle", Category.MISC, true);
    }

}
