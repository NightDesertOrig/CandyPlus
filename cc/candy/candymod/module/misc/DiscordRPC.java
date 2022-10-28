//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.*;

public class DiscordRPC extends Module
{
    public Setting<Boolean> girl;
    
    public DiscordRPC() {
        super("DiscordRPC", Categories.MISC, false, false);
        this.girl = (Setting<Boolean>)this.register(new Setting("Girl", (T)false));
    }
    
    @Override
    public void onEnable() {
        CandyMod.m_rpc.enable(this);
    }
    
    @Override
    public void onDisable() {
        CandyMod.m_rpc.disable();
    }
}
