//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew;

import cc.candy.candymod.module.*;
import java.awt.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.*;
import cc.candy.candymod.utils.*;

public class Component
{
    public Module module;
    public float x;
    public float y;
    public float width;
    public float height;
    public Color color;
    public int color0;
    public int hovering;
    
    public Component() {
        this.color = ((ClickGUI)CandyMod.m_module.getModuleWithClass(ClickGUI.class)).color.getValue();
        this.color0 = ColorUtil.toRGBA(30, 35, 30);
        this.hovering = ColorUtil.toRGBA(170, 170, 170, 100);
    }
    
    public float doRender(final int mouseX, final int mouseY, final float x, final float y) {
        return 0.0f;
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public void onMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
    }
    
    public float getCenter(final float a, final float b, final float c) {
        return a + (b - c) / 2.0f;
    }
    
    public Boolean isMouseHovering(final int mouseX, final int mouseY) {
        if (this.x < mouseX && this.x + this.width > mouseX && this.y < mouseY && this.y + this.height > mouseY) {
            return true;
        }
        return false;
    }
    
    public Boolean isMouseHovering(final float mouseX, final float mouseY, final float cx, final float cy, final float cw, final float ch) {
        if (cx < mouseX && cx + cw > mouseX && cy < mouseY && cy + ch > mouseY) {
            return true;
        }
        return false;
    }
}
