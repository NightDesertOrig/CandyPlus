//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.vapegui.components;

import cc.candy.candymod.gui.clickguis.vapegui.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class EnumButton extends Component
{
    public Setting<Enum> setting;
    public boolean open;
    
    public EnumButton(final Setting<Enum> setting, final float x) {
        this.setting = setting;
        this.x = x;
        this.width = 110.0f;
        this.height = 18.0f;
        this.open = false;
    }
    
    public float doRender(final float y, final int mouseX, final int mouseY) {
        this.y = y;
        final float width = RenderUtil.getStringWidth(this.setting.getValue().name(), 1.0f);
        final float namey = this.getCenter(y, this.height, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawRect(this.x, this.y, width, this.height, this.baseColor);
        RenderUtil.drawString(this.setting.name, this.x + 8.0f, namey, this.white, false, 1.0f);
        RenderUtil.drawString(this.setting.getValue().name(), this.x + this.width - width - 5.0f, namey, this.white, false, 1.0f);
        return this.height;
    }
}
