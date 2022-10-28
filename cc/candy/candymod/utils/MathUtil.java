//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import java.util.*;
import net.minecraft.util.math.*;

public class MathUtil implements Util
{
    public static Random rnd;
    
    public static int getRandom(final int min, final int max) {
        return MathUtil.rnd.nextInt(max - min + 1) + min;
    }
    
    public static ArrayList moveItemToFirst(final ArrayList list, final int index) {
        final ArrayList newlist = new ArrayList();
        newlist.add(list.get(index));
        for (int i = 0; i < list.size(); ++i) {
            if (i != index) {
                newlist.add(list.get(index));
            }
        }
        return new ArrayList(list);
    }
    
    public static float[] calcAngle(final Vec3d from, final Vec3d to) {
        final double difX = to.x - from.x;
        final double difY = (to.y - from.y) * -1.0;
        final double difZ = to.z - from.z;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
    }
    
    public static float square(final float v1) {
        return v1 * v1;
    }
    
    static {
        MathUtil.rnd = new Random();
    }
}
