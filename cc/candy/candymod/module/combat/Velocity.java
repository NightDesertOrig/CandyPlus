//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.world.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;

public class Velocity extends Module
{
    Setting<Integer> horizontal_vel;
    Setting<Integer> vertical_vel;
    Setting<Boolean> explosions;
    Setting<Boolean> bobbers;
    
    public Velocity() {
        super("Velocity", Categories.COMBAT, false, false);
        this.horizontal_vel = (Setting<Integer>)this.register(new Setting("Horizontal", (T)0, (T)100, (T)0));
        this.vertical_vel = (Setting<Integer>)this.register(new Setting("Vertical", (T)0, (T)100, (T)0));
        this.explosions = (Setting<Boolean>)this.register(new Setting("Explosions", (T)true));
        this.bobbers = (Setting<Boolean>)this.register(new Setting("Bobbers", (T)true));
    }
    
    @Override
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (Velocity.mc.player == null) {
            return;
        }
        if (event.packet instanceof SPacketEntityStatus && this.bobbers.getValue()) {
            final SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
            if (packet.getOpCode() == 31) {
                final Entity entity = packet.getEntity((World)Velocity.mc.world);
                if (entity != null && entity instanceof EntityFishHook) {
                    final EntityFishHook fishHook = (EntityFishHook)entity;
                    if (fishHook.caughtEntity == Velocity.mc.player) {
                        event.cancel();
                    }
                }
            }
        }
        if (event.packet instanceof SPacketEntityVelocity) {
            final SPacketEntityVelocity packet2 = (SPacketEntityVelocity)event.packet;
            if (packet2.getEntityID() == Velocity.mc.player.getEntityId()) {
                if (this.horizontal_vel.getValue() == 0 && this.vertical_vel.getValue() == 0) {
                    event.cancel();
                    return;
                }
                if (this.horizontal_vel.getValue() != 100) {}
                if (this.vertical_vel.getValue() != 100) {}
            }
        }
        if (event.packet instanceof SPacketExplosion && this.explosions.getValue()) {
            final SPacketExplosion packet3 = (SPacketExplosion)event.packet;
            if (this.horizontal_vel.getValue() == 0 && this.vertical_vel.getValue() == 0) {
                event.cancel();
                return;
            }
            if (this.horizontal_vel.getValue() != 100) {}
            if (this.vertical_vel.getValue() != 100) {}
        }
    }
}
