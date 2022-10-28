//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import cc.candy.candymod.gui.font.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import java.io.*;
import java.util.*;
import java.awt.*;

public class FontManager extends Manager
{
    public CFontRenderer iconFont;
    public Font fontRenderer_;
    public Font iconFont_;
    public CFontRenderer fontRenderer;
    
    private static Font getFont(final Map<String, Font> locationMap, final String location, final int size) {
        Font font;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(0, (float)size);
            }
            else {
                final InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("fonts/" + location)).getInputStream();
                font = Font.createFont(0, is);
                locationMap.put(location, font);
                font = font.deriveFont(0, (float)size);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, 10);
        }
        return font;
    }
    
    @Override
    public void load() {
        final Map<String, Font> locationmap = new HashMap<String, Font>();
        this.fontRenderer_ = getFont(locationmap, "G.ttf", 20);
        this.iconFont_ = getFont(locationmap, "Comfortaa-Bold.ttf", 35);
        this.fontRenderer = new CFontRenderer(this.fontRenderer_, true, true);
        this.iconFont = new CFontRenderer(this.iconFont_, true, true);
    }
    
    public int getWidth(final String str) {
        return this.fontRenderer.getStringWidth(str);
    }
    
    public int getHeight() {
        return this.fontRenderer.getHeight() + 2;
    }
    
    public void draw(final String str, final int x, final int y, final int color, final float scale) {
        this.fontRenderer.drawString(str, (float)x, (float)y, color, scale);
    }
    
    public void draw(final String str, final int x, final int y, final Color color, final float scale) {
        this.fontRenderer.drawString(str, (float)x, (float)y, color.getRGB(), scale);
    }
    
    public int getIconWidth() {
        return this.iconFont.getStringWidth("q");
    }
    
    public int getIconHeight() {
        return this.iconFont.getHeight();
    }
    
    public void drawIcon(final int x, final int y, final int color, final float scale) {
        this.iconFont.drawString("q", (float)x, (float)y, color, scale);
    }
    
    public void drawIcon(final int x, final int y, final Color color, final float scale) {
        this.iconFont.drawString("+", (float)x, (float)y, color.getRGB(), scale);
    }
}
