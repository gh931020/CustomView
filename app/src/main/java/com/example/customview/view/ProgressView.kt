package com.example.customview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * author: zhang
 * created on: 2020/7/14 15:36
 * description:
 */
class ProgressView :View{
    constructor(context: Context):this(context,null)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs!!,0)
    constructor(context: Context,attrs: AttributeSet?,defStyleAttr:Int = 0):super(context,attrs,defStyleAttr){
        init(context,attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

    }
}