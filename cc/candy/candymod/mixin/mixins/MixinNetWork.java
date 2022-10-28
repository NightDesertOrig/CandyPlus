//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import io.netty.channel.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { NetworkManager.class }, priority = 312312)
public class MixinNetWork
{
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void packetReceived(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            final PacketEvent.Receive event = new PacketEvent.Receive((Packet)packet);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCancelled() && callbackInfo.isCancellable()) {
                callbackInfo.cancel();
            }
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void sendPacket(final Packet<?> packetIn, final CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            final PacketEvent.Send event = new PacketEvent.Send((Packet)packetIn);
            MinecraftForge.EVENT_BUS.post((Event)event);
            if (event.isCancelled() && callbackInfo.isCancellable()) {
                callbackInfo.cancel();
            }
        }
    }
}
