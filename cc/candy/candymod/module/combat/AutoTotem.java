//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.player.*;
import cc.candy.candymod.utils.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class AutoTotem extends Module
{
    public Setting<Boolean> packet;
    public Setting<Boolean> doublE;
    
    public AutoTotem() {
        super("AutoTotem", Categories.COMBAT, false, false);
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)true));
        this.doublE = (Setting<Boolean>)this.register(new Setting("Double", (T)true));
    }
    
    @Override
    public void onTotemPop(final EntityPlayer player) {
        if (player.getEntityId() != AutoTotem.mc.player.getEntityId()) {
            return;
        }
        if (this.packet.getValue()) {
            this.doTotem();
        }
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (this.shouldTotem()) {
            this.doTotem();
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (this.shouldTotem() && this.doublE.getValue()) {
            this.doTotem();
        }
    }
    
    public void doTotem() {
        final int totem = this.findTotemSlot();
        if (totem == -1) {
            return;
        }
        InventoryUtil.moveItemTo(totem, InventoryUtil.offhandSlot);
    }
    
    public boolean shouldTotem() {
        return AutoTotem.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING;
    }
    
    public int findTotemSlot() {
        for (int i = 0; i < AutoTotem.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != InventoryUtil.offhandSlot) {
                final ItemStack stack = (ItemStack)AutoTotem.mc.player.inventoryContainer.getInventory().get(i);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    return i;
                }
            }
        }
        return -1;
    }
}
