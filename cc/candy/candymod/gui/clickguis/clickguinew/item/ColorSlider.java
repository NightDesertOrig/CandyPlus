//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew.item;

import cc.candy.candymod.gui.clickguis.clickguinew.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.utils.*;

public class ColorSlider extends Component
{
    private Setting<Color> setting;
    private boolean isOpening;
    private boolean redChanging;
    private boolean greenChanging;
    private boolean blueChanging;
    private boolean alphaChanging;
    private float rLx;
    private float gLx;
    private float bLx;
    private float aLx;
    
    public ColorSlider(final Setting<Color> setting, final float x) {
        this.isOpening = false;
        this.alphaChanging = false;
        this.aLx = 0.0f;
        this.setting = setting;
        this.x = x;
        this.width = 100.0f;
        this.height = 16.0f;
        this.rLx = setting.getValue().getRed() / 255.0f;
        this.gLx = setting.getValue().getGreen() / 255.0f;
        this.bLx = setting.getValue().getBlue() / 255.0f;
        this.aLx = setting.getValue().getAlpha() / 255.0f;
    }
    
    public float doRender(final int mouseX, final int mouseY, final float x, final float y) {
        if (!this.setting.visible()) {
            return 0.0f;
        }
        RenderUtil.drawRect(x, this.y = y, this.width, this.height, ColorUtil.toRGBA(this.color));
        final float centeredY = y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
        final float rcy1 = y + (this.height - 11.0f) / 2.0f;
        final float rcy2 = y + (this.height - 9.0f) / 2.0f;
        RenderUtil.drawRect(x + this.width - 21.0f, rcy1, 12.0f, 12.0f, this.color0);
        RenderUtil.drawRect(x + this.width - 20.0f, rcy2, 10.0f, 10.0f, ColorUtil.toRGBA(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), this.setting.getValue().getAlpha())));
        RenderUtil.drawString(this.setting.name, x + 5.0f, centeredY, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
        if (this.isOpening) {
            final float colorFieldHeight = 93.0f;
            final float colorFieldY = y + this.height;
            final float colorFieldHeightY = colorFieldY + colorFieldHeight;
            RenderUtil.drawRect(x, colorFieldY, this.width, colorFieldHeight, this.color0);
            final int gray = ColorUtil.toRGBA(50, 50, 50);
            RenderUtil.drawString("Red", x + 5.0f, colorFieldY + 5.0f, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
            RenderUtil.drawRect(x + 10.0f, colorFieldY + 15.0f, this.width - 20.0f, 5.0f, gray);
            RenderUtil.drawRect(x + 10.0f + (this.width - 20.0f) * this.rLx, colorFieldY + 13.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
            RenderUtil.drawString("Green", x + 5.0f, colorFieldY + 27.0f, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
            RenderUtil.drawRect(x + 10.0f, colorFieldY + 37.0f, this.width - 20.0f, 5.0f, gray);
            RenderUtil.drawRect(x + 10.0f + (this.width - 20.0f) * this.gLx, colorFieldY + 35.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
            RenderUtil.drawString("Blue", x + 5.0f, colorFieldY + 49.0f, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
            RenderUtil.drawRect(x + 10.0f, colorFieldY + 59.0f, this.width - 20.0f, 5.0f, gray);
            RenderUtil.drawRect(x + 10.0f + (this.width - 20.0f) * this.bLx, colorFieldY + 57.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
            RenderUtil.drawString("Alpha", x + 5.0f, colorFieldY + 71.0f, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
            RenderUtil.drawRect(x + 10.0f, colorFieldY + 81.0f, this.width - 20.0f, 5.0f, gray);
            RenderUtil.drawRect(x + 10.0f + (this.width - 20.0f) * this.aLx, colorFieldY + 79.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
            return colorFieldHeight + this.height;
        }
        return this.height;
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final float colorFieldHeight = 70.0f;
        final float colorFieldY = this.y + this.height;
        final float colorFieldHeightY = colorFieldY + colorFieldHeight;
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 1) {
            this.isOpening = !this.isOpening;
        }
        if (!this.isOpening) {
            return;
        }
        if (this.isMouseHovering((float)mouseX, (float)mouseY, this.x + 8.0f, (float)(int)(colorFieldY + 7.0f), this.width - 12.0f, 15.0f)) {
            this.redChanging = true;
        }
        if (this.isMouseHovering((float)mouseX, (float)mouseY, this.x + 8.0f, (float)(int)(colorFieldY + 29.0f), this.width - 12.0f, 15.0f)) {
            this.greenChanging = true;
        }
        if (this.isMouseHovering((float)mouseX, (float)mouseY, this.x + 8.0f, (float)(int)(colorFieldY + 51.0f), this.width - 12.0f, 15.0f)) {
            this.blueChanging = true;
        }
        if (this.isMouseHovering((float)mouseX, (float)mouseY, this.x + 8.0f, (float)(int)(colorFieldY + 73.0f), this.width - 12.0f, 15.0f)) {
            this.alphaChanging = true;
        }
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int state) {
        this.redChanging = false;
        this.greenChanging = false;
        this.blueChanging = false;
        this.alphaChanging = false;
    }
    
    public void onMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (!this.isOpening) {
            return;
        }
        final float colorFieldHeight = 70.0f;
        final float colorFieldY = this.y + this.height;
        final float colorFieldHeightY = colorFieldY + colorFieldHeight;
        if (this.x + 8.0f < mouseX && this.x + this.width - 12.0f > mouseX && clickedMouseButton == 0 && this.redChanging) {
            this.rLx = (mouseX - (this.x + 10.0f)) / (this.width - 20.0f);
            if (this.rLx > 1.0f) {
                this.rLx = 1.0f;
            }
            if (this.rLx < 0.0f) {
                this.rLx = 0.0f;
            }
            this.setting.setValue(new Color((int)(Math.round(255.0f * this.rLx * 10.0f) / 10.0f), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), this.setting.getValue().getAlpha()));
        }
        if (this.x + 8.0f < mouseX && this.x + this.width - 12.0f > mouseX && clickedMouseButton == 0 && this.greenChanging) {
            this.gLx = (mouseX - (this.x + 10.0f)) / (this.width - 20.0f);
            if (this.gLx > 1.0f) {
                this.gLx = 1.0f;
            }
            if (this.gLx < 0.0f) {
                this.gLx = 0.0f;
            }
            this.setting.setValue(new Color(this.setting.getValue().getRed(), (int)(Math.round(255.0f * this.gLx * 10.0f) / 10.0f), this.setting.getValue().getBlue(), this.setting.getValue().getAlpha()));
        }
        if (this.x + 8.0f < mouseX && this.x + this.width - 12.0f > mouseX && clickedMouseButton == 0 && this.blueChanging) {
            this.bLx = (mouseX - (this.x + 10.0f)) / (this.width - 20.0f);
            if (this.bLx > 1.0f) {
                this.bLx = 1.0f;
            }
            if (this.bLx < 0.0f) {
                this.bLx = 0.0f;
            }
            this.setting.setValue(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), (int)(Math.round(255.0f * this.bLx * 10.0f) / 10.0f), this.setting.getValue().getAlpha()));
        }
        if (this.x + 8.0f < mouseX && this.x + this.width - 12.0f > mouseX && clickedMouseButton == 0 && this.alphaChanging) {
            this.aLx = (mouseX - (this.x + 10.0f)) / (this.width - 20.0f);
            if (this.aLx > 1.0f) {
                this.aLx = 1.0f;
            }
            if (this.aLx < 0.0f) {
                this.aLx = 0.0f;
            }
            this.setting.setValue(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), (int)(Math.round(255.0f * this.aLx * 10.0f) / 10.0f)));
        }
    }
}
