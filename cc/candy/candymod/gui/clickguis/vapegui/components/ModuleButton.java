//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.gui.clickguis.vapegui.components;

import cc.candy.candymod.gui.clickguis.vapegui.*;
import cc.candy.candymod.module.*;
import java.util.*;
import java.util.function.*;
import cc.candy.candymod.utils.*;
import java.util.concurrent.atomic.*;
import cc.candy.candymod.setting.*;

public class ModuleButton extends Component
{
    public List<Component> componentList;
    public Module module;
    public boolean open;
    
    public ModuleButton(final Module module, final float x) {
        this.componentList = new ArrayList<Component>();
        this.module = module;
        this.x = x;
        this.width = 110.0f;
        this.height = 18.0f;
        this.open = false;
        module.settings.forEach(this::addSetting);
    }
    
    public float doRender(final float y, final int mouseX, final int mouseY) {
        this.y = y;
        RenderUtil.drawRect(this.x, y, this.width, this.height, this.baseColor);
        final boolean hovering = this.isMouseHovering(mouseX, mouseY);
        if (this.module.isEnable) {
            RenderUtil.drawRect(this.x, y, this.width, this.height - 0.2f, this.mainColor);
        }
        if (hovering) {
            RenderUtil.drawRect(this.x, y, this.width, this.height, ColorUtil.toRGBA(255, 255, 255, 50));
        }
        final float namey = this.getCenter(y, this.height, RenderUtil.getStringHeight(1.0f));
        RenderUtil.drawString(this.module.name, this.x + 5.0f, namey, (this.module.isEnable || hovering) ? this.white : this.gray, false, 1.0f);
        final float x = this.x + this.width - RenderUtil.getStringWidth("...", 1.0f) - 3.0f;
        RenderUtil.drawString("...", x, namey, (this.module.isEnable || hovering) ? this.white : this.gray, false, 1.0f);
        final AtomicReference<Float> _height = new AtomicReference<Float>(this.height);
        if (this.open) {
            final AtomicReference<Float> atomicReference;
            final float h;
            this.componentList.forEach(c -> {
                h = c.doRender(atomicReference.get() + this.y, mouseX, mouseY);
                RenderUtil.drawRect(this.x, y + atomicReference.get(), 2.0f, h, this.mainColor);
                atomicReference.updateAndGet(v -> v + h);
                return;
            });
        }
        return _height.get();
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int clickedMouseButton) {
        if (this.isMouseHovering(mouseX, mouseY) && clickedMouseButton == 1) {
            this.open = !this.open;
        }
        if (this.open) {
            this.componentList.forEach(c -> c.onMouseClicked(mouseX, mouseY, clickedMouseButton));
        }
    }
    
    public void addSetting(final Setting stg) {
        if (stg.getValue() instanceof Boolean) {
            this.componentList.add((Component)new BooleanButton(stg, this.x));
        }
        if (stg.getValue() instanceof Enum) {
            this.componentList.add((Component)new EnumButton(stg, this.x));
        }
    }
}
