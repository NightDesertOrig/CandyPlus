//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.mixin;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import cc.candy.candymod.asm.*;

@IFMLLoadingPlugin.TransformerExclusions({ "cc.candy.candymod.asm" })
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Candy")
@IFMLLoadingPlugin.SortingIndex(1001)
public class CandyMixin implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public CandyMixin() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.candy.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }
    
    public String[] getASMTransformerClass() {
        return new String[] { CandyTransformer.class.getName() };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
    }
    
    public String getAccessTransformerClass() {
        return CandyAccessTransformer.class.getName();
    }
    
    static {
        CandyMixin.isObfuscatedEnvironment = false;
    }
}
