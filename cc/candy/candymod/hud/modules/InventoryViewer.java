//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class InventoryViewer extends Hud
{
    public InventoryViewer() {
        super("InventoryViewer", 150.0f, 100.0f);
    }
    
    public void onRender() {
        if (this.nullCheck()) {
            return;
        }
        RenderUtil.drawRect(this.x.getValue() - 6.0f, this.y.getValue() - 6.0f, 180.0f, 72.0f, ColorUtil.toRGBA(0, 0, 0));
        RenderUtil.drawRect(this.x.getValue() - 5.0f, this.y.getValue() - 5.0f, 180.0f, 69.0f, ColorUtil.toRGBA(40, 40, 40));
        float _x = 0.0f;
        float _y = 0.0f;
        int c = 0;
        final int scale = 19;
        final InventoryPlayer inv = InventoryViewer.mc.player.inventory;
        for (int i = 9; i < 36; ++i) {
            final ItemStack item = inv.getStackInSlot(i);
            RenderUtil.renderItem(item, this.x.getValue() + _x + 3.0f, this.y.getValue() + _y + 3.0f);
            _x += scale;
            if (++c == 9) {
                _x = 0.0f;
                _y += scale;
                c = 0;
            }
        }
        this.width = 168.0f;
        this.height = 60.0f;
    }
}
