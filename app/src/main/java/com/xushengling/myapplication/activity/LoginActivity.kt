package com.xushengling.myapplication.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity
import com.xushengling.myapplication.utils.MD5Utils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.main_title_bar.*

class LoginActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        titleTv.text=getString(R.string.user_login)
        backTv.setOnClickListener { this.finish()}
        registerTv.setOnClickListener {
            startActivityForResult(Intent(this,
                RegisterActivity::class.java),1)
        }
        findPswTv.setOnClickListener {
            startActivity(Intent(this,FindPswActivity::class.java))
        }
        btn_login.setOnClickListener {
            val userName=et_user_name.text.toString().trim()
            val psw=et_psw.text.toString().trim()
            val md5= MD5Utils.md5(psw)
            val spPsw=getSharedPreferences("loginInfo", Context.MODE_PRIVATE).getString(userName,"")!!
            when{
                TextUtils.isEmpty(userName) -> {
                    toast(getString(R.string.userName))
                }
                TextUtils.isEmpty(psw) -> {
                    toast(getString(R.string.psw))
                }
                md5 == spPsw->{
                    toast(getString(R.string.spPsw))
                    startActivity(Intent(this,
                        MainActivity::class.java))
                    saveLoginStatus(true,userName)
                    setResult(Activity.RESULT_OK,Intent().putExtra("isLogin",true))
                    this.finish()
                }
                !TextUtils.isEmpty(spPsw)&& md5 != spPsw ->{
                    toast(getString(R.string.spPswPsw))
                }
                else ->{
                    toast(getString(R.string.noUser))
                }

            }
        }
    }
    private fun saveLoginStatus(states:Boolean,userName:String){
        val editor=getSharedPreferences("loginInfo", Context.MODE_PRIVATE).edit()
        editor.putBoolean("isLogin",states)
        editor.putString("loginUserName",userName)
        editor.apply()
        editor.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            val userName=data.getStringExtra("userName")
            if (!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName)
                et_user_name.setSelection(userName!!.length)
            }
        }
    }
}

