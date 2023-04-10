package net.ninjadev.spawnvisualizer.util;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;

import java.util.ArrayList;
import java.util.List;

public class SpawnUtils {

    public static List<ResourceLocation> getOceanBiomes() {
        List<ResourceLocation> list = new ArrayList<>();
        list.add(Biomes.OCEAN.location());
        list.add(Biomes.DEEP_OCEAN.location());
        list.add(Biomes.WARM_OCEAN.location());
        list.add(Biomes.LUKEWARM_OCEAN.location());
        list.add(Biomes.DEEP_LUKEWARM_OCEAN.location());
        list.add(Biomes.COLD_OCEAN.location());
        list.add(Biomes.DEEP_COLD_OCEAN.location());
        list.add(Biomes.FROZEN_OCEAN.location());
        list.add(Biomes.DEEP_FROZEN_OCEAN.location());
        return list;
    }

    public static boolean isOceanBiome(ResourceLocation biomeId) {
        return SpawnUtils.getOceanBiomes().contains(biomeId);
    }
}
