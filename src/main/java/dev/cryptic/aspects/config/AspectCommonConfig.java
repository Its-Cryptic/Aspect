package dev.cryptic.aspects.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AspectCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> BASE_MAX_FLUX;
    public static final ForgeConfigSpec.ConfigValue<Integer> BASE_MAX_SOUL;
    public static final ForgeConfigSpec.ConfigValue<Integer> BASE_FLUX_REGEN;
    public static final ForgeConfigSpec.ConfigValue<Integer> FLUX_USAGE_MULTIPLIER;
    public static final ForgeConfigSpec.ConfigValue<Integer> FLUX_REGEN_UPDATE_INTERVAL;

    static {
        BUILDER.push("Aspects Common Config");
        BASE_MAX_FLUX = BUILDER.comment("The base maximum flux for all players")
                .defineInRange("baseMaxFlux", 100, 0, 1000000);

        BASE_FLUX_REGEN = BUILDER.comment("The base flux regen for all players")
                .defineInRange("baseFluxRegen", 1, 0, 1000000);

        FLUX_REGEN_UPDATE_INTERVAL = BUILDER.comment("Interval of ticks in between each flux regen update")
                .defineInRange("fluxRegenUpdateInterval", 5, 1, 1000000);

        FLUX_USAGE_MULTIPLIER = BUILDER.comment("A value multiplied by the flux usage of all aspects")
                .define("fluxUseMultiplier", 1);

        BASE_MAX_SOUL = BUILDER.comment("The base maximum soul for all players")
                        .defineInRange("baseMaxSoul", 1, 0, 1000);





        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
