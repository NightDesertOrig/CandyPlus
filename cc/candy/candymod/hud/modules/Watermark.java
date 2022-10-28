//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import net.minecraft.client.renderer.*;
import cc.candy.candymod.utils.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import javax.imageio.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import java.awt.image.*;

public class Watermark extends Hud
{
    public Setting<Float> scale;
    public Setting<Boolean> rainbow;
    public Setting<Integer> saturation;
    public Setting<Integer> brightness;
    public Setting<Integer> speed;
    public CandyDynamicTexture watermark;
    
    public Watermark() {
        super("Watermark", 10.0f, 10.0f);
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)0.6f, (T)1.0f, (T)0.1f));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Rainbow", (T)false));
        this.saturation = (Setting<Integer>)this.register(new Setting("Saturation", (T)50, (T)100, (T)0, v -> this.rainbow.getValue()));
        this.brightness = (Setting<Integer>)this.register(new Setting("Brightness", (T)100, (T)100, (T)0, v -> this.rainbow.getValue()));
        this.speed = (Setting<Integer>)this.register(new Setting("Speed", (T)40, (T)100, (T)1, v -> this.rainbow.getValue()));
        this.watermark = null;
        this.loadLogo();
    }
    
    public void onRender() {
        if (this.watermark == null) {
            this.loadLogo();
            return;
        }
        final float width = this.watermark.GetWidth() * this.scale.getValue();
        final float height = this.watermark.GetHeight() * this.scale.getValue();
        GlStateManager.enableTexture2D();
        GlStateManager.disableAlpha();
        RenderHelper.enableGUIStandardItemLighting();
        Watermark.mc.renderEngine.bindTexture(this.watermark.GetResourceLocation());
        GlStateManager.glTexParameteri(3553, 10240, 9729);
        GlStateManager.glTexParameteri(3553, 10241, 9729);
        if (this.rainbow.getValue()) {
            final Color color = new Color(RenderUtil.getRainbow(this.speed.getValue() * 100, 0, this.saturation.getValue() / 100.0f, this.brightness.getValue() / 100.0f));
            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
        }
        else {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GlStateManager.pushMatrix();
        RenderUtil.drawTexture(this.x.getValue(), this.y.getValue(), width, height);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
        this.width = width;
        this.height = height;
    }
    
    public void loadLogo() {
        final InputStream stream = Watermark.class.getResourceAsStream("/assets/candy/watermark.png");
        BufferedImage image;
        try {
            image = ImageIO.read(stream);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        final int height = image.getHeight();
        final int width = image.getWidth();
        (this.watermark = new CandyDynamicTexture(image, height, width)).SetResourceLocation(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("candy/textures", (DynamicTexture)this.watermark));
    }
}
