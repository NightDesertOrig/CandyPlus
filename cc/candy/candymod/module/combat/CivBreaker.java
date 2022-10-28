//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import cc.candy.candymod.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import cc.candy.candymod.module.exploit.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;

public class CivBreaker extends Module
{
    public Setting<Float> preDelay;
    public Setting<Float> crystalDelay;
    public Setting<Float> breakDelay;
    public Setting<Float> attackDelay;
    public Setting<Float> endDelay;
    public Setting<Float> range;
    public Setting<Boolean> tick;
    public Setting<Boolean> toggle;
    public Setting<Boolean> noSwingBlock;
    public Setting<Boolean> packetPlace;
    public Setting<Boolean> packetCrystal;
    public Setting<Boolean> instantBreak;
    public Setting<Boolean> toggleSilentPickel;
    public Setting<Boolean> packetBreak;
    public Setting<Boolean> offHandBreak;
    public Setting<Boolean> skip;
    public Setting<Integer> breakAttempts;
    public Setting<Float> targetRange;
    public Setting<CevBreaker.Target> targetType;
    public Setting<Boolean> render;
    public Setting<Color> blockColor;
    public Setting<Boolean> line;
    public Setting<Float> width;
    public BlockPos base;
    public BlockPos old;
    public boolean builtTrap;
    public boolean placedCrystal;
    public boolean brokeBlock;
    public boolean attackedCrystal;
    public boolean done;
    public int crystalSlot;
    public int obbySlot;
    public int pickelSlot;
    public int attempts;
    public static EntityPlayer target;
    public Timer blockTimer;
    public Timer crystalTimer;
    public Timer breakTimer;
    public Timer attackTimer;
    public Timer endTimer;
    public static boolean breaking;
    
