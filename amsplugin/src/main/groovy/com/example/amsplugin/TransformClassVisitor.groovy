package com.example.amsplugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class TransformClassVisitor extends ClassVisitor{

    private String className

    TransformClassVisitor(int api,ClassVisitor classVisitor) {
        super(api, classVisitor)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor vm = cv.visitMethod(access, name, descriptor, signature, exceptions)
        println "visitor className: " + className
        println "visitor method className : " + name
        println "descriptor: " + descriptor
        println "access: " + access

        //限定条件 只给真正的view的onClick事件添加快速点击
//        if ("onClick" == name && descriptor == "(Landroid/view/View;)V"){
//            println "the method find"
////            return new TransformMethodVisitor(Opcodes.ASM7,vm)
//            return new TransformOnclickMethodVisitor(Opcodes.ASM7,vm)
//        }

        //给setContent添加耗时统计
//        if ("setContent" == name){
//            return new TransformTimeMethodVisitor(Opcodes.ASM7,vm)
//        }

        //给trycatch方法添加trycatch
//        if ("tryCatch" == name){
//            return new TransformTryCatchMethodVisitor(Opcodes.ASM7,vm)
//        }

        //替换Thread
        if ("replaceThread" == name){
            return new TransformThreadMethodVisitor(Opcodes.ASM7,vm)
        }
        return vm
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible)
    }

}


