//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.module.render;

import cc.candy.candymod.module.*;
import cc.candy.candymod.setting.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class Freecam extends Module
{
    public Setting<modes> mode;
    private Entity riding;
    private EntityOtherPlayerMP Camera;
    private Vec3d position;
    private float yaw;
    private float pitch;
    
    public Freecam() {
        super("FreeCam", Module.Categories.RENDER, false, false);
        this.mode = (Setting<modes>)this.register(new Setting("Mode", (T)modes.Camera));
    }
    
    public void onEnable() {
        super.onEnable();
        if (Freecam.mc.world == null) {
            return;
        }
        if (this.mode.getValue() == modes.Normal) {
            this.riding = null;
            if (Freecam.mc.player.getRidingEntity() != null) {
                this.riding = Freecam.mc.player.getRidingEntity();
                Freecam.mc.player.dismountRidingEntity();
            }
            (this.Camera = new EntityOtherPlayerMP((World)Freecam.mc.world, Freecam.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)Freecam.mc.player);
            this.Camera.prevRotationYaw = Freecam.mc.player.rotationYaw;
            this.Camera.rotationYawHead = Freecam.mc.player.rotationYawHead;
            this.Camera.inventory.copyInventory(Freecam.mc.player.inventory);
            Freecam.mc.world.addEntityToWorld(-69, (Entity)this.Camera);
            this.position = Freecam.mc.player.getPositionVector();
            this.yaw = Freecam.mc.player.rotationYaw;
            this.pitch = Freecam.mc.player.rotationPitch;
            Freecam.mc.player.noClip = true;
        }
        else {
            (this.Camera = new EntityOtherPlayerMP((World)Freecam.mc.world, Freecam.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)Freecam.mc.player);
            this.Camera.prevRotationYaw = Freecam.mc.player.rotationYaw;
            this.Camera.rotationYawHead = Freecam.mc.player.rotationYawHead;
            this.Camera.inventory.copyInventory(Freecam.mc.player.inventory);
            this.Camera.noClip = true;
            Freecam.mc.world.addEntityToWorld(-69, (Entity)this.Camera);
            Freecam.mc.setRenderViewEntity((Entity)this.Camera);
        }
    }
    
    public enum modes
    {
        Normal, 
        Camera;
    }
}
