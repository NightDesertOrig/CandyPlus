//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.entity.item.*;
import java.util.concurrent.*;
import net.minecraft.entity.*;
import java.util.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import cc.candy.candymod.event.events.render.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class CandyCrystal extends Module
{
    public Setting<Boolean> chams;
    public Setting<Boolean> throughWalls;
    public Setting<Boolean> wireframeThroughWalls;
    public Setting<Boolean> glint;
    public Setting<Boolean> wireframe;
    public Setting<Float> scale;
    public Setting<Float> lineWidth;
    public Setting<Boolean> colorSync;
    public Setting<Boolean> rainbow;
    public Setting<Integer> saturation;
    public Setting<Integer> brightness;
    public Setting<Integer> speed;
    public Setting<Boolean> xqz;
    public Setting<Color> color;
    public Setting<Color> hiddenColor;
    public Setting<Integer> alpha;
    public Map<EntityEnderCrystal, Float> scaleMap;
    public float hue;
    public Map<Integer, Integer> colorHeightMap;
    
    public CandyCrystal() {
        super("CandyCrystal", Module.Categories.RENDER, false, false);
        this.chams = (Setting<Boolean>)this.register(new Setting("Chams", (T)false));
        this.throughWalls = (Setting<Boolean>)this.register(new Setting("ThroughWalls", (T)true));
        this.wireframeThroughWalls = (Setting<Boolean>)this.register(new Setting("WireThroughWalls", (T)true));
        this.glint = (Setting<Boolean>)this.register(new Setting("Glint", (T)false, v -> this.chams.getValue()));
        this.wireframe = (Setting<Boolean>)this.register(new Setting("Wireframe", (T)false));
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)1.0f, (T)10.0f, (T)0.1f));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)3.0f, (T)0.1f));
        this.colorSync = (Setting<Boolean>)this.register(new Setting("Sync", (T)false));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", (T)false));
        this.saturation = (Setting<Integer>)this.register(new Setting("Saturation", (T)50, (T)100, (T)0, v -> this.rainbow.getValue()));
        this.brightness = (Setting<Integer>)this.register(new Setting("Brightness", (T)100, (T)100, (T)0, v -> this.rainbow.getValue()));
        this.speed = (Setting<Integer>)this.register(new Setting("Speed", (T)40, (T)100, (T)1, v -> this.rainbow.getValue()));
        this.xqz = (Setting<Boolean>)this.register(new Setting("XQZ", (T)false, v -> !this.rainbow.getValue() && this.throughWalls.getValue()));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 100), v -> !this.rainbow.getValue()));
        this.hiddenColor = (Setting<Color>)this.register(new Setting("Hidden Color", (T)new Color(255, 255, 255, 100), v -> this.xqz.getValue()));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)50, (T)255, (T)0, v -> !this.rainbow.getValue()));
        this.scaleMap = new ConcurrentHashMap<EntityEnderCrystal, Float>();
        this.colorHeightMap = new HashMap<Integer, Integer>();
    }
    
    public void onUpdate() {
        try {
            for (final Entity crystal : CandyCrystal.mc.world.loadedEntityList) {
                if (!(crystal instanceof EntityEnderCrystal)) {
                    continue;
                }
                if (!this.scaleMap.containsKey(crystal)) {
                    this.scaleMap.put((EntityEnderCrystal)crystal, 3.125E-4f);
                }
                else {
                    this.scaleMap.put((EntityEnderCrystal)crystal, this.scaleMap.get(crystal) + 3.125E-4f);
                }
                if (this.scaleMap.get(crystal) < 0.0625f * this.scale.getValue()) {
                    continue;
                }
                this.scaleMap.remove(crystal);
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketDestroyEntities) {
            final SPacketDestroyEntities packet = (SPacketDestroyEntities)event.getPacket();
            for (final int id : packet.getEntityIDs()) {
                final Entity entity = CandyCrystal.mc.world.getEntityByID(id);
                if (entity instanceof EntityEnderCrystal) {
                    this.scaleMap.remove(entity);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderModel(final RenderEntityModelEvent event) {
        if (!(event.entity instanceof EntityEnderCrystal) || !this.wireframe.getValue()) {
            return;
        }
        final Color color = this.colorSync.getValue() ? this.getCurrentColor() : this.color.getValue();
        final boolean fancyGraphics = CandyCrystal.mc.gameSettings.fancyGraphics;
        CandyCrystal.mc.gameSettings.fancyGraphics = false;
        final float gamma = CandyCrystal.mc.gameSettings.gammaSetting;
        CandyCrystal.mc.gameSettings.gammaSetting = 10000.0f;
        GL11.glPushMatrix();
        GL11.glPushAttrib(1048575);
        GL11.glPolygonMode(1032, 6913);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        if (this.wireframeThroughWalls.getValue()) {
            GL11.glDisable(2929);
        }
        GL11.glEnable(2848);
        GL11.glEnable(3042);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GlStateManager.glLineWidth((float)this.lineWidth.getValue());
        event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
    
    public void onTick() {
        final int colorSpeed = 101 - this.speed.getValue();
        final float hue = System.currentTimeMillis() % (360 * colorSpeed) / (360.0f * colorSpeed);
        this.hue = hue;
        float tempHue = hue;
        for (int i = 0; i <= 510; ++i) {
            this.colorHeightMap.put(i, Color.HSBtoRGB(tempHue, this.saturation.getValue() / 255.0f, this.brightness.getValue() / 255.0f));
            tempHue += 0.0013071896f;
        }
    }
    
    public Color getCurrentColor() {
        return Color.getHSBColor(this.hue, this.saturation.getValue() / 255.0f, this.brightness.getValue() / 255.0f);
    }
}
