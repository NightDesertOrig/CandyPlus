//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import cc.candy.candymod.module.misc.*;
import club.minnced.discord.rpc.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.combat.*;
import cc.candy.candymod.module.*;

public class RpcManager extends Manager
{
    private Thread _thread;
    
    public RpcManager() {
        this._thread = null;
    }
    
    public void enable(final DiscordRPC module) {
        final club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        final String applicationId = "871752800470728724";
        final String steamId = "";
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        final DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        lib.Discord_UpdatePresence(presence);
        presence.largeImageText = "";
        final club.minnced.discord.rpc.DiscordRPC discordRPC;
        final DiscordRichPresence discordRichPresence;
        (this._thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                discordRPC.Discord_RunCallbacks();
                if (module.girl.getValue()) {
                    discordRichPresence.largeImageKey = "tomari";
                }
                else {
                    discordRichPresence.largeImageKey = "icon";
                }
                discordRichPresence.details = "Enjoying Candy+";
                discordRichPresence.state = this.getState();
                discordRPC.Discord_UpdatePresence(discordRichPresence);
                try {
                    Thread.sleep(3000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public void disable() {
        club.minnced.discord.rpc.DiscordRPC.INSTANCE.Discord_Shutdown();
        this._thread = null;
    }
    
    public String getState() {
        if (RpcManager.mc.player == null) {
            return "Main Menu";
        }
        String state = "HP : " + Math.round(RpcManager.mc.player.getHealth() + RpcManager.mc.player.getAbsorptionAmount()) + " / " + Math.round(RpcManager.mc.player.getMaxHealth() + RpcManager.mc.player.getAbsorptionAmount());
        final Module piston = CandyMod.m_module.getModuleWithClass((Class)PistonAura.class);
        final Module cev = CandyMod.m_module.getModuleWithClass((Class)CevBreaker.class);
        final Module civ = CandyMod.m_module.getModuleWithClass((Class)CivBreaker.class);
        if (piston == null || cev == null || civ == null) {
            return state;
        }
        if (piston.isEnable) {
            state = "Pushing crystal";
        }
        if (cev.isEnable) {
            state = "Breaking ceil";
        }
        if (civ.isEnable) {
            state = "Attacking side";
        }
        return state;
    }
}
