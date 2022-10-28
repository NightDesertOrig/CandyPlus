//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;
import net.minecraft.entity.*;

public class PlayerModel extends Hud
{
    public Setting<Float> scale;
    
    public PlayerModel() {
        super("PlayerModel", 100.0f, 150.0f);
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)50.0f, (T)100.0f, (T)30.0f));
    }
    
    public void onRender() {
        this.width = (PlayerModel.mc.player.width + 0.5f) * this.scale.getValue() + 10.0f;
        this.height = (PlayerModel.mc.player.height + 0.5f) * this.scale.getValue();
        RenderUtil.renderEntity((EntityLivingBase)PlayerModel.mc.player, this.x.getValue() + this.width - 30.0f, this.y.getValue() + this.height - 20.0f, this.scale.getValue());
    }
}
