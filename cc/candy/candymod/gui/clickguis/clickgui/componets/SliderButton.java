//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui.componets;

import cc.candy.candymod.gui.clickguis.clickgui.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class SliderButton extends Component
{
    private float value;
    private Module module;
    private Setting<Integer> setting;
    private boolean changing;
    private int diff;
    
    public SliderButton(final Module module, final Setting<Integer> setting, final int x, final int width, final int height) {
        this.module = module;
        this.setting = setting;
        this.x = x;
        this.width = width;
        this.height = height;
        this.changing = false;
        this.diff = setting.maxValue - setting.minValue;
        this.value = this.getValue();
    }
    
    public void onRender2D(final int y) {
        this.visible = this.setting.visible();
        if (this.visible) {
            this.y = y;
            RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, this.buttonColor);
            RenderUtil.drawRect((float)this.x, (float)y, this.width * this.value, (float)this.height, this.enabledColor);
            final float centeredY = y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
            RenderUtil.drawString(this.setting.name, (float)(this.x + 5), centeredY, this.whiteColor, false, 1.0f);
            RenderUtil.drawString(String.valueOf(this.setting.getValue()), this.x + this.width - RenderUtil.getStringWidth(String.valueOf(this.setting.getValue()), 1.0f) - 3.0f, centeredY, this.whiteColor, false, 1.0f);
            this.drawOutLine();
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 0) {
            this.changing = true;
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.changing = false;
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (this.x - 10 < mouseX && this.x + this.width + 10 > mouseX && clickedMouseButton == 0 && this.changing) {
            this.value = (mouseX - (float)this.x) / this.width;
            if (this.value > 1.0f) {
                this.value = 1.0f;
            }
            if (this.value < 0.0f) {
                this.value = 0.0f;
            }
            this.setting.setValue((int)((this.setting.maxValue - this.setting.minValue) * this.value + this.setting.minValue));
        }
    }
    
    private float getValue() {
        return this.setting.getValue() / ((this.setting.maxValue - this.setting.minValue) * 1.0f);
    }
}
