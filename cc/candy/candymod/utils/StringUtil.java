//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import net.minecraft.util.math.*;

public class StringUtil implements Util
{
    public static String getPositionString(final BlockPos pos) {
        return "X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ();
    }
    
    public static String getName(final String full) {
        String r = "";
        boolean a = false;
        for (final char c : full.toCharArray()) {
            if (!a) {
                r += String.valueOf(c).toUpperCase();
            }
            else {
                r += String.valueOf(c).toLowerCase();
            }
            a = true;
        }
        return r;
    }
}
