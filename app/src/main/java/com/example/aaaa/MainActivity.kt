package com.example.aaaa

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.utils.FastClickUtils

class MainActivity : AppCompatActivity() {

    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this


        replaceThread()
        click()
    }

    //方法计时
    public fun setContent(){
        val startTime = System.currentTimeMillis()
        val endTime = System.currentTimeMillis()
        Log.e("TAG" ,"执行setContent方式耗时：${endTime - startTime}")
    }

    //点击事件
    fun click(){
        val bt = findViewById<TextView>(R.id.mainBt);
        bt.setOnClickListener {
            //此处if分支为即将入的代码
            tryCatch()
            //doSomething
            Log.e("TAG","拦截成功")
        }
    }

    //捕获异常
    fun tryCatch(){
        //异常代码
        val number = 10/0
    }

    //替换Thread
    private fun replaceThread(){
        Thread{
        }.start()
    }



}
