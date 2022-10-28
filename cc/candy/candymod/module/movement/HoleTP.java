//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.util.math.*;
import cc.candy.candymod.*;
import java.util.*;

public class HoleTP extends Module
{
    public Setting<Float> range;
    public Setting<Boolean> stopMotion;
    
    public HoleTP() {
        super("HoleTP", Module.Categories.MOVEMENT, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)1.0f, (T)3.0f, (T)0.1f));
        this.stopMotion = (Setting<Boolean>)this.register(new Setting("StopMotion", (T)false));
    }
    
    public void onEnable() {
        final BlockPos hole = (BlockPos)CandyMod.m_hole.calcHoles().stream().min(Comparator.comparing(p -> HoleTP.mc.player.getDistance((double)p.getX(), (double)p.getY(), (double)p.getZ()))).orElse(null);
        if (hole != null) {
            if (HoleTP.mc.player.getDistance((double)hole.getX(), (double)hole.getY(), (double)hole.getZ()) < this.range.getValue() + 1.5) {
                HoleTP.mc.player.setPosition(hole.getX() + 0.5, HoleTP.mc.player.posY, hole.getZ() + 0.5);
                if (this.stopMotion.getValue()) {
                    HoleTP.mc.player.motionX = 0.0;
                    HoleTP.mc.player.motionZ = 0.0;
                }
                HoleTP.mc.player.motionY = -3.0;
                this.sendMessage("Accepting teleport...");
            }
            else {
                this.sendMessage("Out of range! disabling...");
            }
        }
        else {
            this.sendMessage("Not found hole! disabling...");
        }
        this.disable();
    }
}
