package net.ninjadev.spawnvisualizer.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ninjadev.spawnvisualizer.SpawnVisualizer;
import net.ninjadev.spawnvisualizer.gui.widget.HorizontalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.MobSettingListWidget;
import net.ninjadev.spawnvisualizer.gui.widget.VerticalRangeSlider;
import net.ninjadev.spawnvisualizer.gui.widget.entry.OptionEntry;
import net.ninjadev.spawnvisualizer.init.ModConfigs;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

public class ConfigScreen extends Screen {

    public static final ResourceLocation HUD_RESOURCE = new ResourceLocation(SpawnVisualizer.MOD_ID, "textures/gui/config_screen.png");

    private MobSettingListWidget mobList;

    public ConfigScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        int buttonY = 40;
        int buttonHeight = 24;

        OptionEntry enableButton = new OptionEntry(this.width / 2 - 45, buttonY, Component.nullToEmpty("Toggle On/Off"));

        this.addWidget(enableButton);
        buttonY += buttonHeight + 4;

        int horizontalRange = ModConfigs.GENERAL.getRangeHorizontal();
        HorizontalRangeSlider horizontal = new HorizontalRangeSlider(this.width / 2 - 45, buttonY, horizontalRange);
        this.addWidget(horizontal);
        buttonY += buttonHeight;

        int verticalRange = ModConfigs.GENERAL.getRangeVertical();
        VerticalRangeSlider vertical = new VerticalRangeSlider(this.width / 2 - 45, buttonY, verticalRange);
        this.addWidget(vertical);
        buttonY += buttonHeight;

        mobList = new MobSettingListWidget((this.width / 2) - 135, buttonY, 270, this.height - 15);
        this.addWidget(mobList);
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double p_94688_, double p_299502_) {
        if (mobList.isMouseOver(p_94686_, p_94687_)) {
            return mobList.mouseScrolled(p_94686_, p_94687_, p_94688_, p_299502_);
        }
        return super.mouseScrolled(p_94686_, p_94687_, p_94688_, p_299502_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mobList.isMouseOver(mouseX, mouseY)) {
            return mobList.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            return mobList.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return mobList.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void render(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        for (GuiEventListener child : this.children()) {
            if(child instanceof Renderable widget) {
                widget.render(graphics, mouseX, mouseY, delta);
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_M) {
            Minecraft.getInstance().setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        ModConfigs.GENERAL.writeConfig();
        super.onClose();
    }
}
