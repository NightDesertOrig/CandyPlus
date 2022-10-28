//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import net.minecraft.init.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import cc.candy.candymod.utils.*;

public class HoleManager extends Manager
{
    private static final BlockPos[] surroundOffset;
    private final List<BlockPos> midSafety;
    private List<BlockPos> holes;
    
    public HoleManager() {
        this.midSafety = new ArrayList<BlockPos>();
        this.holes = new ArrayList<BlockPos>();
    }
    
    public void update() {
        if (!this.nullCheck()) {
            this.holes = this.calcHoles();
        }
    }
    
    public List<BlockPos> getHoles() {
        return this.holes;
    }
    
    public List<BlockPos> getMidSafety() {
        return this.midSafety;
    }
    
    public List<BlockPos> getSortedHoles() {
        this.holes.sort(Comparator.comparingDouble(hole -> HoleManager.mc.player.getDistanceSq(hole)));
        return this.getHoles();
    }
    
    public List<BlockPos> calcHoles() {
        final ArrayList<BlockPos> safeSpots = new ArrayList<BlockPos>();
        this.midSafety.clear();
        final List<BlockPos> positions = BlockUtil.getSphere(new BlockPos(HoleManager.mc.player.posX, HoleManager.mc.player.posY, HoleManager.mc.player.posZ), 6.0f, 6, false, true, 0);
        for (final BlockPos pos : positions) {
            if (HoleManager.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleManager.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                if (!HoleManager.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    continue;
                }
                boolean isSafe = true;
                boolean midSafe = true;
                for (final BlockPos offset : HoleManager.surroundOffset) {
                    final Block block = HoleManager.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                    if (BlockUtil.isBlockUnSolid(block)) {
                        midSafe = false;
                    }
                    if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                        isSafe = false;
                    }
                }
                if (isSafe) {
                    safeSpots.add(pos);
                }
                if (!midSafe) {
                    continue;
                }
                this.midSafety.add(pos);
            }
        }
        return safeSpots;
    }
    
    public List<BlockPos> calcHoles(final float range) {
        final ArrayList<BlockPos> safeSpots = new ArrayList<BlockPos>();
        this.midSafety.clear();
        final List<BlockPos> positions = BlockUtil.getSphere(new BlockPos(HoleManager.mc.player.posX, HoleManager.mc.player.posY, HoleManager.mc.player.posZ), range, 6, false, true, 0);
        for (final BlockPos pos : positions) {
            if (HoleManager.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleManager.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                if (!HoleManager.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                    continue;
                }
                boolean isSafe = true;
                boolean midSafe = true;
                for (final BlockPos offset : HoleManager.surroundOffset) {
                    final Block block = HoleManager.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                    if (BlockUtil.isBlockUnSolid(block)) {
                        midSafe = false;
                    }
                    if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                        isSafe = false;
                    }
                }
                if (isSafe) {
                    safeSpots.add(pos);
                }
                if (!midSafe) {
                    continue;
                }
                this.midSafety.add(pos);
            }
        }
        return safeSpots;
    }
    
    public boolean isSafe(final BlockPos pos) {
        boolean isSafe = true;
        for (final BlockPos offset : HoleManager.surroundOffset) {
            final Block block = HoleManager.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
            if (BlockUtil.isBlockUnSafe(block)) {
                isSafe = false;
                break;
            }
        }
        return isSafe;
    }
    
    public boolean inHole() {
        return HoleManager.mc.player != null && this.isSafe(new BlockPos(HoleManager.mc.player.posX, HoleManager.mc.player.posY, HoleManager.mc.player.posZ));
    }
    
    public boolean inHole(final EntityPlayer player) {
        return player != null && this.isSafe(new BlockPos(player.posX, player.posY, player.posZ));
    }
    
    static {
        surroundOffset = BlockUtil.toBlockPos(PlayerUtil.getOffsets(0, true));
    }
}
