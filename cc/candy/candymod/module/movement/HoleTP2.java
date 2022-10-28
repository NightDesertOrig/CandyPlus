//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import cc.candy.candymod.*;
import net.minecraft.util.math.*;
import cc.candy.candymod.utils.*;
import java.util.function.*;
import java.util.*;

public class HoleTP2 extends Module
{
    public Setting<Integer> split;
    public Setting<Float> y;
    public Setting<Boolean> stopMotion;
    public Setting<Boolean> packet;
    public Setting<Boolean> up;
    public Setting<Boolean> trap;
    public Setting<Boolean> calc;
    public Setting<Float> range;
    public Setting<Float> speed;
    public Setting<fall> fallType;
    public Setting<Float> fallSpeed;
    public Vec3d hole;
    public Vec3d py;
    
    public HoleTP2() {
        super("HoleTP2", Module.Categories.MOVEMENT, false, false);
        this.split = (Setting<Integer>)this.register(new Setting("Split", (T)5, (T)15, (T)1));
        this.y = (Setting<Float>)this.register(new Setting("Y", (T)10.0f, (T)15.0f, (T)0.0f));
        this.stopMotion = (Setting<Boolean>)this.register(new Setting("StopMotion", (T)true));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)true));
        this.up = (Setting<Boolean>)this.register(new Setting("Up", (T)false));
        this.trap = (Setting<Boolean>)this.register(new Setting("TrapMode", (T)false));
        this.calc = (Setting<Boolean>)this.register(new Setting("Calc", (T)false));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)5.0f, (T)20.0f, (T)1.0f, v -> this.calc.getValue()));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)5.0f, (T)10.0f, (T)1.5f));
        this.fallType = (Setting<fall>)this.register(new Setting("Fall", (T)fall.Motion));
        this.fallSpeed = (Setting<Float>)this.register(new Setting("FallSpeed", (T)4.0f, (T)15.0f, (T)1.0f, v -> this.fallType.getValue() == fall.Motion));
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        if (!this.setHole()) {
            this.sendMessage("Cannot find hole! disabling");
            this.disable();
        }
        this.py = new Vec3d(this.getPlayerPos().x, (double)this.y.getValue(), this.getPlayerPos().z);
    }
    
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (HoleTP2.mc.player.rotationYawHead != HoleTP2.mc.player.rotationYaw && this.packet.getValue()) {
            HoleTP2.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(HoleTP2.mc.player.rotationYawHead, HoleTP2.mc.player.rotationPitch, HoleTP2.mc.player.onGround));
            return;
        }
        int i = 0;
        Vec3d pos = this.getPlayerPos();
        while (i <= this.split.getValue()) {
            if (this.stopMotion.getValue()) {
                final EntityPlayerSP player = HoleTP2.mc.player;
                player.motionX *= 0.0;
                final EntityPlayerSP player2 = HoleTP2.mc.player;
                player2.motionY *= 0.0;
                final EntityPlayerSP player3 = HoleTP2.mc.player;
                player3.motionZ *= 0.0;
            }
            if (Math.abs(this.py.y - pos.y) > 1.100000023841858) {
                final double offset = (this.py.y - pos.y) / this.speed.getValue();
                pos = pos.add(0.0, offset, 0.0);
                if (this.packet.getValue()) {
                    HoleTP2.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.x, pos.y, pos.z, false));
                    if (this.up.getValue()) {
                        HoleTP2.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.x, pos.y + 1337.0, pos.z, true));
                    }
                }
                else {
                    HoleTP2.mc.player.setPosition(pos.x, pos.y, pos.z);
                }
            }
            else {
                if (Math.abs(this.hole.x - pos.x) <= 1.100000023841858 && Math.abs(this.hole.z - pos.z) <= 1.100000023841858) {
                    this.teleport(new Vec3d(this.hole.x, pos.y, this.hole.z));
                    if (this.fallType.getValue() == fall.Motion) {
                        HoleTP2.mc.player.motionY = this.fallSpeed.getValue() * -1.0f;
                    }
                    else {
                        while (Math.abs(this.hole.y - pos.y) < 1.100000023841858) {
                            final double offset = (this.hole.y - pos.y) / this.speed.getValue();
                            pos = pos.add(0.0, offset, 0.0);
                            this.teleport(pos);
                        }
                    }
                    this.disable();
                    return;
                }
                final double offsetX = (this.hole.x - pos.x) / this.speed.getValue();
                final double offsetZ = (this.hole.z - pos.z) / this.speed.getValue();
                pos = pos.add(offsetX, 0.0, offsetZ);
                this.teleport(pos);
            }
            ++i;
        }
    }
    
    public void teleport(final Vec3d pos) {
        if (this.packet.getValue()) {
            HoleTP2.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.x, pos.y, pos.z, false));
            if (this.up.getValue() && !this.trap.getValue()) {
                HoleTP2.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(pos.x, pos.y + 1337.0, pos.z, true));
            }
        }
        else {
            HoleTP2.mc.player.setPosition(pos.x, pos.y, pos.z);
        }
    }
    
    public boolean setHole() {
        List<BlockPos> holes;
        if (this.calc.getValue()) {
            holes = (List<BlockPos>)CandyMod.m_hole.calcHoles();
        }
        else {
            holes = (List<BlockPos>)CandyMod.m_hole.calcHoles((float)this.range.getValue());
        }
        if (holes == null || holes.size() == 0) {
            return false;
        }
        final BlockPos _hole = holes.stream().filter(h -> HoleTP2.mc.player.getDistance(h.getX() + 0.5, (double)h.getY(), h.getZ() + 0.5) > 0.5).min(Comparator.comparing((Function<? super BlockPos, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
        if (_hole == null) {
            return false;
        }
        this.hole = new Vec3d(_hole.getX() + 0.5, (double)_hole.getY(), _hole.getZ() + 0.5);
        return true;
    }
    
    public double range(final Vec3d a, final Vec3d b) {
        return a.distanceTo(b);
    }
    
    public Vec3d getPlayerPos() {
        return new Vec3d(HoleTP2.mc.player.posX, HoleTP2.mc.player.posY, HoleTP2.mc.player.posZ);
    }
    
    public enum fall
    {
        Packet, 
        Motion;
    }
}
