package net.ninjadev.spawnvisualizer.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModKeybinds {
    public static KeyMapping TOGGLE = createKeybind("key.spawnvisualizer.toggle", GLFW.GLFW_KEY_APOSTROPHE, "category.spawnvisualizer.title");
    public static KeyMapping OPEN_MENU = createKeybind("key.spawnvisualizer.open_menu", GLFW.GLFW_KEY_M, "category.spawnvisualizer.title");

    private static KeyMapping createKeybind(String label, int key, String category) {
        return new KeyMapping(label, InputConstants.Type.KEYSYM, key, category);
    }

    @SubscribeEvent
    public static void onRegisterKeybinds(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE);
        event.register(OPEN_MENU);
    }

}
