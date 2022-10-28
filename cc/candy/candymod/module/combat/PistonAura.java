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
import org.apache.logging.log4j.*;
import cc.candy.candymod.*;
import java.util.*;
import cc.candy.candymod.utils.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;

public class PistonAura extends Module
{
    public Setting<Float> preDelay;
    public Setting<Float> pistonDelay;
    public Setting<Float> crystalDelay;
    public Setting<Float> redstoneDelay;
    public Setting<Float> pushDelay;
    public Setting<Float> breakDelay;
    public Setting<Float> targetRange;
    public Setting<Target> targetType;
    public Setting<Float> range;
    public Setting<RedStone> redStoneType;
    public Setting<Boolean> antiBlock;
    public Setting<Boolean> noSwingBlock;
    public Setting<Boolean> silentSwitch;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> packetCrystal;
    public Setting<Boolean> packetBreak;
    public Setting<Boolean> offHandBreak;
    public Setting<Boolean> sidePiston;
    public Setting<Boolean> tick;
    public Setting<Boolean> toggle;
    public Setting<Boolean> trap;
    public Setting<Float> trapDelay;
    public Setting<Boolean> breakSync;
    public Setting<Float> maxDelay;
    public Setting<Integer> breakAttempts;
    public Setting<Integer> maxY;
    public Setting<Boolean> render;
    public Setting<Color> crystalColor;
    public Setting<Color> pistonColor;
    public Setting<Color> redstoneColor;
    public Setting<Boolean> line;
    public Setting<Float> width;
    public Setting<Boolean> debug;
    public List<BlockPos> debugPosess;
    public int oldslot;
    public EnumHand oldhand;
    public static EntityPlayer target;
    public BlockPos pistonPos;
    public BlockPos crystalPos;
    public BlockPos redStonePos;
    public boolean placedPiston;
    public boolean placedCrystal;
    public boolean placedRedStone;
    public boolean waitedPiston;
    public boolean brokeCrystal;
    public boolean builtTrap;
    public boolean done;
    public boolean retrying;
    public boolean digging;
    public Timer pistonTimer;
    public Timer crystalTimer;
    public Timer redStoneTimer;
    public Timer pistonCrystalTimer;
    public Timer breakTimer;
    public Timer preTimer;
    public Timer trapTimer;
    public Timer syncTimer;
    public int pistonSlot;
    public int crystalSlot;
    public int redstoneSlot;
    public int obbySlot;
    public int pickelSlot;
    public int trapTicks;
    public int attempts;
    public BlockPos oldPiston;
    public BlockPos oldRedstone;
    public int tmpSlot;
    
