//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import cc.candy.candymod.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import cc.candy.candymod.utils.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class HoleFill extends Module
{
    public Setting<Float> range;
    public Setting<Integer> place;
    public Setting<Boolean> toggle;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> silentSwitch;
    private EnumHand oldhand;
    private int oldslot;
    
    public HoleFill() {
        super("HoleFill", Categories.COMBAT, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)12.0f, (T)1.0f));
        this.place = (Setting<Integer>)this.register(new Setting("Place", (T)2, (T)10, (T)1));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)false));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)false));
        this.oldhand = null;
        this.oldslot = -1;
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        final int slot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        int counter = 0;
        this.setItem(slot);
        final List<BlockPos> holes = (List<BlockPos>)CandyMod.m_hole.getHoles().stream().filter(e -> PlayerUtil.getDistance(e) < this.range.getValue()).collect(Collectors.toList());
        for (final BlockPos hole : holes) {
            if (counter >= this.place.getValue()) {
                break;
            }
            ++counter;
            BlockUtil.placeBlock(hole, this.packetPlace.getValue());
        }
        if (holes.size() == 0) {
            this.disable();
        }
        this.restoreItem();
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (HoleFill.mc.player.isHandActive()) {
                this.oldhand = HoleFill.mc.player.getActiveHand();
            }
            this.oldslot = HoleFill.mc.player.inventory.currentItem;
            HoleFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            HoleFill.mc.player.inventory.currentItem = slot;
            HoleFill.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                HoleFill.mc.player.setActiveHand(this.oldhand);
            }
            HoleFill.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
}
