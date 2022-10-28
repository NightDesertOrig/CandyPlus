//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import cc.candy.candymod.module.combat.*;
import com.mojang.realmsclient.gui.*;
import cc.candy.candymod.utils.*;
import cc.candy.candymod.module.*;
import cc.candy.candymod.*;

public class CombatInfo extends Hud
{
    public Setting<Boolean> shadow;
    public Setting<Color> color;
    public Setting<Boolean> mend;
    public Setting<Boolean> blocker;
    public Setting<Boolean> cev;
    public Setting<Boolean> civ;
    public Setting<Boolean> holefill;
    public Setting<Boolean> oyveyaura;
    public Setting<Boolean> pa;
    
    public CombatInfo() {
        super("CombatInfo", 50.0f, 10.0f);
        this.shadow = (Setting<Boolean>)this.register(new Setting("Shadow", (T)true));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 255)));
        this.mend = (Setting<Boolean>)this.register(new Setting("AutoMend", (T)true));
        this.blocker = (Setting<Boolean>)this.register(new Setting("Blocker", (T)true));
        this.cev = (Setting<Boolean>)this.register(new Setting("CevBreaker", (T)true));
        this.civ = (Setting<Boolean>)this.register(new Setting("CivBreaker", (T)true));
        this.holefill = (Setting<Boolean>)this.register(new Setting("HoleFill", (T)true));
        this.oyveyaura = (Setting<Boolean>)this.register(new Setting("OyveyAura", (T)true));
        this.pa = (Setting<Boolean>)this.register(new Setting("PistonAura", (T)true));
    }
    
    public void onRender() {
        try {
            final Module mend = this.getModule(AutoMend.class);
            final Module blocker = this.getModule(Blocker.class);
            final Module cev = this.getModule(CevBreaker.class);
            final Module civ = this.getModule(CivBreaker.class);
            final Module holefill = this.getModule(HoleFill.class);
            final Module oyveyaura = this.getModule(OyveyAura.class);
            final Module pa = this.getModule(PistonAura.class);
            final Module pa2 = this.getModule(PistonAuraRewrite.class);
            final Module pa3 = this.getModule(PistonAuraRewrite2.class);
            float width = 0.0f;
            float height = 0.0f;
            if (this.mend.getValue()) {
                final float _width = this.drawModuleInfo(mend, height);
                if (width < _width) {
                    width = _width;
                }
                height += RenderUtil.getStringHeight(1.0f) + 5.0f;
            }
            if (this.blocker.getValue()) {
                final float _width = this.drawModuleInfo(blocker, height);
                if (width < _width) {
                    width = _width;
                }
                height += RenderUtil.getStringHeight(1.0f) + 5.0f;
            }
            if (this.cev.getValue()) {
                final float _width = this.drawModuleInfo(cev, height);
                if (width < _width) {
                    width = _width;
                }
                height += RenderUtil.getStringHeight(1.0f) + 5.0f;
            }
            if (this.civ.getValue()) {
                final float _width = this.drawModuleInfo(civ, height);
                if (width < _width) {
                    width = _width;
                }
                height += RenderUtil.getStringHeight(1.0f) + 5.0f;
            }
            if (this.holefill.getValue()) {
                final float _width = this.drawModuleInfo(holefill, height);
                if (width < _width) {
                    width = _width;
                }
                height += RenderUtil.getStringHeight(1.0f) + 5.0f;
            }
            if (this.oyveyaura.getValue()) {
                final float _width = this.drawModuleInfo(oyveyaura, height);
                if (width < _width) {
                    width = _width;
                }
                height += RenderUtil.getStringHeight(1.0f) + 5.0f;
            }
            if (this.pa.getValue()) {
                final float _width = RenderUtil.drawString(pa.name + " : " + ((pa.isEnable || pa2.isEnable || pa3.isEnable) ? (ChatFormatting.GREEN + "ON") : (ChatFormatting.RED + "OFF")), this.x.getValue(), this.y.getValue() + height, ColorUtil.toRGBA(this.color.getValue()), this.shadow.getValue(), 1.0f);
                if (width > _width) {
                    width = _width;
                }
                height += RenderUtil.getStringHeight(1.0f) + 5.0f;
            }
            this.width = width - this.x.getValue();
            this.height = height;
        }
        catch (Exception ex) {}
    }
    
    public float drawModuleInfo(final Module module, final float offset) {
        return RenderUtil.drawString(module.name + " : " + (module.isEnable ? (ChatFormatting.GREEN + "ON") : (ChatFormatting.RED + "OFF")), this.x.getValue(), this.y.getValue() + offset, ColorUtil.toRGBA(this.color.getValue()), this.shadow.getValue(), 1.0f);
    }
    
    public Module getModule(final Class clazz) {
        return CandyMod.m_module.getModuleWithClass(clazz);
    }
}
