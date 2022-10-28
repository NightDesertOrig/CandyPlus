//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public class BowSpam extends Module
{
    public BowSpam() {
        super("BowSpam", Categories.COMBAT, false, false);
    }
    
    @Override
    public void onTick() {
        try {
            if (this.nullCheck()) {
                return;
            }
            if (BowSpam.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow) {
                BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, BowSpam.mc.player.getHorizontalFacing()));
                BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(BowSpam.mc.player.getActiveHand()));
                BowSpam.mc.player.stopActiveHand();
            }
        }
        catch (Exception ex) {}
    }
}
