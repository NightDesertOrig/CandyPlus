//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew.item;

import cc.candy.candymod.gui.clickguis.clickguinew.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.utils.*;
import org.lwjgl.input.*;

public class BindButton extends Component
{
    public Module module;
    public boolean keyWaiting;
    
    public BindButton(final Module module, final float x) {
        this.keyWaiting = false;
        this.module = module;
        this.x = x;
        this.width = 100.0f;
        this.height = 16.0f;
    }
    
    public float doRender(final int mouseX, final int mouseY, final float x, final float y) {
        RenderUtil.drawRect(this.x = x, this.y = y, 100.0f, 16.0f, this.color0);
        if (this.keyWaiting) {
            RenderUtil.drawRect(x, y, 100.0f, 16.0f, ColorUtil.toRGBA(this.color));
        }
        if (this.isMouseHovering(mouseX, mouseY)) {
            RenderUtil.drawRect(x, y, 100.0f, 16.0f, this.hovering);
        }
        final float bindy = this.getCenter(y, 16.0f, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawString("Bind : " + (this.keyWaiting ? "..." : ((this.module.key.key == -1) ? "NONE" : Keyboard.getKeyName(this.module.key.key))), x + 6.0f, bindy, ColorUtil.toRGBA(250, 250, 250), false, 1.0f);
        return 16.0f;
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isMouseHovering(mouseX, mouseY)) {
            this.keyWaiting = !this.keyWaiting;
        }
    }
    
    public void onKeyTyped(final char typedChar, final int keyCode) {
        if (this.keyWaiting) {
            if (keyCode == 14 || keyCode == 1) {
                this.module.setKey(-1);
            }
            else {
                this.module.setKey(keyCode);
            }
            this.keyWaiting = false;
        }
    }
}
