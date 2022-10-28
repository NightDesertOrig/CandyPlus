//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import java.util.concurrent.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.*;

public class ResistanceDetector implements Util
{
    public static HashMap<String, Integer> resistanceList;
    
    public static void init() {
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        HashMap<String, Integer> a;
        ArrayList i;
        final HashMap<String, Integer> hashMap;
        final ArrayList<String> list;
        final Object o;
        scheduler.scheduleAtFixedRate(() -> {
            try {
                a = new HashMap<String, Integer>();
                i = new ArrayList();
                ResistanceDetector.resistanceList.forEach((k, v) -> {
                    if (v > 0) {
                        hashMap.put(k, v - 1);
                    }
                    else {
                        list.add(k);
                    }
                    return;
                });
                a.forEach((k, v) -> {
                    if (ResistanceDetector.resistanceList.containsKey(k)) {
                        ResistanceDetector.resistanceList.replace(k, v);
                    }
                    return;
                });
                a.clear();
                i.forEach(w -> {
                    if (ResistanceDetector.resistanceList.containsKey(o)) {
                        ResistanceDetector.resistanceList.remove(o);
                    }
                });
            }
            catch (ConcurrentModificationException ex) {}
        }, 0L, 1L, TimeUnit.SECONDS);
    }
    
    public static void onUpdate() {
        if (ResistanceDetector.mc.world != null && ResistanceDetector.mc.player != null) {
            for (final EntityPlayer uwu : ResistanceDetector.mc.world.playerEntities) {
                if (((EntityLivingBase)uwu).getAbsorptionAmount() < 9.0f) {
                    continue;
                }
                if (ResistanceDetector.resistanceList.containsKey(uwu.getName())) {
                    ResistanceDetector.resistanceList.remove(uwu.getName());
                }
                ResistanceDetector.resistanceList.put(uwu.getName(), 180);
            }
        }
    }
    
    static {
        ResistanceDetector.resistanceList = new HashMap<String, Integer>();
    }
}
