//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import java.util.*;

public class BurrowESP extends Module
{
    public Setting<Boolean> obby;
    public Setting<Color> color;
    
    public BurrowESP() {
        super("BurrowESP", Module.Categories.RENDER, false, false);
        this.obby = (Setting<Boolean>)this.register(new Setting("Only Obby", (T)false));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 64, 207, 210)));
    }
    
    public void onRender3D() {
        final List<EntityPlayer> players = new ArrayList<EntityPlayer>(BurrowESP.mc.world.playerEntities);
        for (final EntityPlayer player : players) {
            final BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
            if (BlockUtil.getBlock(pos) != Blocks.AIR && (!this.obby.getValue() || BlockUtil.getBlock(pos) == Blocks.OBSIDIAN)) {
                RenderUtil3D.drawBox(pos, 1.0, this.color.getValue(), 63);
            }
        }
    }
}
