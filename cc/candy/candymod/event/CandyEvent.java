//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.event;

import net.minecraftforge.fml.common.eventhandler.*;

public class CandyEvent extends Event
{
    private boolean cancelled;
    
    public CandyEvent() {
        this.cancelled = false;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public void cancel() {
        this.cancelled = true;
    }
}
