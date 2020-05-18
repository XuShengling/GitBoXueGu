package com.xushengling.myapplication.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity
import com.xushengling.myapplication.bean.UserBean
import com.xushengling.myapplication.utils.AnalysisUtils
import com.xushengling.myapplication.utils.DBUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.main_title_bar.*

class UserInfoActivity : BaseActivity() {

    private val changeNickName=1
    private val changeSignature=2
    private var spUserName: String? = null
    private fun enterActivityForResult(to:Class<*>,requestCode:Int,b:Bundle){
        startActivityForResult(Intent(this,to).putExtras(b),requestCode)
    }
    override fun getLayoutId(): Int = R.layout.activity_user_info

    override fun initView() {
        spUserName=AnalysisUtils.readLoginUserName(this)
        mainTitleBar.setBackgroundColor(Color.parseColor("#30B4FF"))
        titleTv.text = "个人资料"
        initDate()
        setListener()
    }

    private fun setListener() {
        backTv.setOnClickListener { finish() }
        rl_nickName.setOnClickListener {
            val bdName=Bundle()
            bdName.putString("content", tv_nickName.text.toString().trim())
            bdName.putString("title", "昵称")
            bdName.putInt("flag", 1)
            enterActivityForResult(ChangeUserInfoActivity::class.java,changeNickName,bdName)
        }
        rl_signature.setOnClickListener {
            val bdSignature = Bundle()
            bdSignature.putString("content", tv_signature.text.toString().trim())
            bdSignature.putString("title", "签名")
            bdSignature.putInt("flag", 2)
            enterActivityForResult(ChangeUserInfoActivity::class.java,changeSignature,bdSignature)
        }
        rl_sex.setOnClickListener {
            val sex=tv_sex.text.toString().trim()
            sexDialog(sex)
        }
    }

    private fun sexDialog(sex: String) {
        val sexFlag = if ("男"==sex) 0 else 1
        val items = arrayOf("男", "女")
        val builder=AlertDialog.Builder(this)
        builder.setTitle("性别")
        builder.setSingleChoiceItems(items, sexFlag) { dialog, which->
            dialog.dismiss()
            toast(items[which])
            setSex(items[which])
        }
    }

    private fun setSex(sex: String) {
        tv_sex.text=sex
        DBUtils.getInstance(this).updateUserInfo("sex",sex,spUserName)
    }

    private fun initDate() {
        var bean: UserBean?
         bean =DBUtils.getInstance(this).getUserInfo(spUserName)
        if (bean == null) {
            bean =UserBean()
            bean.userName = spUserName!!
            bean.nickName = "问答精灵"
            bean.sex = "男"
            bean.signature = "问答精灵"
            DBUtils.getInstance(this).saveUserInfo(bean)
        }
        setValue(bean)
    }

    private fun setValue(bean: UserBean?) {
        tv_nickName?.text = bean!!.nickName
        tv_user_name?.text = bean.userName
        tv_sex?.text = bean.sex
        tv_signature?.text = bean.signature
    }
    private var newInfo: String? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            changeNickName ->
                if (data != null) {
                    newInfo = data.getStringExtra("nickName")
                    if (TextUtils.isEmpty(newInfo)) return
                    tv_nickName.text = newInfo
                    DBUtils.getInstance(this)
                        .updateUserInfo("nickName", newInfo!!, spUserName)
                }

            changeSignature ->  if (data != null) {
               newInfo = data.getStringExtra("signature")
               if (TextUtils.isEmpty(newInfo)) return
               tv_signature.text=newInfo
               DBUtils.getInstance(this).updateUserInfo("signature", newInfo!!, spUserName)
           }
        }
    }
}
