//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.*;
import net.minecraft.util.math.*;
import cc.candy.candymod.utils.*;
import java.util.*;

public class HoleESP extends Module
{
    public Setting<Float> range;
    public Setting<Color> obby;
    public Setting<Color> bedrock;
    public Setting<type> renderType;
    public Setting<Boolean> outline;
    public Setting<Float> width;
    
    public HoleESP() {
        super("HoleESP", Module.Categories.RENDER, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)12.0f, (T)1.0f));
        this.obby = (Setting<Color>)this.register(new Setting("ObbyColor", (T)new Color(230, 50, 50, 100)));
        this.bedrock = (Setting<Color>)this.register(new Setting("BedrockColor", (T)new Color(230, 150, 50, 100)));
        this.renderType = (Setting<type>)this.register(new Setting("RenderType", (T)type.Down));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)false));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)3.0f, (T)6.0f, (T)0.1f, v -> this.outline.getValue()));
    }
    
    public void onRender3D() {
        try {
            final List<BlockPos> holes = (List<BlockPos>)CandyMod.m_hole.getHoles();
            for (final BlockPos hole : holes) {
                if (PlayerUtil.getDistance(hole) > this.range.getValue()) {
                    continue;
                }
                Color color = this.obby.getValue();
                if (HoleUtil.isBedrockHole(hole)) {
                    color = this.bedrock.getValue();
                }
                if (this.renderType.getValue() == type.Full) {
                    RenderUtil3D.drawBox(hole, 1.0, color, 63);
                }
                else {
                    RenderUtil3D.drawBox(hole, 1.0, color, 1);
                }
                if (!this.outline.getValue()) {
                    continue;
                }
                RenderUtil3D.drawBoundingBox(hole, 1.0, this.width.getValue(), color);
            }
        }
        catch (Exception ex) {}
    }
    
    public enum type
    {
        Full, 
        Down;
    }
}
