//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.combat;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.init.*;
import cc.candy.candymod.utils.*;
import net.minecraft.network.*;
import org.lwjgl.input.*;
import java.util.concurrent.atomic.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;

public class AutoMend extends Module
{
    public Setting<Float> delay;
    public Setting<Float> armor;
    public Setting<Float> pct;
    public Setting<Boolean> press;
    public Setting<Boolean> silentSwitch;
    public boolean toggleoff;
    public Map<Integer, Integer> armors;
    public Timer xpTimer;
    public Timer armorTimer;
    private EnumHand oldhand;
    private int oldslot;
    
    public AutoMend() {
        super("AutoMend", Categories.COMBAT, false, false);
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)3.0f, (T)10.0f, (T)0.0f));
        this.armor = (Setting<Float>)this.register(new Setting("ArmorDelay", (T)3.0f, (T)20.0f, (T)0.0f));
        this.pct = (Setting<Float>)this.register(new Setting("Pct", (T)80.0f, (T)100.0f, (T)10.0f));
        this.press = (Setting<Boolean>)this.register(new Setting("Press", (T)true));
        this.silentSwitch = (Setting<Boolean>)this.register(new Setting("SilentSwitch", (T)true));
        this.toggleoff = false;
        this.armors = new HashMap<Integer, Integer>();
        this.armorTimer = new Timer();
        this.oldhand = null;
        this.oldslot = -1;
        this.disable();
    }
    
    @Override
    public void onEnable() {
        this.xpTimer = new Timer();
        this.armorTimer = new Timer();
    }
    
    @Override
    public void onDisable() {
        if (!this.toggleoff) {
            this.toggleoff = true;
            this.armors = new HashMap<Integer, Integer>();
            this.enable();
            return;
        }
        this.toggleoff = false;
    }
    
    @Override
    public void onTick() {
        if (this.nullCheck()) {
            return;
        }
        if (!this.toggleoff) {
            if (this.armorTimer == null) {
                this.armorTimer = new Timer();
            }
            this.armorTimer.reset();
            final int xp = InventoryUtil.getItemHotbar(Items.EXPERIENCE_BOTTLE);
            if (xp == -1) {
                this.sendMessage("Cannot find XP! disabling");
                this.setDisable();
                return;
            }
            if (this.isDone()) {
                this.setDisable();
                return;
            }
            if (this.xpTimer == null) {
                this.xpTimer = new Timer();
            }
            if (this.xpTimer.passedX(this.delay.getValue())) {
                this.xpTimer.reset();
                this.setItem(xp);
                AutoMend.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 90.0f, false));
                AutoMend.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.restoreItem();
            }
            if (this.key.key != -1 && this.press.getValue()) {
                if (!Keyboard.isKeyDown(this.key.key)) {
                    this.setDisable();
                }
            }
        }
        else {
            if (this.armorTimer == null) {
                this.armorTimer = new Timer();
            }
            if (this.armorTimer.passedDms(this.armor.getValue())) {
                this.armorTimer.reset();
                final AtomicInteger c = new AtomicInteger();
                final AtomicInteger key = new AtomicInteger();
                final AtomicInteger atomicInteger;
                final AtomicInteger atomicInteger2;
                this.armors.forEach((k, v) -> {
                    if (atomicInteger.get() == 0) {
                        InventoryUtil.moveItemTo(k, v);
                        atomicInteger2.set(k);
                    }
                    atomicInteger.getAndIncrement();
                    return;
                });
                if (c.get() == 0) {
                    this.disable();
                }
                else {
                    this.armors.remove(key.get());
                }
            }
        }
    }
    
    @Override
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer = (CPacketPlayer)event.getPacket();
        }
    }
    
    public void setDisable() {
        this.toggleoff = true;
    }
    
    public void moveArmorToSlot(final int armor, final int empty) {
        InventoryUtil.moveItemTo(armor, empty);
        this.armors.put(empty, armor);
    }
    
    public boolean isDone() {
        boolean done = true;
        for (int i = 0; i < AutoMend.mc.player.inventoryContainer.getInventory().size(); ++i) {
            final ItemStack itemStack = (ItemStack)AutoMend.mc.player.inventoryContainer.getInventory().get(i);
            if (i > 4) {
                if (i < 9) {
                    if (!itemStack.isEmpty()) {
                        if (this.getRate(itemStack) < this.pct.getValue()) {
                            done = false;
                        }
                        else {
                            final int slot = this.getFreeSlot();
                            if (slot != -1) {
                                this.moveArmorToSlot(i, slot);
                            }
                        }
                    }
                }
            }
        }
        return done;
    }
    
    public int getFreeSlot() {
        for (int i = 0; i < AutoMend.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack stack = (ItemStack)AutoMend.mc.player.inventoryContainer.getInventory().get(i);
                    if (stack.isEmpty() || stack.getItem() == Items.AIR) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public float getRate(final ItemStack itemStack) {
        return (itemStack.getMaxDamage() - itemStack.getItemDamage()) / (itemStack.getMaxDamage() * 1.0f) * 100.0f;
    }
    
    public void setItem(final int slot) {
        if (this.silentSwitch.getValue()) {
            this.oldhand = null;
            if (AutoMend.mc.player.isHandActive()) {
                this.oldhand = AutoMend.mc.player.getActiveHand();
            }
            this.oldslot = AutoMend.mc.player.inventory.currentItem;
            AutoMend.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            AutoMend.mc.player.inventory.currentItem = slot;
            AutoMend.mc.playerController.updateController();
        }
    }
    
    public void restoreItem() {
        if (this.oldslot != -1 && this.silentSwitch.getValue()) {
            if (this.oldhand != null) {
                AutoMend.mc.player.setActiveHand(this.oldhand);
            }
            AutoMend.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            this.oldslot = -1;
            this.oldhand = null;
        }
    }
}
