//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import net.minecraft.entity.player.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.combat.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class PopAnnouncer extends Module
{
    public PopAnnouncer() {
        super("PopAnnouncer", Categories.MISC, false, false);
    }
    
    @Override
    public void onTotemPop(final EntityPlayer player) {
        if (player == null || this.nullCheck()) {
            return;
        }
        final Module piston = CandyMod.m_module.getModuleWithClass((Class)PistonAura.class);
        final Module pistonRe = CandyMod.m_module.getModuleWithClass((Class)PistonAuraRewrite.class);
        final Module pistonRe2 = CandyMod.m_module.getModuleWithClass((Class)PistonAuraRewrite2.class);
        if ((piston.isEnable || pistonRe.isEnable || pistonRe2.isEnable) && (player.equals((Object)PistonAura.target) || player.equals((Object)PistonAuraRewrite.target) || player.equals((Object)PistonAuraRewrite2.target))) {
            this.sendChat("ez pop " + player.getName());
            return;
        }
        final Module cev = CandyMod.m_module.getModuleWithClass((Class)CevBreaker.class);
        if (cev.isEnable && player.equals((Object)CevBreaker.target)) {
            this.sendChat("keep popping " + player.getName());
            return;
        }
        final Module civ = CandyMod.m_module.getModuleWithClass((Class)CivBreaker.class);
        if (civ.isEnable && player.equals((Object)CivBreaker.target)) {
            this.sendChat("just keep popping " + player.getName());
        }
    }
    
    public void sendChat(final String str) {
        PopAnnouncer.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(str));
    }
}
