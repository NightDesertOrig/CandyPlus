//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui.componets;

import cc.candy.candymod.module.*;
import java.util.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.utils.*;
import cc.candy.candymod.gui.clickguis.clickgui.*;
import cc.candy.candymod.setting.*;
import java.awt.*;

public class ModuleButton extends Component
{
    public List<Component> components;
    public Module module;
    private boolean isOpening;
    
    public ModuleButton(final Module module, final int x, final int width, final int height) {
        this.components = new ArrayList<Component>();
        this.isOpening = false;
        this.module = module;
        this.x = x;
        this.width = width;
        this.height = height;
        module.settings.forEach(s -> this.addButtonBySetting(s));
        if (module.getClass() != HUDEditor.class) {
            this.components.add((Component)new KeybindButton(module, x, width, height));
        }
    }
    
    public void onRender(final int y) {
        this.y = y;
        RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, this.defaultColor);
        if (this.module.isEnable) {
            RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, this.enabledColor);
        }
        final float centeredY = y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
        RenderUtil.drawString(this.module.name, (float)(this.x + 3), centeredY, this.module.isEnable ? ColorUtil.toRGBA(255, 255, 255, 255) : ColorUtil.toARGB(255, 255, 255, 255), false, 1.0f);
        if (this.isOpening) {
            this.components.forEach(c -> {
                c.onRender2D(Panel.Cy += this.height);
                if (!c.visible) {
                    Panel.Cy -= this.height;
                }
            });
        }
    }
    
    public void addButtonBySetting(final Setting s) {
        if (s.getValue() instanceof Boolean) {
            this.components.add((Component)new BooleanButton(this.module, s, this.x, this.width, this.height));
        }
        else if (s.getValue() instanceof Integer) {
            this.components.add(new SliderButton(this.module, s, this.x, this.width, this.height));
        }
        else if (s.getValue() instanceof Float) {
            this.components.add((Component)new FloatSliderButton(this.module, s, this.x, this.width, this.height));
        }
        else if (s.getValue() instanceof Color) {
            this.components.add((Component)new ColorButton(this.module, s, this.x, this.width, this.height));
        }
        else {
            this.components.add((Component)new EnumButton(this.module, s, this.x, this.width, this.height));
        }
    }
    
    public void changeX(final int x) {
        this.x = x;
        this.components.forEach(c -> c.x = x);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isOpening) {
            this.components.forEach(c -> {
                if (c.visible) {
                    c.mouseClicked(mouseX, mouseY, mouseButton);
                }
                return;
            });
        }
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 0) {
            this.module.toggle();
        }
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 1) {
            this.isOpening = !this.isOpening;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (this.isOpening) {
            this.components.forEach(c -> c.mouseReleased(mouseX, mouseY, state));
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (this.isOpening) {
            this.components.forEach(c -> c.mouseClickMove(mouseX, mouseY, clickedMouseButton));
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.isOpening) {
            this.components.forEach(c -> c.onKeyTyped(typedChar, keyCode));
        }
    }
}
