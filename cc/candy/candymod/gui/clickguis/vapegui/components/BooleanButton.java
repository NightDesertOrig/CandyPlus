//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.vapegui.components;

import cc.candy.candymod.gui.clickguis.vapegui.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class BooleanButton extends Component
{
    public Setting<Boolean> setting;
    public float _x;
    
    public BooleanButton(final Setting setting, final float x) {
        this._x = 0.0f;
        this.setting = (Setting<Boolean>)setting;
        this.x = x;
        this.width = 110.0f;
        this.height = 18.0f;
        this._x = this.getX();
    }
    
    public float doRender(final float y, final int mouseX, final int mouseY) {
        this.y = y;
        RenderUtil.drawRect(this.x, y, this.width, this.height, this.baseColor);
        final float namey = this.getCenter(y, this.height, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawString(this.setting.name, this.x + 8.0f, namey, ((boolean)this.setting.getValue()) ? this.white : this.gray, false, 1.0f);
        final float linex = this.x + this.width - 20.0f;
        RenderUtil.drawRect(linex, namey - 3.0f, 15.0f, 8.0f, ((boolean)this.setting.getValue()) ? this.mainColor : ColorUtil.toRGBA(60, 60, 60));
        RenderUtil.drawRect(this._x, namey - 2.0f, 4.0f, 6.0f, this.baseColor);
        if (this.isMouseHovering((float)mouseX, (float)mouseY, this._x, namey - 2.0f, 4.0f, 6.0f)) {
            RenderUtil.drawRect(this._x, namey - 2.0f, 4.0f, 6.0f, ColorUtil.toRGBA(255, 255, 255, 50));
        }
        this._x += (float)((this.getX() - this._x) * 0.5);
        return this.height;
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int clickedMouseButton) {
        final float namey = this.getCenter(this.y, this.height, RenderUtil.getStringHeight(1.0f));
        if (this.isMouseHovering((float)mouseX, (float)mouseY, this._x, namey - 2.0f, 4.0f, 6.0f) && 0 == clickedMouseButton) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
    
    public float getX() {
        return this.setting.getValue() ? (this.x + this.width - 13.0f) : (this.x + this.width - 20.0f);
    }
}
