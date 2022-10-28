//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import java.io.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.*;
import java.util.*;

public class ConfigManager
{
    public static void saveConfigs() {
        final String folder = "candy/";
        final File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (final Module.Categories category : Module.Categories.values()) {
            final File categoryDir = new File(folder + category.name().toLowerCase());
            if (!categoryDir.exists()) {
                categoryDir.mkdirs();
            }
        }
        final List<Module> modules = CandyMod.m_module.modules;
        for (final Module module : modules) {
            try {
                module.saveConfig();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void loadConfigs() {
        final List<Module> modules = CandyMod.m_module.modules;
        for (final Module module : modules) {
            try {
                module.loadConfig();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
