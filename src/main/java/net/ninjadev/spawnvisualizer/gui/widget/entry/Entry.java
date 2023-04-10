package net.ninjadev.spawnvisualizer.gui.widget.entry;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.gui.widget.ScrollingListWidget;

public abstract class Entry extends AbstractWidget {
    public static final int BUTTON_WIDTH = 90;
    public static final int BUTTON_HEIGHT = 24;
    protected boolean selected;
    private ScrollingListWidget parent;

    public Entry(int x, int y, Component message, ScrollingListWidget parent) {
        super(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, message);
        this.parent = parent;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return this.getX() <= mouseX && mouseX <= this.getX() + BUTTON_WIDTH
                && this.getY() <= mouseY && mouseY <= this.getY() + BUTTON_HEIGHT;
    }

    @Override
    public void renderWidget(PoseStack matrices, int mouseX, int mouseY, float delta) {
        Minecraft client = Minecraft.getInstance();

        boolean isHovered = isHovered(mouseX, mouseY);

        RenderSystem.setShaderTexture(0, ConfigScreen.HUD_RESOURCE);

        blit(matrices, this.getX(), this.getY(), 0, selected ? 24 : 48, BUTTON_WIDTH, BUTTON_HEIGHT, 256, 256);

//        if (isHovered) {
//            drawTexture(matrices, x, y, 0, 48, BUTTON_WIDTH, BUTTON_HEIGHT, 256, 256);
//        }

        RenderSystem.disableDepthTest();
        float startX = (this.getX() + (BUTTON_WIDTH / 2f) - (client.font.width(getMessage()) / 2f));
        client.font.drawShadow(matrices, getMessage(), startX, this.getY() + 8, isHovered ? 0xFF_FFFF00 : 0xFF_FFFFFF);
        RenderSystem.enableDepthTest();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput ignored) {

    }
}
