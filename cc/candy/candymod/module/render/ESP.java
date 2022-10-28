//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import cc.candy.candymod.utils.*;
import net.minecraft.util.math.*;

public class ESP extends Module
{
    public Setting<Float> width;
    public Setting<Color> color;
    
    public ESP() {
        super("ESP", Module.Categories.RENDER, false, false);
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)1.5f, (T)5.0f, (T)0.5f));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 255)));
    }
    
    public void onRender3D() {
        if (this.nullCheck()) {
            return;
        }
        for (final EntityPlayer player : (List)ESP.mc.world.playerEntities.stream().filter(e -> e.getEntityId() != ESP.mc.player.getEntityId()).collect(Collectors.toList())) {
            this.drawESP(player);
        }
    }
    
    public void drawESP(final EntityPlayer player) {
        GlStateManager.pushMatrix();
        final AxisAlignedBB bb = player.getCollisionBoundingBox();
        final double z = bb.minZ + (bb.maxZ - bb.minZ) / 2.0;
        RenderUtil3D.drawLine(bb.minX, bb.maxY, z, bb.maxX, bb.maxY, z, this.color.getValue(), this.width.getValue());
        RenderUtil3D.drawLine(bb.minX, bb.minY, z, bb.maxX, bb.minY, z, this.color.getValue(), this.width.getValue());
        RenderUtil3D.drawLine(bb.minX, bb.minY, z, bb.minX, bb.maxY, z, this.color.getValue(), this.width.getValue());
        RenderUtil3D.drawLine(bb.maxX, bb.minY, z, bb.maxX, bb.maxY, z, this.color.getValue(), this.width.getValue());
        final double x = bb.minX - 0.28;
        final double y = bb.minY;
        final double width = 0.04;
        final double height = bb.maxY - bb.minY;
        RenderUtil3D.drawRect(x, y, z, width, height, new Color(0, 0, 0, 255), 255, 63);
        RenderUtil3D.drawRect(x, y, z, width, height * (player.getHealth() / 36.0), getHealthColor((int)player.getHealth()), 255, 63);
        GlStateManager.popMatrix();
    }
    
    private static Color getHealthColor(int health) {
        if (health > 36) {
            health = 36;
        }
        if (health < 0) {
            health = 0;
        }
        int red = 0;
        int green = 0;
        if (health > 18) {
            red = (int)((36 - health) * 14.1666666667);
            green = 255;
        }
        else {
            red = 255;
            green = (int)(255.0 - (18 - health) * 14.1666666667);
        }
        return new Color(red, green, 0, 255);
    }
}
