//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.combat.*;
import net.minecraft.item.*;

public class AutoDrop extends Module
{
    public Setting<Boolean> sword;
    public Setting<Boolean> bow;
    public Setting<Boolean> pickel;
    public Setting<Boolean> armor;
    public Setting<Boolean> autoMend;
    public Setting<Float> damege;
    public Setting<Float> delay;
    public Timer timer;
    
    public AutoDrop() {
        super("AutoDrop", Categories.MISC, false, false);
        this.sword = (Setting<Boolean>)this.register(new Setting("Sword", (T)true));
        this.bow = (Setting<Boolean>)this.register(new Setting("Bow", (T)true));
        this.pickel = (Setting<Boolean>)this.register(new Setting("Pickel", (T)true));
        this.armor = (Setting<Boolean>)this.register(new Setting("DamageArmor", (T)true));
        this.autoMend = (Setting<Boolean>)this.register(new Setting("PauseWhenAutoMend", (T)true, v -> this.armor.getValue()));
        this.damege = (Setting<Float>)this.register(new Setting("MinDamege", (T)50.0f, (T)100.0f, (T)0.0f, v -> this.armor.getValue()));
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)1.0f, (T)10.0f, (T)0.0f));
        this.timer = new Timer();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (this.timer == null) {
            this.timer = new Timer();
        }
        if (this.timer.passedDms(this.delay.getValue())) {
            this.timer.reset();
            for (int i = 9; i < 36; ++i) {
                final ItemStack itemStack = (ItemStack)AutoDrop.mc.player.inventoryContainer.getInventory().get(i);
                final Item item = itemStack.getItem();
                if (!itemStack.isEmpty()) {
                    if (itemStack.getItem() != Items.AIR) {
                        if (item instanceof ItemSword && this.sword.getValue()) {
                            InventoryUtil.dropItem(i);
                            break;
                        }
                        if (item instanceof ItemBow && this.bow.getValue()) {
                            InventoryUtil.dropItem(i);
                            break;
                        }
                        if (item instanceof ItemPickaxe && this.pickel.getValue()) {
                            InventoryUtil.dropItem(i);
                            break;
                        }
                        if (item instanceof ItemArmor && this.armor.getValue()) {
                            final Module automend = CandyMod.m_module.getModuleWithClass((Class)AutoMend.class);
                            if (!automend.isEnable || !this.autoMend.getValue()) {
                                if (this.getDamage(itemStack) <= this.damege.getValue()) {
                                    InventoryUtil.dropItem(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public float getDamage(final ItemStack itemStack) {
        return (itemStack.getMaxDamage() - itemStack.getItemDamage()) / (itemStack.getMaxDamage() * 1.0f) * 100.0f;
    }
}
