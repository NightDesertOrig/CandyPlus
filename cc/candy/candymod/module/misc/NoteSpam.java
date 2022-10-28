//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import cc.candy.candymod.utils.*;

public class NoteSpam extends Module
{
    private Setting<Integer> range;
    private Setting<Integer> max;
    private Setting<Color> color;
    private List<BlockPos> notes;
    
    public NoteSpam() {
        super("NoteSpam", Categories.MISC, false, false);
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)7, (T)10, (T)1));
        this.max = (Setting<Integer>)this.register(new Setting("MaxBlock", (T)30, (T)150, (T)1));
        this.color = (Setting<Color>)this.register(new Setting("RenderColor", (T)new Color(255, 10, 10, 55)));
        this.notes = new ArrayList<BlockPos>();
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        int counter = 0;
        this.notes = new ArrayList<BlockPos>();
        final List<BlockPos> posList = BlockUtil.getSphere(PlayerUtil.getPlayerPos((EntityPlayer)NoteSpam.mc.player), this.range.getValue(), this.range.getValue(), false, true, 0);
        for (final BlockPos b : posList) {
            if (BlockUtil.getBlock(b) == Blocks.NOTEBLOCK) {
                NoteSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, b, EnumFacing.UP));
                if (++counter > this.max.getValue()) {
                    return;
                }
                continue;
            }
        }
    }
    
    @Override
    public void onRender3D() {
        for (final BlockPos note : this.notes) {
            RenderUtil3D.drawBox(note, 1.0, this.color.getValue(), 63);
        }
    }
}
