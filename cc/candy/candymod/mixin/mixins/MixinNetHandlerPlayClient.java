//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import cc.candy.candymod.event.events.player.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient
{
    @Inject(method = { "handleEntityMetadata" }, at = { @At("RETURN") }, cancellable = true)
    private void handleEntityMetadataHook(final SPacketEntityMetadata packetIn, final CallbackInfo info) {
        final Minecraft mc = Minecraft.getMinecraft();
        final Entity entity;
        final EntityPlayer player;
        if (mc.world != null && (entity = mc.world.getEntityByID(packetIn.getEntityId())) instanceof EntityPlayer && (player = (EntityPlayer)entity).getHealth() <= 0.0f) {
            final PlayerDeathEvent event = new PlayerDeathEvent(player);
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
}
