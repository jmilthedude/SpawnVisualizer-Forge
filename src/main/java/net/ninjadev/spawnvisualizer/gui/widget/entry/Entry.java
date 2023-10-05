package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;

public abstract class Entry implements Renderable, GuiEventListener {
    public static final int BUTTON_WIDTH = 90;
    public static final int BUTTON_HEIGHT = 24;
    protected boolean selected;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Component message;

    protected abstract void onClick(double mouseX, double mouseY);

    public Entry(int x, int y, Component message) {
        this.x = x;
        this.y = y;
        this.width = BUTTON_WIDTH;
        this.height = BUTTON_HEIGHT;
        this.message = message;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Component getMessage() {
        return message;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return this.getX() <= mouseX && mouseX <= this.getX() + BUTTON_WIDTH
                && this.getY() <= mouseY && mouseY <= this.getY() + BUTTON_HEIGHT;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        boolean isHovered = isHovered(mouseX, mouseY);
        graphics.blit(ConfigScreen.HUD_RESOURCE, this.getX(), this.getY(), 0, selected ? 24 : 48, BUTTON_WIDTH, BUTTON_HEIGHT, 256, 256);
        float startX = (this.getX() + (BUTTON_WIDTH / 2f) - (Minecraft.getInstance().font.width(getMessage()) / 2f));
        graphics.drawString(Minecraft.getInstance().font, getMessage().getVisualOrderText(), (int) startX, this.getY() + 8, isHovered ? 0xFF_FFFF00 : 0xFF_FFFFFF);
    }

    @Override
    public void setFocused(boolean p_265728_) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }
}
