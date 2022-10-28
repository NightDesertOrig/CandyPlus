//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui;

import cc.candy.candymod.gui.font.*;
import cc.candy.candymod.*;
import cc.candy.candymod.utils.*;

public class Component implements Util
{
    public int x;
    public int y;
    public int width;
    public int height;
    public int defaultColor;
    public int enabledColor;
    public int whiteColor;
    public int buttonColor;
    public int outlineColor;
    public CFontRenderer fontRenderer;
    public boolean visible;
    
    public Component() {
        this.enabledColor = ColorUtil.toRGBA(230, 90, 100, 255);
        this.defaultColor = ColorUtil.toRGBA(25, 25, 25, 255);
        this.whiteColor = ColorUtil.toRGBA(255, 255, 255, 255);
        this.buttonColor = ColorUtil.toRGBA(35, 35, 35, 255);
        this.outlineColor = ColorUtil.toRGBA(210, 70, 80, 255);
        this.fontRenderer = CandyMod.m_font.fontRenderer;
    }
    
    public void onRender2D(final int y) {
    }
    
    public Boolean isMouseHovering(final int mouseX, final int mouseY) {
        if (this.x < mouseX && this.x + this.width > mouseX && this.y < mouseY && this.y + this.height > mouseY) {
            return true;
        }
        return false;
    }
    
    public Boolean isMouseHovering(final int mouseX, final int mouseY, final int cx, final int cy, final int cw, final int ch) {
        if (cx < mouseX && cx + cw > mouseX && cy < mouseY && cy + ch > mouseY) {
            return true;
        }
        return false;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
    }
    
    public void drawOutLine() {
        RenderUtil.drawLine((float)this.x, (float)this.y, (float)(this.x + this.width), (float)this.y, 2.0f, this.outlineColor);
        RenderUtil.drawLine((float)this.x, (float)(this.y + this.height), (float)(this.x + this.width), (float)(this.y + this.height), 2.0f, this.outlineColor);
        RenderUtil.drawLine((float)this.x, (float)this.y, (float)this.x, (float)(this.y + this.height), 2.0f, this.outlineColor);
        RenderUtil.drawLine((float)(this.x + this.width), (float)this.y, (float)(this.x + this.width), (float)(this.y + this.height), 2.0f, this.outlineColor);
    }
}
