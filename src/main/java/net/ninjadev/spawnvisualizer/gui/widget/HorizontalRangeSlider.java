package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class HorizontalRangeSlider extends RangeSliderWidget {

    public HorizontalRangeSlider(int x, int y, double value) {
        super(x, y, Entry.BUTTON_WIDTH, Entry.BUTTON_HEIGHT - 4, ITextComponent.nullToEmpty("Horizontal: " + value), value / 32d, 1, 32);
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        ITextComponent text = ITextComponent.nullToEmpty("Horizontal: " + MathHelper.clamp(Math.round(value * 32d), 1, 32));
        setMessage(text);
    }

    @Override
    protected void applyValue() {
        ModConfigs.GENERAL.setRangeHorizontal(value * 32d);
    }
}
