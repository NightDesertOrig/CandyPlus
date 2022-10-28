//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import net.minecraft.init.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public class NoOverlay extends Module
{
    public NoOverlay() {
        super("NoOverlay", Module.Categories.RENDER, true, true);
    }
    
    public void onRender3D() {
        if (NoOverlay.mc.player == null) {
            return;
        }
        NoOverlay.mc.player.removeActivePotionEffect(MobEffects.BLINDNESS);
        NoOverlay.mc.player.removeActivePotionEffect(MobEffects.NAUSEA);
    }
    
    public void onPacketReceive(final PacketEvent.Receive event) {
        final Packet<?> packet = (Packet<?>)event.packet;
        if (packet instanceof SPacketSpawnExperienceOrb || packet instanceof SPacketExplosion) {
            event.cancel();
        }
    }
}
