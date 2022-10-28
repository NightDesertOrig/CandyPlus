//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.entity.item.*;
import java.util.function.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;

public class CrystalAura extends Module
{
    public Setting<Boolean> place;
    public Setting<Float> placeDelay;
    public Setting<Float> placeRange;
    public Setting<Float> wallRangePlace;
    public Setting<Boolean> placeSwing;
    public Setting<Boolean> autoSwitch;
    public Setting<Boolean> silentSwitch;
    public Setting<Boolean> opPlace;
    public Setting<Boolean> explode;
    public Setting<Boolean> predict;
    public Setting<Float> explodeDelay;
    public Setting<Float> breakRange;
    public Setting<Float> wallRangeBreak;
    public Setting<Boolean> packetBreak;
    public Setting<swingArm> swing;
    public Setting<Boolean> packetSwing;
    public Setting<Boolean> predictHit;
    public Setting<Integer> amount;
    public Setting<Integer> amountOffset;
    public Setting<Boolean> checkOtherEntity;
    public Setting<Boolean> ignoreSelfDmg;
    public Setting<Float> maxSelf;
    public Setting<Float> minDmg;
    public Setting<Boolean> smartMode;
    public Setting<Float> dmgError;
    public Setting<Boolean> antiSuicide;
    public Setting<Float> pauseHealth;
    public Setting<Boolean> betterFps;
    public EntityPlayer target;
    public int lastEntityID;
    public Timer placeTimer;
    public Timer breakTimer;
    private EnumHand oldhand;
    private int oldslot;
    
