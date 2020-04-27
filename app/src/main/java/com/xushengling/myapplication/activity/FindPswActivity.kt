package com.xushengling.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.View
import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity
import com.xushengling.myapplication.utils.AnalysisUtils
import com.xushengling.myapplication.utils.MD5Utils
import kotlinx.android.synthetic.main.activity_find_psw.*
import kotlinx.android.synthetic.main.main_title_bar.*

class FindPswActivity : BaseActivity() {
    private var from: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_find_psw
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        from = intent.getStringExtra("from")
        backTv.setOnClickListener { finish() }
        if ("security" == from) {
            titleTv.text = "设置密保"
        } else {
            titleTv.text = "找回密码"
            tv_user_name.visibility = View.VISIBLE
            et_user_name.visibility = View.VISIBLE
        }
        btn_validate.setOnClickListener {
            val validateName = et_validate_name.text.toString().trim()
            if ("security" == from) {
                if (TextUtils.isEmpty(validateName)) {
                    toast("请输入要验证的姓名")
                } else {
                    toast("密保设置成功")
                    saveSecurity(validateName)
                    finish()
                }
            } else {
                val userName = et_user_name.text.toString().trimStart()
                val securitySp = readSecurity(validateName)
                if (TextUtils.isEmpty(userName)) {
                    toast("请输入用户名")
                } else if (!isExistUserName(userName)) {
                    toast("您输入的用户名不存在")
                } else if (TextUtils.isEmpty(validateName)) {
                    toast("请输入要验证的姓名")
                } else if (validateName != securitySp) {
                    toast("输入的密保不正确")
                } else {
                    tv_validate_psw.visibility = View.VISIBLE
                    tv_validate_psw.text = "初始密码：123456"
                    savePsw(userName)
                }
            }
        }
    }

    /**
     * 保存初始化的密码
     */
    private fun savePsw(userName: String) {
        val md5Psw = MD5Utils.md5("123456")
        val editor = getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit()
        editor.putString(userName, md5Psw)
        editor.apply()
        editor.commit()
    }

    /**
     * 保存密保到 SharedPreferences 中
     */
    private fun saveSecurity(validateName: String) {
        val editor = getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit()
        editor.putString(AnalysisUtils.readLoginUserName(this) + "_security", validateName)
        editor.apply()
        editor.commit()
    }

    /**
     * 从 SharedPreferences 中读取密保
     */
    private fun readSecurity(userName: String): String? {
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        return sp.getString(userName + "_security", "")
    }

    /**
     * 从 SharedPreferences
     */
    private fun isExistUserName(userName: String): Boolean {
        var hasUserName = false
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val spPsw = sp.getString(userName, "")
        if (!TextUtils.isEmpty(spPsw)) {
            hasUserName = true
        }
        return hasUserName
    }
}
