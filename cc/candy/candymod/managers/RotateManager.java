//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.managers;

import net.minecraft.util.math.*;
import cc.candy.candymod.utils.*;
import net.minecraft.entity.*;

public class RotateManager extends Manager
{
    private float yaw;
    private float pitch;
    
    public void updateRotations() {
        this.yaw = RotateManager.mc.player.rotationYaw;
        this.pitch = RotateManager.mc.player.rotationPitch;
    }
    
    public void restoreRotations() {
        RotateManager.mc.player.rotationYaw = this.yaw;
        RotateManager.mc.player.rotationYawHead = this.yaw;
        RotateManager.mc.player.rotationPitch = this.pitch;
    }
    
    public void setPlayerRotations(final float yaw, final float pitch) {
        RotateManager.mc.player.rotationYaw = yaw;
        RotateManager.mc.player.rotationYawHead = yaw;
        RotateManager.mc.player.rotationPitch = pitch;
    }
    
    public void setPlayerYaw(final float yaw) {
        RotateManager.mc.player.rotationYaw = yaw;
        RotateManager.mc.player.rotationYawHead = yaw;
    }
    
    public void lookAtPos(final BlockPos pos) {
        final float[] angle = MathUtil.calcAngle(RotateManager.mc.player.getPositionEyes(RotateManager.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f)));
        this.setPlayerRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final Vec3d vec3d) {
        final float[] angle = MathUtil.calcAngle(RotateManager.mc.player.getPositionEyes(RotateManager.mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        this.setPlayerRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final double x, final double y, final double z) {
        final Vec3d vec3d = new Vec3d(x, y, z);
        this.lookAtVec3d(vec3d);
    }
    
    public void lookAtEntity(final Entity entity) {
        final float[] angle = MathUtil.calcAngle(RotateManager.mc.player.getPositionEyes(RotateManager.mc.getRenderPartialTicks()), entity.getPositionEyes(RotateManager.mc.getRenderPartialTicks()));
        this.setPlayerRotations(angle[0], angle[1]);
    }
    
    public void setPlayerPitch(final float pitch) {
        RotateManager.mc.player.rotationPitch = pitch;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
}
