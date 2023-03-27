package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.util.text.ITextComponent;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class OptionEntry extends Entry {

    public OptionEntry(int x, int y, ITextComponent message) {
        super(x, y, message, null);
        this.selected = ModConfigs.GENERAL.isEnabled();
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.selected = ModConfigs.GENERAL.toggleEnabled();
    }
}
