//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickguinew;

import cc.candy.candymod.gui.clickguis.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.*;
import cc.candy.candymod.hud.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.util.*;

public class CandyGUI2 extends CGui
{
    public static List<Panel> panelList;
    public static Panel hubPanel;
    
    public void initGui() {
        if (Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null) {
            Minecraft.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        if (CandyGUI2.panelList.size() == 0) {
            int x = 50;
            for (final Module.Categories category : Module.Categories.values()) {
                if (category == Module.Categories.HUB) {
                    CandyGUI2.hubPanel = new Panel(200.0f, 20.0f, category);
                }
                else {
                    CandyGUI2.panelList.add(new Panel((float)x, 20.0f, category));
                    x += 120;
                }
            }
        }
    }
    
    public void onGuiClosed() {
        if (Minecraft.getMinecraft().entityRenderer.getShaderGroup() != null) {
            Minecraft.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.scroll();
        if (HUDEditor.instance.isEnable) {
            CandyGUI2.hubPanel.onRender(mouseX, mouseY);
        }
        else {
            CandyGUI2.panelList.forEach(p -> p.onRender(mouseX, mouseY));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (HUDEditor.instance.isEnable) {
            CandyGUI2.hubPanel.onMouseClicked(mouseX, mouseY, mouseButton);
            CandyMod.m_module.modules.stream().filter(m -> m instanceof Hud).forEach(m -> m.mouseClicked(mouseX, mouseY, mouseButton));
        }
        else {
            CandyGUI2.panelList.forEach(p -> p.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (HUDEditor.instance.isEnable) {
            CandyGUI2.hubPanel.onMouseReleased(mouseX, mouseY, state);
            CandyMod.m_module.modules.stream().filter(m -> m instanceof Hud).forEach(m -> m.mouseReleased(mouseX, mouseY, state));
        }
        else {
            CandyGUI2.panelList.forEach(p -> p.onMouseReleased(mouseX, mouseY, state));
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        if (HUDEditor.instance.isEnable) {
            CandyGUI2.hubPanel.onMouseClickMove(mouseX, mouseY, clickedMouseButton);
            CandyMod.m_module.modules.stream().filter(m -> m instanceof Hud).forEach(m -> m.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
        }
        else {
            CandyGUI2.panelList.forEach(p -> p.onMouseClickMove(mouseX, mouseY, clickedMouseButton));
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (1 != keyCode) {
            if (HUDEditor.instance.isEnable) {
                CandyGUI2.hubPanel.keyTyped(typedChar, keyCode);
            }
            else {
                CandyGUI2.panelList.forEach(p -> p.keyTyped(typedChar, keyCode));
            }
            return;
        }
        if (HUDEditor.instance.isEnable) {
            HUDEditor.instance.disable();
            return;
        }
        this.mc.displayGuiScreen((GuiScreen)null);
    }
    
    public void scroll() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            if (HUDEditor.instance.isEnable) {
                final Panel hubPanel = CandyGUI2.hubPanel;
                hubPanel.y -= 15.0f;
            }
            else {
                CandyGUI2.panelList.forEach(p -> p.y -= 15.0f);
            }
        }
        else if (dWheel > 0) {
            if (HUDEditor.instance.isEnable) {
                final Panel hubPanel2 = CandyGUI2.hubPanel;
                hubPanel2.y += 15.0f;
            }
            else {
                CandyGUI2.panelList.forEach(p -> p.y += 15.0f);
            }
        }
    }
    
    static {
        CandyGUI2.panelList = new ArrayList<Panel>();
    }
}
