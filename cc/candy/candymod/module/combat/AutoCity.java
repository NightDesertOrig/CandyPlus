//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.exploit.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import java.util.*;
import java.util.function.*;

public class AutoCity extends Module
{
    public Setting<Target> targetType;
    public Setting<Float> targetRange;
    public Setting<Float> range;
    public Setting<Boolean> instantBreak;
    public Setting<Boolean> noSwing;
    public Setting<Boolean> switcH;
    public Setting<Boolean> noSuicide;
    public EntityPlayer target;
    public BlockPos breakPos;
    
    public AutoCity() {
        super("AutoCity", Categories.COMBAT, false, false);
        this.targetType = (Setting<Target>)this.register(new Setting("Target", (T)Target.Nearest));
        this.targetRange = (Setting<Float>)this.register(new Setting("Target Range", (T)10.0f, (T)20.0f, (T)0.0f));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)20.0f, (T)0.0f));
        this.instantBreak = (Setting<Boolean>)this.register(new Setting("InstantBreak", (T)true));
        this.noSwing = (Setting<Boolean>)this.register(new Setting("NoSwing", (T)false));
        this.switcH = (Setting<Boolean>)this.register(new Setting("Switch", (T)false));
        this.noSuicide = (Setting<Boolean>)this.register(new Setting("NoSuicide", (T)true));
        this.target = null;
        this.breakPos = null;
    }
    
    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        this.target = this.findTarget();
        if (this.target == null) {
            this.sendMessage("Cannot find target! disabling...");
            this.disable();
            return;
        }
        if (this.findSpace(this.target) == -1) {
            this.sendMessage("Cannot find space! disabling...");
            this.disable();
            return;
        }
        this.sendMessage("Breaking...");
        if (!this.instantBreak.getValue()) {
            if (!this.noSwing.getValue()) {
                AutoCity.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            AutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
            AutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
        }
        else {
            if (!this.noSwing.getValue()) {
                AutoCity.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            CandyMod.m_module.getModuleWithClass((Class)InstantMine.class).enable();
            InstantMine.startBreak(this.breakPos, EnumFacing.DOWN);
        }
        if (this.switcH.getValue()) {
            final int pickel = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
            if (pickel == -1) {
                return;
            }
            AutoCity.mc.player.inventory.currentItem = pickel;
            AutoCity.mc.playerController.updateController();
        }
        this.disable();
    }
    
    public int findSpace(final EntityPlayer target) {
        final BlockPos mypos = new BlockPos(AutoCity.mc.player.posX, AutoCity.mc.player.posY, AutoCity.mc.player.posZ);
        final BlockPos base = new BlockPos(target.posX, target.posY, target.posZ);
        final BlockPos[] offsets = { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        final List<CitySpace> spaces = new ArrayList<CitySpace>();
        for (final BlockPos offset : offsets) {
            final CitySpace pos = new CitySpace();
            final BlockPos breakPos = base.add((Vec3i)offset);
            Label_0349: {
                if (BlockUtil.getBlock(breakPos) == Blocks.OBSIDIAN || BlockUtil.getBlock(breakPos) == Blocks.ENDER_CHEST) {
                    if (this.noSuicide.getValue()) {
                        boolean shouldSkip = false;
                        for (final BlockPos s2 : offsets) {
                            final BlockPos spos = mypos.add((Vec3i)s2);
                            if (spos.equals((Object)breakPos)) {
                                shouldSkip = true;
                            }
                        }
                        if (shouldSkip) {
                            break Label_0349;
                        }
                    }
                    pos.setPos(breakPos);
                    final BlockPos levelPos = breakPos.add((Vec3i)offset);
                    if (BlockUtil.getBlock(levelPos) != Blocks.AIR) {
                        pos.setLevel(0);
                    }
                    else if (BlockUtil.getBlock(levelPos.add(0, 1, 0)) != Blocks.AIR) {
                        pos.setLevel(1);
                    }
                    else {
                        pos.setLevel(2);
                    }
                    spaces.add(pos);
                }
            }
        }
        final CitySpace space = spaces.stream().filter(s -> PlayerUtil.getDistance(s.pos) <= this.range.getValue()).max(Comparator.comparing(s -> s.level + (this.range.getValue() - PlayerUtil.getDistance(s.pos)))).orElse(null);
        if (space == null) {
            return -1;
        }
        this.breakPos = space.pos;
        return space.level;
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        final List<EntityPlayer> players = new ArrayList<EntityPlayer>(AutoCity.mc.world.playerEntities);
        if (this.targetType.getValue() == Target.Nearest) {
            target = PlayerUtil.getNearestPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Looking) {
            target = PlayerUtil.getLookingPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Best) {
            target = players.stream().max(Comparator.comparing((Function<? super EntityPlayer, ? extends Comparable>)this::findSpace)).orElse(null);
        }
        return target;
    }
    
    public enum Target
    {
        Nearest, 
        Looking, 
        Best;
    }
    
    public class CitySpace
    {
        public BlockPos pos;
        public int level;
        
        public CitySpace() {
            this.level = -1;
        }
        
        public void setPos(final BlockPos pos) {
            this.pos = pos;
        }
        
        public void setLevel(final int level) {
            this.level = level;
        }
    }
}
