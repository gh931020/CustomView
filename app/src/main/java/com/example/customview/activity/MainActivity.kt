package com.example.customview.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.customview.R
import com.example.customview.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        Log.e("MainActivity", "getLayoutResId: " )
        return R.layout.activity_main
    }

    override fun initView() {
        mBtnClock.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) = when (v?.id) {
        R.id.mBtnClock -> {
//            startActivity(Intent(this,ClockActivity::class.java))
            ClockActivity.startActivity(this)
        }
        R.id.mBtnCircleProgress -> {

        }
        R.id.mBtnScore -> {
        }
        R.id.mBtnStock -> {
        }
        R.id.mBtnEqueen -> {
        }
        else -> {
        }
    }



}