    public CivBreaker() {
        super("CivBreaker", Categories.COMBAT, false, false);
        this.preDelay = (Setting<Float>)this.register(new Setting("BlockDelay", (T)0.0f, (T)20.0f, (T)0.0f));
        this.crystalDelay = (Setting<Float>)this.register(new Setting("CrystalDelay", (T)0.0f, (T)20.0f, (T)0.0f));
        this.breakDelay = (Setting<Float>)this.register(new Setting("BreakDelay", (T)0.0f, (T)20.0f, (T)0.0f));
        this.attackDelay = (Setting<Float>)this.register(new Setting("AttackDelay", (T)3.0f, (T)20.0f, (T)0.0f));
        this.endDelay = (Setting<Float>)this.register(new Setting("EndDelay", (T)0.0f, (T)20.0f, (T)0.0f));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)20.0f, (T)1.0f));
        this.tick = (Setting<Boolean>)this.register(new Setting("Tick", (T)true));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)true));
        this.noSwingBlock = (Setting<Boolean>)this.register(new Setting("NoSwingBlock", (T)true));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.packetCrystal = (Setting<Boolean>)this.register(new Setting("PacketCrystal", (T)true));
        this.instantBreak = (Setting<Boolean>)this.register(new Setting("InstantBreak", (T)false));
        this.toggleSilentPickel = (Setting<Boolean>)this.register(new Setting("ToggleSilentPickel", (T)false));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.offHandBreak = (Setting<Boolean>)this.register(new Setting("OffhandBreak", (T)true));
        this.skip = (Setting<Boolean>)this.register(new Setting("Skip", (T)false));
        this.breakAttempts = (Setting<Integer>)this.register(new Setting("BreakAttempts", (T)7, (T)20, (T)1));
        this.targetRange = (Setting<Float>)this.register(new Setting("Target Range", (T)10.0f, (T)20.0f, (T)0.0f));
        this.targetType = (Setting<CevBreaker.Target>)this.register(new Setting("Target", (T)CevBreaker.Target.Nearest));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.blockColor = (Setting<Color>)this.register(new Setting("Color", (T)new Color(250, 0, 200, 50)));
        this.line = (Setting<Boolean>)this.register(new Setting("Line", (T)false, s -> this.render.getValue()));
        this.width = (Setting<Float>)this.register(new Setting("Line Width", (T)2.0f, (T)5.0f, (T)0.1f, s -> this.line.getValue() && this.render.getValue()));
        this.old = null;
        this.done = false;
        this.pickelSlot = -1;
        this.attempts = 0;
        this.endTimer = null;
    }
    
    @Override
    public void onEnable() {
        this.reset();
        if (this.toggleSilentPickel.getValue()) {
            this.setToggleSilentPickel(true);
        }
    }
    
    @Override
    public void onDisable() {
        if (this.toggleSilentPickel.getValue()) {
            this.setToggleSilentPickel(false);
        }
    }
    
    public void setToggleSilentPickel(final boolean toggle) {
        final Module silent = CandyMod.m_module.getModuleWithClass((Class)SilentPickel.class);
        if (toggle) {
            silent.enable();
        }
        else {
            silent.disable();
        }
    }
    
    @Override
    public void onTick() {
        if (this.tick.getValue()) {
            this.doCV();
        }
    }
    
    @Override
    public void onUpdate() {
        if (!this.tick.getValue()) {
            this.doCV();
        }
    }
    
    public void doCV() {
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
            CivBreaker.target = this.findTarget();
            if (this.isNull(CivBreaker.target)) {
                if (this.toggle.getValue()) {
                    this.sendMessage("Cannot find target! disabling...");
                    this.disable();
                }
                return;
            }
            if (this.isNull(this.base) && !this.findSpace(CivBreaker.target)) {
                if (this.toggle.getValue()) {
                    this.sendMessage("Cannot find space! disabling...");
                    this.disable();
                }
                return;
            }
            final BlockPos targetPos = new BlockPos(CivBreaker.target.posX, CivBreaker.target.posY, CivBreaker.target.posZ);
            if (this.blockTimer == null) {
                this.blockTimer = new Timer();
            }
            if (!this.builtTrap && BlockUtil.getBlock(this.base) == Blocks.AIR && this.blockTimer.passedX(this.preDelay.getValue())) {
                this.setItem(this.obbySlot);
                this.placeBlock(this.base, false);
                this.blockTimer = null;
                this.builtTrap = true;
            }
            if (this.builtTrap && !this.base.equals((Object)this.old) && this.skip.getValue()) {
                this.placedCrystal = true;
            }
            if (this.crystalTimer == null && this.builtTrap) {
                this.crystalTimer = new Timer();
            }
            if (this.builtTrap && !this.placedCrystal && this.crystalTimer.passedX(this.crystalDelay.getValue())) {
                if (this.crystalSlot != 999) {
                    this.setItem(this.crystalSlot);
                }
                final EnumHand hand = (this.crystalSlot != 999) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
                if (this.packetCrystal.getValue()) {
                    CivBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.base, EnumFacing.DOWN, hand, 0.0f, 0.0f, 0.0f));
                }
                else {
                    CivBreaker.mc.playerController.processRightClickBlock(CivBreaker.mc.player, CivBreaker.mc.world, this.base, EnumFacing.DOWN, new Vec3d(0.0, 0.0, 0.0), hand);
                }
                this.placedCrystal = true;
            }
            if (this.breakTimer == null && this.placedCrystal) {
                this.breakTimer = new Timer();
            }
            if (this.placedCrystal && !this.brokeBlock && this.breakTimer.passedX(this.breakDelay.getValue())) {
                this.setItem(this.pickelSlot);
                if (BlockUtil.getBlock(this.base) == Blocks.AIR) {
                    this.brokeBlock = true;
                }
                if (!CivBreaker.breaking) {
                    if (!this.instantBreak.getValue()) {
                        if (!this.noSwingBlock.getValue()) {
                            CivBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                        CivBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.base, EnumFacing.DOWN));
                        CivBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.base, EnumFacing.DOWN));
                    }
                    else {
                        if (!this.noSwingBlock.getValue()) {
                            CivBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                        CandyMod.m_module.getModuleWithClass((Class)InstantMine.class).enable();
                        InstantMine.startBreak(this.base, EnumFacing.DOWN);
                    }
                    CivBreaker.breaking = true;
                }
            }
            if (this.brokeBlock && !this.base.equals((Object)this.old) && this.skip.getValue()) {
                this.attackedCrystal = true;
            }
            if (this.attackTimer == null && this.brokeBlock) {
                this.attackTimer = new Timer();
            }
            if (this.brokeBlock && !this.attackedCrystal) {
                CivBreaker.breaking = false;
                if (this.attackTimer.passedX(this.attackDelay.getValue())) {
                    final BlockPos plannedCrystalPos = this.base.add(0, 1, 0);
                    final BlockPos blockPos;
                    final Entity crystal = (Entity)CivBreaker.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> new BlockPos(e.posX, e.posY, e.posZ).getDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < 1.5).min(Comparator.comparing(c -> c.getDistance((Entity)CivBreaker.target))).orElse(null);
                    if (crystal == null) {
                        if (this.attempts < this.breakAttempts.getValue()) {
                            this.reset();
                            return;
                        }
                        ++this.attempts;
                        return;
                    }
                    else {
                        final EnumHand hand2 = this.offHandBreak.getValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                        if (this.packetBreak.getValue()) {
                            CivBreaker.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                        }
                        else {
                            CivBreaker.mc.playerController.attackEntity((EntityPlayer)CivBreaker.mc.player, crystal);
                            CivBreaker.mc.player.swingArm(hand2);
                        }
                        this.attackedCrystal = true;
                    }
                }
            }
            if (this.endTimer == null && this.attackedCrystal) {
                this.endTimer = new Timer();
            }
            if (this.attackedCrystal && !this.done && this.endTimer.passedX(this.endDelay.getValue())) {
                this.done = true;
                this.old = new BlockPos(this.base.getX(), this.base.getY(), this.base.getZ());
                this.reset();
            }
            this.restoreItem();
        }
        catch (Exception ex) {}
    }
    
    public void reset() {
        this.base = null;
        this.builtTrap = false;
        this.placedCrystal = false;
        this.brokeBlock = false;
        this.attackedCrystal = false;
        this.done = false;
        this.crystalSlot = -1;
        this.obbySlot = -1;
        this.pickelSlot = -1;
        this.attempts = 0;
        CivBreaker.target = null;
        this.blockTimer = null;
        this.crystalTimer = null;
        this.breakTimer = null;
        this.attackTimer = null;
        this.endTimer = null;
        CivBreaker.breaking = false;
    }
    
    @Override
    public void onRender3D() {
        try {
            if (this.isNull(CivBreaker.target)) {
                return;
            }
            final BlockPos headPos = this.base;
            if (this.line.getValue()) {
                RenderUtil3D.drawBoundingBox(headPos, 1.0, 1.0f, new Color(this.blockColor.getValue().getRed(), this.blockColor.getValue().getGreen(), this.blockColor.getValue().getBlue(), 200));
            }
            else {
                RenderUtil3D.drawBox(headPos, 1.0, this.blockColor.getValue(), 63);
            }
        }
        catch (Exception ex) {}
    }
    
    public void setItem(final int slot) {
        CivBreaker.mc.player.inventory.currentItem = slot;
        CivBreaker.mc.playerController.updateController();
    }
    
    public void restoreItem() {
    }
    
    public boolean findSpace(final EntityPlayer player) {
        final BlockPos targetPos = new BlockPos(player.posX, player.posY, player.posZ);
        final BlockPos mypos = new BlockPos(CivBreaker.mc.player.posX, CivBreaker.mc.player.posY, CivBreaker.mc.player.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<BlockPos> posess = new ArrayList<BlockPos>();
        for (final BlockPos offset : offsets) {
            final BlockPos basePos = targetPos.add((Vec3i)offset);
            if ((BlockUtil.getBlock(basePos) == Blocks.OBSIDIAN || BlockUtil.getBlock(basePos) == Blocks.BEDROCK) && (BlockUtil.canPlaceBlockFuture(basePos.add(0, 1, 0)) || BlockUtil.getBlock(basePos.add(0, 1, 0)) == Blocks.OBSIDIAN)) {
                if (CrystalUtil.canPlaceCrystal(basePos.add(0, 1, 0))) {
                    posess.add(basePos.add(0, 1, 0));
                }
            }
        }
        this.base = posess.stream().filter(p -> CivBreaker.mc.player.getDistance((double)p.getX(), (double)p.getY(), (double)p.getZ()) <= this.range.getValue()).max(Comparator.comparing(p -> PlayerUtil.getDistanceI(p))).orElse(null);
        return this.base != null;
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        final List<EntityPlayer> players = (List<EntityPlayer>)CivBreaker.mc.world.playerEntities;
        if (this.targetType.getValue() == CevBreaker.Target.Nearest) {
            target = PlayerUtil.getNearestPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == CevBreaker.Target.Looking) {
            target = PlayerUtil.getLookingPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == CevBreaker.Target.Best) {
            target = players.stream().filter(p -> this.findSpace(p)).min(Comparator.comparing(p -> PlayerUtil.getDistance(p))).orElse(null);
        }
        return target;
    }
    
    public boolean findMaterials() {
        this.crystalSlot = InventoryUtil.getItemHotbar(Items.END_CRYSTAL);
        this.obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        this.pickelSlot = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
        if (this.itemCheck(this.crystalSlot) && CivBreaker.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystalSlot = 999;
        }
        return !this.itemCheck(this.crystalSlot) && !this.itemCheck(this.obbySlot) && !this.itemCheck(this.pickelSlot);
    }
    
    public boolean itemCheck(final int slot) {
        return slot == -1;
    }
    
    public boolean isNull(final Object o) {
        return o == null;
    }
    
    public void placeBlock(final BlockPos pos, final Boolean packet) {
        BlockUtil.placeBlock(pos, packet);
        if (!this.noSwingBlock.getValue()) {
            CivBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
    
    static {
        CivBreaker.target = null;
        CivBreaker.breaking = false;
    }
    
    public enum Target
    {
        Nearest, 
        Looking, 
        Best;
    }
}
