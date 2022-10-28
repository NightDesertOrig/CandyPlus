//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import net.minecraft.util.math.*;
import net.minecraft.init.*;

public class HoleUtil implements Util
{
    private static BlockPos[] surroundOffsets;
    
    public static boolean isObbyHole(final BlockPos pos) {
        for (final BlockPos offset : HoleUtil.surroundOffsets) {
            if (BlockUtil.getBlock(pos.add((Vec3i)offset)) != Blocks.OBSIDIAN) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBedrockHole(final BlockPos pos) {
        for (final BlockPos offset : HoleUtil.surroundOffsets) {
            if (BlockUtil.getBlock(pos.add((Vec3i)offset)) != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }
    
    static {
        HoleUtil.surroundOffsets = BlockUtil.toBlockPos(PlayerUtil.getOffsets(0, true));
    }
}
