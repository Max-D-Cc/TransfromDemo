package com.example.amsplugin

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TransformTimeMethodVisitor extends MethodVisitor{

    TransformTimeMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv)
    }

    @Override
    void visitCode() {
        super.visitCode()
        //方法前插入
        //val startTime = System.currentTimeMillis()
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,"java/lang/System","currentTimeMillis","()J",false)
        mv.visitVarInsn(Opcodes.LSTORE,1)
    }


    @Override
    void visitInsn(int opcode) {
        //方法结束处
        if (opcode == Opcodes.RETURN){
            //val endTime = System.currentTimeMillis()
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,"java/lang/System","currentTimeMillis","()J",false)
            mv.visitVarInsn(Opcodes.LSTORE,3)

            //Log.e("TAG" ,"执行setContent方式耗时：${endTime - startTime}")
            mv.visitLdcInsn("TAG")
            mv.visitTypeInsn(Opcodes.NEW,"java/lang/StringBuilder")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/StringBuilder","<init>","()V",false)
            mv.visitLdcInsn("\u6267\u884csetContent\u65b9\u5f0f\u8017\u65f6\uff1a")
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","append","(Ljava/lang/String;)Ljava/lang/StringBuilder;",false)
            mv.visitVarInsn(Opcodes.LLOAD,3)
            mv.visitVarInsn(Opcodes.LLOAD,1)
            mv.visitInsn(Opcodes.LSUB)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","append","(J)Ljava/lang/StringBuilder;",false)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","toString","()Ljava/lang/String;",false)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,"android/util/Log","e","(Ljava/lang/String;Ljava/lang/String;)I",false)
            mv.visitInsn(Opcodes.POP)

        }
        super.visitInsn(opcode)
    }
}