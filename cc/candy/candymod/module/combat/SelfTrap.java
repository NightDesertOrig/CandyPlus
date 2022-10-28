//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;
import net.minecraft.util.math.*;
import java.util.*;

public class SelfTrap extends Module
{
    private final Setting<Integer> blocksPerTick;
    private final Setting<Integer> delay;
    private final Setting<Boolean> rotate;
    private final Setting<Integer> disableTime;
    private final Setting<Boolean> disable;
    private final Setting<Boolean> packet;
    private final Timer offTimer;
    private final Timer timer;
    private final Map<BlockPos, Integer> retries;
    private final Timer retryTimer;
    private int blocksThisTick;
    private boolean isSneaking;
    private boolean hasOffhand;
    
    public SelfTrap() {
        super("SelfTrap", Categories.COMBAT, false, false);
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)8, (T)1, (T)20));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)250));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.disableTime = (Setting<Integer>)this.register(new Setting("DisableTime", (T)200, (T)50, (T)300));
        this.disable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)true));
        this.packet = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.offTimer = new Timer();
        this.timer = new Timer();
        this.retries = new HashMap<BlockPos, Integer>();
        this.retryTimer = new Timer();
        this.blocksThisTick = 0;
        this.hasOffhand = false;
    }
}
