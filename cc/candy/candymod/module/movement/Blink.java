//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.client.*;

public class Blink extends Module
{
    public Setting<Boolean> noEntity;
    public Setting<Boolean> limit;
    public Setting<Integer> maxPackets;
    public Setting<Boolean> all;
    public Setting<Integer> skip;
    public EntityPlayer entity;
    public BlockPos startPos;
    public List<Packet<?>> packets;
    
    public Blink() {
        super("Blink", Module.Categories.MOVEMENT, false, false);
        this.noEntity = (Setting<Boolean>)this.register(new Setting("NoEntity", (T)false));
        this.limit = (Setting<Boolean>)this.register(new Setting("Limit", (T)false));
        this.maxPackets = (Setting<Integer>)this.register(new Setting("MaxPackets", (T)20, (T)70, (T)10, s -> this.limit.getValue()));
        this.all = (Setting<Boolean>)this.register(new Setting("Cancel All", (T)false));
        this.skip = (Setting<Integer>)this.register(new Setting("Skip", (T)0, (T)3, (T)0));
        this.entity = null;
        this.startPos = null;
        this.packets = null;
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            this.disable();
            return;
        }
        this.packets = new ArrayList<Packet<?>>();
        if (!this.noEntity.getValue()) {
            (this.entity = (EntityPlayer)new EntityOtherPlayerMP((World)Blink.mc.world, Blink.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)Blink.mc.player);
            this.entity.rotationYaw = Blink.mc.player.rotationYaw;
            this.entity.rotationYawHead = Blink.mc.player.rotationYawHead;
            this.entity.inventory.copyInventory(Blink.mc.player.inventory);
            Blink.mc.world.addEntityToWorld(6942069, (Entity)this.entity);
            this.startPos = Blink.mc.player.getPosition();
        }
    }
    
    public void onDisable() {
        if (this.nullCheck() || this.packets == null) {
            return;
        }
        int counter = 0;
        for (final Packet packet : this.packets) {
            if (this.skip.getValue() <= counter) {
                Blink.mc.player.connection.sendPacket(packet);
                counter = 0;
            }
            ++counter;
        }
        Blink.mc.world.removeEntityFromWorld(this.entity.getEntityId());
    }
    
    public void onUpdate() {
        if (this.nullCheck() || this.packets == null) {
            return;
        }
        if (this.limit.getValue() && this.packets.size() > this.maxPackets.getValue()) {
            this.sendMessage("Packets size has reached the limit! disabling...");
            this.packets = new ArrayList<Packet<?>>();
            this.disable();
        }
    }
    
    public void onPacketSend(final PacketEvent.Send event) {
        if (this.nullCheck() || this.packets == null) {
            return;
        }
        final Packet<?> packet = (Packet<?>)event.packet;
        if (!this.all.getValue()) {
            if (packet instanceof CPacketChatMessage || packet instanceof CPacketConfirmTeleport || packet instanceof CPacketKeepAlive || packet instanceof CPacketTabComplete || packet instanceof CPacketClientStatus) {
                return;
            }
            this.packets.add(packet);
            event.cancel();
        }
        else if (packet instanceof CPacketPlayer) {
            this.packets.add(packet);
            event.cancel();
        }
    }
}
