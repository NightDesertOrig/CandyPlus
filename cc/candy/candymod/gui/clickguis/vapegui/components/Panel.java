//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.vapegui.components;

import cc.candy.candymod.gui.clickguis.vapegui.*;
import cc.candy.candymod.module.*;
import java.util.*;
import cc.candy.candymod.*;
import cc.candy.candymod.utils.*;
import java.util.concurrent.atomic.*;

public class Panel extends Component
{
    public boolean open;
    public List<ModuleButton> moduleButtonList;
    public Module.Categories category;
    
    public Panel(final Module.Categories category, final float x, final float y) {
        this.moduleButtonList = new ArrayList<ModuleButton>();
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 110.0f;
        this.height = 22.0f;
        this.open = true;
        CandyMod.m_module.getModulesWithCategories(category).forEach(m -> this.moduleButtonList.add(new ModuleButton(m, x)));
    }
    
    public void onRender(final int mouseX, final int mouseY) {
        RenderUtil.drawRect(this.x, this.y, this.width, this.height, this.panelColor);
        final float namey = this.getCenter(this.y, this.height, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawString(StringUtil.getName(this.category.name()), this.x + 5.0f, namey, this.white, false, 1.0f);
        final AtomicReference<Float> y = new AtomicReference<Float>(this.y + this.height);
        final Float n;
        this.moduleButtonList.forEach(m -> n = y.updateAndGet(v -> v + m.doRender((float)v, mouseX, mouseY)));
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseHovering(mouseX, mouseY) && 1 == mouseButton) {
            this.open = !this.open;
        }
        if (this.open) {
            this.moduleButtonList.forEach(m -> m.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
}
