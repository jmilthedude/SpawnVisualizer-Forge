package net.ninjadev.spawnvisualizer.gui.widget.entry;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.ninjadev.spawnvisualizer.settings.SpawnValidator;
import org.jetbrains.annotations.NotNull;

public class MobEntry extends Entry {

    private final SpawnValidator validator;

    public MobEntry(int x, int y, SpawnValidator validator) {
        super(x, y, Component.translatable(validator.getType().getDescriptionId()));
        this.validator = validator;
        this.selected = validator.isEnabled();
        this.setX(x);
        this.setY(y);
    }

    public void onClick(double mouseX, double mouseY) {
        validator.toggle();
        this.selected = validator.isEnabled();
    }

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
        return super.isHovered(mouseX, mouseY);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        if (selected) {
            graphics.hLine(this.getX(), this.getX() + BUTTON_WIDTH - 1, this.getY(), validator.getColor().getRGB());
            graphics.hLine(this.getX(), this.getX() + BUTTON_WIDTH - 1, this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            graphics.vLine(this.getX(), this.getY(), this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
            graphics.vLine(this.getX() + BUTTON_WIDTH - 1, this.getY(), this.getY() + BUTTON_HEIGHT - 1, validator.getColor().getRGB());
        }
    }
}
