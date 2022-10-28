//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class InventoryUtil implements Util
{
    public static int offhandSlot;
    
    public static int findHotbarBlock(final Block blockIn) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            final Block block;
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock && (block = ((ItemBlock)stack.getItem()).getBlock()) == blockIn) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findHotbarBlockWithClass(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock().getClass().equals(clazz)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getItemHotbar(final Item input) {
        for (int i = 0; i < 9; ++i) {
            final Item item = InventoryUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (Item.getIdFromItem(item) == Item.getIdFromItem(input)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void moveItemTo(final int item, final int empty) {
        InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, item, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, empty, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, item, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.updateController();
    }
    
    public static void moveItem(final int item) {
        InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, item, 0, ClickType.QUICK_MOVE, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.updateController();
    }
    
    public static void dropItem(final int item) {
        InventoryUtil.mc.playerController.windowClick(InventoryUtil.mc.player.inventoryContainer.windowId, item, 0, ClickType.THROW, (EntityPlayer)InventoryUtil.mc.player);
        InventoryUtil.mc.playerController.updateController();
    }
    
    static {
        InventoryUtil.offhandSlot = 45;
    }
}
