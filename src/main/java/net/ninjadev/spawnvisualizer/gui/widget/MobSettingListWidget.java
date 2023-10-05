package net.ninjadev.spawnvisualizer.gui.widget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ninjadev.spawnvisualizer.gui.widget.entry.MobEntry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import net.ninjadev.spawnvisualizer.init.ModSpawnValidators;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MobSettingListWidget extends AbstractWidget {

    private List<MobEntry> entries = new ArrayList<>();

    public MobSettingListWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());

        init();
    }

    protected void init() {
        int buttonX = this.getX();
        int buttonY = this.getY() + 5;
        int buttonHeight = 24;
        List<ResourceLocation> keys = new ArrayList<>(ModConfigs.MOB_SETTINGS.getEntityIds());
        Collections.sort(keys);

        int maxHorizontalButtons = 3;
        int buttonCount = 1;
        for (ResourceLocation key : keys) {
            this.createButton(key, buttonX, buttonY);
            if (buttonCount != maxHorizontalButtons) {
                buttonX += 90;
                buttonCount++;
            } else {
                buttonY += buttonHeight;
                buttonCount = 1;
                buttonX = this.getX();
            }

        }
    }

    private void createButton(ResourceLocation id, int buttonX, int buttonY) {
        SpawnValidator validator = ModSpawnValidators.getValidator(id);
        MobEntry buttonEntry = new MobEntry(buttonX, buttonY, validator);
        this.entries.add(buttonEntry);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int x, int y, float partialTicks) {
//        super.render(graphics, x, y, partialTicks);
        for (MobEntry entry : this.entries) {
            entry.render(graphics, x, y, partialTicks);
        }
    }

    @Override
    protected void renderWidget(GuiGraphics p_282139_, int p_268034_, int p_268009_, float p_268085_) {

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

    @Override
    public boolean mouseClicked(double x, double y, int buttonId) {
        for (MobEntry entry : this.entries) {
            if (entry.isHovered((int) x, (int) y)) {
                entry.onClick(x, y);
            }
        }
        return super.mouseClicked(x, y, buttonId);
    }
}
