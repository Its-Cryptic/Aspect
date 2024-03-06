package dev.cryptic.aspect.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AspectClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> BASE_MAX_FLUX;
    public static final ForgeConfigSpec.ConfigValue<Integer> BASE_FLUX_REGEN;
    public static final ForgeConfigSpec.ConfigValue<Integer> FLUX_USAGE_MULTIPLIER;

    static {
        BUILDER.push("Aspects Client Config");
        BASE_MAX_FLUX = BUILDER.comment("The base maximum flux for all players")
                .defineInRange("baseMaxFlux", 100, 0, 1000000);
        BASE_FLUX_REGEN = BUILDER.comment("The base flux regen for all players")
                .defineInRange("baseFluxRegen", 1, 0, 1000000);
        FLUX_USAGE_MULTIPLIER = BUILDER.comment("A value multiplied by the flux usage of all aspects")
                .define("fluxUseMultiplier", 1);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
