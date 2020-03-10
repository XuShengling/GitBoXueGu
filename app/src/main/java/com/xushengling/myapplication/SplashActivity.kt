package com.xushengling.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager


import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val info: PackageInfo = packageManager.getPackageInfo(packageName, 0)
        versionTv.text = "V" + info.versionName

        //设置三秒跳转
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this@SplashActivity.finish()
            }
        }
        timer.schedule(task,3000)
    }
}
