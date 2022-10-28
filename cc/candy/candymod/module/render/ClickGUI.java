//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.gui.clickguis.*;
import cc.candy.candymod.gui.clickguis.clickguinew.*;
import net.minecraft.client.gui.*;
import cc.candy.candymod.gui.clickguis.clickgui.*;

public class ClickGUI extends Module
{
    private Setting<type> guiType;
    public Setting<Color> color;
    public Setting<Boolean> outline;
    
    public ClickGUI() {
        super("ClickGUI", Module.Categories.RENDER, 21, false, false);
        this.guiType = (Setting<type>)this.register(new Setting("Type", (T)type.New));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(210, 0, 130, 255), v -> this.guiType.getValue() != type.Old));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)false, v -> this.guiType.getValue() == type.New));
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        if (!(ClickGUI.mc.currentScreen instanceof CGui)) {
            if (this.guiType.getValue() == type.New) {
                ClickGUI.mc.displayGuiScreen((GuiScreen)new CandyGUI2());
            }
            else {
                ClickGUI.mc.displayGuiScreen((GuiScreen)new CandyGUI());
            }
        }
    }
    
    public void onUpdate() {
        if (!(ClickGUI.mc.currentScreen instanceof CGui) && !HUDEditor.instance.isEnable) {
            this.disable();
        }
    }
    
    public enum type
    {
        Old, 
        New;
    }
}
