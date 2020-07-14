package com.example.customview.activity

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.View
import com.example.customview.R
import com.example.customview.base.BaseActivity
import kotlinx.android.synthetic.main.activity_clock.*

/**
 * author: zhang
 * created on: 2020/7/14 10:32
 * description:
 */
class ClockActivity:BaseActivity() {
    val handler = Handler()
    private val runnable = object : Runnable{
        override fun run() {
            mClockView.invalidate()
            handler.postDelayed(this,1000)
        }
    }
    override fun initData() {
    }

    override fun initView() {
        handler.postDelayed(runnable,1000)
    }

    override fun setListener() {
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_clock
    }

    override fun onClick(v: View?) {
    }

    companion object {
        fun startActivity(packageContext: Context){
            val intent = Intent(packageContext, ClockActivity::class.java)
            packageContext.startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

}