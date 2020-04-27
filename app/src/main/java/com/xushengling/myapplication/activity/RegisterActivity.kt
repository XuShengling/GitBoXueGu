package com.xushengling.myapplication.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.text.TextUtils
import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity
import com.xushengling.myapplication.utils.MD5Utils

import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.main_title_bar.*

class RegisterActivity :BaseActivity(){

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        titleTv.text=getString(R.string.user_login)
        mainTitleBar.setBackgroundColor(Color.TRANSPARENT)
        backTv.setOnClickListener { this.finish() }
        registerBtn.setOnClickListener {
            val userName=userNameEt.text.toString().trim()
            val userPsw=pswEt.text.toString().trim()
            val userPswAgain=pswAgainEt.text.toString().trim()
            when {
                TextUtils.isEmpty(userName) -> {
                    toast("请输入用户名")
                }
                TextUtils.isEmpty(userPsw) -> {
                    toast("请输入密码")
                }
                TextUtils.isEmpty(userPswAgain) -> {
                    toast("请再次输入密码")
                }
                userPsw != userPswAgain -> {
                    toast("输入两次密码不一致")
                }
                isExistUserName(userName) -> {
                    toast("此用户已存在")
                }
                else -> {
                    toast("注册成功")
                    saveRegisterInfo(userName,userPsw)
                    setResult(Activity.RESULT_OK, Intent().putExtra("userName",userName))
                    this.finish()
                }
            }
        }

    }

    private fun isExistUserName(userName:String):Boolean{
        var hasUserName=false
        val sp:SharedPreferences=getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val spPsw=sp.getString(userName,"")
        if (!TextUtils.isEmpty(spPsw)){
            hasUserName=true
        }
        return hasUserName
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveRegisterInfo(userName:String, psw:String){
        val md5= MD5Utils.md5(psw)
        val sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val editor=sp.edit()
        editor.putString(userName,md5)
        editor.apply()
        editor.commit()
    }
}