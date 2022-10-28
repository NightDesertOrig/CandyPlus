//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.utils.*;
import java.util.*;

public class ModuleList extends Hud
{
    public Setting<Boolean> shadow;
    public Setting<Color> color;
    public Setting<Boolean> background;
    public Setting<Color> backcolor;
    public Setting<Boolean> edge;
    public Setting<Color> edgeColor;
    
    public ModuleList() {
        super("ModuleList", 0.0f, 110.0f);
        this.shadow = (Setting<Boolean>)this.register(new Setting("Shadow", (T)false));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 255)));
        this.background = (Setting<Boolean>)this.register(new Setting("Background", (T)true));
        this.backcolor = (Setting<Color>)this.register(new Setting("BGColor", (T)new Color(40, 40, 40, 70), v -> this.background.getValue()));
        this.edge = (Setting<Boolean>)this.register(new Setting("Edge", (T)true));
        this.edgeColor = (Setting<Color>)this.register(new Setting("EGColor", (T)new Color(255, 255, 255, 150), v -> this.edge.getValue()));
    }
    
    public void onRender() {
        float y = this.y.getValue();
        final float size = 1.0f;
        float _width = 0.0f;
        final float height = RenderUtil.getStringHeight(size);
        final List<Module> sortedModuleList = new ArrayList<Module>(CandyMod.m_module.modules);
        Collections.sort(sortedModuleList, (c1, c2) -> c1.name.compareToIgnoreCase(c2.name));
        for (final Module module : sortedModuleList) {
            if (!module.isEnable) {
                continue;
            }
            final String name = module.name;
            final float width = RenderUtil.getStringWidth(name, size);
            if (width > _width) {
                _width = width;
            }
            if (this.background.getValue()) {
                RenderUtil.drawRect(this.x.getValue(), y, width + 20.0f * size, height + 10.0f * size, ColorUtil.toRGBA(this.backcolor.getValue()));
                if (this.edge.getValue()) {
                    RenderUtil.drawRect(this.x.getValue(), y, 2.0f * size, height + 10.0f * size, ColorUtil.toRGBA(this.edgeColor.getValue()));
                }
            }
            RenderUtil.drawString(name, this.x.getValue() + 10.0f, y + 5.0f, ColorUtil.toRGBA(this.color.getValue()), this.shadow.getValue(), size);
            y += height + 10.0f;
        }
        y -= height + 11.0f;
        this.width = _width + 20.0f;
        this.height = y - this.y.getValue();
    }
}
