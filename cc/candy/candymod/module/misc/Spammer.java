//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.misc;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import cc.candy.candymod.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Spammer extends Module
{
    public Setting<Float> delay;
    public Setting<type> spam;
    public Setting<Boolean> suffix;
    public Timer timer;
    public final String chinese = "\u8fd9\u662f\u4e00\u9996\u7231\u60c5\u4e4b\u6b4c\u5417\uff1f \u6216\u8005\u662f\u4e00\u9996\u5173\u4e8e\u68a6\u60f3\u7684\u6b4c\uff1f\u65e0\u8bba\u600e\u6837\uff0c\u4ed6\u4eec\u90fd\u5f88\u96be\u8ffd\u4e0a\u3002 \u800c\u6211\u4eec\u5219\u7ee7\u7eed\u5f98\u5f8a\u3002 \u68a6\u60f3\u603b\u662f\u5728\u540e\u9762\u3002 \u53ea\u6709\u5f53\u6211\u4eec\u8ffd\u4e0a\u4ed6\u4eec\u65f6\uff0c\u6211\u4eec\u624d\u80fd\u4ece\u6b63\u9762\u770b\u5230\u4ed6\u4eec\u3002 \u53ea\u6709\u5230\u90a3\u65f6\uff0c\u6211\u4eec\u624d\u610f\u8bc6\u5230\u8fd9\u5f20\u8138\u662f\u6211\u4eec\u81ea\u5df1\u7684\u3002 \"\u7eaf\u6d01\u5c31\u50cf\u73ab\u7470\uff0c\u7f8e\u4e3d\u800c\u523a\u773c\u3002 \u51c0\u5316\u4f60\u7684\u68a6\u60f3 ............. \u4f60\u7684\u8ffd\u6355\u884c\u52a8\u5c06\u6301\u7eed\u591a\u4e45\uff1f";
    public final String korean = "\ud480 \ucfe4 \uae30 \uc8fc \ucfc4 \ub450\ub974 \uace0 \uce5c \uba48\uce6b \ubd80\uc0c1 \ud751\uc778 \ud3f0 \ub378 \ub974 \uc751\uc6a9 \uc138 \uc6b0\uace0 \ud2b8 \ub8cc\ucf00 \uac00\uc2a4 \ub2e4\uc774\ub8e8 \uc9c4\uc758 \uc815 \ub9cc\ub370 \uc624\uc608 \ud06c\ub8e8 \uace0 \uac00\uc2a4 \ub208\ucfe0 \ubaa8\uc2a4 \ubb38 \uc1a1 \uc2b9\uae30 \uc870\uc544\uc138\u3002\uad6d\ub3c4 \ucd94\uc99d \ubc14\ub2e4\uc5d0\uc11c \uac00\uc2dc \uad11\uc120\uc774 \uc6c3\uc74c. \ube44\uc2a4\ub4ec\ud788 \ub4a4\ucabd\uc758 \uc5f4\ubcd1\uc740 \ud1f4\uace0\ub97c \uac70\ub4ed\ud55c\ub2e4.\n";
    public final String[] amongus;
    public int index;
    
    public Spammer() {
        super("Spammer", Categories.MISC, false, false);
        this.delay = (Setting<Float>)this.register(new Setting("Delay", (T)50.0f, (T)100.0f, (T)1.0f));
        this.spam = (Setting<type>)this.register(new Setting("Type", (T)type.chinese));
        this.suffix = (Setting<Boolean>)this.register(new Setting("Suffix", (T)true));
        this.timer = new Timer();
        this.amongus = new String[] { "\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u28c0\u28c0\u28d0\u2840\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u28a0\u2804\u28e0\u28f6\u28ff\u28ff\u28ff\u283f\u283f\u28db\u28c2\u28c0\u28c0\u2852\u2836\u28f6\u28e4\u28e4\u28ec\u28c0\u2840\u2804\u2880\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u2880\u28fe\u28ff\u28ff\u28ff\u285f\u28a1\u28be\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28f6\u28cc\u283b\u28ff\u28ff\u28ff\u28ff\u28f7\u28e6\u28c4\u2840\u2804\u2804\u2804\u2804\u2804\u2804\u2804\u28c8\u28c9\u285b\u28ff\u28ff\u28ff\u284c\u2887\u28bb\u28ff\u28ff\u28ff\u28ff\u28ff\u283f\u281b\u28e1\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28e6\u28c4\u2804\u2804\u2804\u2804\u283a\u281f\u28c9\u28f4\u287f\u281b\u28e9\u28fe\u28ce\u2833\u283f\u281b\u28cb\u28e9\u28f4\u28fe\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28c6\u2804\u2804\u2804\u2804\u2804\u2818\u288b\u28f4\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u2846\u2804\u2804\u2804\u2880\u2880\u28fe\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u2847\u2804\u2804\u2804\u2804\u28fe\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u2803\u28c0\u2804\u2804\u2804\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u287f\u2803\u2818\u281b\u2804\u2804\u2804\u28bb\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u281f\u280b\u28c0\u28c0\u28e0\u28e4\u2804\u2804\u28c0\u28c0\u2859\u283b\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u28ff\u283f\u289b\u28e9\u2824\u283e\u2804\u281b\u280b\u2809\u2889\u2804\u283a\u283f\u281b\u281b\u2803\u2804\u2809\u2819\u281b\u281b\u283b\u283f\u283f\u283f\u281f\u281b\u281b\u281b\u2809\u2801\u2804\u2804\u28c0\u28c0\u28e0\u28e4\u28e0\u28f4\u28f6\u28fc\u28ff" };
        this.index = 0;
    }
    
    @Override
    public void onEnable() {
        this.index = 0;
    }
    
    @Override
    public void onUpdate() {
        if (this.timer == null) {
            this.timer = new Timer();
        }
        if (this.timer.passedDms(this.delay.getValue())) {
            this.timer.reset();
            String msg = "";
            if (this.spam.getValue() == type.chinese) {
                msg += "\u8fd9\u662f\u4e00\u9996\u7231\u60c5\u4e4b\u6b4c\u5417\uff1f \u6216\u8005\u662f\u4e00\u9996\u5173\u4e8e\u68a6\u60f3\u7684\u6b4c\uff1f\u65e0\u8bba\u600e\u6837\uff0c\u4ed6\u4eec\u90fd\u5f88\u96be\u8ffd\u4e0a\u3002 \u800c\u6211\u4eec\u5219\u7ee7\u7eed\u5f98\u5f8a\u3002 \u68a6\u60f3\u603b\u662f\u5728\u540e\u9762\u3002 \u53ea\u6709\u5f53\u6211\u4eec\u8ffd\u4e0a\u4ed6\u4eec\u65f6\uff0c\u6211\u4eec\u624d\u80fd\u4ece\u6b63\u9762\u770b\u5230\u4ed6\u4eec\u3002 \u53ea\u6709\u5230\u90a3\u65f6\uff0c\u6211\u4eec\u624d\u610f\u8bc6\u5230\u8fd9\u5f20\u8138\u662f\u6211\u4eec\u81ea\u5df1\u7684\u3002 \"\u7eaf\u6d01\u5c31\u50cf\u73ab\u7470\uff0c\u7f8e\u4e3d\u800c\u523a\u773c\u3002 \u51c0\u5316\u4f60\u7684\u68a6\u60f3 ............. \u4f60\u7684\u8ffd\u6355\u884c\u52a8\u5c06\u6301\u7eed\u591a\u4e45\uff1f";
            }
            if (this.spam.getValue() == type.korean) {
                msg += "\ud480 \ucfe4 \uae30 \uc8fc \ucfc4 \ub450\ub974 \uace0 \uce5c \uba48\uce6b \ubd80\uc0c1 \ud751\uc778 \ud3f0 \ub378 \ub974 \uc751\uc6a9 \uc138 \uc6b0\uace0 \ud2b8 \ub8cc\ucf00 \uac00\uc2a4 \ub2e4\uc774\ub8e8 \uc9c4\uc758 \uc815 \ub9cc\ub370 \uc624\uc608 \ud06c\ub8e8 \uace0 \uac00\uc2a4 \ub208\ucfe0 \ubaa8\uc2a4 \ubb38 \uc1a1 \uc2b9\uae30 \uc870\uc544\uc138\u3002\uad6d\ub3c4 \ucd94\uc99d \ubc14\ub2e4\uc5d0\uc11c \uac00\uc2dc \uad11\uc120\uc774 \uc6c3\uc74c. \ube44\uc2a4\ub4ec\ud788 \ub4a4\ucabd\uc758 \uc5f4\ubcd1\uc740 \ud1f4\uace0\ub97c \uac70\ub4ed\ud55c\ub2e4.\n";
            }
            if (this.spam.getValue() == type.amongus) {
                msg += this.amongus[this.index];
                ++this.index;
                if (this.amongus.length <= this.index) {
                    this.index = 0;
                }
            }
            if (this.suffix.getValue()) {
                msg += MathUtil.getRandom(1000, 10000);
            }
            Spammer.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(msg));
        }
    }
    
    public enum type
    {
        chinese, 
        korean, 
        amongus;
    }
}