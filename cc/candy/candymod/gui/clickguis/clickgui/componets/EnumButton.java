//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui.componets;

import cc.candy.candymod.gui.clickguis.clickgui.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class EnumButton extends Component
{
    private Module module;
    private Setting<Enum> setting;
    
    public EnumButton(final Module m, final Setting<Enum> setting, final int x, final int width, final int height) {
        this.module = m;
        this.setting = setting;
        this.x = x;
        this.width = width;
        this.height = height;
    }
    
    public void onRender2D(final int y) {
        this.visible = true;
        this.y = y;
        final float centeredY = y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
        RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, this.enabledColor);
        RenderUtil.drawString(this.setting.name + " : " + this.setting.getValue().name(), (float)(this.x + 5), centeredY, this.whiteColor, false, 1.0f);
        this.drawOutLine();
    }
    
    public void mouseClicked(final int x, final int y, final int button) {
        if (this.isMouseHovering(x, y) && button == 0) {
            this.setting.increaseEnum();
        }
    }
}
