//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cc.candy.candymod.hud.modules.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderPlayer.class })
public class MixinRenderPlayer
{
    @Inject(method = { "renderEntityName" }, at = { @At("HEAD") }, cancellable = true)
    private void renderLivingLabel(final AbstractClientPlayer entity, final double x, final double y, final double z, final String name, final double distanceSq, final CallbackInfo callbackInfo) {
        if (entity.equals((Object)TargetHUD.target)) {
            callbackInfo.cancel();
        }
    }
}
