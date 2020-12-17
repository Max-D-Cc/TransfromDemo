package com.example.utils

/**
 * author caiguoqing
 * date 2020/12/14 9:04 PM
 */

class FastClickUtils {


    companion object{
        private var lastClickTime = 0L
        private const val clickDistance = 500L

        @JvmStatic
        fun isFastClick(): Boolean{
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis - lastClickTime > clickDistance) {
                lastClickTime = currentTimeMillis
                return false
            }
            return true
        }
    }

}