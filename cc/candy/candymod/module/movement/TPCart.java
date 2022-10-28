//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.movement;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.item.*;
import cc.candy.candymod.utils.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class TPCart extends Module
{
    public Setting<Float> range;
    public Setting<Float> delay;
    public Timer timer;
    
    public TPCart() {
        super("TPCart", Module.Categories.MOVEMENT, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)10.0f, (T)20.0f, (T)2.0f));
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)10.0f, (T)30.0f, (T)1.0f));
        this.timer = new Timer();
    }
    
    public void onTick() {
        try {
            if (this.nullCheck()) {
                return;
            }
            if (this.timer == null) {
                this.timer = new Timer();
            }
            if (this.timer.passedX(this.delay.getValue())) {
                final List<Entity> carts = (List<Entity>)TPCart.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityMinecart).filter(e -> !e.equals((Object)TPCart.mc.player.getRidingEntity())).filter(e -> PlayerUtil.getDistance(e) <= this.range.getValue()).collect(Collectors.toList());
                final Entity minecart = carts.get(new Random().nextInt(carts.size()));
                if (minecart == null) {
                    return;
                }
                TPCart.mc.playerController.interactWithEntity((EntityPlayer)TPCart.mc.player, minecart, EnumHand.MAIN_HAND);
            }
        }
        catch (Exception ex) {}
    }
}
