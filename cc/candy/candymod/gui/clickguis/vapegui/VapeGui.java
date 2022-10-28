//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.vapegui;

import cc.candy.candymod.gui.clickguis.*;
import cc.candy.candymod.gui.clickguis.vapegui.components.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.*;
import cc.candy.candymod.hud.*;
import org.lwjgl.input.*;
import java.util.*;

public class VapeGui extends CGui
{
    public static List<Panel> panelList;
    public Panel hudPanel;
    
    public void initGui() {
        if (Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null) {
            Minecraft.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        if (!VapeGui.panelList.isEmpty()) {
            return;
        }
        int x = 50;
        for (final Module.Categories category : Module.Categories.values()) {
            if (category == Module.Categories.HUB) {
                this.hudPanel = new Panel(category, 100.0f, 10.0f);
            }
            else {
                VapeGui.panelList.add(new Panel(category, (float)x, 10.0f));
                x += 120;
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.scroll();
        if (HUDEditor.instance.isEnable) {
            this.hudPanel.onRender(mouseX, mouseY);
        }
        else {
            VapeGui.panelList.forEach(p -> p.onRender(mouseX, mouseY));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (HUDEditor.instance.isEnable) {
            this.hudPanel.onMouseClicked(mouseX, mouseY, mouseButton);
            CandyMod.m_module.modules.stream().filter(m -> m instanceof Hud).forEach(m -> m.mouseClicked(mouseX, mouseY, mouseButton));
        }
        else {
            VapeGui.panelList.forEach(p -> p.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    public void scroll() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            if (HUDEditor.instance.isEnable) {
                final Panel hudPanel = this.hudPanel;
                hudPanel.y -= 15.0f;
            }
            else {
                VapeGui.panelList.forEach(p -> p.y -= 15.0f);
            }
        }
        else if (dWheel > 0) {
            if (HUDEditor.instance.isEnable) {
                final Panel hudPanel2 = this.hudPanel;
                hudPanel2.y += 15.0f;
            }
            else {
                VapeGui.panelList.forEach(p -> p.y += 15.0f);
            }
        }
    }
    
    static {
        VapeGui.panelList = new ArrayList<Panel>();
    }
}
