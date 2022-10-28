//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.setting;

import java.util.function.*;

public class Setting<T>
{
    public String name;
    public T value;
    public T maxValue;
    public T minValue;
    public Predicate<T> visible;
    
    public Setting(final String name, final T defaultValue, final T maxValue, final T minValue) {
        this.name = name;
        this.value = defaultValue;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }
    
    public Setting(final String name, final T defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }
    
    public Setting(final String name, final T defaultValue, final Predicate<T> visible) {
        this.name = name;
        this.value = defaultValue;
        this.visible = visible;
    }
    
    public Setting(final String name, final T defaultValue, final T maxValue, final T minValue, final Predicate<T> visible) {
        this.name = name;
        this.value = defaultValue;
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.visible = visible;
    }
    
    public Setting(final String name, final Predicate<T> visible) {
        this.name = name;
        this.visible = visible;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public boolean visible() {
        return this.visible == null || this.visible.test(this.getValue());
    }
    
    public void setValue(final Object value) {
        this.value = (T)value;
    }
    
    public int currentEnum() {
        return EnumConverter.currentEnum((Enum)this.value);
    }
    
    public void increaseEnum() {
        this.value = (T)EnumConverter.increaseEnum((Enum)this.value);
    }
    
    public void setEnum(final int index) {
        this.value = (T)EnumConverter.setEnum((Enum)this.value, index);
    }
}
