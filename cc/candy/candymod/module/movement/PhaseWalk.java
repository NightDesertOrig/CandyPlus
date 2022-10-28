//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import net.minecraft.util.math.*;
import cc.candy.candymod.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.server.*;

public class PhaseWalk extends Module
{
    boolean lastInSideStat;
    BlockPos lastPosition;
    double serverX;
    double serverY;
    double serverZ;
    Vec3d fix;
    int stack;
    
    public PhaseWalk() {
        super("PhaseWalk", Module.Categories.MOVEMENT, false, false);
    }
    
    public void onEnable() {
        this.lastInSideStat = PlayerUtil.isInsideBlock();
        this.lastPosition = new BlockPos((Entity)PhaseWalk.mc.player);
    }
    
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.stack > 0) {
            --this.stack;
        }
        if (PlayerUtil.isInsideBlock() || this.lastInSideStat) {
            this.fix = PhaseWalk.mc.player.getPositionVector();
            PlayerUtil.setPosition(new BlockPos((Entity)PhaseWalk.mc.player));
            PlayerUtil.vClip(5.0);
            final Vec3d motionVector = PlayerUtil.getMotionVector();
            PlayerUtil.move(0.0, -15.0, 0.0);
            PlayerUtil.setMotionVector(motionVector);
            final BlockPos pos = new BlockPos((Entity)PhaseWalk.mc.player).offset(EnumFacing.DOWN, PhaseWalk.mc.player.isSneaking() ? 2 : 1);
            this.lastPosition = pos;
            this.serverX = pos.getX() + 0.5;
            this.serverY = pos.getY();
            this.serverZ = pos.getZ() + 0.5;
            PhaseWalk.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(this.serverX, this.serverY, this.serverZ, true));
            PlayerUtil.setPosition(this.fix.x, this.fix.y, this.fix.z);
            this.lastInSideStat = PhaseWalk.mc.player.collidedVertically;
        }
        if (PhaseWalk.mc.player.posY < 0.0) {
            PhaseWalk.mc.player.setPosition(PhaseWalk.mc.player.posX, 1.1, PhaseWalk.mc.player.posZ);
            this.lastInSideStat = true;
        }
    }
    
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.lastInSideStat && event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            if (packet.getX(this.serverX) != this.serverX) {
                event.cancel();
            }
            if (packet.getY(this.serverY) != this.serverY) {
                event.cancel();
            }
            if (packet.getZ(this.serverZ) != this.serverZ) {
                event.cancel();
            }
        }
        this.stack += 2;
    }
    
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && this.lastInSideStat) {
            PlayerUtil.vClip(-8.0);
            this.toggle();
        }
        this.stack += 2;
    }
}
