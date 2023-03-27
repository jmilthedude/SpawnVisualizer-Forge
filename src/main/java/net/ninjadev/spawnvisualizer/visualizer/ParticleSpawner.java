package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.ninjadev.spawnvisualizer.particle.SpawnDustParticleOptions;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class ParticleSpawner {

    public void spawnParticles(Map<BlockPos, List<Color>> positions) {
        positions.forEach(this::spawn);
    }

    public void spawn(BlockPos pos, List<Color> colors) {
        ClientWorld world = Minecraft.getInstance().level;
        if (world == null) return;

        //TODO: Add simple version for less particles

        colors.forEach(color -> {
            Vector3d colorVec = Vector3d.fromRGB24(color.getRGB());
            SpawnDustParticleOptions particleData = new SpawnDustParticleOptions((float) colorVec.x, (float) colorVec.y, (float) colorVec.z, 1.5f);

            double width = .4D;
            double items = colors.size();
            double index = colors.indexOf(color);

            double start = items == 1 ? 0.5D : (1 - width) / 2;

            double distance = items == 1 ? 0 : (width / (items - 1));

            double x = pos.getX() + start + (index * distance);
            double y = pos.getY() + .1d;
            double z = pos.getZ() + start + (index * distance);
            world.addAlwaysVisibleParticle(particleData, x, y, z, 0, 0, 0);
        });
    }

}
