//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import org.lwjgl.opengl.*;
import cc.candy.candymod.managers.*;
import org.apache.logging.log4j.*;

@Mod(modid = "candyplus", name = "Candy+", version = "0.2.8")
public class CandyMod
{
    public static final String MODID = "candyplus";
    public static final String NAME = "Candy+";
    public static final String VERSION = "0.2.8";
    public static ModuleManager m_module;
    public static EventManager m_event;
    public static FontManager m_font;
    public static HoleManager m_hole;
    public static RpcManager m_rpc;
    public static RotateManager m_rotate;
    public static NotificationManager m_notif;
    private static Logger logger;
    private static boolean savedConfig;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        CandyMod.logger = event.getModLog();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Info("floppa is cute but jumin is gay - 2021");
        Info("loading Candy...");
        Display.setTitle("Candy+ 0.2.8");
        Info("1aaaaaaaaaa");
        CandyMod.m_event.load();
        Info("2aaaaaaaaaa");
        CandyMod.m_module.load();
        Info("3aaaaaaaa");
        CandyMod.m_font.load();
        Info("4aaaaaaaa");
        CandyMod.m_hole.load();
        CandyMod.m_rpc.load();
        CandyMod.m_rotate.load();
        Info("5aaaaaaaa");
        CandyMod.m_notif.load();
        Info("6aaaaaaaa");
        Info("loading configs...");
        ConfigManager.loadConfigs();
        Info("successfully load Candy!");
    }
    
    public static void unload() {
        if (!CandyMod.savedConfig) {
            Info("saving configs...");
            ConfigManager.saveConfigs();
            Info("successfully save configs!");
            CandyMod.savedConfig = true;
        }
    }
    
    public static void Info(final String msg) {
        if (CandyMod.logger == null) {
            return;
        }
        CandyMod.logger.info(msg);
    }
    
    public static void Log(final Level level, final String msg) {
        CandyMod.logger.log(level, msg);
    }
    
    static {
        CandyMod.m_module = new ModuleManager();
        CandyMod.m_event = new EventManager();
        CandyMod.m_font = new FontManager();
        CandyMod.m_hole = new HoleManager();
        CandyMod.m_rpc = new RpcManager();
        CandyMod.m_rotate = new RotateManager();
        CandyMod.m_notif = new NotificationManager();
        CandyMod.savedConfig = false;
    }
}
