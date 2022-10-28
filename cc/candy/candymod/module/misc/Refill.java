//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.item.*;
import cc.candy.candymod.utils.*;

public class Refill extends Module
{
    public Setting<Float> delay;
    public Timer timer;
    
    public Refill() {
        super("Refill", Categories.MISC, false, false);
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
            for (int i = 0; i < 9; ++i) {
                final ItemStack itemstack = (ItemStack)Refill.mc.player.inventory.mainInventory.get(i);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isStackable()) {
                        if (itemstack.getCount() < itemstack.getMaxStackSize()) {
                            if (this.doRefill(itemstack)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean doRefill(final ItemStack stack) {
        for (int i = 9; i < 36; ++i) {
            final ItemStack item = Refill.mc.player.inventory.getStackInSlot(i);
            if (this.CanItemBeMergedWith(item, stack)) {
                InventoryUtil.moveItem(i);
                return true;
            }
        }
        return false;
    }
    
    private boolean CanItemBeMergedWith(final ItemStack p_Source, final ItemStack p_Target) {
        return p_Source.getItem() == p_Target.getItem() && p_Source.getDisplayName().equals(p_Target.getDisplayName());
    }
}
