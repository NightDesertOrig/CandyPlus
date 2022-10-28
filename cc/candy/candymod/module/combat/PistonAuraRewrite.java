//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import java.util.function.*;
import cc.candy.candymod.utils.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;

public class PistonAuraRewrite extends Module
{
    public Setting<Float> preDelay;
    public Setting<Float> pistonDelay;
    public Setting<Float> crystalDelay;
    public Setting<Float> redstoneDelay;
    public Setting<Float> breakDelay;
    public Setting<Float> destroyDelay;
    public Setting<findType> targetFindType;
    public Setting<Redstone> redstone;
    public Setting<Boolean> breakSync;
    public Setting<Float> maxDelay;
    public Setting<Integer> breakAttempts;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> packetCrystal;
    public Setting<Boolean> packetBreak;
    public Setting<Arm> swingArm;
    public Setting<Boolean> silentSwitch;
    public Setting<Boolean> toggle;
    public Setting<Boolean> multiThreading;
    public Setting<Integer> maxY;
    public Setting<Float> range;
    public Setting<Boolean> tick;
    public Setting<Boolean> render;
    public Setting<Boolean> line;
    public Setting<Float> width;
    public Setting<Color> pistonColor;
    public Setting<Color> crystalColor;
    public Setting<Color> redstoneColor;
    public static EntityPlayer target;
    public BlockPos pistonPos;
    public BlockPos crystalPos;
    public BlockPos redStonePos;
    public int pistonSlot;
    public int crystalSlot;
    public int redStoneSlot;
    public int obbySlot;
    public int oldslot;
    public int attempts;
    public EnumHand oldhand;
    public Timer preTimer;
    public Timer pistonTimer;
    public Timer crystalTimer;
    public Timer redstoneTimer;
    public Timer breakTimer;
    public Timer destroyTimer;
    public Timer maxTimer;
    public boolean preparedSpace;
    public boolean placedPiston;
    public boolean placedCrystal;
    public boolean placedRedstone;
    public boolean synced;
    public boolean brokeCrystal;
    
