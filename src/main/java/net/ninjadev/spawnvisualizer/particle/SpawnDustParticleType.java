package net.ninjadev.spawnvisualizer.particle;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

import javax.annotation.Nonnull;

public class SpawnDustParticleType extends ParticleType<SpawnDustParticleOptions> {

    public SpawnDustParticleType(boolean alwaysShow) {
        super(alwaysShow, SpawnDustParticleOptions.FACTORY);
    }

    @Nonnull
    @Override
    public Codec<SpawnDustParticleOptions> codec() {
        return SpawnDustParticleOptions.CODEC;
    }
}
