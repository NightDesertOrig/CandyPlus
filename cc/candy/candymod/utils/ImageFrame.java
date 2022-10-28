//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import java.awt.image.*;

public class ImageFrame
{
    private final int delay;
    private final BufferedImage image;
    private final String disposal;
    private final int width;
    private final int height;
    
    public ImageFrame(final BufferedImage image, final int delay, final String disposal, final int width, final int height) {
        this.image = image;
        this.delay = delay;
        this.disposal = disposal;
        this.width = width;
        this.height = height;
    }
    
    public ImageFrame(final BufferedImage image) {
        this.image = image;
        this.delay = -1;
        this.disposal = null;
        this.width = -1;
        this.height = -1;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public String getDisposal() {
        return this.disposal;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
}
