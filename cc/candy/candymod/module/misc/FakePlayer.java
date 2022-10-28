//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.client.entity.*;
import java.net.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.multiplayer.*;

public class FakePlayer extends Module
{
    public Setting<Boolean> copyInv;
    private final String name = "Hiyokomame844";
    private EntityOtherPlayerMP _fakePlayer;
    
    public static String getUuid(final String name) {
        final JsonParser parser = new JsonParser();
        final String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            final String UUIDJson = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            if (UUIDJson.isEmpty()) {
                return "invalid name";
            }
            final JsonObject UUIDObject = (JsonObject)parser.parse(UUIDJson);
            return reformatUuid(UUIDObject.get("id").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    private static String reformatUuid(final String uuid) {
        String longUuid = "";
        longUuid = longUuid + uuid.substring(1, 9) + "-";
        longUuid = longUuid + uuid.substring(9, 13) + "-";
        longUuid = longUuid + uuid.substring(13, 17) + "-";
        longUuid = longUuid + uuid.substring(17, 21) + "-";
        longUuid += uuid.substring(21, 33);
        return longUuid;
    }
    
    public FakePlayer() {
        super("FakePlayer", Categories.MISC, false, false);
        this.copyInv = (Setting<Boolean>)this.register(new Setting("CopyInv", (T)true));
    }
    
    @Override
    public void onEnable() {
        if (FakePlayer.mc.player == null) {
            this.disable();
            return;
        }
        this._fakePlayer = null;
        if (FakePlayer.mc.player != null) {
            final WorldClient world = FakePlayer.mc.world;
            final UUID fromString = UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03");
            this.getClass();
            (this._fakePlayer = new EntityOtherPlayerMP((World)world, new GameProfile(fromString, "Hiyokomame844"))).copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
            this._fakePlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
            if (this.copyInv.getValue()) {
                this._fakePlayer.inventory.copyInventory(FakePlayer.mc.player.inventory);
            }
            FakePlayer.mc.world.addEntityToWorld(-100, (Entity)this._fakePlayer);
        }
    }
    
    @Override
    public void onDisable() {
        if (FakePlayer.mc.world != null && FakePlayer.mc.player != null) {
            super.onDisable();
            try {
                FakePlayer.mc.world.removeEntity((Entity)this._fakePlayer);
            }
            catch (Exception ex) {}
        }
    }
}
