//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.client.settings.*;

public class CustomFov extends Module
{
    public Setting<Float> cfov;
    
    public CustomFov() {
        super("CustomFov", Module.Categories.RENDER, false, false);
        this.cfov = (Setting<Float>)this.register(new Setting("Fov", (T)0.0f, (T)180.0f, (T)1.0f));
    }
    
    public void onUpdate() {
        CustomFov.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, (float)this.cfov.getValue());
    }
}
