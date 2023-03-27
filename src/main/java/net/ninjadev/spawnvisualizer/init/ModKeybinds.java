package net.ninjadev.spawnvisualizer.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static KeyBinding TOGGLE;
    public static KeyBinding OPEN_MENU;

    public static void init() {
        SpawnVisualizer.LOGGER.info("Initialize Spawn Visualizer Keybinds");
        TOGGLE = createKeybind("key.spawnvisualizer.toggle", GLFW.GLFW_KEY_APOSTROPHE, "category.spawnvisualizer.title");
        OPEN_MENU = createKeybind("key.spawnvisualizer.open_menu", GLFW.GLFW_KEY_M, "category.spawnvisualizer.title");
    }

    private static KeyBinding createKeybind(String label, int key, String category) {
        KeyBinding keyBinding = new KeyBinding(label, InputMappings.Type.KEYSYM, key, category);
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }

}
