//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.network.play.client.*;

public class AutoXP extends Module
{
    public Setting<Boolean> packetThrow;
    public Setting<Boolean> silentSwitch;
    public Setting<Float> delay;
    public int oldSlot;
    public EnumHand oldHand;
    public Timer timer;
    
    public AutoXP() {
        super("AutoXP", Categories.COMBAT, false, false);
        this.packetThrow = (Setting<Boolean>)this.register(new Setting("PacketThrow", (T)true));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)true));
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.oldSlot = -1;
        this.oldHand = null;
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        this.timer = new Timer();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        final int xp = InventoryUtil.getItemHotbar(Items.EXPERIENCE_BOTTLE);
        if (xp == -1) {
            this.restoreItem();
            return;
        }
        if (this.timer.passedX(this.delay.getValue())) {
            this.timer.reset();
            this.setItem(xp);
            AutoXP.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 90.0f, true));
            if (this.packetThrow.getValue()) {
                AutoXP.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            }
            else {
                AutoXP.mc.playerController.processRightClick((EntityPlayer)AutoXP.mc.player, (World)AutoXP.mc.world, EnumHand.MAIN_HAND);
            }
        }
        this.restoreItem();
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldHand = null;
            if (AutoXP.mc.player.isHandActive()) {
                this.oldHand = AutoXP.mc.player.getActiveHand();
            }
            this.oldSlot = AutoXP.mc.player.inventory.currentItem;
            AutoXP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            AutoXP.mc.player.inventory.currentItem = slot;
            AutoXP.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldSlot != -1 && this.silentSwitch.getValue()) {
            if (this.oldHand != null) {
                AutoXP.mc.player.setActiveHand(this.oldHand);
            }
            AutoXP.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
            this.oldSlot = -1;
            this.oldHand = null;
        }
    }
}
