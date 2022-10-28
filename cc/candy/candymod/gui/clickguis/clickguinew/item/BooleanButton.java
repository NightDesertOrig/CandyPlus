//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew.item;

import cc.candy.candymod.gui.clickguis.clickguinew.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class BooleanButton extends Component
{
    public Setting<Boolean> setting;
    
    public BooleanButton(final Setting<Boolean> setting, final float x) {
        this.setting = setting;
        this.x = x;
        this.width = 100.0f;
        this.height = 16.0f;
    }
    
    public float doRender(final int mouseX, final int mouseY, final float x, final float y) {
        if (this.setting.visible()) {
            RenderUtil.drawRect(this.x = x, this.y = y, 100.0f, 16.0f, this.color0);
            if (this.setting.getValue()) {
                RenderUtil.drawRect(x, y, 100.0f, 16.0f, ColorUtil.toRGBA(this.color));
            }
            if (this.isMouseHovering(mouseX, mouseY)) {
                RenderUtil.drawRect(x, y, 100.0f, 16.0f, this.hovering);
            }
            final String name = this.setting.name;
            final float namey = this.getCenter(y, 16.0f, RenderUtil.getStringHeight(1.0f));
            RenderUtil.drawString(name, x + 6.0f, namey, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
            return 16.0f;
        }
        return 0.0f;
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isMouseHovering(mouseX, mouseY)) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
}
