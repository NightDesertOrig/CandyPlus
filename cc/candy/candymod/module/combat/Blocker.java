//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import cc.candy.candymod.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import cc.candy.candymod.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;

public class Blocker extends Module
{
    public Setting<Boolean> piston;
    public Setting<Boolean> crystalSync;
    public Setting<Boolean> breakCrystalPA;
    public Setting<Boolean> teleportPA;
    public Setting<Integer> limitPA;
    public Setting<Float> crystalDelayPA;
    public Setting<Float> range;
    public Setting<Integer> maxY;
    public Setting<Boolean> cev;
    public Setting<Float> crystalDelayCEV;
    public Setting<Boolean> teleportCEV;
    public Setting<Integer> limitCEV;
    public Setting<Boolean> civ;
    public Setting<Float> crystalDelayCIV;
    public Setting<Float> placeDelay;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> packetBreak;
    public Setting<Arm> swingArm;
    public Setting<Boolean> silentSwitch;
    public Setting<Boolean> tick;
    public Setting<Boolean> render;
    public Setting<Color> pistonColor;
    public Entity PAcrystal;
    public List<BlockPos> pistonPos;
    public Timer crystalTimerPA;
    public int oldCrystal;
    public int limitCounterPA;
    public boolean needBlockCEV;
    public Timer crystalTimerCEV;
    public int limitCounterCEV;
    public int stage;
    public Timer timerCiv;
    public List<BlockPos> detectedPosCiv;
    public Timer placeTimer;
    private int oldslot;
    private EnumHand oldhand;
    
