//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.utils.*;

public class Welcomer extends Hud
{
    public Setting<Boolean> scroll;
    public Setting<Float> speed;
    public Setting<Float> size;
    public Setting<Boolean> shadow;
    public Setting<Color> color;
    public Setting<Boolean> background;
    public Setting<Color> backcolor;
    private float offsetx;
    
    public Welcomer() {
        super("Welcomer", 5.0f, 5.0f);
        this.scroll = (Setting<Boolean>)this.register(new Setting("Scroll", (T)false));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)4.0f, (T)10.0f, (T)0.1f));
        this.size = new Setting<Float>("Size", 1.0f, 5.0f, 0.5f);
        this.shadow = (Setting<Boolean>)this.register(new Setting("Shadow", (T)false));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 255)));
        this.background = (Setting<Boolean>)this.register(new Setting("Background", (T)false));
        this.backcolor = (Setting<Color>)this.register(new Setting("BGColor", (T)new Color(40, 40, 40, 60), v -> this.background.getValue()));
        this.offsetx = 0.0f;
    }
    
    public void onRender() {
        final String message = "Welcome " + this.getPlayerName();
        final float size = this.size.getValue();
        final float width = RenderUtil.getStringWidth(message, size);
        final float height = RenderUtil.getStringHeight(size);
        if (this.background.getValue()) {
            RenderUtil.drawRect(this.x.getValue() + this.offsetx, this.y.getValue(), width + 20.0f * size, height + 10.0f * size, ColorUtil.toRGBA(this.backcolor.getValue()));
        }
        RenderUtil.drawString(message, this.x.getValue() + 10.0f + this.offsetx, this.y.getValue() + 5.0f, ColorUtil.toRGBA(this.color.getValue()), this.shadow.getValue(), size);
        if (this.scroll.getValue()) {
            this.offsetx += this.speed.getValue();
        }
        else {
            this.offsetx = 0.0f;
        }
        if (this.scaledWidth + RenderUtil.getStringWidth(message, size) + 10.0f < this.offsetx) {
            this.offsetx = RenderUtil.getStringWidth(message, size) * -1.0f - 10.0f;
        }
        this.width = width + 20.0f * size;
        this.height = height + 10.0f * size;
    }
    
    public String getPlayerName() {
        return Welcomer.mc.player.getName();
    }
}
