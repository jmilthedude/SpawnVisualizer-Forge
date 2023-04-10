package net.ninjadev.spawnvisualizer.init;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticle;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleType;

@Mod.EventBusSubscriber(modid = SpawnVisualizer.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {


    public static final SpawnDustParticleType SPAWN_DUST = register("spawn_dust", new SpawnDustParticleType(true));

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void register(RegisterParticleProvidersEvent event) {
        SpawnVisualizer.LOGGER.info("Registering Particles");
        Minecraft.getInstance().particleEngine.register(SPAWN_DUST, SpawnDustParticle.Factory::new);
    }

    private static <T extends ParticleType<?>> T register(String name, T particle) {
        return particle;
    }

    public static final ResourceLocation PARTICLE_SHEET = new ResourceLocation("textures/atlas/particles.png");

//    @SubscribeEvent
//    public static void textureStitch(TextureStitchEvent event) throws IOException {
//        if (!event.getAtlas().location().equals(PARTICLE_SHEET)) return;
//        event.addSprite(SpawnVisualizer.id("particle/spawn_dust"));
//
//        for (int i = 0; i < 8; i++) {
//            event.addSprite(SpawnVisualizer.id("particle/dust_" + i));
//        }
//    }
}