    public PistonAura() {
        super("PistonAura", Categories.COMBAT, false, false);
        this.preDelay = (Setting<Float>)this.register(new Setting("BlockDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.pistonDelay = (Setting<Float>)this.register(new Setting("PistonDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.crystalDelay = (Setting<Float>)this.register(new Setting("CrystalDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.redstoneDelay = (Setting<Float>)this.register(new Setting("RedStoneDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.pushDelay = (Setting<Float>)this.register(new Setting("PushDelay", (T)0.0f, (T)25.0f, (T)0.0f));
        this.breakDelay = (Setting<Float>)this.register(new Setting("BreakDelay", (T)5.0f, (T)25.0f, (T)0.0f));
        this.targetRange = (Setting<Float>)this.register(new Setting("Target Range", (T)10.0f, (T)20.0f, (T)0.0f));
        this.targetType = (Setting<Target>)this.register(new Setting("Target", (T)Target.Nearest));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)20.0f, (T)1.0f));
        this.redStoneType = (Setting<RedStone>)this.register(new Setting("Redstone", (T)RedStone.Both));
        this.antiBlock = (Setting<Boolean>)this.register(new Setting("AntiBlock", (T)true));
        this.noSwingBlock = (Setting<Boolean>)this.register(new Setting("NoSwingBlock", (T)false));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)false));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)true));
        this.packetCrystal = (Setting<Boolean>)this.register(new Setting("PacketCrystal", (T)true));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.offHandBreak = (Setting<Boolean>)this.register(new Setting("OffhandBreak", (T)true));
        this.sidePiston = (Setting<Boolean>)this.register(new Setting("SidePiston", (T)false));
        this.tick = (Setting<Boolean>)this.register(new Setting("Tick", (T)true));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)true));
        this.trap = (Setting<Boolean>)this.register(new Setting("Trap", (T)false));
        this.trapDelay = (Setting<Float>)this.register(new Setting("TrapDelay", (T)3.0f, (T)25.0f, (T)0.0f, s -> this.trap.getValue()));
        this.breakSync = (Setting<Boolean>)this.register(new Setting("BreakSync", (T)true));
        this.maxDelay = (Setting<Float>)this.register(new Setting("MaxDelay", (T)50.0f, (T)100.0f, (T)1.0f, s -> this.breakSync.getValue()));
        this.breakAttempts = (Setting<Integer>)this.register(new Setting("BreakAttempts", (T)7, (T)20, (T)1));
        this.maxY = (Setting<Integer>)this.register(new Setting("MaxY", (T)2, (T)4, (T)1));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.crystalColor = (Setting<Color>)this.register(new Setting("Crystal Color", (T)new Color(250, 0, 200, 50), s -> this.render.getValue()));
        this.pistonColor = (Setting<Color>)this.register(new Setting("Piston Color", (T)new Color(40, 170, 245, 50), s -> this.render.getValue()));
        this.redstoneColor = (Setting<Color>)this.register(new Setting("RedStone Color", (T)new Color(252, 57, 50, 50), s -> this.render.getValue()));
        this.line = (Setting<Boolean>)this.register(new Setting("Line", (T)false, s -> this.render.getValue()));
        this.width = (Setting<Float>)this.register(new Setting("Line Width", (T)2.0f, (T)5.0f, (T)0.1f, s -> this.line.getValue() && this.render.getValue()));
        this.debug = (Setting<Boolean>)this.register(new Setting("Debug", (T)false));
        this.debugPosess = new ArrayList<BlockPos>();
        this.oldslot = -1;
        this.oldhand = null;
        this.trapTicks = 0;
        this.attempts = 0;
        this.oldRedstone = null;
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
        if (this.nullCheck()) {
            return;
        }
        try {
            if (!this.findMaterials()) {
                if (this.toggle.getValue()) {
                    this.sendMessage("Cannot find materials! disabling...");
                    this.disable();
                }
                return;
            }
            PistonAura.target = this.findTarget();
            if (this.isNull(PistonAura.target)) {
                if (this.toggle.getValue()) {
                    this.sendMessage("Cannot find target! disabling...");
                    this.disable();
                }
                return;
            }
            if ((this.isNull(this.pistonPos) || this.isNull(this.crystalPos) || this.isNull(this.redStonePos)) && !this.findSpace(PistonAura.target, this.redStoneType.getValue())) {
                if (this.toggle.getValue()) {
                    this.sendMessage("Cannot find space! disabling...");
                    this.disable();
                }
                return;
            }
            if (this.preTimer == null) {
                this.preTimer = new Timer();
            }
            if (this.preTimer.passedX(this.preDelay.getValue()) && !this.prepareBlock()) {
                this.restoreItem();
                return;
            }
            if (this.trapTimer == null) {
                this.trapTimer = new Timer();
            }
            if (!this.trap.getValue()) {
                this.builtTrap = true;
            }
            final BlockPos targetPos = new BlockPos(PistonAura.target.posX, PistonAura.target.posY, PistonAura.target.posZ);
            if (BlockUtil.getBlock(targetPos.add(0, 2, 0)) == Blocks.OBSIDIAN || this.pistonPos.getY() >= targetPos.add(0, 2, 0).getY()) {
                this.builtTrap = true;
            }
            if (!this.builtTrap && this.trapTimer.passedX(this.trapDelay.getValue())) {
                final BlockPos offset = new BlockPos(this.crystalPos.getX() - targetPos.getX(), 0, this.crystalPos.getZ() - targetPos.getZ());
                final BlockPos trapBase = targetPos.add(offset.getX() * -1, 0, offset.getZ() * -1);
                if (this.trapTicks == 0 && BlockUtil.getBlock(trapBase) == Blocks.AIR) {
                    this.setItem(this.obbySlot);
                    this.placeBlock(trapBase, false);
                    this.trapTimer = new Timer();
                    this.trapTicks = 1;
                }
                else {
                    this.trapTicks = 1;
                }
                if (this.trapTicks == 1) {
                    this.setItem(this.obbySlot);
                    this.placeBlock(trapBase.add(0, 1, 0), false);
                    this.trapTimer = new Timer();
                    this.trapTicks = 2;
                }
                if (this.trapTicks == 2) {
                    this.setItem(this.obbySlot);
                    this.placeBlock(trapBase.add(0, 2, 0), false);
                    this.trapTimer = new Timer();
                    this.trapTicks = 3;
                }
                if (this.trapTicks == 3) {
                    this.setItem(this.obbySlot);
                    this.placeBlock(targetPos.add(0, 2, 0), false);
                    this.trapTimer = new Timer();
                    this.trapTicks = 4;
                    this.builtTrap = true;
                }
                this.restoreItem();
                return;
            }
            if (this.pistonTimer == null && this.builtTrap) {
                this.pistonTimer = new Timer();
            }
            if (this.builtTrap && !this.placedPiston && this.pistonTimer.passedX(this.pistonDelay.getValue())) {
                this.setItem(this.pistonSlot);
                final float[] angle = MathUtil.calcAngle(new Vec3d((Vec3i)this.crystalPos), new Vec3d((Vec3i)targetPos));
                PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0] + 180.0f, angle[1], true));
                this.placeBlock(this.pistonPos, this.packetPlace.getValue());
                this.placedPiston = true;
            }
            if (this.crystalTimer == null && this.placedPiston) {
                this.crystalTimer = new Timer();
            }
            if (this.placedPiston && !this.placedCrystal && this.crystalTimer.passedX(this.crystalDelay.getValue())) {
                if (this.crystalSlot != 999) {
                    this.setItem(this.crystalSlot);
                }
                final EnumHand hand = (this.crystalSlot != 999) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                if (this.packetCrystal.getValue()) {
                    PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.crystalPos, EnumFacing.DOWN, hand, 0.0f, 0.0f, 0.0f));
                }
                else {
                    PistonAura.mc.playerController.processRightClickBlock(PistonAura.mc.player, PistonAura.mc.world, this.crystalPos, EnumFacing.DOWN, new Vec3d(0.0, 0.0, 0.0), hand);
                }
                this.placedCrystal = true;
            }
            if (this.redStoneTimer == null && this.placedCrystal) {
                this.redStoneTimer = new Timer();
            }
            if (this.placedCrystal && !this.placedRedStone && this.redStoneTimer.passedX(this.redstoneDelay.getValue())) {
                this.setItem(this.redstoneSlot);
                this.placeBlock(this.redStonePos, this.packetPlace.getValue());
                this.placedRedStone = true;
            }
            if (this.pistonCrystalTimer == null && this.placedRedStone) {
                this.pistonCrystalTimer = new Timer();
            }
            if (this.placedRedStone && !this.waitedPiston && this.pistonCrystalTimer.passedX(this.pushDelay.getValue())) {
                this.waitedPiston = true;
            }
            if (this.retrying) {
                this.setItem(this.pickelSlot);
                if (!this.digging) {
                    PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.redStonePos, EnumFacing.DOWN));
                    this.digging = true;
                }
                if (this.digging && BlockUtil.getBlock(this.redStonePos) == Blocks.AIR) {
                    PistonAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.redStonePos, EnumFacing.DOWN));
                    this.placedCrystal = false;
                    this.placedRedStone = false;
                    this.waitedPiston = false;
                    this.brokeCrystal = false;
                    this.done = false;
                    this.digging = false;
                    this.retrying = false;
                    this.crystalTimer = null;
                    this.redStoneTimer = null;
                    this.pistonCrystalTimer = null;
                    this.breakTimer = null;
                    this.attempts = 0;
                }
                this.restoreItem();
                return;
            }
            if (this.waitedPiston && !this.brokeCrystal) {
                final Entity crystal = (Entity)PistonAura.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAura.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAura.target.getDistance(e))).orElse(null);
                if (crystal == null) {
                    if (this.attempts < this.breakAttempts.getValue()) {
                        ++this.attempts;
                    }
                    else {
                        this.attempts = 0;
                        this.digging = false;
                        this.retrying = true;
                    }
                    this.restoreItem();
                    return;
                }
                final EnumHand hand2 = this.offHandBreak.getValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                if (this.packetBreak.getValue()) {
                    PistonAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                }
                else {
                    PistonAura.mc.playerController.attackEntity((EntityPlayer)PistonAura.mc.player, crystal);
                    PistonAura.mc.player.swingArm(hand2);
                }
                this.brokeCrystal = true;
            }
            if (this.breakTimer == null && this.brokeCrystal) {
                this.breakTimer = new Timer();
            }
            if (this.brokeCrystal && !this.done && this.breakTimer.passedX(this.breakDelay.getValue())) {
                this.done = true;
            }
            if (this.done) {
                if ((BlockUtil.getBlock(this.redStonePos) != Blocks.REDSTONE_BLOCK && BlockUtil.getBlock(this.redStonePos) != Blocks.REDSTONE_TORCH) || !this.breakSync.getValue()) {
                    this.reset();
                }
                else {
                    if (this.syncTimer == null) {
                        this.syncTimer = new Timer();
                    }
                    if (this.syncTimer.passedDms(this.maxDelay.getValue()) && this.maxDelay.getValue() != -1.0f) {
                        this.reset();
                    }
                    else {
                        final Entity crystal = (Entity)PistonAura.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> PistonAura.target.getDistance(e) < 5.0f).min(Comparator.comparing(e -> PistonAura.target.getDistance(e))).orElse(null);
                        if (crystal == null) {
                            this.restoreItem();
                            return;
                        }
                        final EnumHand hand2 = this.offHandBreak.getValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                        if (this.packetBreak.getValue()) {
                            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                        }
                        else {
                            PistonAura.mc.playerController.attackEntity((EntityPlayer)PistonAura.mc.player, crystal);
                            PistonAura.mc.player.swingArm(hand2);
                        }
                        this.breakTimer = null;
                        this.done = false;
                    }
                }
            }
            this.restoreItem();
        }
        catch (Exception e2) {
            CandyMod.Log(Level.ERROR, e2.getMessage());
        }
    }
    
    @Override
    public void onRender3D() {
        try {
            if (this.render.getValue()) {
                if (this.isNull(this.pistonPos) || this.isNull(this.crystalPos) || this.isNull(this.redStonePos)) {
                    return;
                }
                if (this.line.getValue()) {
                    RenderUtil3D.drawBoundingBox(this.crystalPos, 1.0, this.width.getValue(), this.convert(this.crystalColor.getValue()));
                    RenderUtil3D.drawBoundingBox(this.pistonPos, 1.0, this.width.getValue(), this.convert(this.pistonColor.getValue()));
                    RenderUtil3D.drawBoundingBox(this.redStonePos, 1.0, this.width.getValue(), this.convert(this.redstoneColor.getValue()));
                }
                else {
                    RenderUtil3D.drawBox(this.crystalPos, 1.0, this.crystalColor.getValue(), 63);
                    RenderUtil3D.drawBox(this.pistonPos, 1.0, this.pistonColor.getValue(), 63);
                    RenderUtil3D.drawBox(this.redStonePos, 1.0, this.redstoneColor.getValue(), 63);
                }
                if (this.debug.getValue()) {
                    for (final BlockPos pos : this.debugPosess) {
                        if (pos == null) {
                            continue;
                        }
                        RenderUtil3D.drawBoundingBox(pos, 1.0, this.width.getValue(), new Color(230, 230, 230));
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public Color convert(final Color c) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), 240);
    }
    
    public boolean findSpace(final EntityPlayer target, final RedStone type) {
        final BlockPos mypos = new BlockPos(PistonAura.mc.player.posX, PistonAura.mc.player.posY, PistonAura.mc.player.posZ);
        final BlockPos base = new BlockPos(target.posX, target.posY, target.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<PistonAuraPos> poses = new ArrayList<PistonAuraPos>();
        for (final BlockPos offset : offsets) {
            for (int y = 0; y <= this.maxY.getValue(); ++y) {
                final PistonAuraPos pos = new PistonAuraPos();
                final BlockPos crystalPos = base.add(offset.getX(), y, offset.getZ());
                if (BlockUtil.getBlock(crystalPos) == Blocks.ENDER_CHEST) {
                    this.sendmsg("x");
                }
                else if ((BlockUtil.getBlock(crystalPos) != Blocks.OBSIDIAN && BlockUtil.getBlock(crystalPos) != Blocks.BEDROCK) || BlockUtil.getBlock(crystalPos.add(0, 1, 0)) != Blocks.AIR || BlockUtil.getBlock(crystalPos.add(0, 2, 0)) != Blocks.AIR) {
                    this.sendmsg("y");
                }
                else if (mypos.getX() == crystalPos.getX() && mypos.getZ() == crystalPos.getZ()) {
                    this.sendmsg("a");
                }
                else {
                    pos.setCrystal(crystalPos);
                    final List<BlockPos> pistonOffsets = new ArrayList<BlockPos>();
                    pistonOffsets.add(new BlockPos(1, 0, 0));
                    pistonOffsets.add(new BlockPos(-1, 0, 0));
                    pistonOffsets.add(new BlockPos(0, 0, 1));
                    pistonOffsets.add(new BlockPos(0, 0, -1));
                    if (!this.antiBlock.getValue()) {
                        pistonOffsets.add(new BlockPos(0, 0, 0));
                    }
                    final BlockPos pistonBase = base.add(offset.getX() * 2, y + 1, offset.getZ() * 2);
                    final List<BlockPos> pistonPoses = new ArrayList<BlockPos>();
                    for (final BlockPos poff : pistonOffsets) {
                        final BlockPos pPos = pistonBase.add((Vec3i)poff);
                        if (BlockUtil.getBlock(pPos) != Blocks.AIR) {
                            this.sendmsg("b");
                        }
                        else {
                            final BlockPos checkPos_c = pPos.add(offset.getX() * -1, offset.getY(), offset.getZ() * -1);
                            final BlockPos checkPos_r = pPos.add(offset.getX() * 1, offset.getY(), offset.getZ() * 1);
                            if (mypos.getDistance(pPos.getX(), pPos.getY(), pPos.getZ()) < 3.6 + (pPos.getY() - (mypos.getY() + 1)) && pPos.getY() > mypos.getY() + 1) {
                                this.sendmsg("++");
                            }
                            else if (BlockUtil.getBlock(checkPos_c) != Blocks.AIR || BlockUtil.getBlock(checkPos_r) != Blocks.AIR || (pPos.getX() == crystalPos.getX() && pPos.getZ() == crystalPos.getZ()) || (mypos.getX() == pPos.getX() && mypos.getZ() == pPos.getZ()) || (mypos.getX() == checkPos_r.getX() && mypos.getZ() == checkPos_r.getZ()) || (mypos.getX() == checkPos_c.getX() && mypos.getZ() == checkPos_c.getZ())) {
                                this.sendmsg("d = " + checkPos_c.toString());
                            }
                            else {
                                pistonPoses.add(pPos);
                            }
                        }
                    }
                    pos.setPiston(pistonPoses.stream().min(Comparator.comparing(p -> mypos.getDistance(p.getX(), p.getY(), p.getZ()))).orElse(null));
                    if (this.isNull(pos.piston)) {
                        this.sendmsg("e");
                    }
                    else {
                        BlockPos redstonePos = null;
                        if ((crystalPos.getX() != pos.piston.getX() && crystalPos.getZ() != pos.piston.getZ()) || (pos.piston.getX() - crystalPos.getX() != 0 && pos.piston.getZ() - crystalPos.getZ() == 0) || (pos.piston.getZ() - crystalPos.getZ() != 0 && pos.piston.getX() - crystalPos.getX() == 0 && (offset.getX() != 0 || offset.getZ() != 0))) {
                            redstonePos = pos.piston.add((Vec3i)offset);
                        }
                        else {
                            redstonePos = pos.piston.add((Vec3i)new BlockPos(pos.piston.getX() - pos.crystal.getX(), 0, pos.piston.getZ() - pos.crystal.getZ()));
                        }
                        if (BlockUtil.getBlock(redstonePos) != Blocks.AIR) {
                            this.sendmsg("f");
                        }
                        else {
                            pos.setRedStone(redstonePos);
                            poses.add(pos);
                        }
                    }
                }
            }
        }
        if (poses.size() == 0) {
            return false;
        }
        final PistonAuraPos bestPos = poses.stream().filter(p -> p.getMaxRange() <= this.range.getValue()).min(Comparator.comparing(p -> p.getMaxRange())).orElse(null);
        if (bestPos == null) {
            return false;
        }
        this.pistonPos = bestPos.piston;
        this.crystalPos = bestPos.crystal;
        this.redStonePos = bestPos.redstone;
        return true;
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        final List<EntityPlayer> players = (List<EntityPlayer>)PistonAura.mc.world.playerEntities;
        if (this.targetType.getValue() == Target.Nearest) {
            target = PlayerUtil.getNearestPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Looking) {
            target = PlayerUtil.getLookingPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Best) {
            target = players.stream().filter(p -> p.getEntityId() != PistonAura.mc.player.getEntityId()).filter(p -> this.findSpace(p, this.redStoneType.getValue())).min(Comparator.comparing(p -> PlayerUtil.getDistance(p))).orElse(null);
        }
        return target;
    }
    
    public boolean findMaterials() {
        this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
        this.crystalSlot = InventoryUtil.getItemHotbar(Items.END_CRYSTAL);
        final int redstoneBlock = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
        final int redstoneTorch = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_TORCH);
        this.obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        this.pickelSlot = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
        if (this.itemCheck(this.crystalSlot) && PistonAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystalSlot = 999;
        }
        if (this.itemCheck(this.pistonSlot)) {
            this.pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.STICKY_PISTON);
        }
        if (this.redStoneType.getValue() == RedStone.Block) {
            this.redstoneSlot = redstoneBlock;
        }
        if (this.redStoneType.getValue() == RedStone.Torch) {
            this.redstoneSlot = redstoneTorch;
        }
        if (this.redStoneType.getValue() == RedStone.Both) {
            this.redstoneSlot = redstoneTorch;
            if (this.itemCheck(this.redstoneSlot)) {
                this.redstoneSlot = redstoneBlock;
            }
        }
        return !this.itemCheck(this.crystalSlot) && !this.itemCheck(this.obbySlot) && !this.itemCheck(this.pickelSlot) && !this.itemCheck(this.redstoneSlot) && !this.itemCheck(this.pistonSlot);
    }
    
    public boolean itemCheck(final int slot) {
        return slot == -1;
    }
    
    public boolean prepareBlock() {
        final BlockPos targetPos = new BlockPos(PistonAura.target.posX, PistonAura.target.posY, PistonAura.target.posZ);
        final BlockPos crystal = this.crystalPos;
        final BlockPos piston = this.pistonPos.add(0, -1, 0);
        final BlockPos redstone = this.redStonePos.add(0, -1, 0);
        if (BlockUtil.getBlock(crystal) == Blocks.AIR) {
            this.setItem(this.obbySlot);
            this.placeBlock(crystal, this.packetPlace.getValue());
            if (this.delayCheck()) {
                return false;
            }
        }
        if (!BlockUtil.hasNeighbour(piston)) {
            this.setItem(this.obbySlot);
            final BlockPos base = crystal.add(crystal.getX() - targetPos.getX(), 0, crystal.getZ() - targetPos.getZ());
            this.placeBlock(base, this.packetPlace.getValue());
            if (this.delayCheck()) {
                return false;
            }
        }
        if (BlockUtil.getBlock(piston) == Blocks.AIR) {
            this.setItem(this.obbySlot);
            this.placeBlock(piston, this.packetPlace.getValue());
            if (this.delayCheck()) {
                return false;
            }
        }
        if (BlockUtil.getBlock(redstone) == Blocks.AIR && (piston.getX() != redstone.getX() || piston.getZ() != redstone.getZ())) {
            this.setItem(this.obbySlot);
            this.placeBlock(redstone, this.packetPlace.getValue());
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
    
    public Vec3i getOffset(final BlockPos base, final int x, final int z) {
        return new Vec3i(base.getX() * x, 0, base.getZ() * z);
    }
    
    public boolean isNull(final Object b) {
        return b == null;
    }
    
    public void setTmp() {
        this.tmpSlot = PistonAura.mc.player.inventory.currentItem;
    }
    
    public void updateItem() {
        PistonAura.mc.player.inventory.currentItem = this.tmpSlot;
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (PistonAura.mc.player.isHandActive()) {
                this.oldhand = PistonAura.mc.player.getActiveHand();
            }
            this.oldslot = PistonAura.mc.player.inventory.currentItem;
            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            PistonAura.mc.player.inventory.currentItem = slot;
            PistonAura.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                PistonAura.mc.player.setActiveHand(this.oldhand);
            }
            PistonAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
    
    public void sendmsg(final String s) {
        if (this.debug.getValue()) {
            this.sendMessage(s);
        }
    }
    
    public void placeBlock(final BlockPos pos, final Boolean packet) {
        BlockUtil.placeBlock(pos, packet);
        if (!this.noSwingBlock.getValue()) {
            PistonAura.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    public void reset() {
        this.oldPiston = this.pistonPos;
        this.oldRedstone = this.redStonePos;
        PistonAura.target = null;
        this.pistonPos = null;
        this.crystalPos = null;
        this.redStonePos = null;
        this.placedPiston = false;
        this.placedCrystal = false;
        this.placedRedStone = false;
        this.waitedPiston = false;
        this.brokeCrystal = false;
        this.builtTrap = false;
        this.done = false;
        this.digging = false;
        this.retrying = false;
        this.pistonTimer = null;
        this.crystalTimer = null;
        this.redStoneTimer = null;
        this.pistonCrystalTimer = null;
        this.breakTimer = null;
        this.preTimer = null;
        this.trapTimer = null;
        this.syncTimer = null;
        this.pistonSlot = -1;
        this.crystalSlot = -1;
        this.redstoneSlot = -1;
        this.obbySlot = -1;
        this.pickelSlot = -1;
        this.trapTicks = 0;
        this.attempts = 0;
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
    
    public class PistonAuraPos
    {
        private BlockPos piston;
        private BlockPos crystal;
        private BlockPos redstone;
        
        public void setPiston(final BlockPos piston) {
            this.piston = piston;
        }
        
        public void setCrystal(final BlockPos crystal) {
            this.crystal = crystal;
        }
        
        public void setRedStone(final BlockPos redstone) {
            this.redstone = redstone;
        }
        
        public BlockPos getCrystalPos() {
            return this.crystal;
        }
        
        public BlockPos getPistonPos() {
            return this.piston;
        }
        
        public BlockPos getRedstone() {
            return this.redstone;
        }
        
        public double getMaxRange() {
            final double p = PlayerUtil.getDistanceI(this.piston);
            final double c = PlayerUtil.getDistanceI(this.crystal);
            final double r = PlayerUtil.getDistanceI(this.redstone);
            return Math.max(Math.max(p, c), r);
        }
    }
}
