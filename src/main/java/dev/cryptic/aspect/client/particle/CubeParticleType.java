package dev.cryptic.aspect.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class CubeParticleType extends ParticleType {
    public CubeParticleType(boolean p_123740_, ParticleOptions.Deserializer p_123741_) {
        super(p_123740_, p_123741_);
    }

    @Override
    public Codec codec() {
        return null;
    }
}
