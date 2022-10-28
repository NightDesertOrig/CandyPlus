//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.event.events.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class BreadCrumbs extends Module
{
    public Setting<Color> color;
    public Setting<Float> fadeSpeed;
    public Setting<Float> thickness;
    public Setting<Float> offset;
    public Setting<Boolean> other;
    public List<Trace> traces;
    
    public BreadCrumbs() {
        super("BreadCrumbs", Module.Categories.RENDER, false, false);
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(130, 10, 220, 200)));
        this.fadeSpeed = (Setting<Float>)this.register(new Setting("Fadeout Speed", (T)10.0f, (T)20.0f, (T)1.0f));
        this.thickness = (Setting<Float>)this.register(new Setting("Thickness", (T)3.0f, (T)10.0f, (T)1.0f));
        this.offset = (Setting<Float>)this.register(new Setting("OffsetY", (T)5.0f, (T)10.0f, (T)0.0f));
        this.other = (Setting<Boolean>)this.register(new Setting("Other", (T)false));
        this.traces = new ArrayList<Trace>();
    }
    
    public void onRender3D(final float ticks) {
        try {
            this.doRender(ticks);
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onUpdateWalkingEvent(final UpdateWalkingPlayerEvent event) {
        this.traces.add(new Trace(BreadCrumbs.mc.player.posX, BreadCrumbs.mc.player.posY + (this.offset.getValue() - 5.0), BreadCrumbs.mc.player.posZ, this.color.getValue()));
    }
    
    public void doRender(final float ticks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        final float posx = (float)(BreadCrumbs.mc.player.lastTickPosX + (BreadCrumbs.mc.player.posX - BreadCrumbs.mc.player.lastTickPosX) * ticks);
        final float posy = (float)(BreadCrumbs.mc.player.lastTickPosY + (BreadCrumbs.mc.player.posY - BreadCrumbs.mc.player.lastTickPosY) * ticks);
        final float posz = (float)(BreadCrumbs.mc.player.lastTickPosZ + (BreadCrumbs.mc.player.posZ - BreadCrumbs.mc.player.lastTickPosZ) * ticks);
        GlStateManager.translate(posx * -1.0f, posy * -1.0f, posz * -1.0f);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder worldRenderer = tessellator.getBuffer();
        final float thickness = this.thickness.getValue();
        GL11.glEnable(2848);
        GL11.glLineWidth(thickness);
        worldRenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        for (final Trace trace2 : this.traces) {
            final int r = trace2.r;
            final int g = trace2.g;
            final int b = trace2.b;
            final int a = trace2.a;
            worldRenderer.pos(trace2.x, trace2.y, trace2.z).color(r, g, b, a).endVertex();
            trace2.includeAlpha(this.fadeSpeed.getValue());
        }
        tessellator.draw();
        GL11.glLineWidth(1.0f);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        this.traces.removeIf(trace -> trace.a <= 0);
    }
    
    public class Trace
    {
        public double x;
        public double y;
        public double z;
        public int r;
        public int g;
        public int b;
        public int a;
        
        public Trace(final double x, final double y, final double z, final Color color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = color.getRed();
            this.g = color.getGreen();
            this.b = color.getBlue();
            this.a = color.getAlpha();
        }
        
        public void includeAlpha(final Float speed) {
            this.a -= (int)(Object)speed;
            if (this.a < 0) {
                this.a = 0;
            }
        }
    }
}
