//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.client.renderer.entity.*;
import cc.candy.candymod.event.events.player.*;
import cc.candy.candymod.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import java.util.*;

public class Afterglow extends Module
{
    public Setting<Float> delay;
    public Setting<Color> color;
    public Setting<Float> fadeSpeed;
    public Setting<Float> thickness;
    public static RenderPlayer render;
    public List<AfterGlow> glows;
    public Timer timer;
    
    public Afterglow() {
        super("Afterglow", Module.Categories.RENDER, false, false);
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)10.0f, (T)20.0f, (T)1.0f));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 200)));
        this.fadeSpeed = (Setting<Float>)this.register(new Setting("Fadeout Speed", (T)10.0f, (T)20.0f, (T)1.0f));
        this.thickness = (Setting<Float>)this.register(new Setting("Thickness", (T)3.0f, (T)10.0f, (T)1.0f));
        this.glows = new ArrayList<AfterGlow>();
        this.timer = new Timer();
    }
    
    @SubscribeEvent
    public void onUpdateWalkingEvent(final UpdateWalkingPlayerEvent event) {
        if (this.timer == null) {
            this.timer = new Timer();
        }
        if (this.timer.passedDms(this.delay.getValue())) {
            if (Afterglow.mc.player.motionX == 0.0 && Afterglow.mc.player.motionZ == 0.0) {
                return;
            }
            final double[] forward = PlayerUtil.forward(-0.5);
            this.glows.add(new AfterGlow(forward[0] + Afterglow.mc.player.posX, Afterglow.mc.player.posY, forward[1] + Afterglow.mc.player.posZ, Afterglow.mc.player.rotationYaw, new Color(this.color.getValue().getRed(), this.color.getValue().getGreen(), this.color.getValue().getBlue(), 150)));
        }
    }
    
    public void onRender3D(final float ticks) {
        if (Afterglow.render == null) {
            Afterglow.render = new RenderPlayer(Afterglow.mc.getRenderManager());
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        final float posx = (float)(Afterglow.mc.player.lastTickPosX + (Afterglow.mc.player.posX - Afterglow.mc.player.lastTickPosX) * ticks);
        final float posy = (float)(Afterglow.mc.player.lastTickPosY + (Afterglow.mc.player.posY - Afterglow.mc.player.lastTickPosY) * ticks);
        final float posz = (float)(Afterglow.mc.player.lastTickPosZ + (Afterglow.mc.player.posZ - Afterglow.mc.player.lastTickPosZ) * ticks);
        GlStateManager.translate(posx * -1.0f, posy * -1.0f, posz * -1.0f);
        for (final AfterGlow glow : this.glows) {
            GL11.glPushMatrix();
            GL11.glDepthRange(0.0, 0.01);
            GL11.glDisable(2896);
            GL11.glDisable(3553);
            GL11.glPolygonMode(1032, 6913);
            GL11.glEnable(3008);
            GL11.glEnable(3042);
            GL11.glLineWidth((float)this.thickness.getValue());
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glColor4f(glow.r / 255.0f, glow.g / 255.0f, glow.b / 255.0f, glow.a / 255.0f);
            Afterglow.mc.getRenderManager().renderEntityStatic((Entity)Afterglow.mc.player, 0.0f, false);
            GL11.glHint(3154, 4352);
            GL11.glPolygonMode(1032, 6914);
            GL11.glEnable(2896);
            GL11.glDepthRange(0.0, 1.0);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
            glow.includeAlpha(this.fadeSpeed.getValue());
        }
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
        this.glows.removeIf(g -> g.a >= 255);
    }
    
    static {
        Afterglow.render = null;
    }
    
    public class AfterGlow
    {
        public double x;
        public double y;
        public double z;
        public float yaw;
        public int r;
        public int g;
        public int b;
        public int a;
        
        public AfterGlow(final double x, final double y, final double z, final float yaw, final Color color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.r = color.getRed();
            this.g = color.getGreen();
            this.b = color.getBlue();
            this.a = color.getAlpha();
        }
        
        public void includeAlpha(final Float speed) {
            this.a += (int)(Object)speed;
            if (this.a > 255) {
                this.a = 255;
            }
        }
    }
}
