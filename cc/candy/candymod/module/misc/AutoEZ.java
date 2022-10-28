//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.event.events.player.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.combat.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class AutoEZ extends Module
{
    public Setting<Boolean> floppaGod;
    
    public AutoEZ() {
        super("AutoEZ", Categories.MISC, false, false);
        this.floppaGod = (Setting<Boolean>)this.register(new Setting("FloppaGod", (T)false));
    }
    
    @Override
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (this.nullCheck()) {
            return;
        }
        final EntityPlayer player = event.player;
        if (player.getHealth() > 0.0f) {
            return;
        }
        final Module piston = CandyMod.m_module.getModuleWithClass((Class)PistonAura.class);
        final Module pistonRe = CandyMod.m_module.getModuleWithClass((Class)PistonAuraRewrite.class);
        final Module pistonRe2 = CandyMod.m_module.getModuleWithClass((Class)PistonAuraRewrite2.class);
        if ((piston.isEnable || pistonRe.isEnable || pistonRe2.isEnable) && (player.equals((Object)PistonAura.target) || player.equals((Object)PistonAuraRewrite.target) || player.equals((Object)PistonAuraRewrite2.target))) {
            this.EZ();
            return;
        }
        final Module cev = CandyMod.m_module.getModuleWithClass((Class)CevBreaker.class);
        if (cev.isEnable && player.equals((Object)CevBreaker.target)) {
            this.EZ();
            return;
        }
        final Module civ = CandyMod.m_module.getModuleWithClass((Class)CivBreaker.class);
        if (civ.isEnable && player.equals((Object)CivBreaker.target)) {
            this.EZ();
        }
    }
    
    public void EZ() {
        if (this.floppaGod.getValue()) {
            this.sendChat("you just got nae nae'd by FloppaGod");
        }
        else {
            this.sendChat("you just got ez'd by candy+");
        }
    }
    
    public void sendChat(final String str) {
        AutoEZ.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(str));
    }
}
