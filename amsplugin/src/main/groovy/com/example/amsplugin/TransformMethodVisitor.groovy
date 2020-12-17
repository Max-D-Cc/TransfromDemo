package com.example.amsplugin

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TransformMethodVisitor extends MethodVisitor{

    private def from = new Label(),to = new Label(),end = new Label()

    private final static String THREAD = "java/lang/Thread"
    private final static String MY_THREAD = "com/example/aaaa/MyThread"

    TransformMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv)
    }

    @Override
    void visitTypeInsn(int opcode, String type) {
        if (opcode == Opcodes.NEW && type == THREAD){
            mv.visitTypeInsn(Opcodes.NEW,MY_THREAD)
            return
        }
        super.visitTypeInsn(opcode, type)
    }

    @Override
    void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (owner == THREAD && name.equalsIgnoreCase("<init>")){
            mv.visitMethodInsn(opcode,MY_THREAD,name,descriptor,isInterface)
            return
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
    }

    @Override
    void visitCode() {
        super.visitCode()


//        mv.visitTryCatchBlock(from,to,end,"java/lang/Exception")
//        mv.visitLabel(from)
//        //全局加tryCatch
//        mv.visitInsn(Opcodes.NOP)

//        mv.visitVarInsn(Opcodes.BIPUSH,10)
//        mv.visitInsn(Opcodes.ICONST_0)
//        mv.visitInsn(Opcodes.IDIV)
//        mv.visitVarInsn(Opcodes.ISTORE,2)
//
//        mv.visitLabel(to)
//        def label = new Label()
//        mv.visitJumpInsn(Opcodes.GOTO,label)

        //全局onClick添加快速点击
//        mv.visitFieldInsn(Opcodes.GETSTATIC,"com/example/utils/FastClickUtils","Companion","Lcom/example/utils/FastClickUtils\$Companion;")
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"com/example/utils/FastClickUtils\$Companion","isFastClick","()Z",false)
//        def label = new Label()
//        mv.visitJumpInsn(Opcodes.IFEQ,label)
//        mv.visitVarInsn(Opcodes.ALOAD,0)
//        mv.visitFieldInsn(Opcodes.GETSTATIC,"com/example/aaaa/App","Companion","Lcom/example/aaaa/App\$Companion;")
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"com/example/aaaa/App\$Companion","getApplication","()Landroid/app/Application;",false)
//        mv.visitTypeInsn(Opcodes.CHECKCAST,"android/content/Context")
//        mv.visitLdcInsn("onClick")
//        mv.visitTypeInsn(Opcodes.CHECKCAST,"java/lang/CharSequence")
//        mv.visitInsn(Opcodes.ICONST_1)
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC,"android/widget/Toast","makeText","(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;",false)
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"android/widget/Toast","show","()V",false)


//        mv.visitLdcInsn("TAG")
//        mv.visitLdcInsn("\u5feb\u901f\u70b9\u51fb")
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC,"android/util/Log","e","(Ljava/lang/String;Ljava/lang/String;)I",false)
//        mv.visitInsn(Opcodes.POP)
//        mv.visitInsn(Opcodes.RETURN)
    }


    @Override
    void visitInsn(int opcode) {
        if (opcode == Opcodes.RETURN){
//            mv.visitLabel(to)
//            def label = new Label()
//            mv.visitJumpInsn(Opcodes.GOTO,label)
//            visitLabel(end)
//            mv.visitVarInsn(Opcodes.ASTORE,2)
//            mv.visitVarInsn(Opcodes.ALOAD,2)
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/Exception","printStackTrace","()V",false)
//            mv.visitLabel(label)


//            mv.visitMethodInsn(Opcodes.INVOKESTATIC,"java/lang/System","currentTimeMillis","()J",false)
//            mv.visitVarInsn(Opcodes.LSTORE,4)
//            mv.visitVarInsn(Opcodes.ALOAD,0)
//            mv.visitTypeInsn(Opcodes.CHECKCAST,"android/content/Context")
//            mv.visitTypeInsn(Opcodes.NEW,"java/lang/StringBuilder")
//            mv.visitInsn(Opcodes.DUP)
//            mv.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/StringBuilder","<init>","()V",false)
//            mv.visitLdcInsn("\u65b9\u6cd5\u8bbf\u95ee\u65f6\u95f4\uff1a")
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","append","(Ljava/lang/String;)Ljava/lang/StringBuilder;",false)
//            mv.visitVarInsn(Opcodes.LLOAD,4)
//            mv.visitVarInsn(Opcodes.LLOAD,2)
//            mv.visitInsn(Opcodes.LSUB)
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","append","(J)Ljava/lang/StringBuilder;",false)
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/lang/StringBuilder","toString","()Ljava/lang/String;",false)
//            mv.visitTypeInsn(Opcodes.CHECKCAST,"java/lang/CharSequence")
//            mv.visitInsn(Opcodes.ICONST_1)
//            mv.visitMethodInsn(Opcodes.INVOKESTATIC,"android/widget/Toast","makeText","(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;",false)
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"android/widget/Toast","show","()V",false)
        }
        super.visitInsn(opcode)
    }



}