package com.example.amsplugin

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TransformThreadMethodVisitor extends MethodVisitor{

    private final static String THREAD = "java/lang/Thread"  //系统Thread的路径
    private final static String MY_THREAD = "com/example/aaaa/MyThread" //自定义Thread的路径

    TransformThreadMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv)
    }

    @Override
    void visitTypeInsn(int opcode, String type) {
        //替换NEW指令的Thread
        if (opcode == Opcodes.NEW && type == THREAD){
            mv.visitTypeInsn(Opcodes.NEW,MY_THREAD)
            return
        }
        super.visitTypeInsn(opcode, type)
    }

    @Override
    void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        //替换init的Thread
        if (owner == THREAD && name.equalsIgnoreCase("<init>")){
            mv.visitMethodInsn(opcode,MY_THREAD,name,descriptor,isInterface)
            return
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }

    @Override
    void visitCode() {
        super.visitCode()
    }


    @Override
    void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN){
        }
        super.visitInsn(opcode)
    }
}