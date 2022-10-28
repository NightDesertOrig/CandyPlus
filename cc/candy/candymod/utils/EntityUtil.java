//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.utils;

import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class EntityUtil implements Util
{
    public static BlockPos getFlooredPos(final Entity e) {
        return new BlockPos(Math.floor(e.posX), Math.floor(e.posY), Math.floor(e.posZ));
    }
    
    public static Entity getPredictedPosition(final Entity entity, final double x) {
        if (x == 0.0) {
            return entity;
        }
        EntityPlayer e = null;
        final double motionX = entity.posX - entity.lastTickPosX;
        double motionY = entity.posY - entity.lastTickPosY;
        final double motionZ = entity.posZ - entity.lastTickPosZ;
        boolean shouldPredict = false;
        boolean shouldStrafe = false;
        final double motion = Math.sqrt(Math.pow(motionX, 2.0) + Math.pow(motionZ, 2.0) + Math.pow(motionY, 2.0));
        if (motion > 0.1) {
            shouldPredict = true;
        }
        if (!shouldPredict) {
            return entity;
        }
        if (motion > 0.31) {
            shouldStrafe = true;
        }
        for (int i = 0; i < x; ++i) {
            if (e == null) {
                if (isOnGround(0.0, 0.0, 0.0, entity)) {
                    motionY = (shouldStrafe ? 0.4 : -0.07840015258789);
                }
                else {
                    motionY -= 0.08;
                    motionY *= 0.9800000190734863;
                }
                e = placeValue(motionX, motionY, motionZ, (EntityPlayer)entity);
            }
            else {
                if (isOnGround(0.0, 0.0, 0.0, (Entity)e)) {
                    motionY = (shouldStrafe ? 0.4 : -0.07840015258789);
                }
                else {
                    motionY -= 0.08;
                    motionY *= 0.9800000190734863;
                }
                e = placeValue(motionX, motionY, motionZ, e);
            }
        }
        return (Entity)e;
    }
    
    public static boolean isOnGround(final double x, double y, final double z, final Entity entity) {
        final double d3 = y;
        final List list1 = Minecraft.getMinecraft().world.getCollisionBoxes(entity, entity.getEntityBoundingBox().offset(x, y, z));
        if (y != 0.0) {
            for (int l = list1.size(), k = 0; k < l; ++k) {
                y = list1.get(k).calculateYOffset(entity.getEntityBoundingBox(), y);
            }
        }
        return d3 != y && d3 < 0.0;
    }
    
    public static EntityPlayer placeValue(double x, double y, double z, final EntityPlayer entity) {
        final List list1 = Minecraft.getMinecraft().world.getCollisionBoxes((Entity)entity, entity.getEntityBoundingBox().offset(x, y, z));
        if (y != 0.0) {
            for (int l = list1.size(), k = 0; k < l; ++k) {
                y = list1.get(k).calculateYOffset(entity.getEntityBoundingBox(), y);
            }
            if (y != 0.0) {
                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0.0, y, 0.0));
            }
        }
        if (x != 0.0) {
            for (int l2 = list1.size(), j5 = 0; j5 < l2; ++j5) {
                x = calculateXOffset(entity.getEntityBoundingBox(), x, list1.get(j5));
            }
            if (x != 0.0) {
                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(x, 0.0, 0.0));
            }
        }
        if (z != 0.0) {
            for (int i6 = list1.size(), k2 = 0; k2 < i6; ++k2) {
                z = calculateZOffset(entity.getEntityBoundingBox(), z, list1.get(k2));
            }
            if (z != 0.0) {
                entity.setEntityBoundingBox(entity.getEntityBoundingBox().offset(0.0, 0.0, z));
            }
        }
        return entity;
    }
    
    public static double calculateXOffset(final AxisAlignedBB other, double offsetX, final AxisAlignedBB this1) {
        if (other.maxY > this1.minY && other.minY < this1.maxY && other.maxZ > this1.minZ && other.minZ < this1.maxZ) {
            if (offsetX > 0.0 && other.maxX <= this1.minX) {
                final double d1 = this1.minX - other.maxX;
                if (d1 < offsetX) {
                    offsetX = d1;
                }
            }
            else {
                final double d2;
                if (offsetX < 0.0 && other.minX >= this1.maxX && (d2 = this1.maxX - other.minX) > offsetX) {
                    offsetX = d2;
                }
            }
        }
        return offsetX;
    }
    
    public static double calculateZOffset(final AxisAlignedBB other, double offsetZ, final AxisAlignedBB this1) {
        if (other.maxX > this1.minX && other.minX < this1.maxX && other.maxY > this1.minY && other.minY < this1.maxY) {
            if (offsetZ > 0.0 && other.maxZ <= this1.minZ) {
                final double d1 = this1.minZ - other.maxZ;
                if (d1 < offsetZ) {
                    offsetZ = d1;
                }
            }
            else {
                final double d2;
                if (offsetZ < 0.0 && other.minZ >= this1.maxZ && (d2 = this1.maxZ - other.minZ) > offsetZ) {
                    offsetZ = d2;
                }
            }
        }
        return offsetZ;
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
    
    public static boolean isPassive(final Entity e) {
        return (!(e instanceof EntityWolf) || !((EntityWolf)e).isAngry()) && (e instanceof EntityAmbientCreature || (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntitySquid) || (e instanceof EntityIronGolem && ((EntityIronGolem)e).getAttackTarget() == null));
    }
    
    public static void attackEntity(final Entity entity, final boolean packet) {
        if (packet) {
            EntityUtil.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
        }
        else {
            EntityUtil.mc.playerController.attackEntity((EntityPlayer)EntityUtil.mc.player, entity);
        }
    }
    
    public static float getHealth(final Entity entity) {
        if (entity.isEntityAlive()) {
            final EntityLivingBase livingBase = (EntityLivingBase)entity;
            return livingBase.getHealth() + livingBase.getAbsorptionAmount();
        }
        return 0.0f;
    }
    
    public static Block isColliding(final double posX, final double posY, final double posZ) {
        Block block = null;
        if (EntityUtil.mc.player != null) {
            final AxisAlignedBB bb = (EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(posX, posY, posZ) : EntityUtil.mc.player.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(posX, posY, posZ);
            final int y = (int)bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                    block = EntityUtil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                }
            }
        }
        return block;
    }
    
    public static boolean isInLiquid() {
        if (EntityUtil.mc.player == null) {
            return false;
        }
        if (EntityUtil.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        boolean inLiquid = false;
        final AxisAlignedBB bb = (EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().getEntityBoundingBox() : EntityUtil.mc.player.getEntityBoundingBox();
        final int y = (int)bb.minY;
        for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; ++x) {
            for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; ++z) {
                final Block block = EntityUtil.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (!(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static boolean isFullArmor(final EntityPlayer entity) {
        boolean fullArmor = true;
        int diamondItems = 0;
        boolean hasBlast = false;
        for (final ItemStack is : entity.getArmorInventoryList()) {
            if (is.isEmpty()) {
                fullArmor = false;
                break;
            }
            if (is.getItem() == Items.DIAMOND_HELMET) {
                ++diamondItems;
            }
            if (is.getItem() == Items.DIAMOND_CHESTPLATE) {
                ++diamondItems;
            }
            if (is.getItem() == Items.DIAMOND_LEGGINGS) {
                ++diamondItems;
            }
            if (is.getItem() == Items.DIAMOND_BOOTS) {
                ++diamondItems;
            }
            final NBTTagList enchants = is.getEnchantmentTagList();
            final List<String> enchantments = new ArrayList<String>();
            if (enchants != null) {
                for (int index = 0; index < enchants.tagCount(); ++index) {
                    final short id = enchants.getCompoundTagAt(index).getShort("id");
                    final short level = enchants.getCompoundTagAt(index).getShort("lvl");
                    final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                    if (enc != null) {
                        enchantments.add(enc.getTranslatedName((int)level));
                    }
                }
            }
            if (!enchantments.contains("Blast Protection IV")) {
                continue;
            }
            hasBlast = true;
        }
        return fullArmor && diamondItems == 4 && hasBlast;
    }
    
    public static boolean isInHole(final Entity entity) {
        return isBlockValid(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }
    
    public static boolean isBlockValid(final BlockPos blockPos) {
        return isBedrockHole(blockPos) || isObbyHole(blockPos) || isBothHole(blockPos);
    }
    
    public static boolean isObbyHole(final BlockPos blockPos) {
        final BlockPos[] array;
        final BlockPos[] touchingBlocks = array = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
        for (final BlockPos pos : array) {
            final IBlockState touchingState = EntityUtil.mc.world.getBlockState(pos);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBedrockHole(final BlockPos blockPos) {
        final BlockPos[] array;
        final BlockPos[] touchingBlocks = array = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
        for (final BlockPos pos : array) {
            final IBlockState touchingState = EntityUtil.mc.world.getBlockState(pos);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isBothHole(final BlockPos blockPos) {
        final BlockPos[] array;
        final BlockPos[] touchingBlocks = array = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
        for (final BlockPos pos : array) {
            final IBlockState touchingState = EntityUtil.mc.world.getBlockState(pos);
            if (touchingState.getBlock() == Blocks.AIR || (touchingState.getBlock() != Blocks.BEDROCK && touchingState.getBlock() != Blocks.OBSIDIAN)) {
                return false;
            }
        }
        return true;
    }
}
