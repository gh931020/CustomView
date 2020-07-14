package com.example.customview.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * author: zhang
 * created on: 2020/7/14 9:58
 * description:
 */
abstract class BaseActivity : AppCompatActivity() ,View.OnClickListener{
//    此处不要override两参的方法,会造成启动app显示空白的问题
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("BaseActivity", "onCreate: ")
        setContentView( getLayoutResId())
        initView()
        setListener()
        initData()
    }

    @LayoutRes
    abstract fun getLayoutResId():Int

    abstract fun initView()

    abstract fun setListener()

    abstract fun initData()
}