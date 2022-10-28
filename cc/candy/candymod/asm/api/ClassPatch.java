//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.asm.api;

public class ClassPatch
{
    public final String className;
    public final String notchName;
    
    public ClassPatch(final String nameIn, final String notchIn) {
        this.className = nameIn;
        this.notchName = notchIn;
    }
    
    public byte[] transform(final byte[] bytes) {
        return new byte[0];
    }
    
    public boolean equalName(final String name) {
        return this.className.equals(name) || this.notchName.equals(name);
    }
}
