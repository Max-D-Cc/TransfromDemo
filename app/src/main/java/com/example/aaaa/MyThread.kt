package com.example.aaaa

/**
 * author caiguoqing
 * date 2020/12/16 4:09 PM
 */

class MyThread(runnable: Runnable) : Thread(runnable){

    override fun start() {
        println("成功替换")
        val stackTrace = Throwable().stackTrace
        stackTrace.forEach {
            println(it.className + "." + it.methodName + "(${it.fileName}:${it.lineNumber})")
        }
        super.start()
    }
}