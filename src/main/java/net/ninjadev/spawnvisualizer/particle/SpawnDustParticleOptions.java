package net.ninjadev.spawnvisualizer.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraftforge.registries.ForgeRegistries;
import net.ninjadev.spawnvisualizer.init.ModParticles;

import javax.annotation.Nonnull;
import java.util.Locale;

public class SpawnDustParticleOptions implements ParticleOptions {

    @SuppressWarnings("deprecation")
    public static final Deserializer<SpawnDustParticleOptions> FACTORY = new Deserializer<>() {

        @Nonnull
        @Override
        public SpawnDustParticleOptions fromCommand(@Nonnull ParticleType<SpawnDustParticleOptions> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float red = (float) reader.readDouble();
            reader.expect(' ');
            float green = (float) reader.readDouble();
            reader.expect(' ');
            float blue = (float) reader.readDouble();
            reader.expect(' ');
            float scale = (float) reader.readDouble();
            return new SpawnDustParticleOptions(red, green, blue, scale);
        }

        @Nonnull
        @Override
        public SpawnDustParticleOptions fromNetwork(@Nonnull ParticleType<SpawnDustParticleOptions> particleType, FriendlyByteBuf buffer) {
            float red = buffer.readFloat();
            float green = buffer.readFloat();
            float blue = buffer.readFloat();
            float scale = buffer.readFloat();
            return new SpawnDustParticleOptions(red, green, blue, scale);
        }
    };

    public static final Codec<SpawnDustParticleOptions> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(Codec.FLOAT.fieldOf("red").forGetter((dustParticleOptions) -> dustParticleOptions.red),
                            Codec.FLOAT.fieldOf("green").forGetter((dustParticleOptions) -> dustParticleOptions.green),
                            Codec.FLOAT.fieldOf("blue").forGetter((dustParticleOptions) -> dustParticleOptions.blue),
                            Codec.FLOAT.fieldOf("scale").forGetter((dustParticleOptions) -> dustParticleOptions.scale))
                    .apply(instance, SpawnDustParticleOptions::new));

    private final float red;
    private final float green;
    private final float blue;
    protected final float scale;

    public SpawnDustParticleOptions(float red, float green, float blue, float scale) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.scale = Mth.clamp(scale, 0.01f, 4.0f);
    }

    @Nonnull
    @Override
    public ParticleType<?> getType() {
        return ModParticles.SPAWN_DUST;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.red);
        buf.writeFloat(this.green);
        buf.writeFloat(this.blue);
        buf.writeFloat(this.scale);
    }

    @Nonnull
    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f",
                ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()),
                this.red, this.green, this.blue,
                this.scale);
    }

    public float getRed() {
        return this.red;
    }

    public float getGreen() {
        return this.green;
    }

    public float getBlue() {
        return this.blue;
    }

    public float getScale() {
        return this.scale;
    }
}