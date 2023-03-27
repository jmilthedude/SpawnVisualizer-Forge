package net.ninjadev.spawnvisualizer.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticle;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleType;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SpawnVisualizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {


    public static final SpawnDustParticleType SPAWN_DUST = register("spawn_dust", new SpawnDustParticleType(true));

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void register(ParticleFactoryRegisterEvent event) {
        SpawnVisualizer.LOGGER.info("Registering Particles");
        Minecraft.getInstance().particleEngine.register(SPAWN_DUST, SpawnDustParticle.Factory::new);
    }

    private static <T extends ParticleType<?>> T register(String name, T particle) {
        particle.setRegistryName(name);
        return particle;
    }

    public static final ResourceLocation PARTICLE_SHEET = new ResourceLocation("textures/atlas/particles.png");

    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event) throws IOException {
        if (!event.getMap().location().equals(PARTICLE_SHEET)) return;

        event.addSprite(SpawnVisualizer.id("particle/spawn_dust"));

        for (int i = 0; i < 8; i++) {
            event.addSprite(SpawnVisualizer.id("particle/dust_" + i));
        }
    }
}
