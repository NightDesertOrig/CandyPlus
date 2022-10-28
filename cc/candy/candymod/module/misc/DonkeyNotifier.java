//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import cc.candy.candymod.utils.*;
import java.util.*;

public class DonkeyNotifier extends Module
{
    private Setting<Boolean> donkey;
    private Setting<Boolean> llama;
    private List<Entity> entities;
    
    public DonkeyNotifier() {
        super("DonkeyNotifer", Categories.MISC, false, false);
        this.donkey = (Setting<Boolean>)this.register(new Setting("Donkey", (T)true));
        this.llama = (Setting<Boolean>)this.register(new Setting("Llama", (T)true));
        this.entities = new ArrayList<Entity>();
    }
    
    @Override
    public void onDisable() {
        this.entities = new ArrayList<Entity>();
    }
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        final List<Entity> donkeys = new ArrayList<Entity>(DonkeyNotifier.mc.world.loadedEntityList);
        donkeys.removeIf(e -> (!this.donkey.getValue() || !(e instanceof EntityDonkey)) && (!this.llama.value || !(e instanceof EntityLlama)));
        for (final Entity e2 : donkeys) {
            if (!this.entities.contains(e2)) {
                this.entities.add(e2);
                this.sendMessage("Found a " + ((e2 instanceof EntityDonkey) ? "Donkey" : "Llama") + " at " + StringUtil.getPositionString(e2.getPosition()));
            }
        }
    }
}
