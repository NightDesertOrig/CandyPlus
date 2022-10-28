//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew.item;

import cc.candy.candymod.gui.clickguis.clickguinew.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class IntegerSlider extends Component
{
    public Setting<Integer> setting;
    public float ratio;
    public boolean changing;
    
    public IntegerSlider(final Setting<Integer> setting, final float x) {
        this.setting = setting;
        this.x = x;
        this.width = 100.0f;
        this.height = 16.0f;
        this.ratio = this.getRatio(setting.getValue(), setting.maxValue, setting.minValue);
        this.changing = false;
    }
    
    public float doRender(final int mouseX, final int mouseY, final float x, final float y) {
        if (this.setting.visible()) {
            RenderUtil.drawRect(this.x = x, this.y = y, 100.0f, 16.0f, this.color0);
            final float width = this.width * this.ratio;
            RenderUtil.drawRect(x, y, width, 16.0f, ColorUtil.toRGBA(this.color));
            final float fonty = this.y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
            RenderUtil.drawString(this.setting.name, x + 6.0f, fonty, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
            RenderUtil.drawString(this.setting.getValue().toString(), this.x + this.width - RenderUtil.getStringWidth(this.setting.getValue().toString(), 1.0f) - 6.0f, fonty, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
            return 16.0f;
        }
        return 0.0f;
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 0) {
            this.setValue((float)mouseX);
            this.changing = true;
        }
    }
    
    public void onMouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (this.changing && clickedMouseButton == 0) {
            this.setValue((float)mouseX);
        }
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int state) {
        this.changing = false;
    }
    
    public void setValue(final float mouseX) {
        float v = (mouseX - this.x) / this.width;
        if (v > 1.0f) {
            v = 1.0f;
        }
        if (v < 0.0f) {
            v = 0.0f;
        }
        this.ratio = v;
        final float newValue = (this.setting.maxValue - this.setting.minValue) * this.ratio + this.setting.minValue;
        this.setting.setValue(Math.round(newValue));
    }
    
    public float getRatio(final float value, final float maxValue, final float minValue) {
        return (value - minValue) / maxValue;
    }
}
