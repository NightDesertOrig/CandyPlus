//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ItemViewModel extends Module
{
    public Setting<Float> fov;
    
    public ItemViewModel() {
        super("ItemViewModel", Module.Categories.RENDER, false, false);
        this.fov = (Setting<Float>)this.register(new Setting("Fov", (T)120.0f, (T)300.0f, (T)0.0f));
    }
    
    @SubscribeEvent
    public void onEntityViewRender(final EntityViewRenderEvent.FOVModifier event) {
        event.setFOV((float)this.fov.getValue());
    }
}
