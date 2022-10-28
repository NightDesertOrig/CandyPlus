//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.asm;

import net.minecraft.launchwrapper.*;
import cc.candy.candymod.asm.api.*;
import java.util.*;
import cc.candy.candymod.asm.impl.*;

public class CandyTransformer implements IClassTransformer
{
    public static final List<ClassPatch> patches;
    
    public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        for (final ClassPatch it : CandyTransformer.patches) {
            if (it.className.equals(transformedName)) {
                return it.transform(bytes);
            }
        }
        return bytes;
    }
    
    static {
        (patches = new ArrayList<ClassPatch>()).add(new PatchEntityRenderer());
    }
}
