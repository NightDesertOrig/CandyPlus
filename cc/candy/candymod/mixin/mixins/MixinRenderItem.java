//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.mixin.mixins;

import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import java.awt.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.utils.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;

@Mixin({ RenderItem.class })
public abstract class MixinRenderItem implements Util
{
    private static final ResourceLocation RES_ITEM_GLINT;
    
    @Shadow
    public abstract void renderModel(final IBakedModel p0, final int p1);
    
    private void renderEffect(final IBakedModel model, final CallbackInfo info) {
        final int color = ColorUtil.toRGBA(EnchantmentColor.INSTANCE.color.getValue());
        final TextureManager textureManager = MixinRenderItem.mc.getTextureManager();
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        textureManager.bindTexture(MixinRenderItem.RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        final float f = Minecraft.getSystemTime() % 3000L / 3000.0f / 8.0f;
        GlStateManager.translate(f, 0.0f, 0.0f);
        GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f);
        this.renderModel(model, color);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        final float f2 = Minecraft.getSystemTime() % 4873L / 4873.0f / 8.0f;
        GlStateManager.translate(-f2, 0.0f, 0.0f);
        GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f);
        this.renderModel(model, color);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.popMatrix();
    }
    
    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
