//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.hud.modules;

import cc.candy.candymod.hud.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import cc.candy.candymod.utils.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;

public class PlayerList extends Hud
{
    public Setting<Integer> maxPlayers;
    public Setting<Boolean> health;
    public Setting<Boolean> distance;
    public Setting<Boolean> shadow;
    public Setting<Color> color;
    
    public PlayerList() {
        super("PlayerList", 50.0f, 50.0f);
        this.maxPlayers = (Setting<Integer>)this.register(new Setting("MaxPlayers", (T)5, (T)10, (T)3));
        this.health = (Setting<Boolean>)this.register(new Setting("Health", (T)true));
        this.distance = (Setting<Boolean>)this.register(new Setting("Distance", (T)true));
        this.shadow = (Setting<Boolean>)this.register(new Setting("Shadow", (T)false));
        this.color = (Setting<Color>)this.register(new Setting("Color", (T)new Color(255, 255, 255, 255)));
    }
    
    public void onRender() {
        try {
            final List<EntityPlayer> players = this.getPlayerList();
            float width = 0.0f;
            float height = 0.0f;
            for (final EntityPlayer player : players) {
                final int health = PlayerUtil.getHealth(player);
                final double distance = PlayerUtil.getDistance((Entity)player);
                String str = player.getName();
                if (this.health.getValue()) {
                    str = str + " " + this.getHealthColor(health) + health;
                }
                if (this.distance.getValue()) {
                    str = str + " " + this.getDistanceColor(distance) + (int)distance;
                }
                if (RenderUtil.getStringWidth(str, 1.0f) > width) {
                    width = RenderUtil.getStringWidth(str, 1.0f);
                }
                if (width < RenderUtil.getStringWidth(str, 1.0f)) {
                    width = RenderUtil.getStringWidth(str, 1.0f);
                }
                RenderUtil.drawString(str, this.x.getValue(), this.y.getValue() + height, ColorUtil.toRGBA(this.color.getValue()), this.shadow.getValue(), 1.0f);
                height += RenderUtil.getStringHeight(1.0f) + 4.0f;
            }
            this.width = width;
            this.height = height - RenderUtil.getStringHeight(1.0f) + 5.0f;
        }
        catch (Exception ex) {}
    }
    
    public ChatFormatting getDistanceColor(final double distance) {
        if (distance > 20.0) {
            return ChatFormatting.GREEN;
        }
        if (distance > 6.0) {
            return ChatFormatting.YELLOW;
        }
        return ChatFormatting.RED;
    }
    
    public ChatFormatting getHealthColor(final int health) {
        if (health > 23) {
            return ChatFormatting.GREEN;
        }
        if (health > 7) {
            return ChatFormatting.YELLOW;
        }
        return ChatFormatting.RED;
    }
    
    public List<EntityPlayer> getPlayerList() {
        final List<EntityPlayer> players = new ArrayList<EntityPlayer>();
        final List<EntityPlayer> _players = new ArrayList<EntityPlayer>(PlayerList.mc.world.playerEntities);
        _players.sort(new Comparator<EntityPlayer>() {
            @Override
            public int compare(final EntityPlayer o1, final EntityPlayer o2) {
                if (PlayerUtil.getDistance((Entity)o1) == PlayerUtil.getDistance((Entity)o2)) {
                    return 0;
                }
                return (PlayerUtil.getDistance((Entity)o1) < PlayerUtil.getDistance((Entity)o2)) ? -1 : 1;
            }
        });
        final Iterator<EntityPlayer> iterator = _players.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            players.add(iterator.next());
            if (count >= this.maxPlayers.getValue()) {
                break;
            }
            ++count;
        }
        return players;
    }
}
