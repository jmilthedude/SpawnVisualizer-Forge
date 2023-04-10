package net.ninjadev.spawnvisualizer.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.init.ModParticles;

import javax.annotation.Nonnull;

public class SpawnDustParticle<T extends SpawnDustParticleOptions>
        extends TextureSheetParticle {

    private final SpriteSet spriteProvider;

    public SpawnDustParticle(ClientLevel clientWorld, double x, double y, double z, T parameters, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z);
        this.rCol = parameters.getRed();
        this.gCol = parameters.getGreen();
        this.bCol = parameters.getBlue();
        this.quadSize = .1f;
        this.spriteProvider = spriteProvider;

        this.lifetime = 5;
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.spriteProvider);
    }

    @Override
    protected int getLightColor(float f) {
        return LightTexture.FULL_BRIGHT;
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Factory implements ParticleProvider<SpawnDustParticleOptions> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet ignored) {
            this.spriteProvider = new SpriteSet() {

                @Override
                @Nonnull
                public TextureAtlasSprite get(int pAge, int pLifetime) {
                    TextureAtlas texture = (TextureAtlas) Minecraft.getInstance().textureManager.getTexture(ModParticles.PARTICLE_SHEET);
                    int spriteId = (pAge * 7) / pLifetime;
                    return texture.getSprite(SpawnVisualizer.id("particle/spawn_dust"));
                }

                @Override
                @Nonnull
                public TextureAtlasSprite get(RandomSource random) {
                    TextureAtlas texture = (TextureAtlas) Minecraft.getInstance().textureManager.getTexture(ModParticles.PARTICLE_SHEET);
                    return texture.getSprite(SpawnVisualizer.id("particle/spawn_dust"));
                }
            };
        }

        @Override
        public Particle createParticle(@Nonnull SpawnDustParticleOptions effect, @Nonnull ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            SpawnDustParticle<SpawnDustParticleOptions> particle = new SpawnDustParticle<>(clientWorld, x, y, z, effect, spriteProvider);
            particle.pickSprite(this.spriteProvider);
            return particle;
        }
    }


}
