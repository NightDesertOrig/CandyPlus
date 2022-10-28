//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.client.*;

public class ChatSuffix extends Module
{
    public Setting<Boolean> floppaGod;
    
    public ChatSuffix() {
        super("ChatSuffix", Categories.MISC, false, false);
        this.floppaGod = (Setting<Boolean>)this.register(new Setting("FloppaGod", (T)false));
    }
    
    @Override
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.packet instanceof CPacketChatMessage) {
            if (((CPacketChatMessage)event.getPacket()).getMessage().startsWith("/")) {
                return;
            }
            final CPacketChatMessage p = (CPacketChatMessage)event.packet;
            final String msg = p.getMessage();
            final String newMsg = msg + this.toUnicode(" || " + (this.floppaGod.getValue() ? "FloppaGod" : "Candy+".toLowerCase()));
            if (newMsg.length() > 255) {
                return;
            }
        }
    }
    
    public String toUnicode(final String s) {
        return s.toLowerCase().replace("a", "\u1d00").replace("b", "\u0299").replace("c", "\u1d04").replace("d", "\u1d05").replace("e", "\u1d07").replace("f", "\ua730").replace("g", "\u0262").replace("h", "\u029c").replace("i", "\u026a").replace("j", "\u1d0a").replace("k", "\u1d0b").replace("l", "\u029f").replace("m", "\u1d0d").replace("n", "\u0274").replace("o", "\u1d0f").replace("p", "\u1d18").replace("q", "\u01eb").replace("r", "\u0280").replace("s", "\ua731").replace("t", "\u1d1b").replace("u", "\u1d1c").replace("v", "\u1d20").replace("w", "\u1d21").replace("x", "\u02e3").replace("y", "\u028f").replace("z", "\u1d22");
    }
}
