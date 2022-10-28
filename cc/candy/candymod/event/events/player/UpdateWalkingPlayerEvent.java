//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.event.events.player;

import cc.candy.candymod.event.*;

public class UpdateWalkingPlayerEvent extends CandyEvent
{
    public int stage;
    
    public UpdateWalkingPlayerEvent(final int stage) {
        this.stage = stage;
    }
}
