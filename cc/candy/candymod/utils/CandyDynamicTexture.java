//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import net.minecraft.client.renderer.texture.*;
import java.awt.image.*;
import net.minecraft.util.*;

public class CandyDynamicTexture extends DynamicTexture
{
    private int Height;
    private int Width;
    private BufferedImage m_BufferedImage;
    private ResourceLocation m_TexturedLocation;
    private ImageFrame m_Frame;
    
    public CandyDynamicTexture(final BufferedImage bufferedImage, final int p_Height, final int p_Width) {
        super(bufferedImage);
        this.m_Frame = null;
        this.m_BufferedImage = bufferedImage;
        this.Height = p_Height;
        this.Width = p_Width;
    }
    
    public int GetHeight() {
        return this.Height;
    }
    
    public int GetWidth() {
        return this.Width;
    }
    
    public final DynamicTexture GetDynamicTexture() {
        return this;
    }
    
    public final BufferedImage GetBufferedImage() {
        return this.m_BufferedImage;
    }
    
    public void SetResourceLocation(final ResourceLocation dynamicTextureLocation) {
        this.m_TexturedLocation = dynamicTextureLocation;
    }
    
    public final ResourceLocation GetResourceLocation() {
        return this.m_TexturedLocation;
    }
    
    public void SetImageFrame(final ImageFrame p_Frame) {
        this.m_Frame = p_Frame;
    }
    
    public final ImageFrame GetFrame() {
        return this.m_Frame;
    }
}
