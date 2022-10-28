//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.*;

public class SmallShield extends Module
{
    public Setting<Boolean> normalOffset;
    public Setting<Float> offset;
    public Setting<Float> offX;
    public Setting<Float> offY;
    public Setting<Float> mainX;
    public Setting<Float> mainY;
    
    public SmallShield() {
        super("SmallShield", Module.Categories.RENDER, false, false);
        this.normalOffset = (Setting<Boolean>)this.register(new Setting("OffNormal", (T)false));
        this.offset = (Setting<Float>)this.register(new Setting("Offset", (T)0.7f, (T)1.0f, (T)0.0f, v -> this.normalOffset.getValue()));
        this.offX = (Setting<Float>)this.register(new Setting("OffX", (T)0.0f, (T)1.0f, (T)(-1.0f), v -> !this.normalOffset.getValue()));
        this.offY = (Setting<Float>)this.register(new Setting("OffY", (T)0.0f, (T)1.0f, (T)(-1.0f), v -> !this.normalOffset.getValue()));
        this.mainX = (Setting<Float>)this.register(new Setting("MainX", (T)0.0f, (T)1.0f, (T)(-1.0f)));
        this.mainY = (Setting<Float>)this.register(new Setting("MainY", (T)0.0f, (T)1.0f, (T)(-1.0f)));
    }
    
    public static SmallShield getINSTANCE() {
        return (SmallShield)CandyMod.m_module.getModuleWithClass((Class)SmallShield.class);
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (this.normalOffset.getValue()) {}
    }
}
