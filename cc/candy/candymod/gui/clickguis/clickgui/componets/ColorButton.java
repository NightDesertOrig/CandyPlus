//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui.componets;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.utils.*;
import cc.candy.candymod.gui.clickguis.clickgui.*;

public class ColorButton extends Component
{
    private Module module;
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
    
    public ColorButton(final Module module, final Setting<Color> setting, final int x, final int width, final int height) {
        this.isOpening = false;
        this.alphaChanging = false;
        this.aLx = 0.0f;
        this.module = module;
        this.setting = setting;
        this.x = x;
        this.width = width;
        this.height = height;
        this.rLx = setting.getValue().getRed() / 255.0f;
        this.gLx = setting.getValue().getGreen() / 255.0f;
        this.bLx = setting.getValue().getBlue() / 255.0f;
        this.aLx = setting.getValue().getAlpha() / 255.0f;
    }
    
    public void onRender2D(final int y) {
        this.visible = this.setting.visible();
        if (this.visible) {
            this.y = y;
            RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, this.enabledColor);
            final float centeredY = y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
            final float rcy1 = y + (this.height - 11) / 2.0f;
            final float rcy2 = y + (this.height - 9) / 2.0f;
            RenderUtil.drawRect((float)(this.x + this.width - 21), rcy1, 12.0f, 12.0f, this.outlineColor);
            RenderUtil.drawRect((float)(this.x + this.width - 20), rcy2, 10.0f, 10.0f, ColorUtil.toRGBA(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), this.setting.getValue().getAlpha())));
            RenderUtil.drawString(this.setting.name, (float)(this.x + 5), centeredY, this.whiteColor, false, 1.0f);
            if (this.isOpening) {
                final float colorFieldHeight = 93.0f;
                Panel.Cy += (int)colorFieldHeight;
                final float colorFieldY = (float)(y + this.height);
                final float colorFieldHeightY = colorFieldY + colorFieldHeight;
                RenderUtil.drawRect((float)this.x, colorFieldY, (float)this.width, colorFieldHeight, this.defaultColor);
                final int gray = ColorUtil.toRGBA(50, 50, 50);
                RenderUtil.drawString("Red", (float)(this.x + 5), colorFieldY + 5.0f, this.whiteColor, false, 1.0f);
                RenderUtil.drawRect((float)(this.x + 10), colorFieldY + 15.0f, (float)(this.width - 20), 5.0f, gray);
                RenderUtil.drawRect(this.x + 10 + (this.width - 20) * this.rLx, colorFieldY + 13.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
                RenderUtil.drawString("Green", (float)(this.x + 5), colorFieldY + 27.0f, this.whiteColor, false, 1.0f);
                RenderUtil.drawRect((float)(this.x + 10), colorFieldY + 37.0f, (float)(this.width - 20), 5.0f, gray);
                RenderUtil.drawRect(this.x + 10 + (this.width - 20) * this.gLx, colorFieldY + 35.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
                RenderUtil.drawString("Blue", (float)(this.x + 5), colorFieldY + 49.0f, this.whiteColor, false, 1.0f);
                RenderUtil.drawRect((float)(this.x + 10), colorFieldY + 59.0f, (float)(this.width - 20), 5.0f, gray);
                RenderUtil.drawRect(this.x + 10 + (this.width - 20) * this.bLx, colorFieldY + 57.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
                RenderUtil.drawString("Alpha", (float)(this.x + 5), colorFieldY + 71.0f, this.whiteColor, false, 1.0f);
                RenderUtil.drawRect((float)(this.x + 10), colorFieldY + 81.0f, (float)(this.width - 20), 5.0f, gray);
                RenderUtil.drawRect(this.x + 10 + (this.width - 20) * this.aLx, colorFieldY + 79.0f, 3.0f, 9.0f, ColorUtil.toRGBA(255, 255, 255));
                RenderUtil.drawLine((float)this.x, (float)y, (float)(this.x + this.width), (float)y, 2.0f, this.outlineColor);
                RenderUtil.drawLine((float)this.x, colorFieldY, (float)(this.x + this.width), colorFieldY, 2.0f, this.outlineColor);
                RenderUtil.drawLine((float)this.x, (float)y, (float)this.x, colorFieldHeightY, 2.0f, this.outlineColor);
                RenderUtil.drawLine((float)(this.x + this.width), (float)y, (float)(this.x + this.width), colorFieldHeightY, 2.0f, this.outlineColor);
            }
            else {
                this.drawOutLine();
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final float colorFieldHeight = 70.0f;
        final float colorFieldY = (float)(this.y + this.height);
        final float colorFieldHeightY = colorFieldY + colorFieldHeight;
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 1) {
            this.isOpening = !this.isOpening;
        }
        if (!this.isOpening) {
            return;
        }
        if (this.isMouseHovering(mouseX, mouseY, this.x + 8, (int)(colorFieldY + 7.0f), this.width - 12, 15)) {
            this.redChanging = true;
        }
        if (this.isMouseHovering(mouseX, mouseY, this.x + 8, (int)(colorFieldY + 29.0f), this.width - 12, 15)) {
            this.greenChanging = true;
        }
        if (this.isMouseHovering(mouseX, mouseY, this.x + 8, (int)(colorFieldY + 51.0f), this.width - 12, 15)) {
            this.blueChanging = true;
        }
        if (this.isMouseHovering(mouseX, mouseY, this.x + 8, (int)(colorFieldY + 73.0f), this.width - 12, 15)) {
            this.alphaChanging = true;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.redChanging = false;
        this.greenChanging = false;
        this.blueChanging = false;
        this.alphaChanging = false;
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (!this.isOpening) {
            return;
        }
        final float colorFieldHeight = 70.0f;
        final float colorFieldY = (float)(this.y + this.height);
        final float colorFieldHeightY = colorFieldY + colorFieldHeight;
        if (this.x + 8 < mouseX && this.x + this.width - 12 > mouseX && clickedMouseButton == 0 && this.redChanging) {
            this.rLx = (mouseX - (float)(this.x + 10)) / (this.width - 20.0f);
            if (this.rLx > 1.0f) {
                this.rLx = 1.0f;
            }
            if (this.rLx < 0.0f) {
                this.rLx = 0.0f;
            }
            this.setting.setValue(new Color((int)(Math.round(255.0f * this.rLx * 10.0f) / 10.0f), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), this.setting.getValue().getAlpha()));
        }
        if (this.x + 8 < mouseX && this.x + this.width - 12 > mouseX && clickedMouseButton == 0 && this.greenChanging) {
            this.gLx = (mouseX - (float)(this.x + 10)) / (this.width - 20.0f);
            if (this.gLx > 1.0f) {
                this.gLx = 1.0f;
            }
            if (this.gLx < 0.0f) {
                this.gLx = 0.0f;
            }
            this.setting.setValue(new Color(this.setting.getValue().getRed(), (int)(Math.round(255.0f * this.gLx * 10.0f) / 10.0f), this.setting.getValue().getBlue(), this.setting.getValue().getAlpha()));
        }
        if (this.x + 8 < mouseX && this.x + this.width - 12 > mouseX && clickedMouseButton == 0 && this.blueChanging) {
            this.bLx = (mouseX - (float)(this.x + 10)) / (this.width - 20.0f);
            if (this.bLx > 1.0f) {
                this.bLx = 1.0f;
            }
            if (this.bLx < 0.0f) {
                this.bLx = 0.0f;
            }
            this.setting.setValue(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), (int)(Math.round(255.0f * this.bLx * 10.0f) / 10.0f), this.setting.getValue().getAlpha()));
        }
        if (this.x + 8 < mouseX && this.x + this.width - 12 > mouseX && clickedMouseButton == 0 && this.alphaChanging) {
            this.aLx = (mouseX - (float)(this.x + 10)) / (this.width - 20.0f);
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
