package net.ninjadev.spawnvisualizer.visualizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SpawnVisualizerEvent {

    static HashMap<BlockPos, List<Color>> positions;
    static ParticleSpawner particleSpawner;

    public static void tick(Minecraft minecraft) {
        checkKeyPresses(minecraft);

        if (!ModConfigs.GENERAL.isEnabled()) return;

        ClientWorld level = minecraft.level;
        if (level == null) return;

        showParticles();
        if (level.getGameTime() % 5 == 0) {
            scanPositions();
        }

    }

    private static void scanPositions() {
        CompletableFuture.supplyAsync(PositionScanner::new)
                .thenApply(PositionScanner::findSpawnablePositions)
                .thenAccept(map -> positions = map);
    }

    private static void showParticles() {
        if (positions != null && !positions.isEmpty()) {
            if (particleSpawner == null) particleSpawner = new ParticleSpawner();
            particleSpawner.spawnParticles(positions);
        }
    }

    private static void checkKeyPresses(Minecraft minecraft) {
        if (minecraft.screen != null) return;
        while (ModKeybinds.OPEN_MENU.consumeClick()) {
            minecraft.setScreen(new ConfigScreen(ITextComponent.nullToEmpty("Spawn Visualizer Options")));
        }
        while (ModKeybinds.TOGGLE.consumeClick()) {
            if (!ModConfigs.GENERAL.toggleEnabled()) {
                if (positions != null) positions.clear();
            }
        }
    }


}
