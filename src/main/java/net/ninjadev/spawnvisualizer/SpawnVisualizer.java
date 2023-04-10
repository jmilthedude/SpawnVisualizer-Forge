package net.ninjadev.spawnvisualizer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(SpawnVisualizer.MOD_ID)
public class SpawnVisualizer {

    public static final String MOD_ID = "spawnvisualizer";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Random RANDOM = new Random();

    public SpawnVisualizer() {
        //ModParticles.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
