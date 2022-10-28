//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.event.events.world;

import cc.candy.candymod.event.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class BlockEvent extends CandyEvent
{
    public BlockPos pos;
    public EnumFacing facing;
    public int stage;
    
    public BlockEvent(final int stage, final BlockPos pos, final EnumFacing facing) {
        this.stage = 0;
        this.stage = stage;
        this.pos = pos;
        this.facing = facing;
    }
}
