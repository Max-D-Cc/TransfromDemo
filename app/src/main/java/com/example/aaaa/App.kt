package com.example.aaaa

import android.app.Application

/**
 * author caiguoqing
 * date 2020/12/15 9:32 PM
 */

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object{
        lateinit var application: Application
    }
}