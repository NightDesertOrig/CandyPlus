//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew;

import java.awt.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.gui.clickguis.clickguinew.item.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import cc.candy.candymod.utils.*;

public class Panel
{
    public float x;
    public float y;
    public float width;
    public float height;
    public boolean open;
    public Color color;
    public Module.Categories category;
    public List<ModuleButton> modules;
    public boolean moving;
    public float diffx;
    public float diffy;
    
    public Panel(final float x, final float y, final Module.Categories category) {
        this.modules = new ArrayList<ModuleButton>();
        this.moving = false;
        this.diffy = 0.0f;
        this.x = x;
        this.y = y;
        this.width = 100.0f;
        this.height = 15.0f;
        this.open = true;
        this.color = ((ClickGUI)CandyMod.m_module.getModuleWithClass(ClickGUI.class)).color.getValue();
        this.category = category;
        final List<Module> modules = new ArrayList<Module>(CandyMod.m_module.getModulesWithCategories(category));
        modules.sort((c1, c2) -> c1.name.compareToIgnoreCase(c2.name));
        modules.forEach(m -> this.modules.add(new ModuleButton(m, x)));
    }
    
    public void onRender(final int mouseX, final int mouseY) {
        final AtomicReference<Float> _y = new AtomicReference<Float>(this.y + 15.0f);
        if (this.open) {
            final AtomicReference<Float> atomicReference;
            this.modules.forEach(m -> atomicReference.updateAndGet(v -> v + m.onRender(mouseX, mouseY, this.x, (float)atomicReference.get())));
        }
        final String name = StringUtil.getName(this.category.name());
        RenderUtil.drawRect(this.x, this.y, 100.0f, 15.0f, ColorUtil.toRGBA(30, 30, 30));
        RenderUtil.drawLine(this.x, this.y + 15.0f, this.x + 100.0f, this.y + 15.0f, 2.0f, ColorUtil.toRGBA(this.color));
        final float namex = this.getCenter(this.x, 100.0f, RenderUtil.getStringWidth(name, 1.0f));
        final float namey = this.getCenter(this.y, 15.0f, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawString(name, namex, namey, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
        final ClickGUI module = (ClickGUI)CandyMod.m_module.getModuleWithClass(ClickGUI.class);
        if (module == null) {
            return;
        }
        if (module.outline.getValue()) {
            RenderUtil.drawLine(this.x, this.y, this.x + 100.0f, this.y, 1.0f, ColorUtil.toRGBA(this.color));
            RenderUtil.drawLine(this.x, _y.get(), this.x + 100.0f, _y.get(), 1.0f, ColorUtil.toRGBA(this.color));
            RenderUtil.drawLine(this.x, this.y, this.x, _y.get(), 1.0f, ColorUtil.toRGBA(this.color));
            RenderUtil.drawLine(this.x + 100.0f, this.y, this.x + 100.0f, _y.get(), 1.0f, ColorUtil.toRGBA(this.color));
        }
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isMouseHovering(mouseX, mouseY)) {
            this.moving = true;
            this.diffx = this.x - mouseX;
            this.diffy = this.y - mouseY;
        }
        if (mouseButton == 1 && this.isMouseHovering(mouseX, mouseY)) {
            this.open = !this.open;
        }
        if (this.open) {
            this.modules.forEach(m -> m.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int state) {
        this.moving = false;
        if (this.open) {
            this.modules.forEach(m -> m.onMouseReleased(mouseX, mouseY, state));
        }
    }
    
    public void onMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (clickedMouseButton == 0 && this.moving) {
            this.x = mouseX + this.diffx;
            this.y = mouseY + this.diffy;
        }
        if (this.open) {
            this.modules.forEach(m -> m.onMouseClickMove(mouseX, mouseY, clickedMouseButton));
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.open) {
            this.modules.forEach(m -> m.onKeyTyped(typedChar, keyCode));
        }
    }
    
    public Boolean isMouseHovering(final int mouseX, final int mouseY) {
        if (this.x < mouseX && this.x + this.width > mouseX && this.y < mouseY && this.y + this.height > mouseY) {
            return true;
        }
        return false;
    }
    
    public float getCenter(final float a, final float b, final float c) {
        return a + (b - c) / 2.0f;
    }
}
