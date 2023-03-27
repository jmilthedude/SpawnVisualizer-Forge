package net.ninjadev.spawnvisualizer.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.init.ModParticles;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Random;

public class SpawnDustParticle<T extends SpawnDustParticleOptions>
        extends SpriteTexturedParticle {

    private final IAnimatedSprite spriteProvider;

    public SpawnDustParticle(ClientWorld clientWorld, double x, double y, double z, T parameters, IAnimatedSprite spriteProvider) {
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
        return LightTexture.pack(15, 15);
    }

    @Nonnull
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public static class Factory implements IParticleFactory<SpawnDustParticleOptions> {
        private final IAnimatedSprite spriteProvider;

        public Factory(IAnimatedSprite ignored) {
            this.spriteProvider = new IAnimatedSprite() {

                @Override
                @Nonnull
                public TextureAtlasSprite get(int pAge, int pLifetime) {
                    AtlasTexture texture = (AtlasTexture) Minecraft.getInstance().textureManager.getTexture(ModParticles.PARTICLE_SHEET);
                    int spriteId = (pAge * 7) / pLifetime;
                    return texture.getSprite(SpawnVisualizer.id("particle/spawn_dust"));
                }

                @Override
                @Nonnull
                public TextureAtlasSprite get(Random random) {
                    AtlasTexture texture = (AtlasTexture) Minecraft.getInstance().textureManager.getTexture(ModParticles.PARTICLE_SHEET);
                    return texture.getSprite(SpawnVisualizer.id("particle/spawn_dust"));
                }
            };
        }

        @Override
        public Particle createParticle(@Nonnull SpawnDustParticleOptions effect, @Nonnull ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            SpawnDustParticle<SpawnDustParticleOptions> particle = new SpawnDustParticle<>(clientWorld, x, y, z, effect, spriteProvider);
            particle.pickSprite(this.spriteProvider);
            return particle;
        }
    }


}
