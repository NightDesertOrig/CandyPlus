//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.clickgui;

import cc.candy.candymod.gui.clickguis.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.hud.*;
import org.lwjgl.input.*;
import java.util.*;

public class CandyGUI extends CGui
{
    private static List<Panel> panels;
    private static Panel hubPanel;
    public static boolean isHovering;
    
    public void initGui() {
        if (CandyGUI.panels.size() == 0) {
            int x = 30;
            for (final Module.Categories c : Module.Categories.values()) {
                if (c == Module.Categories.HUB) {
                    CandyGUI.hubPanel = new Panel(c, 30, 30, true);
                }
                else {
                    CandyGUI.panels.add(new Panel(c, x, 20, true));
                    CandyMod.Info("Loaded module panel : " + c.name());
                    x += 120;
                }
            }
        }
    }
    
    public void onGuiClosed() {
        if (HUDEditor.instance.isEnable) {
            HUDEditor.instance.disable();
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.scroll();
        if (HUDEditor.instance.isEnable) {
            CandyGUI.hubPanel.onRender(mouseX, mouseY, partialTicks);
        }
        else {
            CandyGUI.panels.forEach(p -> p.onRender(mouseX, mouseY, partialTicks));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (HUDEditor.instance.isEnable) {
            CandyGUI.hubPanel.mouseClicked(mouseX, mouseY, mouseButton);
            CandyMod.m_module.modules.stream().filter(m -> m instanceof Hud).forEach(m -> m.mouseClicked(mouseX, mouseY, mouseButton));
        }
        else {
            CandyGUI.panels.forEach(p -> p.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (HUDEditor.instance.isEnable) {
            CandyGUI.hubPanel.mouseReleased(mouseX, mouseY, state);
            CandyMod.m_module.modules.stream().filter(m -> m instanceof Hud).forEach(m -> m.mouseReleased(mouseX, mouseY, state));
        }
        else {
            CandyGUI.panels.forEach(p -> p.mouseReleased(mouseX, mouseY, state));
        }
    }
    
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        if (HUDEditor.instance.isEnable) {
            CandyGUI.hubPanel.mouseClickMove(mouseX, mouseY, clickedMouseButton);
            CandyMod.m_module.modules.stream().filter(m -> m instanceof Hud).forEach(m -> m.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
        }
        else {
            CandyGUI.panels.forEach(p -> p.mouseClickMove(mouseX, mouseY, clickedMouseButton));
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (HUDEditor.instance.isEnable) {
            CandyGUI.hubPanel.keyTyped(typedChar, keyCode);
        }
        else {
            CandyGUI.panels.forEach(p -> p.keyTyped(typedChar, keyCode));
        }
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (Exception ex) {}
    }
    
    public void scroll() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            if (HUDEditor.instance.isEnable) {
                final Panel hubPanel = CandyGUI.hubPanel;
                hubPanel.y -= 15;
            }
            else {
                CandyGUI.panels.forEach(p -> p.y -= 15);
            }
        }
        else if (dWheel > 0) {
            if (HUDEditor.instance.isEnable) {
                final Panel hubPanel2 = CandyGUI.hubPanel;
                hubPanel2.y += 15;
            }
            else {
                CandyGUI.panels.forEach(p -> p.y += 15);
            }
        }
    }
    
    static {
        CandyGUI.panels = new ArrayList<Panel>();
        CandyGUI.hubPanel = null;
        CandyGUI.isHovering = false;
    }
}
