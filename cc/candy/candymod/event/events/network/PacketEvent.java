//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.event.events.network;

import cc.candy.candymod.event.*;
import net.minecraft.network.*;

public class PacketEvent extends CandyEvent
{
    public final Packet<?> packet;
    
    public PacketEvent(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
    
    public static class Receive extends PacketEvent
    {
        public Receive(final Packet<?> packet) {
            super(packet);
        }
    }
    
    public static class Send extends PacketEvent
    {
        public Send(final Packet<?> packet) {
            super(packet);
        }
    }
}