    public PistonAuraRewrite() {
        super("PistonAuraRewrite", Categories.COMBAT, false, false);
        this.preDelay = (Setting<Float>)this.register(new Setting("BlockDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.pistonDelay = (Setting<Float>)this.register(new Setting("PistonDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.crystalDelay = (Setting<Float>)this.register(new Setting("CrystalDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.redstoneDelay = (Setting<Float>)this.register(new Setting("RedstoneDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.breakDelay = (Setting<Float>)this.register(new Setting("BreakDelay", (T)1.0f, (T)25.0f, (T)0.0f));
        this.destroyDelay = (Setting<Float>)this.register(new Setting("DestroyDelay", (T)1.0f, (T)25.0f, (T)0.0f));
        this.targetFindType = (Setting<findType>)this.register(new Setting("Target", (T)findType.Nearest));
        this.redstone = (Setting<Redstone>)this.register(new Setting("Redstone", (T)Redstone.Block));
        this.breakSync = (Setting<Boolean>)this.register(new Setting("BreakSync", (T)true));
        this.maxDelay = (Setting<Float>)this.register(new Setting("MaxDelay", (T)30.0f, (T)60.0f, (T)5.0f, v -> this.breakSync.getValue()));
        this.breakAttempts = (Setting<Integer>)this.register(new Setting("BreakAttempts", (T)7, (T)20, (T)1));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)true));
        this.packetCrystal = (Setting<Boolean>)this.register(new Setting("PacketCrystal", (T)true));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.swingArm = (Setting<Arm>)this.register(new Setting("SwingArm", (T)Arm.None));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)true));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)true));
        this.multiThreading = (Setting<Boolean>)this.register(new Setting("MultiThreading", (T)false));
        this.maxY = (Setting<Integer>)this.register(new Setting("MaxY", (T)3, (T)5, (T)1));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)15.0f, (T)0.0f));
        this.tick = (Setting<Boolean>)this.register(new Setting("Tick", (T)true));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.line = (Setting<Boolean>)this.register(new Setting("Line", (T)false, v -> this.render.getValue()));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)2.0f, (T)5.0f, (T)0.1f, s -> this.line.getValue() && this.render.getValue()));
        this.pistonColor = (Setting<Color>)this.register(new Setting("PistonColor", (T)new Color(10, 100, 100, 100), v -> this.render.getValue()));
        this.crystalColor = (Setting<Color>)this.register(new Setting("CrystalColor", (T)new Color(250, 0, 200, 50), v -> this.render.getValue()));
        this.redstoneColor = (Setting<Color>)this.register(new Setting("RedstoneColor", (T)new Color(250, 10, 10, 50), v -> this.render.getValue()));
        this.redStonePos = null;
        this.obbySlot = -1;
        this.oldslot = -1;
        this.attempts = 0;
        this.oldhand = null;
        this.maxTimer = new Timer();
        this.brokeCrystal = false;
    }
    
    @Override
    public void onEnable() {
        this.reset();
    }
    
    @Override
    public void onUpdate() {
        if (!this.tick.getValue()) {
            this.doPA();
        }
    }
    
    @Override
    public void onTick() {
        if (this.tick.getValue()) {
            this.doPA();
        }
    }
    
    public void doPA() {
        if (this.multiThreading.getValue()) {
            MultiThreading.runAsync(this::_doPA);
        }
        else {
            this._doPA();
        }
    }
    
    public void _doPA() {
        try {
            if (this.nullCheck()) {
                return;
            }
            if (this.setup()) {
                return;
            }
            final BlockPos targetPos = new BlockPos(PistonAuraRewrite.target.posX, PistonAuraRewrite.target.posY, PistonAuraRewrite.target.posZ);
            if (this.preTimer.passedX(this.preDelay.getValue()) && !this.preparedSpace) {
                this.preTimer.reset();
                this.pistonTimer.reset();
                this.preparedSpace = this.prepareSpace();
            }
            if (this.pistonTimer.passedX(this.pistonDelay.getValue()) && this.preparedSpace && !this.placedPiston) {
                this.crystalTimer.reset();
                this.setItem(this.pistonSlot);
                final float[] angle = MathUtil.calcAngle(new Vec3d((Vec3i)this.crystalPos), new Vec3d((Vec3i)targetPos));
                PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0] + 180.0f, angle[1], true));
                BlockUtil.placeBlock(this.pistonPos, this.packetPlace.getValue());
                this.placedPiston = true;
            }
            if (this.crystalTimer.passedX(this.crystalDelay.getValue()) && this.placedPiston && !this.placedCrystal) {
                this.redstoneTimer.reset();
                this.setItem(this.crystalSlot);
                final EnumHand hand = (this.crystalSlot != 999) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                if (this.packetCrystal.getValue()) {
                    PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.crystalPos, EnumFacing.DOWN, hand, 0.0f, 0.0f, 0.0f));
                }
                else {
                    PistonAuraRewrite.mc.playerController.processRightClickBlock(PistonAuraRewrite.mc.player, PistonAuraRewrite.mc.world, this.crystalPos, EnumFacing.DOWN, new Vec3d(0.0, 0.0, 0.0), hand);
                }
                this.placedCrystal = true;
            }
            if (this.redstoneTimer.passedX(this.redstoneDelay.getValue()) && this.placedCrystal && !this.placedRedstone) {
                this.breakTimer.reset();
                this.setItem(this.redStoneSlot);
                if (this.redstone.getValue() == Redstone.Block) {
                    EnumFacing facing = null;
                    for (final EnumFacing facing2 : EnumFacing.values()) {
                        if (this.pistonPos.add(facing2.getDirectionVec()).equals((Object)this.redStonePos)) {
                            facing = facing2;
                        }
                    }
                    BlockUtil.rightClickBlock(this.pistonPos, facing, this.packetPlace.getValue());
                }
                else {
                    BlockUtil.placeBlock(this.redStonePos, this.packetPlace.getValue());
                }
                this.placedRedstone = true;
            }
            if (this.breakTimer.passedX(this.breakDelay.getValue()) && this.placedRedstone && !this.brokeCrystal) {
                this.maxTimer.reset();
                final Entity crystal = (Entity)PistonAuraRewrite.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAuraRewrite.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAuraRewrite.target.getDistance(e))).orElse(null);
                if (crystal == null) {
                    if (this.attempts <= this.breakAttempts.getValue()) {
                        ++this.attempts;
                    }
                    else {
                        this.reset();
                    }
                    this.restoreItem();
                    return;
                }
                if (this.packetBreak.getValue()) {
                    PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                }
                else {
                    PistonAuraRewrite.mc.playerController.attackEntity((EntityPlayer)PistonAuraRewrite.mc.player, crystal);
                }
                final EnumHand hand2 = (this.swingArm.getValue() == Arm.Mainhand) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                if (this.swingArm.getValue() != Arm.None) {
                    PistonAuraRewrite.mc.player.swingArm(hand2);
                }
                this.brokeCrystal = true;
            }
            if (!this.breakSync.getValue()) {
                this.synced = true;
            }
            if (this.breakSync.getValue() && this.brokeCrystal && !this.synced) {
                this.destroyTimer.reset();
                if (this.maxTimer.passedDms(this.maxDelay.getValue())) {
                    this.synced = true;
                }
                if (BlockUtil.getBlock(this.pistonPos) == Blocks.AIR) {
                    this.synced = true;
                }
                else {
                    final Entity crystal = (Entity)PistonAuraRewrite.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAuraRewrite.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAuraRewrite.target.getDistance(e))).orElse(null);
                    if (crystal == null) {
                        this.restoreItem();
                        return;
                    }
                    if (this.packetBreak.getValue()) {
                        PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                    }
                    else {
                        PistonAuraRewrite.mc.playerController.attackEntity((EntityPlayer)PistonAuraRewrite.mc.player, crystal);
                    }
                    final EnumHand hand2 = (this.swingArm.getValue() == Arm.Mainhand) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                    if (this.swingArm.getValue() != Arm.None) {
                        PistonAuraRewrite.mc.player.swingArm(hand2);
                    }
                }
            }
            if (this.destroyTimer.passedX(this.destroyDelay.getValue()) && this.synced) {
                this.reset();
            }
            this.restoreItem();
        }
        catch (Exception ex) {}
    }
    
    public boolean setup() {
        if (!this.findMaterials()) {
            if (this.toggle.getValue()) {
                this.sendMessage("Cannot find materials! disabling");
                this.disable();
            }
            return true;
        }
        if (!this.findTarget()) {
            if (this.toggle.getValue()) {
                this.sendMessage("Cannot find target! disabling");
                this.disable();
            }
            return true;
        }
        if (this.pistonPos == null && !this.findSpace(PistonAuraRewrite.target)) {
            if (this.toggle.getValue()) {
                this.sendMessage("Cannot find space! disabling");
                this.disable();
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void onRender3D() {
        try {
            if (this.crystalPos == null) {
                return;
            }
            if (this.line.getValue()) {
                RenderUtil3D.drawBoundingBox(this.pistonPos, 1.0, this.width.getValue(), this.pistonColor.getValue());
                RenderUtil3D.drawBoundingBox(this.crystalPos, 1.0, this.width.getValue(), this.crystalColor.getValue());
                RenderUtil3D.drawBoundingBox(this.redStonePos, 1.0, this.width.getValue(), this.redstoneColor.getValue());
            }
            else {
                RenderUtil3D.drawBox(this.pistonPos, 1.0, this.pistonColor.getValue(), 63);
                RenderUtil3D.drawBox(this.crystalPos, 1.0, this.crystalColor.getValue(), 63);
                RenderUtil3D.drawBox(this.redStonePos, 1.0, this.redstoneColor.getValue(), 63);
            }
        }
        catch (Exception ex) {}
    }
    
    public boolean prepareSpace() {
        final BlockPos targetPos = new BlockPos(PistonAuraRewrite.target.posX, PistonAuraRewrite.target.posY, PistonAuraRewrite.target.posZ);
        final BlockPos piston = this.pistonPos.add(0, -1, 0);
        final BlockPos redstone = this.redStonePos.add(0, -1, 0);
        if (BlockUtil.getBlock(piston) == Blocks.AIR) {
            final BlockPos offset = new BlockPos(this.crystalPos.getX() - targetPos.getX(), 0, this.crystalPos.getZ() - targetPos.getZ());
            final BlockPos crystalOffset = this.crystalPos.add((Vec3i)offset);
            final BlockPos crystalOffset2 = crystalOffset.add((Vec3i)offset);
            if (BlockUtil.hasNeighbour(piston)) {
                this.setItem(this.obbySlot);
                BlockUtil.placeBlock(piston, this.packetPlace.getValue());
                return false;
            }
            if (BlockUtil.getBlock(crystalOffset) == Blocks.AIR) {
                this.setItem(this.obbySlot);
                BlockUtil.placeBlock(crystalOffset, this.packetPlace.getValue());
                return false;
            }
            if (BlockUtil.getBlock(crystalOffset2) == Blocks.AIR) {
                this.setItem(this.obbySlot);
                BlockUtil.placeBlock(crystalOffset2, this.packetPlace.getValue());
                return false;
            }
        }
        if (BlockUtil.getBlock(redstone) == Blocks.AIR && (this.pistonPos.getX() != this.redStonePos.getX() || this.pistonPos.getZ() != this.redStonePos.getZ())) {
            this.setItem(this.obbySlot);
            BlockUtil.placeBlock(redstone, this.packetPlace.getValue());
            return false;
        }
        return true;
    }
    
    public boolean findSpace(final EntityPlayer target) {
        final BlockPos mypos = new BlockPos(PistonAuraRewrite.mc.player.posX, PistonAuraRewrite.mc.player.posY, PistonAuraRewrite.mc.player.posZ);
        final BlockPos targetPos = new BlockPos(target.posX, target.posY, target.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<AuraPos> aurapos = new ArrayList<AuraPos>();
        for (int y = 0; y <= this.maxY.getValue(); ++y) {
            for (final BlockPos offset : offsets) {
                final BlockPos crystalPos = targetPos.add((Vec3i)offset.add(0, y, 0));
                if (BlockUtil.getBlock(crystalPos) == Blocks.OBSIDIAN || BlockUtil.getBlock(crystalPos) == Blocks.BEDROCK) {
                    if (BlockUtil.getBlock(crystalPos.add(0, 1, 0)) == Blocks.AIR) {
                        if (BlockUtil.getBlock(crystalPos.add(0, 2, 0)) == Blocks.AIR) {
                            if (!this.checkPos(crystalPos)) {
                                final BlockPos normal = crystalPos.add((Vec3i)offset);
                                final BlockPos oneBlock = normal.add((Vec3i)offset);
                                final BlockPos side0 = normal.add(offset.getZ(), offset.getY(), offset.getX());
                                final BlockPos side2 = normal.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                final BlockPos side3 = oneBlock.add(offset.getZ(), offset.getY(), offset.getX());
                                final BlockPos side4 = oneBlock.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                final List<PistonPos> pistons = new ArrayList<PistonPos>();
                                this.add(pistons, new PistonPos(normal, false));
                                this.add(pistons, new PistonPos(oneBlock, true));
                                this.add(pistons, new PistonPos(side0, false));
                                this.add(pistons, new PistonPos(side2, false));
                                this.add(pistons, new PistonPos(side3, true));
                                this.add(pistons, new PistonPos(side4, true));
                                final BlockPos blockPos;
                                final BlockPos headPos;
                                final BlockPos blockPos2;
                                final PistonPos piston = pistons.stream().filter(p -> BlockUtil.getBlock(p.pos) == Blocks.AIR).filter(p -> {
                                    headPos = p.pos.add(blockPos.getX() * -1, 0, blockPos.getZ() * -1);
                                    return BlockUtil.getBlock(headPos) == Blocks.AIR && !this.checkPos(headPos);
                                }).filter(p -> this.getRedStonePos(crystalPos, p, offset) != null).filter(p -> !this.checkPos(p.pos)).filter(p -> PlayerUtil.getDistanceI(p.pos) <= this.range.getValue()).filter(p -> blockPos2.getDistance(p.pos.getX(), p.pos.getY(), p.pos.getZ()) >= 3.6 + (p.pos.getY() - (blockPos2.getY() + 1)) || p.pos.getY() <= blockPos2.getY() + 1).min(Comparator.comparing(p -> PlayerUtil.getDistanceI(p.pos))).orElse(null);
                                if (piston != null) {
                                    aurapos.add(new AuraPos(piston.pos, crystalPos, this.getRedStonePos(crystalPos, piston, offset)));
                                }
                            }
                        }
                    }
                }
            }
        }
        final AuraPos best = aurapos.stream().min(Comparator.comparing(p -> p.range())).orElse(null);
        if (best == null) {
            return false;
        }
        this.pistonPos = best.piston;
        this.crystalPos = best.crystal;
        this.redStonePos = best.redstone;
        return true;
    }
    
    public BlockPos getRedStonePos(final BlockPos crystalPos, final PistonPos piston, final BlockPos offset) {
        final BlockPos pistonPos = piston.pos;
        if (this.redstone.getValue() == Redstone.Block) {
            final List<BlockPos> redstone = new ArrayList<BlockPos>();
            redstone.add(pistonPos.add((Vec3i)offset));
            if (piston.allowUpside) {
                redstone.add(pistonPos.add(0, 1, 0));
            }
            return redstone.stream().filter(p -> BlockUtil.getBlock(p) == Blocks.AIR).filter(p -> !this.checkPos(p)).min(Comparator.comparing((Function<? super BlockPos, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
        }
        if (this.redstone.getValue() == Redstone.Torch) {
            final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
            final List<BlockPos> torchs = new ArrayList<BlockPos>();
            for (final BlockPos offs : offsets) {
                final BlockPos torch = pistonPos.add((Vec3i)offs);
                final BlockPos pistonP = pistonPos.add(offset.getX() * -1, 0, offset.getZ() * -1);
                if (torch.getX() != crystalPos.getX() || torch.getZ() != crystalPos.getZ()) {
                    if (torch.getX() != pistonP.getX() || torch.getZ() != pistonP.getZ()) {
                        if (BlockUtil.getBlock(torch) == Blocks.AIR) {
                            if (!this.checkPos(torch)) {
                                torchs.add(torch);
                            }
                        }
                    }
                }
            }
            return torchs.stream().min(Comparator.comparing((Function<? super BlockPos, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
        }
        return null;
    }
    
    public void add(final List<PistonPos> pistons, final PistonPos pos) {
        pistons.add(new PistonPos(pos.pos.add(0, 1, 0), pos.allowUpside));
        pistons.add(new PistonPos(pos.pos.add(0, 2, 0), true));
    }
    
    public boolean findTarget() {
        if (this.targetFindType.getValue() == findType.Nearest) {
            PistonAuraRewrite.target = PlayerUtil.getNearestPlayer(this.range.getValue());
        }
        if (this.targetFindType.getValue() == findType.Looking) {
            PistonAuraRewrite.target = PlayerUtil.getLookingPlayer(this.range.getValue());
        }
        return PistonAuraRewrite.target != null;
    }
    
    public boolean findMaterials() {
        this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
        this.obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        this.crystalSlot = InventoryUtil.getItemHotbar(Items.END_CRYSTAL);
        if (this.pistonSlot == -1) {
            this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.STICKY_PISTON);
        }
        if (PistonAuraRewrite.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystalSlot = 999;
        }
        final int block = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
        final int torch = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_TORCH);
        if (this.redstone.getValue() == Redstone.Block) {
            this.redStoneSlot = block;
        }
        if (this.redstone.getValue() == Redstone.Torch) {
            this.redStoneSlot = torch;
        }
        return this.pistonSlot != -1 && this.obbySlot != -1 && this.crystalSlot != -1 && this.redStoneSlot != -1;
    }
    
    public void reset() {
        PistonAuraRewrite.target = null;
        this.pistonPos = null;
        this.crystalPos = null;
        this.redStonePos = null;
        this.pistonSlot = -1;
        this.crystalSlot = -1;
        this.redStoneSlot = -1;
        this.obbySlot = -1;
        this.oldslot = -1;
        this.oldhand = null;
        this.preTimer = new Timer();
        this.pistonTimer = new Timer();
        this.crystalTimer = new Timer();
        this.redstoneTimer = new Timer();
        this.breakTimer = new Timer();
        this.destroyTimer = new Timer();
        this.maxTimer = new Timer();
        this.preparedSpace = false;
        this.placedPiston = false;
        this.placedCrystal = false;
        this.placedRedstone = false;
        this.brokeCrystal = false;
        this.synced = false;
        this.attempts = 0;
    }
    
    public boolean checkPos(final BlockPos pos) {
        final BlockPos mypos = new BlockPos(PistonAuraRewrite.mc.player.posX, PistonAuraRewrite.mc.player.posY, PistonAuraRewrite.mc.player.posZ);
        return pos.getX() == mypos.getX() && pos.getZ() == mypos.getZ();
    }
    
    public void setItem(final int slot) {
        if (slot == 999) {
            return;
        }
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (PistonAuraRewrite.mc.player.isHandActive()) {
                this.oldhand = PistonAuraRewrite.mc.player.getActiveHand();
            }
            this.oldslot = PistonAuraRewrite.mc.player.inventory.currentItem;
            PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            PistonAuraRewrite.mc.player.inventory.currentItem = slot;
            PistonAuraRewrite.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                PistonAuraRewrite.mc.player.setActiveHand(this.oldhand);
            }
            PistonAuraRewrite.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
    
    static {
        PistonAuraRewrite.target = null;
    }
    
    public enum findType
    {
        Nearest, 
        Looking, 
        Best;
    }
    
    public enum Redstone
    {
        Block, 
        Torch;
    }
    
    public enum Arm
    {
        Mainhand, 
        Offhand, 
        None;
    }
    
    public class AuraPos
    {
        public BlockPos piston;
        public BlockPos crystal;
        public BlockPos redstone;
        
        public AuraPos(final BlockPos piston, final BlockPos crystal, final BlockPos redstone) {
            this.redstone = null;
            this.piston = piston;
            this.crystal = crystal;
            this.redstone = redstone;
        }
        
        public double range() {
            final double pistonRange = PlayerUtil.getDistanceI(this.piston);
            final double crystalRange = PlayerUtil.getDistanceI(this.crystal);
            final double redstoneRange = PlayerUtil.getDistanceI(this.redstone);
            return Math.max(Math.max(pistonRange, crystalRange), redstoneRange);
        }
    }
    
    public class PistonPos
    {
        public BlockPos pos;
        public boolean allowUpside;
        
        public PistonPos(final BlockPos pos, final boolean allowUpside) {
            this.pos = pos;
            this.allowUpside = allowUpside;
        }
    }
}
