package net.ninjadev.spawnvisualizer.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.ninjadev.spawnvisualizer.gui.ConfigScreen;
import net.ninjadev.spawnvisualizer.gui.widget.entry.Entry;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ScrollingListWidget extends AbstractGui implements IRenderable, IGuiEventListener {

    protected final Rectangle bounds;
    private final List<Entry> entries = new ArrayList<>();
    protected final int scrollBarWidth = 8;
    private boolean scrolling;
    protected int yOffset;
    private double scrollAmount;
    protected double scrollingStartY;
    protected int scrollingOffsetY;

    public ScrollingListWidget(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width + scrollBarWidth, height);

        init();
    }

    public int getYOffset() {
        return yOffset;
    }

    public boolean isScrolling() {
        return scrolling;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    protected Rectangle getRenderableBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width - scrollBarWidth, bounds.height);
    }

    protected Rectangle getScrollableBounds() {
        return new Rectangle(bounds.x, bounds.y, bounds.width - scrollBarWidth, 10 + (entries.size() * 24));
    }

    protected Rectangle getScrollbarBounds() {
        return new Rectangle(bounds.x + bounds.width - scrollBarWidth, bounds.y, scrollBarWidth, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    private boolean isScrollbarClicked(double mouseX, double mouseY) {
        return getScrollbarBounds().contains(mouseX, mouseY);
    }


    public int getSize() {
        return entries.size();
    }

    protected abstract void init();

    protected void addEntry(Entry entry) {
        this.entries.add(entry);
    }


    @Override
    public void render(@Nonnull MatrixStack matrices, int mouseX, int mouseY, float delta) {
        //drawBounds(matrices);

        Rectangle renderableBounds = getRenderableBounds();
        Rectangle scrollBounds = getScrollbarBounds();
        Rectangle scrollableBounds = getScrollableBounds();

        renderOverflowHidden(matrices,
                (ms) -> fill(ms, renderableBounds.x + 1, renderableBounds.y + 1, renderableBounds.x + renderableBounds.width, renderableBounds.y + renderableBounds.height, 0x01_000000),
                (ms) -> {
                    ms.pushPose();
                    ms.translate(0, -yOffset, 0);

                    int containerX = mouseX - scrollableBounds.x;
                    int containerY = mouseY - scrollableBounds.y + yOffset;

                    entries.forEach(entry -> entry.render(matrices, containerX, containerY, delta));
                    ms.popPose();
                });


        float scrollPercentage = (float) yOffset / (scrollableBounds.height - scrollBounds.height);
        float viewportRatio = (float) renderableBounds.getHeight() / scrollableBounds.height;
        int scrollHeight = (int) (renderableBounds.height * viewportRatio);

        if (viewportRatio <= 1) {

            Minecraft.getInstance().getTextureManager().bind(ConfigScreen.HUD_RESOURCE);
            matrices.pushPose();
            matrices.translate(scrollBounds.x, scrollBounds.y, 0);
            matrices.scale(1, scrollBounds.height, 1);
            blit(matrices, 1, 0, 0, 146, 8, 1);
            matrices.popPose();
            blit(matrices, scrollBounds.x + 1, scrollBounds.y, 0, 145, 8, 1);
            blit(matrices, scrollBounds.x + 1, scrollBounds.y + scrollBounds.height, 0, 251, 8, 1);

            int scrollU = scrolling ? 28 : scrollBounds.contains(mouseX, mouseY) ? 18 : 8;

            matrices.pushPose();
            matrices.translate(0, (scrollBounds.getHeight() - scrollHeight) * scrollPercentage, 0);
            blit(matrices, scrollBounds.x + 1, scrollBounds.y,
                    scrollU, 104,
                    8, scrollHeight);
            blit(matrices, scrollBounds.x + 1, scrollBounds.y - 2,
                    scrollU, 101,
                    8, 2);
            blit(matrices, scrollBounds.x + 1, scrollBounds.y + scrollHeight,
                    scrollU, 253,
                    8, 2);
            matrices.popPose();
        }
    }

    private void renderOverflowHidden(MatrixStack matrices, Consumer<MatrixStack> backgroundRenderer, Consumer<MatrixStack> innerRenderer) {
        matrices.pushPose();
        RenderSystem.enableDepthTest();

        matrices.translate(0, 0, 950);
        RenderSystem.colorMask(false, false, false, false);
        AbstractGui.fill(matrices, 4680, 2260, -4680, -2260, 0xff_000000);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0, 0, -950);

        RenderSystem.depthFunc(GL11.GL_GEQUAL);
        backgroundRenderer.accept(matrices);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        innerRenderer.accept(matrices);
        RenderSystem.depthFunc(GL11.GL_GEQUAL);

        matrices.translate(0, 0, -950);
        RenderSystem.colorMask(false, false, false, false);
        AbstractGui.fill(matrices, 4680, 2260, -4680, -2260, 0xff_000000);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0, 0, 950);
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        RenderSystem.disableDepthTest();
        matrices.popPose();
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isScrollbarClicked(mouseX, mouseY)) {
            scrollingStartY = mouseY;
            scrollingOffsetY = yOffset;
            scrolling = true;
            return true;
        }
        for (Entry entry1 : entries) {
            if (entry1.isHovered(MathHelper.floor(mouseX), MathHelper.floor(mouseY + yOffset))) {
                entry1.mouseClicked(mouseX, mouseY + scrollingOffsetY, button);
                Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }
        return IGuiEventListener.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        scrolling = false;
        return IGuiEventListener.super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (scrolling) {
            double dY = mouseY - scrollingStartY;
            Rectangle scrollableBounds = getScrollableBounds();
            Rectangle renderableBounds = getRenderableBounds();
            Rectangle scrollbarBounds = getScrollbarBounds();
            double deltaOffset = dY * renderableBounds.height / scrollbarBounds.getHeight();

            yOffset = MathHelper.clamp(scrollingOffsetY + (int) (deltaOffset * scrollableBounds.height / scrollbarBounds.height),
                    0,
                    scrollableBounds.height - renderableBounds.height + 2);
        }
        return IGuiEventListener.super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        Rectangle scrollableBounds = getScrollableBounds();
        Rectangle renderableBounds = getRenderableBounds();
        float viewportRatio = (float) renderableBounds.getHeight() / scrollableBounds.height;

        if (viewportRatio < 1) {
            yOffset = MathHelper.clamp(yOffset + (int) (-delta * 5),
                    0,
                    scrollableBounds.height - renderableBounds.height + 2);
            return true;
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return bounds.contains(mouseX, mouseY);
    }

    private void drawBounds(MatrixStack matrices) {
        // scroll

        drawRectangle(matrices, getRenderableBounds(), 0xFF_00FFFF);
        drawRectangle(matrices, getScrollableBounds(), 0xFF_FF0000);
        drawRectangle(matrices, getScrollbarBounds(), 0xFF_00FF00);


    }

    private void drawRectangle(MatrixStack matrices, Rectangle bounds, int color) {
        hLine(matrices, bounds.x, bounds.x + bounds.width, bounds.y, color);
        hLine(matrices, bounds.x, bounds.x + bounds.width, bounds.y + bounds.height, color);
        vLine(matrices, bounds.x, bounds.y, bounds.y + bounds.height, color);
        vLine(matrices, bounds.x + bounds.width, bounds.y, bounds.y + bounds.height, color);
    }
}
