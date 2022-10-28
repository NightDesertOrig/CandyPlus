//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui.componets;

import cc.candy.candymod.gui.clickguis.clickgui.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.utils.*;
import org.lwjgl.input.*;
import cc.candy.candymod.module.render.*;

public class KeybindButton extends Component
{
    private Module module;
    private boolean isWaiting;
    
    public KeybindButton(final Module m, final int x, final int width, final int height) {
        this.isWaiting = false;
        this.module = m;
        this.x = x;
        this.width = width;
        this.height = height;
    }
    
    public void onRender2D(final int y) {
        this.visible = true;
        this.y = y;
        final float centeredY = y + (this.height - RenderUtil.getStringHeight(1.0f)) / 2.0f;
        if (this.isWaiting) {
            RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, this.enabledColor);
            RenderUtil.drawString("Key : ...", (float)(this.x + 5), centeredY, this.whiteColor, false, 1.0f);
        }
        else {
            RenderUtil.drawRect((float)this.x, (float)y, (float)this.width, (float)this.height, this.buttonColor);
            RenderUtil.drawString("Key : " + ((this.module.key.getKey() != -1) ? Keyboard.getKeyName(this.module.key.getKey()) : "NONE"), (float)(this.x + 5), centeredY, this.whiteColor, false, 1.0f);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 0) {
            this.isWaiting = !this.isWaiting;
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.isWaiting) {
            if (keyCode == 14 || keyCode == 1) {
                if (!(this.module instanceof ClickGUI)) {
                    this.module.setKey(-1);
                }
            }
            else {
                this.module.setKey(keyCode);
            }
            this.isWaiting = false;
        }
    }
}
