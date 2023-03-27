package net.ninjadev.spawnvisualizer.settings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.ninjadev.spawnvisualizer.config.MobSettingsConfig.MobConfig;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.util.SpawnUtils;

import java.awt.*;

public class SpawnValidator {

    private final EntityType<?> type;
    private final MobConfig config;
    private boolean enabled;

    public SpawnValidator(EntityType<?> type, MobConfig config) {
        this.type = type;
        this.config = config;
    }

    public EntityType<?> getType() {
        return type;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Color getColor() {
        return Color.decode("#" + config.getHexColor());
    }

    public boolean canSpawn(World level, BlockPos pos) {
        if (this.getType().equals(EntityType.SLIME)) return canSlimeSpawn(level, pos);
        if (this.getType().equals(EntityType.DROWNED)) {
            if (!validDrownedSpawnHeight(level, pos)) return false;
        }
        return validDimension(level)
                && validBiome(level, pos)
                && validPosition(level, pos)
                && validLightLevel(level, pos);
    }

    private boolean validDrownedSpawnHeight(World level, BlockPos pos) {
        ResourceLocation biomeId = level.getBiomeName(pos).map(RegistryKey::location).orElse(null);
        if (biomeId == null || !SpawnUtils.isOceanBiome(biomeId)) return true;

        return pos.getY() < 58;
    }

    private boolean canSlimeSpawn(World level, BlockPos pos) {
        if (!validDimension(level)) return false;
        if (isSlimeChunk(level, pos)) {
            return pos.getY() < 40 && validPosition(level, pos);
        } else {
            return validBiome(level, pos) && validPosition(level, pos) && validLightLevel(level, pos);
        }
    }

    protected boolean validDimension(World level) {
        return config.getValidDimensions().contains(getWorldId(level));
    }

    protected boolean validBiome(World level, BlockPos pos) {
        ResourceLocation biomeId = level.getBiomeName(pos).map(RegistryKey::location).orElse(null);
        if (biomeId == null) return false;
        if (config.getBlacklistedBiomes(getWorldId(level)).contains(biomeId)) return false;
        return config.getWhitelistedBiomes(getWorldId(level)).isEmpty() || config.getWhitelistedBiomes(getWorldId(level)).contains(biomeId);
    }

    protected boolean validPosition(World world, BlockPos pos) {
        if (WorldEntitySpawner.isSpawnPositionOk(EntitySpawnPlacementRegistry.getPlacementType(this.getType()), world, pos, this.getType())) {
            Entity entity = this.getType().create(world);
            return world.noCollision(entity, this.getType().getAABB((double) pos.getX() + 0.5, Math.floor(pos.getY()), (double) pos.getZ() + 0.5));
        }
        return false;
    }

    protected boolean validLightLevel(World level, BlockPos pos) {
        int currentLight = level.getBrightness(LightType.BLOCK, pos);
        return currentLight <= config.getLightLevelByDimension(level.dimension().location());
    }

    private ResourceLocation getWorldId(World level) {
        return level.dimension().location();
    }


    public static boolean isSlimeChunk(World world, BlockPos pos) {
        return SharedSeedRandom.seedSlimeChunk(world.getChunk(pos).getPos().x, world.getChunk(pos).getPos().z, ModConfigs.GENERAL.getSeed(), 987234911L).nextInt(10) == 0;
    }

}
