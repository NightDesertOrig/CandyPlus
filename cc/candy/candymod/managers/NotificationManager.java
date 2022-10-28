//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import net.minecraft.entity.player.*;
import cc.candy.candymod.*;
import cc.candy.candymod.module.render.*;
import java.util.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.eventhandler.*;
import cc.candy.candymod.event.events.player.*;
import com.mojang.realmsclient.gui.*;
import java.awt.*;
import cc.candy.candymod.utils.*;
import java.util.stream.*;
import net.minecraft.util.math.*;
import net.minecraft.client.*;

public class NotificationManager extends Manager
{
    public List<Notif> notifs;
    public List<EntityPlayer> players;
    private Map<String, Integer> popCounter;
    private int scaledWidth;
    private int scaledHeight;
    private int scaleFactor;
    
    public NotificationManager() {
        this.notifs = new ArrayList<Notif>();
        this.players = new ArrayList<EntityPlayer>();
        this.popCounter = new HashMap<String, Integer>();
        this.scaleFactor = 0;
    }
    
    public void showNotification(final String msg) {
        if (NotificationManager.mc.player == null) {
            return;
        }
        if (!CandyMod.m_module.getModuleWithClass((Class)Notification.class).isEnable) {
            return;
        }
        final Notif notif = new Notif(msg);
        for (final Notif notif2 : this.notifs) {
            final Notif n = notif2;
            notif2.y -= CandyMod.m_font.getHeight() + 40;
        }
        this.updateResolution();
        notif.y = (float)(this.scaledHeight - 50);
        notif._y = (float)(this.scaledHeight - 50);
        this.notifs.add(notif);
    }
    
    public void onUpdate() {
        if (NotificationManager.mc.world == null) {
            return;
        }
        for (final EntityPlayer player : new ArrayList<EntityPlayer>(NotificationManager.mc.world.playerEntities)) {
            if (!this.players.contains(player)) {
                this.showNotification(player.getName() + " is coming towards you!");
            }
        }
        this.players = new ArrayList<EntityPlayer>(NotificationManager.mc.world.playerEntities);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity((World)NotificationManager.mc.world) instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)packet.getEntity((World)NotificationManager.mc.world);
                final Notification notification = (Notification)CandyMod.m_module.getModuleWithClass((Class)Notification.class);
                if (notification.pop.getValue()) {
                    final int pop = this.countPop(player.getName());
                    if (pop == 1) {
                        this.showNotification(player.getName() + " popped a totem!");
                    }
                    else {
                        this.showNotification(player.getName() + " popped " + pop + " totems!");
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final PlayerDeathEvent event) {
        final Notification notification = (Notification)CandyMod.m_module.getModuleWithClass((Class)Notification.class);
        if (notification.death.getValue()) {
            final EntityPlayer player = event.player;
            if (player == null) {
                return;
            }
            final int pop = this.getPop(player.getName());
            if (pop == 0) {
                this.showNotification(ChatFormatting.RED + player.getName() + " dead!");
            }
            else {
                this.showNotification(ChatFormatting.RED + player.getName() + " dead after " + pop + " pop!");
            }
            if (this.popCounter.containsKey(player.getName())) {
                this.popCounter.remove(player.getName());
            }
        }
    }
    
    public int countPop(final String name) {
        if (!this.popCounter.containsKey(name)) {
            this.popCounter.put(name, 1);
            return 1;
        }
        this.popCounter.replace(name, this.popCounter.get(name) + 1);
        return this.popCounter.get(name);
    }
    
    public int getPop(final String name) {
        if (!this.popCounter.containsKey(name)) {
            return 0;
        }
        return this.popCounter.get(name);
    }
    
    public void onRender2D() {
        try {
            if (NotificationManager.mc.player == null) {
                return;
            }
            for (final Notif notification : this.notifs) {
                this.updateResolution();
                final String msg = notification.msg;
                final int width = CandyMod.m_font.getWidth(msg);
                RenderUtil.drawRect(this.scaledWidth - width - 26 + notification.offsetX, notification._y - 21.0f, (float)(width + 27), (float)(CandyMod.m_font.getHeight() + 12), ColorUtil.toRGBA(new Color(35, 35, 35, 255)));
                RenderUtil.drawRect(this.scaledWidth - width - 25 + notification.offsetX, notification._y - 20.0f, (float)(width + 25), (float)(CandyMod.m_font.getHeight() + 10), ColorUtil.toRGBA(new Color(45, 45, 45, 255)));
                RenderUtil.drawRect(this.scaledWidth - width - 26 + notification.offsetX, notification._y - 20.0f + CandyMod.m_font.getHeight() + 10.0f, (width + 26) * ((notification.max - notification.ticks) / notification.max), 1.0f, ColorUtil.toRGBA(new Color(170, 170, 170, 255)));
                RenderUtil.drawString(msg, this.scaledWidth - width - 20 + notification.offsetX, notification._y - 10.0f - 3.0f, ColorUtil.toRGBA(255, 255, 255), false, 1.0f);
                if (notification.ticks <= 0.0f) {
                    final Notif notif = notification;
                    notif.offsetX += (500.0f - notification.offsetX) / 10.0f;
                }
                else {
                    final Notif notif2 = notification;
                    --notif2.ticks;
                    final Notif notif3 = notification;
                    notif3.offsetX += (0.0f - notification.offsetX) / 4.0f;
                    final Notif notif4 = notification;
                    notif4._y += (notification.y - notification._y) / 4.0f;
                }
            }
            this.notifs = this.notifs.stream().filter(n -> (n.offsetX < 450.0f || n.ticks != 0.0f) && n._y >= -100.0f).collect((Collector<? super Object, ?, List<Notif>>)Collectors.toList());
        }
        catch (Exception ex) {}
    }
    
    public void updateResolution() {
        this.scaledWidth = NotificationManager.mc.displayWidth;
        this.scaledHeight = NotificationManager.mc.displayHeight;
        this.scaleFactor = 1;
        final boolean flag = NotificationManager.mc.isUnicode();
        int i = NotificationManager.mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        final double scaledWidthD = this.scaledWidth / (double)this.scaleFactor;
        final double scaledHeightD = this.scaledHeight / (double)this.scaleFactor;
        this.scaledWidth = MathHelper.ceil(scaledWidthD);
        this.scaledHeight = MathHelper.ceil(scaledHeightD);
    }
    
    public class Notif
    {
        public String msg;
        public float offsetX;
        public float y;
        public float _y;
        public float ticks;
        public float max;
        
        public Notif(final String msg) {
            this.offsetX = 300.0f;
            this.y = 0.0f;
            this._y = 0.0f;
            this.ticks = 0.0f;
            this.max = 0.0f;
            this.msg = msg;
            int fps = Minecraft.getDebugFPS();
            if (fps == 0) {
                fps = 60;
            }
            final int seconds = ((Notification)CandyMod.m_module.getModuleWithClass((Class)Notification.class)).time.getValue();
            this.ticks = (float)(seconds * fps);
            this.max = (float)(seconds * fps);
        }
    }
}
