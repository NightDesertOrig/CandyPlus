//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;

public class EnchantmentColor extends Module
{
    public Setting<Color> color;
    public static EnchantmentColor INSTANCE;
    
    public EnchantmentColor() {
        super("EnchantmentColor", Module.Categories.RENDER, false, false);
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 50)));
        EnchantmentColor.INSTANCE = this;
    }
}
