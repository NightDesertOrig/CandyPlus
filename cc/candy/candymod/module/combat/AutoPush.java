//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.init.*;
import java.util.*;
import cc.candy.candymod.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class AutoPush extends Module
{
    public Setting<Float> preDelay;
    public Setting<Float> placeDelay;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> silentSwitch;
    public Setting<Float> range;
    public Setting<RedStone> redStoneType;
    public Setting<Target> targetType;
    public Setting<Float> targetRange;
    public EntityPlayer target;
    public int pistonSlot;
    public int redstoneSlot;
    public int obbySlot;
    public BlockPos pistonPos;
    public BlockPos redstonePos;
    public int stage;
    public Timer preTimer;
    public Timer timer;
    public int oldslot;
    public EnumHand oldhand;
    public boolean isTorch;
    
    public AutoPush() {
        super("AutoPush", Categories.COMBAT, false, false);
        this.preDelay = (Setting<Float>)this.register(new Setting("BlockDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.placeDelay = (Setting<Float>)this.register(new Setting("PlaceDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)false));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)20.0f, (T)1.0f));
        this.redStoneType = (Setting<RedStone>)this.register(new Setting("Redstone", (T)RedStone.Both));
        this.targetType = (Setting<Target>)this.register(new Setting("Target", (T)Target.Nearest));
        this.targetRange = (Setting<Float>)this.register(new Setting("Target Range", (T)10.0f, (T)20.0f, (T)0.0f));
        this.target = null;
        this.obbySlot = -1;
        this.redstonePos = null;
        this.stage = 0;
        this.timer = null;
        this.oldslot = -1;
        this.oldhand = null;
        this.isTorch = false;
    }
    
    public void reset() {
        this.target = null;
        this.pistonSlot = -1;
        this.redstoneSlot = -1;
        this.obbySlot = -1;
        this.pistonPos = null;
        this.redstonePos = null;
        this.stage = 0;
        this.preTimer = null;
        this.timer = null;
        this.oldslot = -1;
        this.oldhand = null;
        this.isTorch = false;
    }
    
    @Override
    public void onEnable() {
        this.reset();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (!this.findMaterials()) {
            this.sendMessage("Cannot find materials! disabling...");
            this.disable();
            return;
        }
        this.target = this.findTarget();
        if (this.target == null) {
            this.sendMessage("Cannot find target! disabling...");
            this.disable();
            return;
        }
        if ((this.isNull(this.pistonPos) || this.isNull(this.redstonePos)) && !this.findSpace(this.target)) {
            this.sendMessage("Cannot find space! disabling...");
            this.disable();
            return;
        }
        if (this.preTimer == null) {
            this.preTimer = new Timer();
        }
        if (this.preTimer.passedX(this.preDelay.getValue()) && !this.prepareBlock()) {
            this.restoreItem();
            return;
        }
        if (this.timer == null) {
            this.timer = new Timer();
        }
        if (this.stage == 0 && this.timer.passedX(this.placeDelay.getValue())) {
            this.setItem(this.pistonSlot);
            final BlockPos targetPos = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
            final float[] angle = MathUtil.calcAngle(new Vec3d((Vec3i)this.pistonPos), new Vec3d((Vec3i)targetPos));
            AutoPush.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0] + 180.0f, angle[1], true));
            BlockUtil.placeBlock(this.pistonPos, this.packetPlace.getValue());
            this.stage = 1;
            this.timer.reset();
        }
        if (this.stage == 1 && this.timer.passedX(this.placeDelay.getValue())) {
            this.setItem(this.redstoneSlot);
            BlockUtil.placeBlock(this.redstonePos, this.packetPlace.getValue());
            this.stage = 2;
            this.disable();
            this.reset();
        }
        this.restoreItem();
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (AutoPush.mc.player.isHandActive()) {
                this.oldhand = AutoPush.mc.player.getActiveHand();
            }
            this.oldslot = AutoPush.mc.player.inventory.currentItem;
            AutoPush.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            AutoPush.mc.player.inventory.currentItem = slot;
            AutoPush.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                AutoPush.mc.player.setActiveHand(this.oldhand);
            }
            AutoPush.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
    
    public boolean isNull(final Object object) {
        return object == null;
    }
    
    public boolean findSpace(final EntityPlayer target) {
        final BlockPos targetPos = new BlockPos(target.posX, target.posY, target.posZ);
        final BlockPos mypos = new BlockPos(AutoPush.mc.player.posX, AutoPush.mc.player.posY, AutoPush.mc.player.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<AutoPushPos> poses = new ArrayList<AutoPushPos>();
        for (final BlockPos offset : offsets) {
            final AutoPushPos pos = new AutoPushPos();
            final BlockPos base = targetPos.add((Vec3i)offset);
            if (BlockUtil.getBlock(base) != Blocks.AIR) {
                final BlockPos pistonPos = base.add(0, 1, 0);
                if (BlockUtil.getBlock(pistonPos) == Blocks.AIR) {
                    if (!this.checkPos(pistonPos)) {
                        if (PlayerUtil.getDistance(pistonPos) >= 3.6 || pistonPos.getY() <= mypos.getY() + 1) {
                            if (BlockUtil.getBlock(targetPos.add(offset.getX() * -1, 1, offset.getZ() * -1)) == Blocks.AIR) {
                                final List<BlockPos> redstonePoses = new ArrayList<BlockPos>();
                                final List<BlockPos> roffsets = new ArrayList<BlockPos>();
                                roffsets.add(new BlockPos(1, 0, 0));
                                roffsets.add(new BlockPos(-1, 0, 0));
                                roffsets.add(new BlockPos(0, 0, 1));
                                roffsets.add(new BlockPos(0, 0, -1));
                                if (this.redStoneType.getValue() == RedStone.Block) {
                                    roffsets.add(new BlockPos(0, 1, 0));
                                }
                                for (final BlockPos roffset : roffsets) {
                                    final BlockPos redstonePos = pistonPos.add((Vec3i)roffset);
                                    if (redstonePos.getX() == targetPos.getX() && redstonePos.getZ() == targetPos.getZ()) {
                                        continue;
                                    }
                                    if (this.checkPos(redstonePos)) {
                                        continue;
                                    }
                                    if (BlockUtil.getBlock(redstonePos) != Blocks.AIR) {
                                        continue;
                                    }
                                    redstonePoses.add(redstonePos);
                                }
                                final BlockPos redstonePos2 = redstonePoses.stream().min(Comparator.comparing(b -> AutoPush.mc.player.getDistance((double)b.getX(), (double)b.getY(), (double)b.getZ()))).orElse(null);
                                if (redstonePos2 != null) {
                                    pos.setPiston(pistonPos);
                                    pos.setRedStone(redstonePos2);
                                    poses.add(pos);
                                }
                            }
                        }
                    }
                }
            }
        }
        final AutoPushPos bestPos = poses.stream().filter(p -> p.getMaxRange() <= this.range.getValue()).min(Comparator.comparing(p -> p.getMaxRange())).orElse(null);
        if (bestPos != null) {
            this.pistonPos = bestPos.piston;
            this.redstonePos = bestPos.redstone;
            return true;
        }
        return false;
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        final List<EntityPlayer> players = (List<EntityPlayer>)AutoPush.mc.world.playerEntities;
        if (this.targetType.getValue() == Target.Nearest) {
            target = PlayerUtil.getNearestPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Looking) {
            target = PlayerUtil.getLookingPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Best) {
            target = players.stream().filter(p -> p.getEntityId() != AutoPush.mc.player.getEntityId()).filter(p -> this.findSpace(p)).min(Comparator.comparing(p -> PlayerUtil.getDistance(p))).orElse(null);
        }
        return target;
    }
    
    public boolean findMaterials() {
        this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
        final int redstoneBlock = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
        final int redstoneTorch = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_TORCH);
        this.obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.itemCheck(this.pistonSlot)) {
            this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.STICKY_PISTON);
        }
        if (this.redStoneType.getValue() == RedStone.Block) {
            this.isTorch = false;
            this.redstoneSlot = redstoneBlock;
        }
        if (this.redStoneType.getValue() == RedStone.Torch) {
            this.isTorch = true;
            this.redstoneSlot = redstoneTorch;
        }
        if (this.redStoneType.getValue() == RedStone.Both) {
            this.isTorch = true;
            this.redstoneSlot = redstoneTorch;
            if (this.itemCheck(this.redstoneSlot)) {
                this.isTorch = false;
                this.redstoneSlot = redstoneBlock;
            }
        }
        return !this.itemCheck(this.redstoneSlot) && !this.itemCheck(this.pistonSlot) && !this.itemCheck(this.obbySlot);
    }
    
    public boolean checkPos(final BlockPos pos) {
        final BlockPos mypos = new BlockPos(AutoPush.mc.player.posX, AutoPush.mc.player.posY, AutoPush.mc.player.posZ);
        return pos.getX() == mypos.getX() && pos.getZ() == mypos.getZ();
    }
    
    public boolean itemCheck(final int slot) {
        return slot == -1;
    }
    
    public boolean prepareBlock() {
        final BlockPos targetPos = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        final BlockPos piston = this.pistonPos.add(0, -1, 0);
        final BlockPos redstone = this.redstonePos.add(0, -1, 0);
        if (BlockUtil.getBlock(piston) == Blocks.AIR) {
            this.setItem(this.obbySlot);
            BlockUtil.placeBlock(piston, this.packetPlace.getValue());
            if (this.delayCheck()) {
                return false;
            }
        }
        if (BlockUtil.getBlock(redstone) == Blocks.AIR) {
            this.setItem(this.obbySlot);
            BlockUtil.placeBlock(redstone, this.packetPlace.getValue());
            if (this.delayCheck()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean delayCheck() {
        final boolean hasDelay = this.preDelay.getValue() != 0.0f;
        return hasDelay;
    }
    
    public enum Target
    {
        Nearest, 
        Looking, 
        Best;
    }
    
    public enum RedStone
    {
        Block, 
        Torch, 
        Both;
    }
    
    public class AutoPushPos
    {
        public BlockPos piston;
        public BlockPos redstone;
        
        public double getMaxRange() {
            if (this.piston == null || this.redstone == null) {
                return 999999.0;
            }
            return Math.max(PlayerUtil.getDistance(this.piston), PlayerUtil.getDistance(this.redstone));
        }
        
        public void setPiston(final BlockPos piston) {
            this.piston = piston;
        }
        
        public void setRedStone(final BlockPos redstone) {
            this.redstone = redstone;
        }
    }
}
