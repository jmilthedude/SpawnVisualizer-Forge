package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class OptionEntry extends AbstractButton {
    public static final int BUTTON_WIDTH = 90;
    public static final int BUTTON_HEIGHT = 24;

    private boolean selected;

    public OptionEntry(int x, int y, Component message) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, message);
        this.selected = ModConfigs.GENERAL.isEnabled();
    }

    @Override
    public void onPress() {
        this.selected = ModConfigs.GENERAL.toggleEnabled();
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float partialTicks) {
        super.render(graphics, x, y, partialTicks);
        graphics.blit(ConfigScreen.HUD_RESOURCE, this.getX(), this.getY(), 0, selected ? 24 : 48, BUTTON_WIDTH, BUTTON_HEIGHT, 256, 256);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

}
