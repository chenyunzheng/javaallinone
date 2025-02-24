package com.chris.allinone.solution.dynamicload.plugindemo.asm;

import org.springframework.asm.ClassVisitor;

/**
 * @author chrischen
 * @date 2025/2/22
 * @desc TODO描述主要功能
 */
public class PluginClassVisitor extends ClassVisitor {

    public PluginClassVisitor(int api) {
        super(api);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }
}
