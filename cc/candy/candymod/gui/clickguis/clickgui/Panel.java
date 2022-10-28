//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui;

import cc.candy.candymod.gui.clickguis.clickgui.componets.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.*;
import java.util.*;
import cc.candy.candymod.utils.*;

public class Panel implements Util
{
    public String name;
    public int x;
    public int y;
    public int tmpy;
    public int width;
    public int height;
    public boolean opening;
    private boolean isMoving;
    private int diff_x;
    private int diff_y;
    public static int Cy;
    public List<ModuleButton> buttons;
    
    public Panel(final Module.Categories category, final int x, final int y, final boolean isOpening) {
        this.tmpy = 0;
        this.name = category.name();
        this.x = x;
        this.y = y;
        this.width = 95;
        this.height = 17;
        this.opening = isOpening;
        this.isMoving = false;
        this.diff_x = 0;
        this.diff_y = 0;
        this.buttons = new ArrayList<ModuleButton>();
        final List<Module> modules = new ArrayList<Module>(CandyMod.m_module.getModulesWithCategories(category));
        Collections.sort(modules, new Comparator<Module>() {
            @Override
            public int compare(final Module c1, final Module c2) {
                return c1.name.compareToIgnoreCase(c2.name);
            }
        });
        final int buttonY = y;
        for (final Module m : modules) {
            if (m.hide) {
                continue;
            }
            this.buttons.add(new ModuleButton(m, x, this.width, 15));
        }
    }
    
    public void onRender(final int mouseX, final int mouseY, final float partialTicks) {
        RenderUtil.drawRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, ColorUtil.toARGB(50, 50, 50, 255));
        final float width = RenderUtil.getStringWidth(this.name, 1.0f);
        final float centeredX = this.x + (this.width - width) / 2.0f;
        final float centeredY = this.y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
        RenderUtil.drawString(this.name, centeredX, centeredY, ColorUtil.toARGB(255, 255, 255, 255), false, 1.0f);
        Panel.Cy = this.y + 2;
        if (this.opening) {
            Panel.Cy = this.y + 2;
            this.buttons.forEach(b -> b.onRender(Panel.Cy += 15));
        }
        final int outlineColor = ColorUtil.toRGBA(210, 70, 80, 255);
        final float y1 = (float)(Panel.Cy - 2 + this.height);
        RenderUtil.drawLine((float)this.x, (float)this.y, (float)this.x, y1, 2.0f, outlineColor);
        RenderUtil.drawLine((float)(this.x + this.width), (float)this.y, (float)(this.x + this.width), y1, 2.0f, outlineColor);
        RenderUtil.drawLine((float)this.x, (float)this.y, (float)(this.x + this.width), (float)this.y, 2.0f, outlineColor);
        RenderUtil.drawLine((float)this.x, y1, (float)(this.x + this.width), y1, 2.0f, outlineColor);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.buttons.forEach(b -> b.mouseClicked(mouseX, mouseY, mouseButton));
        if (this.x < mouseX && this.x + this.width > mouseX && mouseButton == 0 && !CandyGUI.isHovering && this.y < mouseY && this.y + this.height > mouseY) {
            this.isMoving = true;
            CandyGUI.isHovering = true;
            this.diff_x = this.x - mouseX;
            this.diff_y = this.y - mouseY;
        }
        if (this.x < mouseX && this.x + this.width > mouseX && mouseButton == 1 && this.y < mouseY && this.y + this.height > mouseY) {
            this.opening = !this.opening;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.buttons.forEach(b -> b.mouseReleased(mouseX, mouseY, state));
        this.isMoving = false;
        CandyGUI.isHovering = false;
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        this.buttons.forEach(b -> b.mouseClickMove(mouseX, mouseY, clickedMouseButton));
        if (this.isMoving) {
            this.x = mouseX + this.diff_x;
            this.y = mouseY + this.diff_y;
            this.buttons.forEach(b -> b.changeX(this.x));
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        this.buttons.forEach(b -> b.onKeyTyped(typedChar, keyCode));
    }
    
    static {
        Panel.Cy = 0;
    }
}
