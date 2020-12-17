package com.example.amsplugin

import com.android.build.api.artifact.OutputFileProvider
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.impldep.com.amazonaws.util.Md5Utils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class TransformPlugin extends Transform implements Plugin<Project>{

    @Override
    void apply(Project target) {
        println "---------TransformPlugin init ----------"
        def extension = target.extensions.getByType(AppExtension.class)
        extension.registerTransform(this)
    }

    @Override
    String getName() {
        return "AmsPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        def provider = transformInvocation.outputProvider
        def inputs = transformInvocation.inputs
        if (provider != null){
            provider.deleteAll()
        }
        inputs.each { it ->
            it.directoryInputs.each { directive ->
                handlerClass(directive,provider)
            }

            it.jarInputs.each { jarInput ->
                handlerJar(jarInput,provider)
            }
        }
    }


    private void handlerClass(DirectoryInput directoryInput, TransformOutputProvider outputFileProvider){
        if (directoryInput.file.isDirectory()){
            directoryInput.file.eachFileRecurse { file ->
                def fileName = file.name
                println "allName: $fileName"
                if (fileName.endsWith(".class")){
                    println "------------开始修改 MainActivity.class -------------"
                    ClassReader classReader = new ClassReader(file.bytes)
                    ClassWriter classWriter = new ClassWriter(classReader,ClassWriter.COMPUTE_MAXS)
                    ClassVisitor classVisitor = new TransformClassVisitor(Opcodes.ASM7,classWriter)
                    classReader.accept(classVisitor,ClassReader.EXPAND_FRAMES)
                    byte[] code = classWriter.toByteArray()
                    FileOutputStream fileOutputStream = new FileOutputStream(file.parentFile.absolutePath + File.separator + fileName)
                    fileOutputStream.write(code)
                    fileOutputStream.close()
                    println "------------开始完成 MainActivity.class -------------"
                }
            }
        }

        def dest = outputFileProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes, Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file,dest)
    }


    private void handlerJar(JarInput jarInput, TransformOutputProvider outputFileProvider){
        println "jarAbsolutePath: " + jarInput.file.absolutePath
        if (jarInput.file.absolutePath.endsWith(".jar")){
            def jarName = jarInput.name
            println "jarName: " + jarName
            def md5Name = DigestUtils.md5(jarInput.file.absolutePath)
            if (jarName.endsWith(".jar")){
                jarName = jarName.substring(0,jarName.length() - 4)
            }
            JarFile jarFile = new JarFile(jarInput.file)
            def entries = jarFile.entries()
            File temFile = new File(jarInput.file.getParent() + File.separator + "class_temp.jar")
            if (temFile.exists()){
                temFile.delete()
            }

            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(temFile))
            while (entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement()
                String entryName = jarEntry.getName()
                println "entryName:  $entryName"
                ZipEntry zipEntry = new ZipEntry(entryName)
                InputStream stream = jarFile.getInputStream(zipEntry)
                if (entryName == "MainActivity.class"){
                   jarOutputStream.putNextEntry(zipEntry)
                    ClassReader reader = new ClassReader(IOUtils.toByteArray(stream))
                    ClassWriter classWriter = w ClassWriter(reader,ClassWriter.COMPUTE_MAXS)
                    ClassVisitor classVisitor = new TransformClassVisitor(Opcodes.ASM7,classWriter)
                    reader.accept(classVisitor,ClassReader.EXPAND_FRAMES)
                    byte[] code = classWriter.toByteArray()
                    jarOutputStream.write(code)
                }else{
                    jarOutputStream.putNextEntry(zipEntry)
                    jarOutputStream.write(IOUtils.toByteArray(stream))
                }
                jarOutputStream.closeEntry()
            }

            jarOutputStream.close()
            jarFile.close()
            def dest = outputFileProvider.getContentLocation(jarName + md5Name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
            FileUtils.copyFile(temFile,dest)
            temFile.delete()
        }



    }
}