//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import cc.candy.candymod.utils.*;
import net.minecraft.item.*;

public class PvPResources extends Hud
{
    public Setting<Boolean> crystal;
    public Setting<Boolean> xp;
    public Setting<Boolean> gap;
    public Setting<Boolean> totem;
    public Setting<Boolean> obby;
    public Setting<Boolean> piston;
    public Setting<Boolean> redstone;
    public Setting<Boolean> torch;
    public Setting<Boolean> block;
    public Setting<Boolean> shadow;
    public Setting<Color> color;
    public Setting<Boolean> background;
    public Setting<Color> backcolor;
    
    public PvPResources() {
        super("PvPResources", 300.0f, 100.0f);
        this.crystal = (Setting<Boolean>)this.register(new Setting("Crystal", (T)true));
        this.xp = (Setting<Boolean>)this.register(new Setting("Xp", (T)true));
        this.gap = (Setting<Boolean>)this.register(new Setting("Gap", (T)true));
        this.totem = (Setting<Boolean>)this.register(new Setting("Totem", (T)true));
        this.obby = (Setting<Boolean>)this.register(new Setting("Obsidian", (T)true));
        this.piston = (Setting<Boolean>)this.register(new Setting("Piston", (T)true));
        this.redstone = (Setting<Boolean>)this.register(new Setting("RedStone", (T)true));
        this.torch = (Setting<Boolean>)this.register(new Setting("Torch", (T)true, v -> this.redstone.getValue()));
        this.block = (Setting<Boolean>)this.register(new Setting("Block", (T)true, v -> this.redstone.getValue()));
        this.shadow = (Setting<Boolean>)this.register(new Setting("Shadow", (T)false));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 255)));
        this.background = (Setting<Boolean>)this.register(new Setting("Background", (T)false));
        this.backcolor = (Setting<Color>)this.register(new Setting("BGColor", (T)new Color(40, 40, 40, 60), v -> this.background.getValue()));
    }
    
    public void onRender() {
        final float x = this.x.getValue();
        float y = this.y.getValue();
        if (this.crystal.getValue()) {
            this.renderItem(Items.END_CRYSTAL, this.getItemCount(Items.END_CRYSTAL), x, y);
            y += RenderUtil.getStringHeight(1.0f) + 13.0f;
        }
        if (this.xp.getValue()) {
            this.renderItem(Items.EXPERIENCE_BOTTLE, this.getItemCount(Items.EXPERIENCE_BOTTLE), x, y);
            y += RenderUtil.getStringHeight(1.0f) + 13.0f;
        }
        if (this.gap.getValue()) {
            this.renderItem(Items.GOLDEN_APPLE, this.getItemCount(Items.GOLDEN_APPLE), x, y);
            y += RenderUtil.getStringHeight(1.0f) + 13.0f;
        }
        if (this.totem.getValue()) {
            this.renderItem(Items.TOTEM_OF_UNDYING, this.getItemCount(Items.TOTEM_OF_UNDYING), x, y);
            y += RenderUtil.getStringHeight(1.0f) + 13.0f;
        }
        if (this.obby.getValue()) {
            this.renderBlock(Blocks.OBSIDIAN, this.getBlockCount(Blocks.OBSIDIAN), x, y);
            y += RenderUtil.getStringHeight(1.0f) + 13.0f;
        }
        if (this.piston.getValue()) {
            this.renderBlock((Block)Blocks.PISTON, this.getBlockCount((Block)Blocks.PISTON) + this.getBlockCount((Block)Blocks.STICKY_PISTON), x, y);
            y += RenderUtil.getStringHeight(1.0f) + 13.0f;
        }
        if (this.redstone.getValue()) {
            if (this.block.getValue()) {
                this.renderBlock(Blocks.REDSTONE_BLOCK, this.getBlockCount(Blocks.REDSTONE_BLOCK), x, y);
                y += RenderUtil.getStringHeight(1.0f) + 13.0f;
            }
            if (this.torch.getValue()) {
                this.renderBlock(Blocks.REDSTONE_TORCH, this.getBlockCount(Blocks.REDSTONE_TORCH), x, y);
                y += RenderUtil.getStringHeight(1.0f) + 13.0f;
            }
        }
        y -= RenderUtil.getStringHeight(1.0f) + 13.0f;
        this.width = x + 20.0f + RenderUtil.getStringWidth(" : 64", 1.0f) - this.x.getValue();
        this.height = y - this.y.getValue();
    }
    
    public void renderItem(final Item item, final int count, final float x, final float y) {
        RenderUtil.renderItem(new ItemStack(item), x, y - 8.0f, false);
        RenderUtil.drawString(" : " + count, x + 20.0f, y, ColorUtil.toRGBA(this.color.getValue()), this.shadow.getValue(), 1.0f);
    }
    
    public void renderBlock(final Block block, final int count, final float x, final float y) {
        RenderUtil.renderItem(new ItemStack(block), x, y - 10.0f, false);
        RenderUtil.drawString(" : " + count, x + 20.0f, y, ColorUtil.toRGBA(this.color.getValue()), this.shadow.getValue(), 1.0f);
    }
    
    public int getItemCount(final Item item) {
        int count = 0;
        for (int i = 0; i < PvPResources.mc.player.inventory.getSizeInventory(); ++i) {
            final ItemStack itemStack = PvPResources.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == item) {
                count += itemStack.getCount();
            }
        }
        return count;
    }
    
    public int getBlockCount(final Block block) {
        int count = 0;
        for (int i = 0; i < PvPResources.mc.player.inventory.getSizeInventory(); ++i) {
            final ItemStack itemStack = PvPResources.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemBlock && ((ItemBlock)itemStack.getItem()).getBlock() == block) {
                count += itemStack.getCount();
            }
        }
        return count;
    }
}
