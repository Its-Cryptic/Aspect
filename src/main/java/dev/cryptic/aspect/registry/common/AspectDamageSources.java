package dev.cryptic.aspect.registry.common;

import dev.cryptic.aspect.Aspect;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

import java.util.logging.Level;

public class AspectDamageSources {
    public static final ResourceKey<DamageType> FLAME = create("flame");
    public static final ResourceKey<DamageType> LIGHTNING = create("frost");
    public static final ResourceKey<DamageType> BLOOD = create("frost");



    public static DamageSource aspectFlameDamage(ServerLevel level) {
        return new DamageSource(level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(FLAME));
    }

    public static DamageSource aspectLightningDamage(ServerLevel level) {
        return new DamageSource(level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(LIGHTNING));
    }

    public static DamageSource aspectBloodDamage(ServerLevel level) {
        return new DamageSource(level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(BLOOD));
    }


    private static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Aspect.id(name));
    }
}
