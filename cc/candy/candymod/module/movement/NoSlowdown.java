//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class NoSlowdown extends Module
{
    public final Setting<Float> webHorizontalFactor;
    public final Setting<Float> webVerticalFactor;
    public Setting<Boolean> noSlow;
    public Setting<Boolean> strict;
    public Setting<Boolean> sneakPacket;
    public Setting<Boolean> webs;
    private boolean sneaking;
    
    public NoSlowdown() {
        super("NoSlowdown", Module.Categories.MOVEMENT, false, false);
        this.webHorizontalFactor = (Setting<Float>)this.register(new Setting("WebHSpeed", (T)2.0f, (T)50.0f, (T)0.0f));
        this.webVerticalFactor = (Setting<Float>)this.register(new Setting("WebVSpeed", (T)2.0f, (T)50.0f, (T)0.0f));
        this.noSlow = (Setting<Boolean>)this.register(new Setting("NoSlow", (T)true));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.sneakPacket = (Setting<Boolean>)this.register(new Setting("SneakPacket", (T)false));
        this.webs = (Setting<Boolean>)this.register(new Setting("Webs", (T)false));
        this.sneaking = false;
    }
    
    public void onUpdate() {
        final Item item = NoSlowdown.mc.player.getActiveItemStack().getItem();
        if (this.sneaking && !NoSlowdown.mc.player.isHandActive() && this.sneakPacket.getValue()) {
            NoSlowdown.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlowdown.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.sneaking = false;
        }
    }
    
    @SubscribeEvent
    public void onUseItem(final PlayerInteractEvent.RightClickItem event) {
        final Item item = NoSlowdown.mc.player.getHeldItem(event.getHand()).getItem();
        if ((item instanceof ItemFood || item instanceof ItemBow || (item instanceof ItemPotion && this.sneakPacket.getValue())) && !this.sneaking) {
            NoSlowdown.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)NoSlowdown.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.sneaking = true;
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.strict.getValue() && this.noSlow.getValue() && NoSlowdown.mc.player.isHandActive() && !NoSlowdown.mc.player.isRiding()) {
            NoSlowdown.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(NoSlowdown.mc.player.posX), Math.floor(NoSlowdown.mc.player.posY), Math.floor(NoSlowdown.mc.player.posZ)), EnumFacing.DOWN));
        }
    }
}
