//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;

public class Speed extends Module
{
    public Setting<Float> speed;
    
    public Speed() {
        super("Speed", Module.Categories.MOVEMENT, false, false);
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)2.0f, (T)10.0f, (T)1.0f));
    }
    
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        final double[] s = PlayerUtil.forward(this.speed.getValue() / 10.0f);
        Speed.mc.player.motionX = s[0];
        Speed.mc.player.motionZ = s[1];
    }
}
