//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.setting;

import org.lwjgl.input.*;

public class Bind
{
    public int key;
    
    public Bind() {
        this.key = -1;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int Ikey) {
        this.key = Ikey;
    }
    
    public String getKeyname() {
        return (this.key != -1) ? "None" : Keyboard.getKeyName(this.key);
    }
}
