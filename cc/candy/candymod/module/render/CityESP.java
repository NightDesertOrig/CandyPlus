//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import java.util.*;

public class CityESP extends Module
{
    public Setting<Float> range;
    public Setting<Color> color;
    public Setting<Boolean> outline;
    public Setting<Float> width;
    
    public CityESP() {
        super("CityESP", Module.Categories.RENDER, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)12.0f, (T)1.0f));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(230, 50, 50, 100)));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)false));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)3.0f, (T)6.0f, (T)0.1f, v -> this.outline.getValue()));
    }
    
    public void onRender3D() {
        final List<EntityPlayer> players = (List<EntityPlayer>)CityESP.mc.world.playerEntities.stream().filter(e -> e.getEntityId() != CityESP.mc.player.getEntityId()).collect(Collectors.toList());
        for (final EntityPlayer player : players) {
            final BlockPos[] array;
            final BlockPos[] surroundOffset = array = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
            for (final BlockPos offset : array) {
                final BlockPos position = new BlockPos(player.posX, player.posY, player.posZ).add((Vec3i)offset);
                if (PlayerUtil.getDistance(position) <= this.range.getValue()) {
                    if (BlockUtil.getBlock(position) == Blocks.OBSIDIAN) {
                        RenderUtil3D.drawBox(position, 1.0, this.color.getValue(), 63);
                        if (this.outline.getValue()) {
                            RenderUtil3D.drawBoundingBox(position, 1.0, this.width.getValue(), this.color.getValue());
                        }
                    }
                }
            }
        }
    }
}
