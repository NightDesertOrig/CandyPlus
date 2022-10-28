//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.asm.api;

public class MappingName
{
    private final String mappingName;
    private final String notchName;
    private final String originName;
    
    public MappingName(final String mappingName, final String notchName, final String originName) {
        this.mappingName = mappingName;
        this.notchName = notchName;
        this.originName = originName;
    }
    
    public boolean equalName(final String name) {
        return this.mappingName.equals(name) || this.notchName.equals(name) || this.originName.equals(name);
    }
}
