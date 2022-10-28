//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package cc.candy.candymod.asm.impl;

import cc.candy.candymod.asm.api.*;
import java.util.*;
import cc.candy.candymod.utils.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class PatchEntityRenderer extends ClassPatch
{
    public PatchEntityRenderer() {
        super("net.minecraft.client.renderer.EntityRenderer", "buq");
    }
    
    public byte[] transform(final byte[] bytes) {
        final ClassNode classNode = new ClassNode();
        final ClassReader classReader = new ClassReader(bytes);
        classReader.accept((ClassVisitor)classNode, 0);
        final MappingName method = new MappingName("updateCameraAndRender", "a", "updateCameraAndRender");
        final String desc = "(FJ)V";
        for (final MethodNode it : classNode.methods) {
            if (method.equalName(it.name) && it.desc.equals(desc)) {
                this.patchUpdateCameraAndRender(it);
            }
        }
        final ClassWriter writer = new ClassWriter(0);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
    
    public void patchUpdateCameraAndRender(final MethodNode methodNode) {
        final AbstractInsnNode target = ASMUtil.findMethodInsn(methodNode, 182, "biq", "a", "(F)V");
        if (target != null) {
            final InsnList insnList = new InsnList();
            insnList.add((AbstractInsnNode)new VarInsnNode(23, 1));
            insnList.add((AbstractInsnNode)new MethodInsnNode(184, Type.getInternalName((Class)this.getClass()), "updateCameraAndRenderHook", "(F)V", false));
            methodNode.instructions.insert(target, insnList);
        }
    }
    
    public static void updateCameraAndRenderHook(final float partialTicks) {
    }
}
