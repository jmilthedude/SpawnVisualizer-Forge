package net.ninjadev.spawnvisualizer.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModKeybinds;
import net.ninjadev.spawnvisualizer.init.ModSpawnValidators;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvent {

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        ModConfigs.init();
        ModKeybinds.init();
        ModSpawnValidators.init();
    }
}
