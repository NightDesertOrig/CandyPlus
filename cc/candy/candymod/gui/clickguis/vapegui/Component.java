//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.vapegui;

import cc.candy.candymod.utils.*;
import java.awt.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.*;

public class Component
{
    public float x;
    public float y;
    public float width;
    public float height;
    public int white;
    public int gray;
    public int panelColor;
    public int baseColor;
    public int mainColor;
    
    public Component() {
        this.white = ColorUtil.toRGBA(255, 255, 255);
        this.gray = ColorUtil.toRGBA(200, 200, 200);
        this.panelColor = ColorUtil.toRGBA(19, 19, 19);
        this.baseColor = ColorUtil.toRGBA(25, 25, 25);
        this.updateColor();
    }
    
    public void onRender(final int mouseX, final int mouseY) {
        this.updateColor();
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public void onMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
    }
    
    public float doRender(final float y, final int mouseX, final int mouseY) {
        return 0.0f;
    }
    
    public float getCenter(final float a, final float b, final float c) {
        return a + (b - c) / 2.0f;
    }
    
    public Boolean isMouseHovering(final int mouseX, final int mouseY) {
        if (this.x < mouseX && this.x + this.width > mouseX) {
            return this.y < mouseY && this.y + this.height > mouseY;
        }
        return false;
    }
    
    public Boolean isMouseHovering(final float mouseX, final float mouseY, final float cx, final float cy, final float cw, final float ch) {
        if (cx < mouseX && cx + cw > mouseX) {
            return cy < mouseY && cy + ch > mouseY;
        }
        return false;
    }
    
    public void updateColor() {
        this.mainColor = ColorUtil.toRGBA(((ClickGUI)CandyMod.m_module.getModuleWithClass(ClickGUI.class)).color.getValue());
    }
}
