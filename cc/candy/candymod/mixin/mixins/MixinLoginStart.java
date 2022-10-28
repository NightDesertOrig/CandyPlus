//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.login.client.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ CPacketLoginStart.class })
public class MixinLoginStart
{
    @Inject(method = { "writePacketData" }, cancellable = true, at = { @At("HEAD") })
    public void writePacketData(final PacketBuffer buf, final CallbackInfo ci) {
    }
}