    public Blocker() {
        super("Blocker", Categories.COMBAT, false, false);
        this.piston = (Setting<Boolean>)this.register(new Setting("Piston", (T)true));
        this.crystalSync = (Setting<Boolean>)this.register(new Setting("CrystalSyncPA", (T)false, v -> this.piston.getValue()));
        this.breakCrystalPA = (Setting<Boolean>)this.register(new Setting("BreakCrystalPA", (T)false, v -> this.piston.getValue()));
        this.teleportPA = (Setting<Boolean>)this.register(new Setting("FlightBreakPA", (T)true, v -> this.piston.getValue() && this.breakCrystalPA.getValue()));
        this.limitPA = (Setting<Integer>)this.register(new Setting("LimitPA", (T)3, (T)10, (T)1, v -> this.piston.getValue() && this.breakCrystalPA.getValue() && this.teleportPA.getValue()));
        this.crystalDelayPA = (Setting<Float>)this.register(new Setting("CrystalDelayPA", (T)3.0f, (T)25.0f, (T)0.0f, v -> this.piston.getValue() && this.breakCrystalPA.getValue()));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)7.0f, (T)13.0f, (T)0.0f, v -> this.piston.getValue()));
        this.maxY = (Setting<Integer>)this.register(new Setting("MaxY", (T)4, (T)6, (T)2, v -> this.piston.getValue()));
        this.cev = (Setting<Boolean>)this.register(new Setting("CevBreaker", (T)true));
        this.crystalDelayCEV = (Setting<Float>)this.register(new Setting("CrystalDelayCEV", (T)3.0f, (T)25.0f, (T)0.0f, v -> this.cev.getValue()));
        this.teleportCEV = (Setting<Boolean>)this.register(new Setting("FlightBreakCEV", (T)true, v -> this.cev.getValue()));
        this.limitCEV = (Setting<Integer>)this.register(new Setting("LimitCEV", (T)3, (T)10, (T)1, v -> this.piston.getValue() && this.teleportCEV.getValue()));
        this.civ = (Setting<Boolean>)this.register(new Setting("CivBreaker", (T)true));
        this.crystalDelayCIV = (Setting<Float>)this.register(new Setting("CrystalDelayCIV", (T)3.0f, (T)25.0f, (T)0.0f, v -> this.cev.getValue()));
        this.placeDelay = (Setting<Float>)this.register(new Setting("PlaceDelay", (T)3.0f, (T)25.0f, (T)0.0f));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.swingArm = (Setting<Arm>)this.register(new Setting("SwingArm", (T)Arm.None));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)false));
        this.tick = (Setting<Boolean>)this.register(new Setting("Tick", (T)true));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.pistonColor = (Setting<Color>)this.register(new Setting("PistonColor", (T)new Color(230, 10, 10, 50), v -> this.piston.getValue()));
        this.PAcrystal = null;
        this.pistonPos = new ArrayList<BlockPos>();
        this.crystalTimerPA = new Timer();
        this.oldCrystal = -1;
        this.limitCounterPA = 0;
        this.needBlockCEV = false;
        this.crystalTimerCEV = new Timer();
        this.limitCounterCEV = 0;
        this.stage = 0;
        this.timerCiv = new Timer();
        this.detectedPosCiv = new ArrayList<BlockPos>();
        this.placeTimer = new Timer();
        this.oldslot = -1;
        this.oldhand = null;
    }
    
    @Override
    public void onEnable() {
        this.PAcrystal = null;
        this.pistonPos = new ArrayList<BlockPos>();
        this.crystalTimerPA = new Timer();
        this.needBlockCEV = false;
        this.crystalTimerCEV = new Timer();
        this.limitCounterCEV = 0;
        this.stage = 0;
        this.timerCiv = new Timer();
        this.detectedPosCiv = new ArrayList<BlockPos>();
        this.placeTimer = new Timer();
    }
    
    @Override
    public void onTick() {
        if (this.tick.getValue()) {
            this.doBlock();
        }
    }
    
    @Override
    public void onUpdate() {
        if (!this.tick.getValue()) {
            this.doBlock();
        }
    }
    
    public void doBlock() {
        if (this.nullCheck()) {
            return;
        }
        final int obby = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (obby == -1) {
            return;
        }
        final Module pa = CandyMod.m_module.getModuleWithClass((Class)PistonAura.class);
        final Module paRe = CandyMod.m_module.getModuleWithClass((Class)PistonAuraRewrite.class);
        final Module paRe2 = CandyMod.m_module.getModuleWithClass((Class)PistonAuraRewrite2.class);
        if (this.piston.getValue() && !pa.isEnable && !paRe.isEnable && !paRe2.isEnable) {
            final int obby2;
            this.execute(() -> {
                this.detectPA();
                this.blockPA(obby2);
                return;
            });
        }
        if (this.cev.getValue()) {
            this.execute(() -> this.blockCEV(obby));
        }
        final Module civ = CandyMod.m_module.getModuleWithClass((Class)CivBreaker.class);
        if (this.civ.getValue() && !civ.isEnable && !pa.isEnable && !paRe.isEnable) {
            this.execute(() -> this.blockCIV(obby));
        }
        this.restoreItem();
    }
    
    public void execute(final Runnable action) {
        try {
            action.run();
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void onRender3D() {
        try {
            if (this.pistonPos != null && this.piston.getValue() && this.render.getValue()) {
                for (final BlockPos piston : this.pistonPos) {
                    RenderUtil3D.drawBox(piston, 1.0, this.pistonColor.getValue(), 63);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public void blockCIV(final int obby) {
        final BlockPos mypos = new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY, Blocker.mc.player.posZ);
        final BlockPos[] array;
        final BlockPos[] offsets = array = new BlockPos[] { new BlockPos(1, 1, 0), new BlockPos(-1, 1, 0), new BlockPos(0, 1, 1), new BlockPos(0, 1, -1), new BlockPos(1, 1, 1), new BlockPos(1, 1, -1), new BlockPos(-1, 1, 1), new BlockPos(-1, 1, -1) };
        for (final BlockPos offset : array) {
            final BlockPos base = mypos.add((Vec3i)offset);
            final List<Entity> crystals = (List<Entity>)Blocker.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
            if (BlockUtil.getBlock(base) == Blocks.OBSIDIAN) {
                for (final Entity crystal : crystals) {
                    final BlockPos crystalPos = new BlockPos(crystal.posX, crystal.posY, crystal.posZ);
                    if (base.equals((Object)crystalPos.add(0, -1, 0))) {
                        Blocker.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                        this.detectedPosCiv.add(crystalPos);
                    }
                }
            }
        }
        if (this.timerCiv.passedX(this.crystalDelayCIV.getValue())) {
            this.timerCiv.reset();
            final Iterator<BlockPos> poses = this.detectedPosCiv.iterator();
            while (poses.hasNext()) {
                final BlockPos pos = poses.next();
                if (BlockUtil.getBlock(pos) == Blocks.AIR) {
                    this.setItem(obby);
                    if (BlockUtil.getBlock(pos.add(0, -1, 0)) == Blocks.AIR) {
                        BlockUtil.placeBlock(pos.add(0, -1, 0), this.packetPlace.getValue());
                    }
                    BlockUtil.placeBlock(pos, this.packetPlace.getValue());
                    poses.remove();
                }
            }
        }
    }
    
    public void blockCEV(final int obby) {
        final BlockPos mypos = new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY, Blocker.mc.player.posZ);
        final BlockPos ceilPos = mypos.add(0, 2, 0);
        if (this.placeTimer.passedX(this.crystalDelayCEV.getValue())) {
            if (this.stage == 1) {
                this.crystalTimerCEV.reset();
                this.setItem(obby);
                BlockUtil.placeBlock(ceilPos.add(0, 1, 0), this.packetPlace.getValue());
                this.stage = 0;
            }
            if (BlockUtil.getBlock(ceilPos) == Blocks.OBSIDIAN && CrystalUtil.hasCrystal(ceilPos) && this.teleportCEV.getValue() && this.limitCounterCEV < this.limitCEV.getValue()) {
                this.crystalTimerCEV.reset();
                ++this.limitCounterCEV;
                Blocker.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Blocker.mc.player.posX, mypos.getY() + 0.2, Blocker.mc.player.posZ, false));
                this.breakCrystal(CrystalUtil.getCrystal(ceilPos));
                this.swingArm();
                this.stage = 1;
            }
            else {
                this.limitCounterCEV = 0;
            }
        }
    }
    
    public void blockPA(final int obby) {
        if (this.crystalTimerPA.passedX(this.crystalDelayPA.getValue()) && this.piston.getValue() && this.breakCrystalPA.getValue() && this.PAcrystal != null) {
            this.crystalTimerPA.reset();
            if (this.PAcrystal.getEntityId() == this.oldCrystal) {
                ++this.limitCounterPA;
            }
            else {
                this.limitCounterPA = 0;
            }
            final BlockPos mypos = new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY, Blocker.mc.player.posZ);
            final BlockPos crystalPos = new BlockPos(this.PAcrystal.posX, this.PAcrystal.posY, this.PAcrystal.posZ);
            if (mypos.getY() + 2 == crystalPos.getY() && BlockUtil.getBlock(mypos.add(0, 2, 0)) == Blocks.OBSIDIAN && this.teleportPA.getValue() && this.limitCounterPA <= this.limitPA.getValue()) {
                final double offsetx = (mypos.getX() - crystalPos.getX()) * 0.4;
                final double offsetz = (mypos.getZ() - crystalPos.getZ()) * 0.4;
                Blocker.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mypos.getX() + 0.5 + offsetx, mypos.getY() + 0.2, mypos.getZ() + 0.5 + offsetz, false));
            }
            this.breakCrystal(this.PAcrystal);
            this.swingArm();
            this.oldCrystal = this.PAcrystal.getEntityId();
            this.PAcrystal = null;
        }
        if (this.placeTimer.passedX(this.placeDelay.getValue()) && this.piston.getValue()) {
            this.placeTimer.reset();
            final Iterator<BlockPos> pistons = this.pistonPos.iterator();
            while (pistons.hasNext()) {
                final BlockPos pos = pistons.next();
                if (BlockUtil.getBlock(pos) == Blocks.AIR) {
                    this.setItem(obby);
                    if (BlockUtil.hasNeighbour(pos)) {
                        BlockUtil.placeBlock(pos, this.packetPlace.getValue());
                    }
                    else {
                        BlockUtil.placeBlock(pos.add(0, -1, 0), this.packetPlace.getValue());
                        BlockUtil.rightClickBlock(pos.add(0, -1, 0), EnumFacing.UP, this.packetPlace.getValue());
                    }
                    pistons.remove();
                }
            }
        }
    }
    
    public void detectPA() {
        final List<BlockPos> tmp = new ArrayList<BlockPos>();
        final Iterator<BlockPos> iterator = this.pistonPos.iterator();
        while (iterator.hasNext()) {
            final BlockPos pos = iterator.next();
            if (tmp.contains(pos) || PlayerUtil.getDistance(pos) > this.range.getValue()) {
                iterator.remove();
            }
            tmp.add(pos);
        }
        final BlockPos mypos = new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY, Blocker.mc.player.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        for (int y = 0; y <= this.maxY.getValue(); ++y) {
            for (final BlockPos offset : offsets) {
                final BlockPos crystalPos = mypos.add(offset.getX(), y, offset.getZ());
                if (CrystalUtil.hasCrystal(crystalPos) || !this.crystalSync.getValue()) {
                    final List<BlockPos> pistonPos = new ArrayList<BlockPos>();
                    final BlockPos noOldCandy = crystalPos.add((Vec3i)offset);
                    final BlockPos sidePos0 = crystalPos.add(offset.getZ(), 0, offset.getX());
                    final BlockPos sidePos2 = crystalPos.add(offset.getZ() * -1, 0, offset.getX() * -1);
                    final BlockPos noSushi0 = noOldCandy.add((Vec3i)offset);
                    final BlockPos noSushi2 = noOldCandy.add(offset.getZ(), 0, offset.getX());
                    final BlockPos noSushi3 = noOldCandy.add(offset.getZ() * -1, 0, offset.getX() * -1);
                    final BlockPos noSushi4 = noSushi2.add((Vec3i)offset);
                    final BlockPos noSushi5 = noSushi3.add((Vec3i)offset);
                    this.add(pistonPos, noOldCandy);
                    this.add(pistonPos, sidePos0);
                    this.add(pistonPos, sidePos2);
                    this.add(pistonPos, noSushi0);
                    this.add(pistonPos, noSushi2);
                    this.add(pistonPos, noSushi3);
                    this.add(pistonPos, noSushi4);
                    this.add(pistonPos, noSushi5);
                    final List<BlockPos> imNoob = new ArrayList<BlockPos>();
                    pistonPos.forEach(b -> imNoob.add(b.add(0, 1, 0)));
                    imNoob.forEach(b -> pistonPos.add(b));
                    for (final BlockPos piston : pistonPos) {
                        if (this.isPiston(piston)) {
                            this.pistonPos.add(piston);
                            if (!CrystalUtil.hasCrystal(crystalPos)) {
                                continue;
                            }
                            this.PAcrystal = CrystalUtil.getCrystal(crystalPos);
                        }
                    }
                }
            }
        }
    }
    
    public void add(final List<BlockPos> target, final BlockPos base) {
        target.add(base.add(0, 1, 0));
    }
    
    public boolean isPiston(final BlockPos pos) {
        return BlockUtil.getBlock(pos) == Blocks.PISTON || BlockUtil.getBlock(pos) == Blocks.STICKY_PISTON;
    }
    
    public void swingArm() {
        final EnumHand arm = (this.swingArm.getValue() == Arm.Offhand) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        if (this.swingArm.getValue() != Arm.None) {
            Blocker.mc.player.swingArm(arm);
        }
    }
    
    public void breakCrystal(final Entity crystal) {
        if (!(crystal instanceof EntityEnderCrystal)) {
            return;
        }
        if (this.packetBreak.getValue()) {
            Blocker.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
        }
        else {
            Blocker.mc.playerController.attackEntity((EntityPlayer)Blocker.mc.player, crystal);
        }
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (Blocker.mc.player.isHandActive()) {
                this.oldhand = Blocker.mc.player.getActiveHand();
            }
            this.oldslot = Blocker.mc.player.inventory.currentItem;
            Blocker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            Blocker.mc.player.inventory.currentItem = slot;
            Blocker.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                Blocker.mc.player.setActiveHand(this.oldhand);
            }
            Blocker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
    
    public enum Arm
    {
        Mainhand, 
        Offhand, 
        None;
    }
}
