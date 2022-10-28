//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class SelfAnvil2 extends Module
{
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> silentSwitch;
    public Setting<Boolean> entityCheck;
    public Setting<Boolean> crystalOnly;
    public BlockPos basePos;
    public int stage;
    private EnumHand oldhand;
    private int oldslot;
    
    public SelfAnvil2() {
        super("SelfAnvil2", Categories.COMBAT, false, false);
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)true));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)true));
        this.entityCheck = (Setting<Boolean>)this.register(new Setting("EntityCheck", (T)true));
        this.crystalOnly = (Setting<Boolean>)this.register(new Setting("CrystalOnly", (T)false, v -> this.entityCheck.getValue()));
        this.oldhand = null;
        this.oldslot = -1;
    }
    
    @Override
    public void onEnable() {
        this.basePos = null;
        this.stage = 0;
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        final int obby = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        final int anvil = InventoryUtil.findHotbarBlock(Blocks.ANVIL);
        if (obby == -1 || anvil == -1) {
            this.sendMessage("Cannot find materials! disabling");
            this.disable();
            return;
        }
        final BlockPos anvilPos = PlayerUtil.getPlayerPos().add(0, 2, 0);
        if (!BlockUtil.hasNeighbour(anvilPos)) {
            if ((this.basePos = this.findPos()) == null) {
                this.sendMessage("Cannot find space! disabling");
                this.disable();
                return;
            }
            this.setItem(obby);
            final BlockPos pos0 = this.basePos.add(0, 1, 0);
            final BlockPos pos2 = this.basePos.add(0, 2, 0);
            BlockUtil.placeBlock(pos0, this.packetPlace.getValue());
            BlockUtil.rightClickBlock(pos0, EnumFacing.UP, this.packetPlace.getValue());
            this.setItem(anvil);
            EnumFacing facing = null;
            for (final EnumFacing f : EnumFacing.values()) {
                if (pos2.add(f.getDirectionVec()).equals((Object)anvilPos)) {
                    facing = f;
                }
            }
            BlockUtil.rightClickBlock(anvilPos, facing, this.packetPlace.getValue());
            this.restoreItem();
            this.disable();
        }
        else {
            this.setItem(anvil);
            BlockUtil.placeBlock(anvilPos, this.packetPlace.getValue());
            this.restoreItem();
            this.disable();
        }
    }
    
    public BlockPos findPos() {
        final BlockPos playerPos = PlayerUtil.getPlayerPos();
        final BlockPos lookingPos = playerPos.add(BlockUtil.getBackwardFacing(PlayerUtil.getLookingFacing()).getDirectionVec());
        final List<BlockPos> possiblePlacePositions = new ArrayList<BlockPos>();
        final BlockPos[] array;
        final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        for (final BlockPos offset : array) {
            final BlockPos pos = playerPos.add((Vec3i)offset);
            if (BlockUtil.getBlock(pos) != Blocks.AIR) {
                if (BlockUtil.canRightClickForPlace(pos)) {
                    final BlockPos pos2 = pos.add(0, 1, 0);
                    if (!this.entityCheck(pos2)) {
                        final BlockPos pos3 = pos2.add(0, 1, 0);
                        if (!this.entityCheck(pos3)) {
                            final BlockPos anvil = playerPos.add(0, 2, 0);
                            if (!this.entityCheck(anvil)) {
                                possiblePlacePositions.add(pos);
                            }
                        }
                    }
                }
            }
        }
        return possiblePlacePositions.stream().min(Comparator.comparing(b -> lookingPos.getDistance(b.getX(), b.getY(), b.getZ()))).orElse(null);
    }
    
    public boolean entityCheck(final BlockPos pos) {
        if (!this.entityCheck.getValue()) {
            return false;
        }
        for (final Entity e : SelfAnvil2.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (!(e instanceof EntityEnderCrystal) && this.crystalOnly.getValue()) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (SelfAnvil2.mc.player.isHandActive()) {
                this.oldhand = SelfAnvil2.mc.player.getActiveHand();
            }
            this.oldslot = SelfAnvil2.mc.player.inventory.currentItem;
            SelfAnvil2.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            SelfAnvil2.mc.player.inventory.currentItem = slot;
            SelfAnvil2.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                SelfAnvil2.mc.player.setActiveHand(this.oldhand);
            }
            SelfAnvil2.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
}
