package net.ninjadev.spawnvisualizer.gui.widget.entry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.init.ModConfigs;

public class OptionEntry extends Entry {

    public OptionEntry(int x, int y, Component message) {
        super(x, y, message, null);
        this.selected = ModConfigs.GENERAL.isEnabled();
    }

    @Override
    public void renderWidget(PoseStack p_268228_, int p_268034_, int p_268009_, float p_268085_) {
super.renderWidget(p_268228_, p_268034_, p_268009_, p_268085_);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.selected = ModConfigs.GENERAL.toggleEnabled();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}
