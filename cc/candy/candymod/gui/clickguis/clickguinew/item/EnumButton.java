//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew.item;

import cc.candy.candymod.gui.clickguis.clickguinew.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class EnumButton extends Component
{
    public Setting<Enum> setting;
    
    public EnumButton(final Setting<Enum> setting, final float x) {
        this.setting = setting;
        this.x = x;
        this.width = 100.0f;
        this.height = 16.0f;
    }
    
    public float doRender(final int mouseX, final int mouseY, final float x, final float y) {
        RenderUtil.drawRect(this.x = x, this.y = y, 100.0f, 16.0f, this.color0);
        if (this.isMouseHovering(mouseX, mouseY)) {
            RenderUtil.drawRect(x, y, 100.0f, 16.0f, this.hovering);
        }
        final float fonty = this.getCenter(y, 16.0f, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawString(this.setting.name + " : " + this.setting.getValue().name(), x + 6.0f, fonty, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
        return 16.0f;
    }
    
    public void onMouseClicked(final int x, final int y, final int button) {
        if (this.isMouseHovering(x, y) && button == 0) {
            this.setting.increaseEnum();
        }
    }
}
