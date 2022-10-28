//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;

public class Notification extends Module
{
    public Setting<Integer> time;
    public Setting<Boolean> togglE;
    public Setting<Boolean> message;
    public Setting<Boolean> player;
    public Setting<Boolean> pop;
    public Setting<Boolean> death;
    
    public Notification() {
        super("Notification", Module.Categories.RENDER, false, false);
        this.time = (Setting<Integer>)this.register(new Setting("Time", (T)2, (T)5, (T)1));
        this.togglE = (Setting<Boolean>)this.register(new Setting("Toggle", (T)false));
        this.message = (Setting<Boolean>)this.register(new Setting("Message", (T)true));
        this.player = (Setting<Boolean>)this.register(new Setting("Player", (T)true));
        this.pop = (Setting<Boolean>)this.register(new Setting("Totem", (T)true));
        this.death = (Setting<Boolean>)this.register(new Setting("Death", (T)true));
    }
}
