package com.example.amsplugin

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TransformTryCatchMethodVisitor extends MethodVisitor{

    private def from = new Label(),to = new Label(),end = new Label()

    TransformTryCatchMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv)
    }

    @Override
    void visitCode() {
        super.visitCode()
        //全局加tryCatch  try{ 部分
        mv.visitTryCatchBlock(from,to,end,"java/lang/Exception")
        mv.visitLabel(from)
        mv.visitInsn(Opcodes.NOP)

    }


    @Override
    void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN){
            // } catch(e : Exception) { e.printStackTrace() } 部分
            mv.visitLabel(to)
            def label = new Label()
            mv.visitJumpInsn(Opcodes.GOTO,label)
            visitLabel(end)
            mv.visitVarInsn(Opcodes.ASTORE,1)
            mv.visitVarInsn(Opcodes.ALOAD,1)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/Exception","printStackTrace","()V",false)
            mv.visitLabel(label)
        }
        super.visitInsn(opcode)
    }
}