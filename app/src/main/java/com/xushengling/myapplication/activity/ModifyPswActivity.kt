package com.xushengling.myapplication.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity
import com.xushengling.myapplication.utils.AnalysisUtils
import com.xushengling.myapplication.utils.MD5Utils
import kotlinx.android.synthetic.main.activity_modify_psw.*
import kotlinx.android.synthetic.main.main_title_bar.*

class ModifyPswActivity : BaseActivity() {
    var mUserName: String? = null
    private lateinit var originalPsw: String
    private lateinit var newPsw: String
    private lateinit var newPswAgain: String
    override fun getLayoutId(): Int {
        return R.layout.activity_modify_psw
    }

    override fun initView() {
        mUserName = AnalysisUtils.readLoginUserName(this)
        titleTv.text = "修改密码"
        backTv.setOnClickListener { this.finish() }
        btn_save.setOnClickListener {
            originalPsw = et_original_psw.text.toString().trim()
            newPsw = et_psw_again.text.toString().trim()
            newPswAgain = et_new_psw_again.text.toString().trim()
            when {
                TextUtils.isEmpty(originalPsw) -> {
                    toast("请输入原始密码")
                }
                MD5Utils.md5(originalPsw) != readPaw() -> {
                    toast("输入的密码与原始密码不一致")
                }
                MD5Utils.md5(newPsw) == readPaw() -> {
                    toast("输入新密码与原始密码不能一致")
                }
                TextUtils.isEmpty(newPsw) -> {
                    toast("请输入新密码")
                }
                TextUtils.isEmpty(newPswAgain) -> {
                    toast("请再次输入新密码")
                }
                newPsw != newPswAgain -> {
                    toast("两次输入密码不一致")
                }
                else -> {
                    toast("新密码设置成功")
                    modifyPsw(newPsw)
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )
                    SettingActivity.instance!!.finish()
                    finish()
                }
            }
        }
    }


    private fun modifyPsw(newPsw: String) {
        val md5Psw = MD5Utils.md5(newPsw)
        val editor = getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit()
        editor.putString(mUserName, md5Psw)
        editor.apply()
        editor.commit()
    }

    private fun readPaw(): String? {
        return getSharedPreferences("loginInfo", Context.MODE_PRIVATE).getString(mUserName, "")
    }

}