    public CrystalAura() {
        super("CrystalAura", Categories.COMBAT, false, false);
        this.place = (Setting<Boolean>)this.register(new Setting("Place", (T)true));
        this.placeDelay = (Setting<Float>)this.register(new Setting("PlaceDelay", (T)6.0f, (T)16.0f, (T)0.0f, v -> this.place.getValue()));
        this.placeRange = (Setting<Float>)this.register(new Setting("PlaceRange", (T)7.0f, (T)16.0f, (T)1.0f, v -> this.place.getValue()));
        this.wallRangePlace = (Setting<Float>)this.register(new Setting("WallRangePlace", (T)4.0f, (T)16.0f, (T)1.0f, v -> this.place.getValue()));
        this.placeSwing = (Setting<Boolean>)this.register(new Setting("Swing", (T)false));
        this.autoSwitch = (Setting<Boolean>)this.register(new Setting("Switch", (T)true));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)false, v -> this.autoSwitch.getValue()));
        this.opPlace = (Setting<Boolean>)this.register(new Setting("1.13", (T)false, v -> this.place.getValue()));
        this.explode = (Setting<Boolean>)this.register(new Setting("Explode", (T)true));
        this.predict = (Setting<Boolean>)this.register(new Setting("Predict", (T)true));
        this.explodeDelay = (Setting<Float>)this.register(new Setting("ExplodeDelay", (T)6.0f, (T)16.0f, (T)0.0f, v -> this.explode.getValue()));
        this.breakRange = (Setting<Float>)this.register(new Setting("ExplodeRange", (T)6.0f, (T)16.0f, (T)1.0f, v -> this.explode.getValue()));
        this.wallRangeBreak = (Setting<Float>)this.register(new Setting("WallRangeBreak", (T)3.0f, (T)16.0f, (T)1.0f, v -> this.explode.getValue()));
        this.packetBreak = (Setting<Boolean>)this.register(new Setting("PacketBreak", (T)true));
        this.swing = (Setting<swingArm>)this.register(new Setting("SwingArm", (T)swingArm.Mainhand));
        this.packetSwing = (Setting<Boolean>)this.register(new Setting("PacketSwing", (T)true, v -> this.swing.getValue() != swingArm.None));
        this.predictHit = (Setting<Boolean>)this.register(new Setting("PredictHit", (T)false));
        this.amount = (Setting<Integer>)this.register(new Setting("Amount", (T)1, (T)15, (T)1, v -> this.predictHit.getValue()));
        this.amountOffset = (Setting<Integer>)this.register(new Setting("Offset", (T)1, (T)10, (T)0, v -> this.predictHit.getValue()));
        this.checkOtherEntity = (Setting<Boolean>)this.register(new Setting("OtherEntity", (T)false, v -> this.predictHit.getValue()));
        this.ignoreSelfDmg = (Setting<Boolean>)this.register(new Setting("IgnoreSelfDamage", (T)false));
        this.maxSelf = (Setting<Float>)this.register(new Setting("MaxSelfDamage", (T)5.0f, (T)36.0f, (T)0.0f, v -> !this.ignoreSelfDmg.getValue()));
        this.minDmg = (Setting<Float>)this.register(new Setting("MinDamage", (T)3.0f, (T)36.0f, (T)0.0f));
        this.smartMode = (Setting<Boolean>)this.register(new Setting("SmartMode", (T)true));
        this.dmgError = (Setting<Float>)this.register(new Setting("DamageError", (T)3.0f, (T)15.0f, (T)1.0f, v -> this.smartMode.getValue()));
        this.antiSuicide = (Setting<Boolean>)this.register(new Setting("AntiSuicide", (T)true));
        this.pauseHealth = (Setting<Float>)this.register(new Setting("RequireHealth", (T)3.0f, (T)36.0f, (T)0.0f));
        this.betterFps = (Setting<Boolean>)this.register(new Setting("BetterFps", (T)true));
        this.lastEntityID = -1;
        this.breakTimer = new Timer();
        this.oldhand = null;
        this.oldslot = -1;
    }
    
    @Override
    public void onTick() {
        this.doCrystalAura();
    }
    
    public void doCrystalAura() {
        try {
            if (this.nullCheck()) {
                return;
            }
            this.target = PlayerUtil.getNearestPlayer(35.0);
            if (this.target == null) {
                return;
            }
            if (this.placeTimer == null || this.breakTimer == null) {
                this.placeTimer = new Timer();
                this.breakTimer = new Timer();
            }
            if (this.pauseHealth.getValue() > CrystalAura.mc.player.getHealth()) {
                return;
            }
            if (this.place.getValue()) {
                this.doPlace();
            }
            if (this.explode.getValue()) {
                this.doBreak();
            }
        }
        catch (Exception ex) {}
    }
    
    public void doPlace() {
        if (this.placeTimer.passedDms(this.placeDelay.getValue())) {
            this.placeTimer.reset();
            EnumHand hand;
            if (this.autoSwitch.getValue()) {
                if (CrystalAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                    hand = EnumHand.OFF_HAND;
                }
                else {
                    final int crystalSlot = InventoryUtil.getItemHotbar(Items.END_CRYSTAL);
                    if (crystalSlot == -1) {
                        return;
                    }
                    this.setItem(crystalSlot);
                    hand = EnumHand.MAIN_HAND;
                }
            }
            else if (CrystalAura.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                hand = EnumHand.MAIN_HAND;
            }
            else {
                if (CrystalAura.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                    return;
                }
                hand = EnumHand.OFF_HAND;
            }
            double maxDmg = 0.0;
            final List<CrystalPos> posList = new ArrayList<CrystalPos>();
            final List<BlockPos> possiblePlacePositions = CrystalUtil.possiblePlacePositions(this.placeRange.getValue(), true, this.opPlace.getValue());
            for (final BlockPos pos : possiblePlacePositions) {
                if (!CrystalUtil.canSeePos(pos) && PlayerUtil.getDistance(pos) > this.wallRangePlace.getValue()) {
                    continue;
                }
                final double selfDamage = CrystalUtil.calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, (Entity)CrystalAura.mc.player);
                if (selfDamage > this.maxSelf.getValue() && !this.ignoreSelfDmg.getValue()) {
                    continue;
                }
                final double enemyDamage = CrystalUtil.calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, (Entity)this.target);
                if (enemyDamage < this.minDmg.getValue()) {
                    continue;
                }
                if (selfDamage > enemyDamage && Math.abs(selfDamage - enemyDamage) >= this.dmgError.getValue() && this.smartMode.getValue()) {
                    continue;
                }
                if (enemyDamage > maxDmg) {
                    maxDmg = enemyDamage;
                }
                posList.add(new CrystalPos(pos, enemyDamage));
            }
            final double finalMaxDmg = maxDmg;
            final CrystalPos _bestPos = posList.stream().filter(e -> finalMaxDmg <= e.dmg).min(Comparator.comparing(e -> PlayerUtil.getDistance(e.pos))).orElse(null);
            if (_bestPos == null) {
                return;
            }
            final BlockPos bestPos = _bestPos.pos;
            CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(bestPos, EnumFacing.UP, hand, 0.5f, 0.5f, 0.5f));
            if (this.placeSwing.getValue()) {
                CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
            }
            if (this.predictHit.getValue() && this.packetBreak.getValue() && this.lastEntityID != -1) {
                for (int i = this.amountOffset.getValue(); i < this.amount.getValue(); ++i) {
                    final CPacketUseEntity packet = new CPacketUseEntity();
                    CrystalAura.mc.player.connection.sendPacket((Packet)packet);
                }
            }
            this.restoreItem();
        }
    }
    
    public void doBreak() {
        if (this.breakTimer.passedDms(this.explodeDelay.getValue())) {
            this.breakTimer.reset();
            final List<Entity> crystalList = new ArrayList<Entity>();
            for (final Entity entity : new ArrayList<Entity>(CrystalAura.mc.player.world.loadedEntityList)) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    continue;
                }
                if (entity.isDead) {
                    continue;
                }
                if (!this.isValidCrystal(entity.posX, entity.posY, entity.posZ)) {
                    continue;
                }
                crystalList.add(entity);
            }
            final Entity bestCrystal = crystalList.stream().max(Comparator.comparing((Function<? super Entity, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
            if (bestCrystal == null) {
                return;
            }
            if (this.packetBreak.getValue()) {
                CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(bestCrystal));
            }
            else {
                CrystalAura.mc.playerController.attackEntity((EntityPlayer)CrystalAura.mc.player, bestCrystal);
            }
            final EnumHand hand = (this.swing.getValue() == swingArm.Mainhand) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            if (this.swing.getValue() != swingArm.None) {
                if (this.packetSwing.getValue()) {
                    CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                else {
                    CrystalAura.mc.player.swingArm(hand);
                }
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    @Override
    public void onPacketReceive(final PacketEvent.Receive event) {
        final SPacketSpawnObject packet;
        if (event.getPacket() instanceof SPacketSpawnObject && (packet = (SPacketSpawnObject)event.getPacket()).getType() == 51 && this.explode.getValue() && this.predict.getValue() && this.packetBreak.getValue() && this.target != null) {
            this.lastEntityID = packet.getEntityID();
            if (!this.isValidCrystal(packet.getX(), packet.getY(), packet.getZ())) {
                return;
            }
            final CPacketUseEntity cpacket = new CPacketUseEntity();
            CrystalAura.mc.player.connection.sendPacket((Packet)cpacket);
            final EnumHand hand = (this.swing.getValue() == swingArm.Mainhand) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
            if (this.swing.getValue() != swingArm.None) {
                if (this.packetSwing.getValue()) {
                    CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                }
                else {
                    CrystalAura.mc.player.swingArm(hand);
                }
            }
        }
        if (this.checkOtherEntity.getValue()) {
            if (event.getPacket() instanceof SPacketSpawnExperienceOrb) {
                this.lastEntityID = ((SPacketSpawnExperienceOrb)event.getPacket()).getEntityID();
            }
            if (event.getPacket() instanceof SPacketSpawnMob) {
                this.lastEntityID = ((SPacketSpawnMob)event.getPacket()).getEntityID();
            }
            if (event.getPacket() instanceof SPacketSpawnPainting) {
                this.lastEntityID = ((SPacketSpawnPainting)event.getPacket()).getEntityID();
            }
            if (event.getPacket() instanceof SPacketSpawnPlayer) {
                this.lastEntityID = ((SPacketSpawnPlayer)event.getPacket()).getEntityID();
            }
        }
    }
    
    public boolean isValidCrystal(final double posX, final double posY, final double posZ) {
        final BlockPos pos = new BlockPos(posX, posY, posZ);
        if (PlayerUtil.getDistance(pos) > this.breakRange.getValue()) {
            return false;
        }
        if (!CrystalUtil.canSeePos(pos) && PlayerUtil.getDistance(pos) > this.wallRangeBreak.getValue()) {
            return false;
        }
        final double selfDamage = CrystalUtil.calculateDamage(posX, posY, posZ, (Entity)CrystalAura.mc.player);
        if (selfDamage > this.maxSelf.getValue() && !this.ignoreSelfDmg.getValue()) {
            return false;
        }
        if (CrystalAura.mc.player.getHealth() - selfDamage <= 0.0 && this.antiSuicide.getValue()) {
            return false;
        }
        final double enemyDamage = CrystalUtil.calculateDamage(posX, posY, posZ, (Entity)this.target);
        return enemyDamage >= this.minDmg.getValue() && (selfDamage <= enemyDamage || Math.abs(selfDamage - enemyDamage) < this.dmgError.getValue() || !this.smartMode.getValue());
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (CrystalAura.mc.player.isHandActive()) {
                this.oldhand = CrystalAura.mc.player.getActiveHand();
            }
            this.oldslot = CrystalAura.mc.player.inventory.currentItem;
            CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            CrystalAura.mc.player.inventory.currentItem = slot;
            CrystalAura.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                CrystalAura.mc.player.setActiveHand(this.oldhand);
            }
            CrystalAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
    
    public enum swingArm
    {
        Mainhand, 
        Offhand, 
        None;
    }
    
    public class CrystalPos
    {
        public BlockPos pos;
        public double dmg;
        
        public CrystalPos(final BlockPos pos, final double dmg) {
            this.pos = pos;
            this.dmg = dmg;
        }
    }
}
