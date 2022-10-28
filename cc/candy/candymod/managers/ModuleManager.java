//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import cc.candy.candymod.module.*;
import cc.candy.candymod.module.combat.*;
import cc.candy.candymod.module.exploit.*;
import cc.candy.candymod.module.misc.*;
import cc.candy.candymod.module.movement.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.hud.modules.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.entity.player.*;
import cc.candy.candymod.event.events.player.*;
import java.util.*;

public class ModuleManager extends Manager
{
    public List<Module> modules;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
    }
    
    public void load() {
        this.register(new PistonAura());
        this.register(new Velocity());
        this.register(new Blocker());
        this.register(new SelfAnvil());
        this.register(new CevBreaker());
        this.register(new CivBreaker());
        this.register(new AutoPush());
        this.register(new AutoMend());
        this.register(new AutoCity());
        this.register(new PistonAuraRewrite());
        this.register(new PistonAuraRewrite2());
        this.register(new OyveyAura());
        this.register(new AntiBurrow());
        this.register(new AutoTotem());
        this.register(new HoleFill());
        this.register(new BowSpam());
        this.register(new CrystalAura());
        this.register(new SelfAnvil2());
        this.register(new InstantMine());
        this.register(new SilentPickel());
        this.register(new XCarry());
        this.register(new Burrow());
        this.register(new NoMiningTrace());
        this.register(new PingBypass());
        this.register(new TrapPhase());
        this.register(new DonkeyNotifier());
        this.register(new FakePlayer());
        this.register(new ChatSuffix());
        this.register(new NoteSpam());
        this.register(new DiscordRPC());
        this.register(new PopAnnouncer());
        this.register(new AutoEZ());
        this.register(new Refill());
        this.register(new AutoDrop());
        this.register(new Spammer());
        this.register(new HoleTP());
        this.register(new Speed());
        this.register(new Blink());
        this.register(new TPCart());
        this.register(new NoWeb());
        this.register(new NoSlowdown());
        this.register(new PhaseWalk());
        this.register(new Afterglow());
        this.register(new BreadCrumbs());
        this.register(new ClickGUI());
        this.register(new BurrowESP());
        this.register(new NoOverlay());
        this.register(new CustomFov());
        this.register(new Notification());
        this.register(new CandyCrystal());
        this.register(new HoleESP());
        this.register(new CityESP());
        this.register(new HUDEditor());
        this.register(new ItemViewModel());
        this.register(new SmallShield());
        this.register(new EnchantmentColor());
        this.register((Module)new InventoryViewer());
        this.register((Module)new ModuleList());
        this.register((Module)new Welcomer());
        this.register((Module)new PvPResources());
        this.register((Module)new PlayerList());
        this.register((Module)new CombatInfo());
        this.register((Module)new PlayerModel());
        this.register((Module)new TargetHUD());
    }
    
    public void register(final Module module) {
        this.modules.add(module);
    }
    
    public void onUpdate() {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onUpdate());
    }
    
    public void onTick() {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onTick());
    }
    
    public void onConnect() {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onConnect());
    }
    
    public void onRender2D() {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onRender2D());
    }
    
    public void onRender3D() {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> {
            ModuleManager.mc.profiler.startSection(m.name);
            m.onRender3D();
            ModuleManager.mc.profiler.endSection();
        });
    }
    
    public void onRender3D(final float ticks) {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> {
            ModuleManager.mc.profiler.startSection(m.name);
            m.onRender3D(ticks);
            ModuleManager.mc.profiler.endSection();
        });
    }
    
    public void onPacketSend(final PacketEvent.Send event) {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onPacketSend(event));
    }
    
    public void onPacketReceive(final PacketEvent.Receive event) {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onPacketReceive(event));
    }
    
    public void onTotemPop(final EntityPlayer player) {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onTotemPop(player));
    }
    
    public void onPlayerDeath(final PlayerDeathEvent event) {
        this.modules.stream().filter(m -> m.isEnable).forEach(m -> m.onPlayerDeath(event));
    }
    
    public void onKeyInput(final int key) {
        this.modules.stream().filter(m -> m.key.getKey() == key).forEach(m -> m.toggle());
    }
    
    public List<Module> getModulesWithCategories(final Module.Categories c) {
        final List<Module> moduleList = new ArrayList<Module>();
        for (final Module m : this.modules) {
            if (m.category == c) {
                moduleList.add(m);
            }
        }
        return moduleList;
    }
    
    public Module getModuleWithName(final String name) {
        Module r = null;
        for (final Module m : this.modules) {
            if (Objects.equals(m.name, name)) {
                r = m;
            }
        }
        return r;
    }
    
    public Module getModuleWithClass(final Class clazz) {
        Module r = null;
        for (final Module m : this.modules) {
            if (m.getClass() == clazz) {
                r = m;
            }
        }
        return r;
    }
}
