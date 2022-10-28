//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.event.events.player;

import cc.candy.candymod.event.*;
import net.minecraft.entity.player.*;

public class PlayerDeathEvent extends CandyEvent
{
    public EntityPlayer player;
    
    public PlayerDeathEvent(final EntityPlayer player) {
        this.player = player;
    }
}
