//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;

public class HUDEditor extends Module
{
    public static HUDEditor instance;
    
    public HUDEditor() {
        super("HUDEditor", Module.Categories.RENDER, false, false);
        HUDEditor.instance = this;
    }
    
    static {
        HUDEditor.instance = null;
    }
}
