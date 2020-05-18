package com.xushengling.myapplication.activity

import android.app.Activity
import android.graphics.Color
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.xushengling.myapplication.R
import com.xushengling.myapplication.base.BaseActivity
import kotlinx.android.synthetic.main.activity_change_user_info.*
import kotlinx.android.synthetic.main.main_title_bar.*

class ChangeUserInfoActivity : BaseActivity() {

    var title: String? = null
    var content: String? = null
    var flag:Int?=null

    override fun getLayoutId(): Int = R.layout.activity_change_user_info

    override fun initView() {
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        flag=intent.getIntExtra("flag",0)
        titleTv.text=title
        mainTitleBar.setBackgroundColor(Color.parseColor("#30B4FF"))
        backTv.setOnClickListener { finish() }
        tv_save.visibility=View.VISIBLE
        if (!TextUtils.isEmpty(content)){
            et_content.setText(content)
            et_content.setSelection(content!!.length)
        }
        contentListener()
        iv_delete.setOnClickListener { et_content.setText("") }
        tv_save.setOnClickListener {
            val etContent=et_content.text.toString().trim()
            when(flag){
                1 ->{
                    if (!TextUtils.isEmpty(etContent)){
                        intent.putExtra("nickName",etContent)
                        setResult(Activity.RESULT_OK,intent)
                        toast("保存成功")
                        this.finish()
                    }else{
                        toast("昵称不能为空")
                    }
                }
                2 ->{
                    if (!TextUtils.isEmpty(etContent)){
                        intent.putExtra("signature",etContent)
                        setResult(Activity.RESULT_OK,intent)
                        toast("保存成功")
                        this.finish()
                    }else{
                        toast("签名不能为空")
                    }
                }
            }
        }
    }

    private fun contentListener() {
        et_content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var editable=et_content.text
                val len=editable.length
                if (len>0) iv_delete.visibility=View.VISIBLE else iv_delete.visibility=View.GONE
                when (flag){
                    1 -> {
                        if (len>8){
                            var selEndIndex=Selection.getSelectionEnd(editable)
                            val str=editable.toString()
                            //截取新字符串
                            val newStr=str.subSequence(0,8)
                            et_content.setText(newStr)
                            editable=et_content.text
                            //新字符串的长度
                            val newLen=editable.length
                            //旧光标位置超过新字符串的长度
                            if (selEndIndex>newLen) selEndIndex=editable.length
                            //设置鑫光标所在的位置
                            Selection.setSelection(editable,selEndIndex)
                        }
                    }
                    2 ->{
                        if (len>16){
                            var selEndIndex=Selection.getSelectionEnd(editable)
                            val str=editable.toString()
                            //截取新字符串
                            val newStr=str.subSequence(0,16)
                            et_content.setText(newStr)
                            editable=et_content.text
                            //新字符串的长度
                            val newLen=editable.length
                            //旧字符串位置超过新字符串的长度
                            if (selEndIndex>newLen) selEndIndex=editable.length
                            Selection.setSelection(editable,selEndIndex)
                        }
                    }
                }
            }
        })
    }

}
