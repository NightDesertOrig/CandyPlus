//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import cc.candy.candymod.event.events.player.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.function.*;
import cc.candy.candymod.utils.*;
import net.minecraft.block.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;

public class PistonAuraRewrite2 extends Module
{
    public Setting<Float> preDelay;
    public Setting<Float> pistonDelay;
    public Setting<Float> breakDelay;
    public Setting<Float> destroyDelay;
    public Setting<Float> range;
    public Setting<findType> targetFindType;
    public Setting<Boolean> breakSync;
    public Setting<Float> maxDelay;
    public Setting<Integer> breakAttempts;
    public Setting<_type> type;
    public Setting<Redstone> redstone;
    public Setting<Boolean> toggle;
    public Setting<Boolean> multiThreading;
    public Setting<Boolean> sound;
    public Setting<Integer> maxY;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> packetCrystal;
    public Setting<Boolean> packetBreak;
    public Setting<Arm> swingArm;
    public Setting<Boolean> silentSwitch;
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
    public boolean isTorchSlot;
    public Timer preTimer;
    public Timer pistonTimer;
    public Timer breakTimer;
    public Timer destroyTimer;
    public Timer maxTimer;
    public boolean preparedSpace;
    public boolean placedPiston;
    public boolean placedRedstone;
    public boolean placedCrystal;
    public boolean brokeCrystal;
    public boolean synced;
    public int attempts;
    public int lastCrystal;
    private EnumHand oldhand;
    private int oldslot;
    
