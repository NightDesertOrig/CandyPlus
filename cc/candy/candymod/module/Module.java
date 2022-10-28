//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module;

import cc.candy.candymod.utils.*;
import cc.candy.candymod.event.events.network.*;
import net.minecraftforge.common.*;
import cc.candy.candymod.event.events.player.*;
import net.minecraft.entity.player.*;
import com.mojang.realmsclient.gui.*;
import com.google.gson.*;
import cc.candy.candymod.setting.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import cc.candy.candymod.module.misc.*;
import java.util.*;
import cc.candy.candymod.module.render.*;
import cc.candy.candymod.*;
import cc.candy.candymod.managers.*;
import net.minecraft.util.text.*;
import java.util.regex.*;

public class Module implements Util
{
    public String name;
    public Categories category;
    public boolean hide;
    public boolean isEnable;
    public Bind key;
    public List<Setting> settings;
    
    public Module(final String name, final Categories category, final int defaultKey, final boolean hide, final boolean defaultStatus) {
        this.name = "";
        this.category = Categories.MISC;
        this.hide = false;
        this.isEnable = false;
        this.key = new Bind();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.category = category;
        this.setKey(defaultKey);
        this.hide = hide;
        this.isEnable = defaultStatus;
    }
    
    public Module(final String name, final Categories category, final boolean hide, final boolean defaultStatus) {
        this.name = "";
        this.category = Categories.MISC;
        this.hide = false;
        this.isEnable = false;
        this.key = new Bind();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.category = category;
        this.setKey(-1);
        this.hide = hide;
        this.isEnable = defaultStatus;
    }
    
    public void onUpdate() {
    }
    
    public void onTick() {
    }
    
    public void onConnect() {
    }
    
    public void onRender2D() {
    }
    
    public void onRender3D() {
    }
    
    public void onRender3D(final float ticks) {
    }
    
