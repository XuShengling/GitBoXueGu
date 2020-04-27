package com.xushengling.myapplication.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo

import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity


import kotlinx.android.synthetic.main.activity_splash.*




class SplashActivity :BaseActivity(){

    override fun getLayoutId(): Int {
        return  R.layout.activity_splash
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val info: PackageInfo = packageManager.getPackageInfo(packageName, 0)
        versionTv.text = "V" + info.versionName
        mHandler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            this@SplashActivity.finish()
        },3000)

    }
}