    public PistonAuraRewrite2() {
        super("PistonAuraRewrite2", Categories.COMBAT, false, false);
        this.preDelay = (Setting<Float>)this.register(new Setting("BlockDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.pistonDelay = (Setting<Float>)this.register(new Setting("PistonDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.breakDelay = (Setting<Float>)this.register(new Setting("BreakDelay", (T)1.0f, (T)25.0f, (T)0.0f));
        this.destroyDelay = (Setting<Float>)this.register(new Setting("DestroyDelay", (T)1.0f, (T)25.0f, (T)0.0f));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)5.0f, (T)15.0f, (T)0.0f));
        this.targetFindType = (Setting<findType>)this.register(new Setting("Target", (T)findType.Nearest));
        this.breakSync = (Setting<Boolean>)this.register(new Setting("BreakSync", (T)true));
        this.maxDelay = (Setting<Float>)this.register(new Setting("MaxDelay", (T)30.0f, (T)60.0f, (T)5.0f, v -> this.breakSync.getValue()));
        this.breakAttempts = (Setting<Integer>)this.register(new Setting("BreakAttempts", (T)7, (T)20, (T)1));
        this.type = (Setting<_type>)this.register(new Setting("Type", (T)_type.Tick));
        this.redstone = (Setting<Redstone>)this.register(new Setting("Redstone", (T)Redstone.Block));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)true));
        this.multiThreading = (Setting<Boolean>)this.register(new Setting("MultiThreading", (T)false));
        this.sound = (Setting<Boolean>)this.register(new Setting("Sound", (T)true));
        this.maxY = (Setting<Integer>)this.register(new Setting("MaxY", (T)3, (T)5, (T)1));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)true));
        this.packetCrystal = (Setting<Boolean>)this.register(new Setting("PacketCrystal", (T)true));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.swingArm = (Setting<Arm>)this.register(new Setting("SwingArm", (T)Arm.None));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)true));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.line = (Setting<Boolean>)this.register(new Setting("Line", (T)false, v -> this.render.getValue()));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)2.0f, (T)5.0f, (T)0.1f, s -> this.line.getValue() && this.render.getValue()));
        this.pistonColor = (Setting<Color>)this.register(new Setting("PistonColor", (T)new Color(10, 100, 150, 100), v -> this.render.getValue()));
        this.crystalColor = (Setting<Color>)this.register(new Setting("CrystalColor", (T)new Color(250, 0, 200, 50), v -> this.render.getValue()));
        this.redstoneColor = (Setting<Color>)this.register(new Setting("RedstoneColor", (T)new Color(250, 10, 10, 50), v -> this.render.getValue()));
        this.redStonePos = null;
        this.obbySlot = -1;
        this.isTorchSlot = false;
        this.maxTimer = new Timer();
        this.synced = false;
        this.attempts = 0;
        this.lastCrystal = -1;
        this.oldhand = null;
        this.oldslot = -1;
    }
    
    @Override
    public void onTick() {
        if (this.type.getValue() == _type.Tick) {
            this.doPA();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.type.getValue() == _type.Update) {
            this.doPA();
        }
    }
    
    @Override
    public void onEnable() {
        this.lastCrystal = -1;
        this.reset();
    }
    
    @SubscribeEvent
    public void onUpdateWalking(final UpdateWalkingPlayerEvent event) {
        if (this.type.getValue() == _type.Walking) {
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
        if (this.nullCheck()) {
            return;
        }
        if (!this.setup()) {
            return;
        }
        final BlockPos targetPos = new BlockPos(PistonAuraRewrite2.target.posX, PistonAuraRewrite2.target.posY, PistonAuraRewrite2.target.posZ);
        if (this.preTimer.passedX(this.preDelay.getValue()) && !this.preparedSpace) {
            this.pistonTimer.reset();
            this.preparedSpace = this.prepareSpace();
        }
        if (this.preparedSpace && !this.placedPiston && !this.placedRedstone && this.redStonePos.equals((Object)this.pistonPos.add(0, -1, 0))) {
            this.setItem(this.redStoneSlot);
            BlockUtil.placeBlock(this.redStonePos, this.packetPlace.getValue());
            this.placedRedstone = true;
        }
        if (this.pistonTimer.passedX(this.pistonDelay.getValue()) && this.preparedSpace && !this.placedPiston) {
            this.setItem(this.pistonSlot);
            final float[] angle = MathUtil.calcAngle(new Vec3d((Vec3i)this.crystalPos), new Vec3d((Vec3i)targetPos));
            PistonAuraRewrite2.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0] + 180.0f, angle[1], true));
            BlockUtil.placeBlock(this.pistonPos, this.packetPlace.getValue());
            this.placedPiston = true;
        }
        if (this.placedPiston && !this.placedRedstone) {
            this.setItem(this.redStoneSlot);
            if (!this.isTorchSlot) {
                EnumFacing facing = null;
                for (final EnumFacing facing2 : EnumFacing.values()) {
                    if (this.pistonPos.add(facing2.getDirectionVec()).equals((Object)this.redStonePos)) {
                        facing = facing2;
                    }
                }
                if (facing == null) {
                    return;
                }
                BlockUtil.rightClickBlock(this.pistonPos, facing, this.packetPlace.getValue());
            }
            else {
                BlockUtil.placeBlock(this.redStonePos, this.packetPlace.getValue());
            }
            this.placedRedstone = true;
        }
        if (this.placedRedstone && !this.placedCrystal) {
            this.setItem(this.crystalSlot);
            final EnumHand hand = (this.crystalSlot != 999) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            if (this.packetCrystal.getValue()) {
                PistonAuraRewrite2.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.crystalPos, EnumFacing.DOWN, hand, 0.0f, 0.0f, 0.0f));
            }
            else {
                PistonAuraRewrite2.mc.playerController.processRightClickBlock(PistonAuraRewrite2.mc.player, PistonAuraRewrite2.mc.world, this.crystalPos, EnumFacing.DOWN, new Vec3d(0.0, 0.0, 0.0), hand);
            }
            this.breakTimer.reset();
            this.placedCrystal = true;
        }
        if (this.breakTimer.passedX(this.breakDelay.getValue()) && this.placedCrystal && !this.brokeCrystal) {
            final Entity crystal = (Entity)PistonAuraRewrite2.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAuraRewrite2.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAuraRewrite2.target.getDistance(e))).orElse(null);
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
                PistonAuraRewrite2.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
            }
            else {
                PistonAuraRewrite2.mc.playerController.attackEntity((EntityPlayer)PistonAuraRewrite2.mc.player, crystal);
            }
            final EnumHand hand2 = (this.swingArm.getValue() == Arm.Mainhand) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            if (this.swingArm.getValue() != Arm.None) {
                PistonAuraRewrite2.mc.player.swingArm(hand2);
            }
            this.maxTimer.reset();
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
                final Entity crystal = (Entity)PistonAuraRewrite2.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAuraRewrite2.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAuraRewrite2.target.getDistance(e))).orElse(null);
                if (crystal == null) {
                    this.restoreItem();
                    return;
                }
                this.lastCrystal = crystal.getEntityId();
                if (this.packetBreak.getValue()) {
                    PistonAuraRewrite2.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                }
                else {
                    PistonAuraRewrite2.mc.playerController.attackEntity((EntityPlayer)PistonAuraRewrite2.mc.player, crystal);
                }
                final EnumHand hand2 = (this.swingArm.getValue() == Arm.Mainhand) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                if (this.swingArm.getValue() != Arm.None) {
                    PistonAuraRewrite2.mc.player.swingArm(hand2);
                }
            }
        }
        if (this.destroyTimer.passedX(this.destroyDelay.getValue()) && this.synced) {
            this.reset();
        }
        this.restoreItem();
    }
    
    public boolean prepareSpace() {
        final BlockPos targetPos = new BlockPos(PistonAuraRewrite2.target.posX, PistonAuraRewrite2.target.posY, PistonAuraRewrite2.target.posZ);
        final BlockPos redstone = this.redStonePos.add(0, -1, 0);
        final BlockPos base = this.pistonPos.add(0, -1, 0);
        if (BlockUtil.getBlock(base) == Blocks.AIR) {
            final BlockPos offset = new BlockPos(this.crystalPos.getX() - targetPos.getX(), 0, this.crystalPos.getZ() - targetPos.getZ());
            final BlockPos crystalOffset = this.crystalPos.add((Vec3i)offset);
            final BlockPos crystalOffset2 = crystalOffset.add((Vec3i)offset);
            if (BlockUtil.hasNeighbour(base) || base.equals((Object)this.redStonePos)) {
                if (!base.equals((Object)this.redStonePos)) {
                    this.setItem(this.obbySlot);
                    BlockUtil.placeBlock(base, this.packetPlace.getValue());
                    return false;
                }
            }
            else {
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
        }
        if (BlockUtil.getBlock(redstone) == Blocks.AIR && (this.pistonPos.getX() != this.redStonePos.getX() || this.pistonPos.getZ() != this.redStonePos.getZ()) && this.isTorchSlot) {
            this.setItem(this.obbySlot);
            BlockUtil.placeBlock(redstone, this.packetPlace.getValue());
            return false;
        }
        return true;
    }
    
    public boolean setup() {
        if (!this.findMaterials()) {
            this.disablePA("Cannot find materials! disabling");
            return false;
        }
        if (!this.findTarget()) {
            this.disablePA("Cannot find target! disabling");
            return false;
        }
        if (this.pistonPos == null && !this.findSpace(PistonAuraRewrite2.target)) {
            this.disablePA("Cannot find space! disabling");
            return false;
        }
        return true;
    }
    
    @Override
    public void onRender3D() {
        try {
            if (this.render.getValue()) {
                if (this.crystalPos == null) {
                    return;
                }
                if (this.line.getValue()) {
                    RenderUtil3D.drawBoundingBoxWithSides(this.crystalPos, (int)(this.width.getValue() + 3.0f), this.crystalColor.getValue(), 2);
                    RenderUtil3D.drawBoundingBox(this.pistonPos, 1.0, this.width.getValue(), this.pistonColor.getValue());
                    RenderUtil3D.drawBoundingBox(this.redStonePos, 1.0, this.width.getValue(), this.redstoneColor.getValue());
                }
                else {
                    RenderUtil3D.drawBox(this.crystalPos, 1.0, this.crystalColor.getValue(), 2);
                    RenderUtil3D.drawBox(this.pistonPos, 1.0, this.pistonColor.getValue(), 63);
                    RenderUtil3D.drawBox(this.redStonePos, 1.0, this.redstoneColor.getValue(), 63);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public void reset() {
        this.preTimer = new Timer();
        this.pistonTimer = new Timer();
        this.breakTimer = new Timer();
        this.maxTimer = new Timer();
        this.destroyTimer = new Timer();
        this.preparedSpace = false;
        this.placedPiston = false;
        this.placedRedstone = false;
        this.placedCrystal = false;
        this.brokeCrystal = false;
        this.synced = false;
        this.pistonPos = null;
        this.crystalPos = null;
        this.redStonePos = null;
        this.attempts = 0;
    }
    
    public boolean findSpace(final EntityPlayer target) {
        final BlockPos mypos = new BlockPos(PistonAuraRewrite2.mc.player.posX, PistonAuraRewrite2.mc.player.posY, PistonAuraRewrite2.mc.player.posZ);
        final BlockPos targetPos = new BlockPos(target.posX, target.posY, target.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<PistonAuraPos> can = new ArrayList<PistonAuraPos>();
        for (int y = 0; y <= this.maxY.getValue(); ++y) {
            for (final BlockPos offset : offsets) {
                final BlockPos crystalPos = targetPos.add(offset.getX(), y, offset.getZ());
                if (BlockUtil.getBlock(crystalPos) == Blocks.OBSIDIAN || BlockUtil.getBlock(crystalPos) == Blocks.BEDROCK) {
                    if (BlockUtil.getBlock(crystalPos.add(0, 1, 0)) == Blocks.AIR) {
                        if (BlockUtil.getBlock(crystalPos.add(0, 2, 0)) == Blocks.AIR) {
                            if (PlayerUtil.getDistanceI(crystalPos) <= this.range.getValue()) {
                                if (!this.checkPos(crystalPos)) {
                                    final BlockPos normal = crystalPos.add((Vec3i)offset);
                                    final BlockPos oneBlock = normal.add((Vec3i)offset);
                                    final BlockPos side0 = normal.add(offset.getZ(), offset.getY(), offset.getX());
                                    final BlockPos side2 = normal.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                    final BlockPos side3 = oneBlock.add(offset.getZ(), offset.getY(), offset.getX());
                                    final BlockPos side4 = oneBlock.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                    final BlockPos side5 = crystalPos.add(offset.getZ(), offset.getY(), offset.getX());
                                    final BlockPos side6 = crystalPos.add(offset.getZ() * -1, offset.getY(), offset.getX() * -1);
                                    final List<PistonPos> pistons = new ArrayList<PistonPos>();
                                    this.add(pistons, new PistonPos(normal));
                                    this.add(pistons, new PistonPos(oneBlock));
                                    this.add(pistons, new PistonPos(side0));
                                    this.add(pistons, new PistonPos(side2));
                                    this.add(pistons, new PistonPos(side3));
                                    this.add(pistons, new PistonPos(side4));
                                    this.add(pistons, new PistonPos(side5, true));
                                    this.add(pistons, new PistonPos(side6, true));
                                    final BlockPos blockPos;
                                    final BlockPos headPos;
                                    final BlockPos blockPos2;
                                    final PistonPos piston = pistons.stream().filter(p -> BlockUtil.getBlock(p.pos) == Blocks.AIR).filter(p -> PlayerUtil.getDistanceI(p.pos) <= this.range.getValue()).filter(p -> !this.checkPos(p.pos)).filter(p -> {
                                        headPos = p.pos.add(blockPos.getX() * -1, 0, blockPos.getZ() * -1);
                                        return BlockUtil.getBlock(headPos) == Blocks.AIR && !this.checkPos(headPos);
                                    }).filter(p -> blockPos2.getDistance(p.pos.getX(), p.pos.getY(), p.pos.getZ()) >= 3.6 + (p.pos.getY() - (blockPos2.getY() + 1)) || p.pos.getY() <= blockPos2.getY() + 1).filter(p -> this.getRedStonePos(p.pos, crystalPos, offset, p.swap) != null).min(Comparator.comparing(p -> PlayerUtil.getDistanceI(p.pos))).orElse(null);
                                    if (piston != null) {
                                        final PistonAuraPos pos = new PistonAuraPos(crystalPos, piston.pos, this.getRedStonePos(piston.pos, crystalPos, offset, piston.swap));
                                        can.add(pos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        final PistonAuraPos best = can.stream().min(Comparator.comparing((Function<? super PistonAuraPos, ? extends Comparable>)PistonAuraPos::range)).orElse(null);
        if (best == null) {
            return false;
        }
        this.crystalPos = best.crystal;
        this.pistonPos = best.piston;
        this.redStonePos = best.redstone;
        return true;
    }
    
    public BlockPos getRedStonePos(final BlockPos pistonPos, final BlockPos crystalPos, final BlockPos _offset, final boolean swap) {
        final BlockPos reverseOffset = new BlockPos(_offset.getX() * -1, _offset.getY(), _offset.getZ() * -1);
        final BlockPos pistonOffset = pistonPos.add((Vec3i)reverseOffset);
        final BlockPos[] array;
        final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(0, 1, 0), new BlockPos(0, -1, 0) };
        for (final BlockPos offset : array) {
            final BlockPos redstone = pistonPos.add((Vec3i)offset);
            if (!redstone.equals((Object)pistonOffset)) {
                if (BlockUtil.getBlock(redstone) == Blocks.REDSTONE_BLOCK || (BlockUtil.getBlock(redstone) == Blocks.REDSTONE_TORCH && offset.getY() != 1)) {
                    return redstone;
                }
            }
        }
        final List<BlockPos> pos = new ArrayList<BlockPos>();
        for (final BlockPos offset2 : offsets) {
            final BlockPos redstone2 = pistonPos.add((Vec3i)offset2);
            if (!this.isTorchSlot || offset2.getY() != 1) {
                if (BlockUtil.getBlock(redstone2) == Blocks.AIR) {
                    if (redstone2.getX() != crystalPos.getX() || redstone2.getZ() != crystalPos.getZ()) {
                        if (!redstone2.equals((Object)pistonOffset)) {
                            if (!this.checkPos(redstone2)) {
                                pos.add(redstone2);
                            }
                        }
                    }
                }
            }
        }
        return pos.stream().filter(b -> PlayerUtil.getDistance(b) <= this.range.getValue()).max(Comparator.comparing((Function<? super BlockPos, ? extends Comparable>)PlayerUtil::getDistanceI)).orElse(null);
    }
    
    public boolean checkPos(final BlockPos pos) {
        final BlockPos mypos = new BlockPos(PistonAuraRewrite2.mc.player.posX, PistonAuraRewrite2.mc.player.posY, PistonAuraRewrite2.mc.player.posZ);
        return pos.getX() == mypos.getX() && pos.getZ() == mypos.getZ();
    }
    
    public void add(final List<PistonPos> pistons, final PistonPos pos) {
        pistons.add(new PistonPos(pos.pos.add(0, 1, 0), pos.swap));
        pistons.add(new PistonPos(pos.pos.add(0, 2, 0), pos.swap));
    }
    
    public boolean findMaterials() {
        this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
        this.obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        this.crystalSlot = InventoryUtil.getItemHotbar(Items.END_CRYSTAL);
        if (this.pistonSlot == -1) {
            this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.STICKY_PISTON);
        }
        if (PistonAuraRewrite2.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
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
        if (this.redstone.getValue() == Redstone.Both) {
            if (block != -1) {
                this.redStoneSlot = block;
            }
            else {
                this.redStoneSlot = torch;
            }
        }
        this.isTorchSlot = (this.redStoneSlot == torch);
        return this.pistonSlot != -1 && this.obbySlot != -1 && this.crystalSlot != -1 && this.redStoneSlot != -1;
    }
    
    public boolean findTarget() {
        if (this.targetFindType.getValue() == findType.Nearest) {
            PistonAuraRewrite2.target = PlayerUtil.getNearestPlayer(30.0);
        }
        if (this.targetFindType.getValue() == findType.Looking) {
            PistonAuraRewrite2.target = PlayerUtil.getLookingPlayer(30.0);
        }
        if (this.targetFindType.getValue() == findType.Best) {
            PistonAuraRewrite2.target = (EntityPlayer)PistonAuraRewrite2.mc.world.playerEntities.stream().filter(e -> e.getEntityId() != PistonAuraRewrite2.mc.player.getEntityId()).filter(e -> !this.findSpace(e)).min(Comparator.comparing((Function<? super T, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
        }
        return PistonAuraRewrite2.target != null;
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    @Override
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (!this.isEnable) {
            return;
        }
        if (this.sound.getValue() && event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).getCategory() == SoundCategory.BLOCKS && ((SPacketSoundEffect)event.getPacket()).getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            for (final Entity crystal : new ArrayList<Entity>(PistonAuraRewrite2.mc.world.loadedEntityList)) {
                if (crystal instanceof EntityEnderCrystal && crystal.getDistance(((SPacketSoundEffect)event.getPacket()).getX(), ((SPacketSoundEffect)event.getPacket()).getY(), ((SPacketSoundEffect)event.getPacket()).getZ()) <= this.range.getValue() + 5.0f) {
                    crystal.setDead();
                }
            }
        }
    }
    
    public void disablePA(final String msg) {
        if (this.toggle.getValue()) {
            this.sendMessage(msg);
            this.disable();
        }
    }
    
    public void setItem(final int slot) {
        if (slot == 999) {
            return;
        }
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (PistonAuraRewrite2.mc.player.isHandActive()) {
                this.oldhand = PistonAuraRewrite2.mc.player.getActiveHand();
            }
            this.oldslot = PistonAuraRewrite2.mc.player.inventory.currentItem;
            PistonAuraRewrite2.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            PistonAuraRewrite2.mc.player.inventory.currentItem = slot;
            PistonAuraRewrite2.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                PistonAuraRewrite2.mc.player.setActiveHand(this.oldhand);
            }
            PistonAuraRewrite2.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
    
    static {
        PistonAuraRewrite2.target = null;
    }
    
    public enum _type
    {
        Tick, 
        Update, 
        Walking;
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
        Torch, 
        Both;
    }
    
    public enum Arm
    {
        Mainhand, 
        Offhand, 
        None;
    }
    
    public class PistonAuraPos
    {
        public BlockPos crystal;
        public BlockPos piston;
        public BlockPos redstone;
        
        public PistonAuraPos(final BlockPos crystal, final BlockPos piston, final BlockPos redstone) {
            this.crystal = crystal;
            this.piston = piston;
            this.redstone = redstone;
        }
        
        public double range() {
            final double crystalRange = PlayerUtil.getDistanceI(this.crystal);
            final double pistonRange = PlayerUtil.getDistanceI(this.piston);
            return Math.max(pistonRange, crystalRange);
        }
    }
    
    public class PistonPos
    {
        public BlockPos pos;
        public boolean swap;
        
        public PistonPos(final BlockPos pos) {
            this.pos = pos;
            this.swap = false;
        }
        
        public PistonPos(final BlockPos pos, final boolean swap) {
            this.pos = pos;
            this.swap = swap;
        }
    }
}
