//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import cc.candy.candymod.event.events.network.*;

public class NoWeb extends Module
{
    public NoWeb() {
        super("NoWeb", Module.Categories.MOVEMENT, false, false);
    }
    
    public void onUpdate() {
    }
    
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
    }
    
    public void onPacketReceive(final PacketEvent.Receive event) {
    }
}
