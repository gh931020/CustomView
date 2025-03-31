package com.example.customview

import android.app.Application

/**
 * author: zhang
 * created on: 2020/7/14 9:57
 * description:
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // 测试修改文件是否会被加密
    }
}