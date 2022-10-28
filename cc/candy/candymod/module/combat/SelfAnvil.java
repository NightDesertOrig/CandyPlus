//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class SelfAnvil extends Module
{
    public Setting<Float> blockDelay;
    public Setting<Float> anvilDelay;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> silentSwitch;
    private int progress;
    private Timer blockT;
    private Timer anvilT;
    private BlockPos base;
    private EnumHand oldhand;
    private int oldslot;
    
    public SelfAnvil() {
        super("SelfAnvil", Categories.COMBAT, false, false);
        this.blockDelay = (Setting<Float>)this.register(new Setting("BlockDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.anvilDelay = (Setting<Float>)this.register(new Setting("AnvilDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)false));
        this.progress = 0;
        this.anvilT = null;
        this.base = null;
        this.oldhand = null;
        this.oldslot = -1;
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        if (BlockUtil.getBlock(new BlockPos(SelfAnvil.mc.player.posX, SelfAnvil.mc.player.posY, SelfAnvil.mc.player.posZ)) != Blocks.AIR) {
            this.sendMessage("you are already in block! disabling...");
            this.disable();
        }
    }
    
    @Override
    public void onDisable() {
        this.reset();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        final int anvil = InventoryUtil.findHotbarBlock(Blocks.ANVIL);
        final int obby = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (anvil == -1 || (obby == -1 | this.nullCheck())) {
            return;
        }
        final BlockPos mypos = new BlockPos(SelfAnvil.mc.player.posX, SelfAnvil.mc.player.posY, SelfAnvil.mc.player.posZ);
        if (this.base == null) {
            final BlockPos[] array;
            final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
            for (final BlockPos offset : array) {
                final BlockPos pos = mypos.add((Vec3i)offset.add(0, 1, 0));
                if (BlockUtil.canPlaceBlock(pos) || BlockUtil.getBlock(pos) == Blocks.OBSIDIAN || BlockUtil.getBlock(pos) == Blocks.BEDROCK) {
                    this.base = pos;
                }
            }
        }
        if (this.base == null) {
            return;
        }
        if (this.blockT == null && this.progress <= 1) {
            this.blockT = new Timer();
        }
        if (this.progress <= 1 && this.blockT.passedX(this.blockDelay.getValue())) {
            this.setItem(obby);
            if (this.progress == 0 && BlockUtil.getBlock(this.base.add(0, 0, 0)) == Blocks.AIR) {
                BlockUtil.placeBlock(this.base.add(0, 0, 0), this.packetPlace.getValue());
            }
            if (this.progress == 1 && BlockUtil.getBlock(this.base.add(0, 1, 0)) == Blocks.AIR) {
                BlockUtil.placeBlock(this.base.add(0, 1, 0), this.packetPlace.getValue());
            }
            this.blockT = new Timer();
            ++this.progress;
        }
        if (this.anvilT == null && this.progress == 2) {
            this.anvilT = new Timer();
        }
        if (this.progress == 2 && this.anvilT.passedX(this.blockDelay.getValue())) {
            this.setItem(anvil);
            BlockUtil.placeBlock(mypos.add(0, 2, 0), this.packetPlace.getValue());
            this.disable();
            this.reset();
        }
        this.restoreItem();
    }
    
    public void reset() {
        this.base = null;
        this.anvilT = null;
        this.blockT = null;
        this.progress = 0;
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (SelfAnvil.mc.player.isHandActive()) {
                this.oldhand = SelfAnvil.mc.player.getActiveHand();
            }
            this.oldslot = SelfAnvil.mc.player.inventory.currentItem;
            SelfAnvil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            SelfAnvil.mc.player.inventory.currentItem = slot;
            SelfAnvil.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                SelfAnvil.mc.player.setActiveHand(this.oldhand);
            }
            SelfAnvil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
}