    public void onPacketSend(final PacketEvent.Send event) {
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public void onPlayerDeath(final PlayerDeathEvent event) {
    }
    
    public void onPacketReceive(final PacketEvent.Receive event) {
    }
    
    public void onTotemPop(final EntityPlayer entity) {
    }
    
    public boolean isEnable() {
        return this.isEnable;
    }
    
    public boolean toggle() {
        if (!this.isEnable) {
            this.enable();
        }
        else {
            this.disable();
        }
        return this.isEnable;
    }
    
    public void enable() {
        if (!this.isEnable) {
            this.SendMessage(this.name + " : " + ChatFormatting.GREEN + "Enabled");
            this.isEnable = true;
            this.onEnable();
        }
    }
    
    public void disable() {
        if (this.isEnable) {
            this.SendMessage(this.name + " : " + ChatFormatting.RED + "Disabled");
            this.isEnable = false;
            this.onDisable();
        }
    }
    
    public Setting register(final Setting setting) {
        this.settings.add(setting);
        return setting;
    }
    
    public boolean nullCheck() {
        return Module.mc.player == null || Module.mc.world == null;
    }
    
    public void setKey(final int Nkey) {
        this.key.setKey(Nkey);
    }
    
    public void saveConfig() throws IOException {
        final Gson gson = new Gson();
        final Map<String, Object> mappedSettings = new HashMap<String, Object>();
        for (final Setting s : this.settings) {
            if (s.value instanceof Enum) {
                mappedSettings.put(s.name, EnumConverter.currentEnum((Enum)s.value) + "N");
            }
            else if (s.value instanceof Color) {
                final Color color = (Color)s.value;
                mappedSettings.put(s.name, color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getAlpha() + "C");
            }
            else if (s.value instanceof Integer) {
                mappedSettings.put(s.name, s.value + "I");
            }
            else {
                mappedSettings.put(s.name, s.value);
            }
        }
        mappedSettings.put("ismoduleenabled", this.isEnable);
        mappedSettings.put("keybindcode", this.key.getKey());
        final String json = gson.toJson((Object)mappedSettings);
        final File config = new File("candy/" + this.category.name().toLowerCase() + "/" + this.name.toLowerCase() + ".json");
        final FileWriter writer = new FileWriter(config);
        writer.write(json);
        writer.close();
    }
    
    public void loadConfig() throws Exception {
        final Path path = Paths.get("candy/" + this.category.name().toLowerCase() + "/" + this.name.toLowerCase() + ".json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final String context = readAll(path);
        final Gson gson = new Gson();
        final Map<String, Object> mappedSettings = (Map<String, Object>)gson.fromJson(context, (Class)Map.class);
        mappedSettings.forEach((name, value) -> this.setConfig(name, value));
    }
    
    public void setConfig(final String name, final Object value) {
        if (Objects.equals(name, "ismoduleenabled")) {
            this.isEnable = (boolean)value;
            if (this.isEnable) {
                MinecraftForge.EVENT_BUS.register((Object)this);
                if (this instanceof DiscordRPC) {
                    this.onEnable();
                }
            }
            return;
        }
        if (Objects.equals(name, "keybindcode")) {
            this.key.setKey(((Double)value).intValue());
            return;
        }
        final List<Setting> settings = new ArrayList<Setting>(this.settings);
        for (int i = 0; i < settings.size(); ++i) {
            final Setting setting = settings.get(i);
            final String n = setting.name;
            if (Objects.equals(n, name)) {
                final char c = value.toString().charAt(value.toString().length() - 1);
                if (c == 'N') {
                    final String enumValue = value.toString().replace("N", "");
                    setting.setEnum(Integer.parseInt(enumValue));
                }
                else if (c == 'C') {
                    final String[] color = value.toString().replace("C", "").split(",");
                    setting.setValue(new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]), Integer.parseInt(color[3])));
                }
                else if (c == 'I') {
                    final String intValue = value.toString().replace("I", "");
                    setting.setValue(Double.valueOf(intValue).intValue());
                }
                else if (value instanceof Double) {
                    setting.setValue(((Double)value).floatValue());
                }
                else {
                    setting.setValue(value);
                }
            }
        }
        this.settings = new ArrayList<Setting>(settings);
    }
    
    public static String readAll(final Path path) throws IOException {
        return Files.lines(path).reduce("", (prev, line) -> prev + line + System.getProperty("line.separator"));
    }
    
    public void sendMessage(final String str) {
        final Notification notif = (Notification)CandyMod.m_module.getModuleWithClass((Class)Notification.class);
        if (notif.isEnable && notif.message.getValue()) {
            CandyMod.m_notif.showNotification(str);
        }
        else {
            Module.mc.player.sendMessage((ITextComponent)new Manager.ChatMessage(ChatFormatting.RED + "[" + "Candy+" + "] " + ChatFormatting.GRAY + str));
        }
    }
    
    private void SendMessage(final String str) {
        if (this.GUICheck()) {
            final Notification notif = (Notification)CandyMod.m_module.getModuleWithClass((Class)Notification.class);
            if (notif.isEnable && notif.togglE.getValue()) {
                CandyMod.m_notif.showNotification(str);
            }
            else {
                Module.mc.player.sendMessage((ITextComponent)new Manager.ChatMessage(ChatFormatting.RED + "[" + "Candy+" + "] " + ChatFormatting.GRAY + str));
            }
        }
    }
    
    private boolean GUICheck() {
        return !this.name.toLowerCase().equals("clickgui");
    }
    
    public enum Categories
    {
        COMBAT, 
        EXPLOIT, 
        MISC, 
        MOVEMENT, 
        RENDER, 
        HUB;
    }
    
    public static class ChatMessage extends TextComponentBase
    {
        private final String text;
        
        public ChatMessage(final String text) {
            final Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
            final Matcher matcher = pattern.matcher(text);
            final StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                final String replacement = matcher.group().substring(1);
                matcher.appendReplacement(stringBuffer, replacement);
            }
            matcher.appendTail(stringBuffer);
            this.text = stringBuffer.toString();
        }
        
        public String getUnformattedComponentText() {
            return this.text;
        }
        
        public ITextComponent createCopy() {
            return null;
        }
        
        public ITextComponent shallowCopy() {
            return (ITextComponent)new Manager.ChatMessage(this.text);
        }
    }
}
