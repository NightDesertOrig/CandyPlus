//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.exploit.*;
import java.util.function.*;
import java.util.*;
import cc.candy.candymod.utils.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class AntiBurrow extends Module
{
    public Setting<Target> targetType;
    public Setting<Float> targetRange;
    public Setting<Float> range;
    public Setting<Boolean> instantBreak;
    public Setting<Boolean> noSwing;
    public Setting<Boolean> switcH;
    public Setting<Boolean> obby;
    public Setting<Boolean> echest;
    public EntityPlayer target;
    public BlockPos breakPos;
    
    public AntiBurrow() {
        super("AntiBurrow", Categories.COMBAT, false, false);
        this.targetType = (Setting<Target>)this.register(new Setting("Target", (T)Target.Nearest));
        this.targetRange = (Setting<Float>)this.register(new Setting("Target Range", (T)10.0f, (T)20.0f, (T)0.0f));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)20.0f, (T)0.0f));
        this.instantBreak = (Setting<Boolean>)this.register(new Setting("InstantBreak", (T)true));
        this.noSwing = (Setting<Boolean>)this.register(new Setting("NoSwing", (T)false));
        this.switcH = (Setting<Boolean>)this.register(new Setting("Switch", (T)false));
        this.obby = (Setting<Boolean>)this.register(new Setting("Obby", (T)false));
        this.echest = (Setting<Boolean>)this.register(new Setting("EChest", (T)false));
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
            this.sendMessage("Cannot find target! disabling");
            this.disable();
            return;
        }
        if (!this.canBreak(this.getPos(this.target))) {
            this.sendMessage("Target is not in block! disabling");
            return;
        }
        this.breakPos = this.getPos(this.target);
        this.sendMessage("Breaking...");
        if (!this.instantBreak.getValue()) {
            if (!this.noSwing.getValue()) {
                AntiBurrow.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            AntiBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
            AntiBurrow.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
        }
        else {
            if (!this.noSwing.getValue()) {
                AntiBurrow.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            CandyMod.m_module.getModuleWithClass((Class)InstantMine.class).enable();
            InstantMine.startBreak(this.breakPos, EnumFacing.DOWN);
        }
        if (this.switcH.getValue()) {
            final int pickel = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
            if (pickel == -1) {
                return;
            }
            AntiBurrow.mc.player.inventory.currentItem = pickel;
            AntiBurrow.mc.playerController.updateController();
        }
        this.disable();
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        final List<EntityPlayer> players = new ArrayList<EntityPlayer>(AntiBurrow.mc.world.playerEntities);
        if (this.targetType.getValue() == Target.Nearest) {
            target = PlayerUtil.getNearestPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Looking) {
            target = PlayerUtil.getLookingPlayer(this.targetRange.getValue());
        }
        if (this.targetType.getValue() == Target.Best) {
            target = players.stream().filter(e -> this.canBreak(this.getPos(e))).min(Comparator.comparing((Function<? super EntityPlayer, ? extends Comparable>)PlayerUtil::getDistance)).orElse(null);
        }
        return target;
    }
    
    public BlockPos getPos(final EntityPlayer player) {
        return new BlockPos(player.posX, player.posY, player.posZ);
    }
    
    public boolean canBreak(final BlockPos pos) {
        final Block block = BlockUtil.getBlock(pos);
        boolean can = false;
        if (block == Blocks.ANVIL) {
            can = true;
        }
        if (block == Blocks.ENDER_CHEST && this.echest.getValue()) {
            can = true;
        }
        if (block == Blocks.OBSIDIAN && this.obby.getValue()) {
            can = true;
        }
        return can;
    }
    
    public enum Target
    {
        Nearest, 
        Looking, 
        Best;
    }
}
