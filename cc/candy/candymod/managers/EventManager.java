//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import net.minecraftforge.event.entity.living.*;
import cc.candy.candymod.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.client.event.*;
import cc.candy.candymod.utils.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import cc.candy.candymod.event.events.player.*;

public class EventManager extends Manager
{
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (!this.nullCheck()) {
            CandyMod.m_notif.onUpdate();
            CandyMod.m_rotate.updateRotations();
            CandyMod.m_hole.update();
            CandyMod.m_module.onUpdate();
        }
    }
    
    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        CandyMod.m_module.onConnect();
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        CandyMod.Info("Saving configs...");
        ConfigManager.saveConfigs();
        CandyMod.Info("Successfully save configs!");
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        CandyMod.m_module.onTick();
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            CandyMod.m_module.onKeyInput(Keyboard.getEventKey());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(final RenderGameOverlayEvent.Text event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.TEXT)) {
            final ScaledResolution resolution = new ScaledResolution(EventManager.mc);
            CandyMod.m_module.onRender2D();
            CandyMod.m_notif.onRender2D();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (EventManager.mc.player == null || EventManager.mc.world == null) {
            return;
        }
        EventManager.mc.profiler.startSection("candy");
        EventManager.mc.profiler.startSection("setup");
        RenderUtil3D.prepare();
        EventManager.mc.profiler.endSection();
        CandyMod.m_module.onRender3D();
        CandyMod.m_module.onRender3D(event.getPartialTicks());
        EventManager.mc.profiler.startSection("release");
        RenderUtil3D.release();
        EventManager.mc.profiler.endSection();
        EventManager.mc.profiler.endSection();
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        CandyMod.m_module.onPacketSend(event);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        CandyMod.m_module.onPacketReceive(event);
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity((World)EventManager.mc.world) instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)packet.getEntity((World)EventManager.mc.world);
                CandyMod.m_module.onTotemPop(player);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final PlayerDeathEvent event) {
        CandyMod.m_module.onPlayerDeath(event);
    }
}
