//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.utils.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class TargetHUD extends Hud
{
    public static EntityPlayer target;
    public double health;
    public Setting<Boolean> shadow;
    public Setting<Color> color;
    
    public TargetHUD() {
        super("TargetHud", 100.0f, 50.0f);
        this.health = 36.0;
        this.shadow = (Setting<Boolean>)this.register(new Setting("Shadow", (T)true));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 255)));
    }
    
    public void onRender() {
        try {
            if (this.nullCheck()) {
                return;
            }
            final float width = 200.0f;
            final float height = 70.0f;
            this.width = width;
            this.height = height;
            TargetHUD.target = PlayerUtil.getNearestPlayer(30.0);
            if (TargetHUD.target == null) {
                return;
            }
            RenderUtil.drawRect(this.x.getValue(), this.y.getValue(), width, height, ColorUtil.toRGBA(40, 40, 40));
            RenderUtil.renderEntity((EntityLivingBase)TargetHUD.target, this.x.getValue() + 30.0f, this.y.getValue() + 63.0f, 30.0f);
            final float health = TargetHUD.target.getHealth() + TargetHUD.target.getAbsorptionAmount();
            this.health += (health - this.health) * 0.4;
            final double lineWidth = width * (this.health / 36.0);
            final float thickness = 1.0f;
            RenderUtil.drawGradientRect(this.x.getValue(), this.y.getValue() + height - thickness, (float)(this.x.getValue() + lineWidth), this.y.getValue() + height, ColorUtil.toRGBA(255, 0, 0), ColorUtil.toRGBA(getHealthColor((int)health)));
            final int white = ColorUtil.toRGBA(255, 255, 255);
            RenderUtil.drawString(TargetHUD.target.getName(), this.x.getValue() + 60.0f, this.y.getValue() + 10.0f, white, this.shadow.getValue(), 1.0f);
            final ItemStack slot4 = this.getArmorInv(3);
            final ItemStack slot5 = this.getArmorInv(2);
            final ItemStack slot6 = this.getArmorInv(1);
            final ItemStack slot7 = this.getArmorInv(0);
            final ItemStack mainHand = TargetHUD.target.getHeldItemMainhand();
            final ItemStack offHand = TargetHUD.target.getHeldItemOffhand();
            this.renderItem(slot4, 60, 20);
            this.renderItem(slot5, 80, 20);
            this.renderItem(slot6, 100, 20);
            this.renderItem(slot7, 120, 20);
            this.renderItem(mainHand, 140, 20);
            this.renderItem(offHand, 160, 20);
            RenderUtil.drawString("Health : " + (int)health, this.x.getValue() + 60.0f, this.y.getValue() + 42.0f, white, this.shadow.getValue(), 1.0f);
            RenderUtil.drawString("Distance : " + (int)PlayerUtil.getDistance((Entity)TargetHUD.target), this.x.getValue() + 60.0f, this.y.getValue() + 57.0f, white, this.shadow.getValue(), 1.0f);
        }
        catch (Exception ex) {}
    }
    
    public void renderItem(final ItemStack item, final int x, final int y) {
        if (item == null) {
            return;
        }
        if (!item.isEmpty()) {
            RenderUtil.renderItem(item, (float)(int)(this.x.getValue() + x), (float)(int)(this.y.getValue() + y - 4.0f));
        }
    }
    
    public void renderDmg(final ItemStack item, final float x, final float y) {
        final float width = 10.0f;
        RenderUtil.drawRect(x + 3.0f, y, width, 1.0f, ColorUtil.toRGBA(0, 0, 0));
        final float dmg = this.getItemDmg(item) / 100.0f;
        RenderUtil.drawRect(x + 3.0f, y, width * dmg, 1.0f, this.getItemDmgColor(item));
    }
    
    public int getItemDmgColor(final ItemStack is) {
        final float maxDmg = (float)is.getMaxDamage();
        final float dmg = (float)(is.getMaxDamage() - is.getItemDamage());
        final double offset = 255.0f / (maxDmg / 2.0f);
        int red = 0;
        int green = 0;
        if (dmg > maxDmg / 2.0f) {
            red = (int)((maxDmg - dmg) * offset);
            green = 255;
        }
        else {
            red = 255;
            green = (int)(255.0 - (maxDmg / 2.0f - dmg) * offset);
        }
        return ColorUtil.toRGBA(red, green, 0, 255);
    }
    
    public float getItemDmg(final ItemStack is) {
        return (is.getMaxDamage() - is.getItemDamage()) / (float)is.getMaxDamage() * 100.0f;
    }
    
    public ItemStack getArmorInv(final int slot) {
        final InventoryPlayer inv = TargetHUD.target.inventory;
        return inv.armorItemInSlot(slot);
    }
    
    private static Color getHealthColor(int health) {
        if (health > 36) {
            health = 36;
        }
        if (health < 0) {
            health = 0;
        }
        int red = 0;
        int green = 0;
        if (health > 18) {
            red = (int)((36 - health) * 14.1666666667);
            green = 255;
        }
        else {
            red = 255;
            green = (int)(255.0 - (18 - health) * 14.1666666667);
        }
        return new Color(red, green, 0, 255);
    }
}
