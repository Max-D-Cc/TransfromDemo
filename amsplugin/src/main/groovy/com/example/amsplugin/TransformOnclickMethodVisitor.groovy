package com.example.amsplugin

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TransformOnclickMethodVisitor extends MethodVisitor{

    TransformOnclickMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv)
    }

    private Label L1 = new Label()

    @Override
    void visitCode() {
        super.visitCode()
        //方法前插入
        mv.visitFieldInsn(Opcodes.GETSTATIC,"com/example/utils/FastClickUtils","Companion","Lcom/example/utils/FastClickUtils\$Companion;")
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"com/example/utils/FastClickUtils\$Companion","isFastClick","()Z",false)
        mv.visitJumpInsn(Opcodes.IFEQ,L1)
        mv.visitInsn(Opcodes.RETURN)
        mv.visitLabel(L1)
    }

    @Override
    void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN){
        }
        super.visitInsn(opcode)
    }

}