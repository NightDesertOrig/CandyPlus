//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew.item;

import cc.candy.candymod.gui.clickguis.clickguinew.*;
import cc.candy.candymod.module.*;
import java.util.*;
import java.util.function.*;
import cc.candy.candymod.utils.*;
import java.util.concurrent.atomic.*;
import cc.candy.candymod.setting.*;
import java.awt.*;

public class ModuleButton extends Component
{
    public List<Component> componentList;
    public boolean open;
    
    public ModuleButton(final Module module, final float x) {
        this.componentList = new ArrayList<Component>();
        this.module = module;
        this.x = x;
        this.width = 100.0f;
        this.open = false;
        this.height = 16.0f;
        module.settings.forEach(this::addSetting);
        this.add((Component)new BindButton(module, x));
    }
    
    public float onRender(final int mouseX, final int mouseY, final float x, final float y) {
        RenderUtil.drawRect(this.x = x, this.y = y, 100.0f, 16.0f, this.color0);
        if (this.module.isEnable) {
            RenderUtil.drawRect(x, y, 100.0f, 16.0f, ColorUtil.toRGBA(this.color));
        }
        if (this.isMouseHovering(mouseX, mouseY)) {
            RenderUtil.drawRect(x, y, 100.0f, 16.0f, this.hovering);
        }
        final String name = this.module.name;
        final float namey = this.getCenter(y, 16.0f, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawString(name, x + 3.0f, namey, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
        RenderUtil.drawString("...", x + 100.0f - RenderUtil.getStringWidth("...", 1.0f) - 3.0f, namey - 1.0f, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
        final AtomicReference<Float> height = new AtomicReference<Float>(16.0f);
        if (this.open) {
            final AtomicReference<Float> atomicReference;
            final float h;
            this.componentList.forEach(c -> {
                h = c.doRender(mouseX, mouseY, x, y + atomicReference.get());
                RenderUtil.drawRect(x, y + atomicReference.get(), 2.0f, h, ColorUtil.toRGBA(this.color));
                atomicReference.updateAndGet(v -> v + h);
                return;
            });
        }
        return height.get();
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isMouseHovering(mouseX, mouseY)) {
            this.module.toggle();
        }
        if (mouseButton == 1 && this.isMouseHovering(mouseX, mouseY)) {
            this.open = !this.open;
        }
        if (this.open) {
            this.componentList.forEach(c -> c.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.open) {
            this.componentList.forEach(c -> c.onMouseReleased(mouseX, mouseY, state));
        }
    }
    
    public void onMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (this.open) {
            this.componentList.forEach(c -> c.onMouseClickMove(mouseX, mouseY, clickedMouseButton));
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.open) {
            this.componentList.forEach(c -> c.onKeyTyped(typedChar, keyCode));
        }
    }
    
    public void addSetting(final Setting setting) {
        final Object value = setting.value;
        if (value instanceof Boolean) {
            this.add((Component)new BooleanButton(setting, this.x));
        }
        else if (value instanceof Integer) {
            this.add((Component)new IntegerSlider(setting, this.x));
        }
        else if (value instanceof Float) {
            this.add((Component)new FloatSlider(setting, this.x));
        }
        else if (value instanceof Color) {
            this.add((Component)new ColorSlider(setting, this.x));
        }
        else {
            this.add((Component)new EnumButton(setting, this.x));
        }
    }
    
    private void add(final Component component) {
        this.componentList.add(component);
    }
}
