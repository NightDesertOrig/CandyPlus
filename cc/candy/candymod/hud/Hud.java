//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.render.*;
import java.awt.*;
import cc.candy.candymod.utils.*;
import net.minecraft.util.math.*;

public class Hud extends Module
{
    public Setting<Float> x;
    public Setting<Float> y;
    public float width;
    public float height;
    public boolean selecting;
    private float diffX;
    private float diffY;
    public float scaledWidth;
    public float scaledHeight;
    public float scaleFactor;
    
    public Hud(final String name, final float x, final float y) {
        super(name, Categories.HUB, false, false);
        this.height = 0.0f;
        this.selecting = false;
        this.diffY = 0.0f;
        this.scaleFactor = 0.0f;
        this.x = (Setting<Float>)this.register(new Setting("X", (T)x, (T)2000.0f, (T)0.0f));
        this.y = (Setting<Float>)this.register(new Setting("Y", (T)x, (T)2000.0f, (T)0.0f));
    }
    
    @Override
    public void onRender2D() {
        if (CandyMod.m_module.getModuleWithClass(HUDEditor.class).isEnable) {
            final Color color = this.selecting ? new Color(20, 20, 20, 110) : new Color(20, 20, 20, 80);
            RenderUtil.drawRect(this.x.getValue() - 10.0f, this.y.getValue() - 5.0f, this.width + 20.0f, this.height + 10.0f, ColorUtil.toRGBA(color));
        }
        this.onRender();
    }
    
    public void onRender() {
    }
    
    @Override
    public void onUpdate() {
        this.updateResolution();
        this.x.maxValue = this.scaledWidth;
        this.y.maxValue = this.scaledHeight;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isMouseHovering(mouseX, mouseY)) {
            this.diffX = this.x.getValue() - mouseX;
            this.diffY = this.y.getValue() - mouseY;
            this.selecting = true;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.selecting = false;
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        if (this.selecting && clickedMouseButton == 0) {
            this.x.setValue(mouseX + this.diffX);
            this.y.setValue(mouseY + this.diffY);
        }
    }
    
    public Boolean isMouseHovering(final int mouseX, final int mouseY) {
        if (this.x.getValue() - 10.0f < mouseX && this.x.getValue() + this.width + 10.0f > mouseX && this.y.getValue() - 10.0f < mouseY && this.y.getValue() + this.height + 10.0f > mouseY) {
            return true;
        }
        return false;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public void updateResolution() {
        this.scaledWidth = (float)Hud.mc.displayWidth;
        this.scaledHeight = (float)Hud.mc.displayHeight;
        this.scaleFactor = 1.0f;
        final boolean flag = Hud.mc.isUnicode();
        int i = Hud.mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1.0f) >= 320.0f && this.scaledHeight / (this.scaleFactor + 1.0f) >= 240.0f) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2.0f != 0.0f && this.scaleFactor != 1.0f) {
            --this.scaleFactor;
        }
        final double scaledWidthD = this.scaledWidth / (double)this.scaleFactor;
        final double scaledHeightD = this.scaledHeight / (double)this.scaleFactor;
        this.scaledWidth = (float)MathHelper.ceil(scaledWidthD);
        this.scaledHeight = (float)MathHelper.ceil(scaledHeightD);
    }
}
