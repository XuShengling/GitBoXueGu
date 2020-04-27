package com.xushengling.myapplication.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.main_title_bar.*

class SettingActivity : BaseActivity() {
    companion object {
        var instance: SettingActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        titleTv.text = getText(R.string.setting)
        mainTitleBar.setBackgroundColor(Color.parseColor("#30B4FF"))
        backTv.setOnClickListener { this.finish() }
        //修改密码
        modifyPswRl.setOnClickListener {
            startActivity(Intent(this, ModifyPswActivity::class.java))
        }
        //设置密保
        securitySettingRl.setOnClickListener {
            startActivity(Intent(this, FindPswActivity::class.java).putExtra("from", "security"))
        }
        exitLoginRl.setOnClickListener {
            toast("退出登录")
            clearLoginStatus()
            setResult(Activity.RESULT_OK, Intent().putExtra("isLogin", false))
            this.finish()
        }
    }

    private fun clearLoginStatus() {
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean("isLogin", false)
        editor.putString("loginUserName", "")
        editor.apply()
        editor.commit()
    }
}
