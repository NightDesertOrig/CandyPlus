//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui.componets;

import cc.candy.candymod.gui.clickguis.clickgui.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class BooleanButton extends Component
{
    public Module module;
    public Setting<Boolean> setting;
    
    public BooleanButton(final Module module, final Setting setting, final int x, final int width, final int height) {
        this.module = module;
        this.setting = (Setting<Boolean>)setting;
        this.x = x;
        this.width = width;
        this.height = height;
    }
    
    public void onRender2D(final int y) {
        this.visible = this.setting.visible();
        if (this.visible) {
            this.y = y;
            RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, ((boolean)this.setting.getValue()) ? this.enabledColor : this.buttonColor);
            final float centeredY = y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
            RenderUtil.drawString(this.setting.name, (float)(this.x + 5), centeredY, this.whiteColor, false, 1.0f);
            if (this.setting.getValue()) {
                this.drawOutLine();
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 0) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton) {
    }
}
