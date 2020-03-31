package com.xushengling.myapplication.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.xushengling.myapplication.R
import com.xushengling.myapplication.activity.LoginActivity
import com.xushengling.myapplication.utils.AnalysisUtils

/**
 *
 * @ProjectName:    GitBoXueGu
 * @Package:        com.xushengling.myapplication.view
 * @ClassName:      MyInfoView
 * @Author:         徐圣领
 * @CreateDate:     2020/3/27 21:34
 */
class MyInfoView(private var mContext: Activity) {
    private var userNameTV: TextView? = null
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mCurrentView: View? = null
    /**
     * 获取界面控件
     */
    @SuppressLint("InflateParams")
    private fun initView() {
        mCurrentView = mInflater.inflate(R.layout.main_view_myinfo, null)
        val headLl = mCurrentView!!.findViewById<LinearLayout>(R.id.ll_head)
        //        ImageView iv_head = mCurrentView.findViewById(R.id.iv_head_icon);
        val courseHistoryRl =
            mCurrentView!!.findViewById<RelativeLayout>(R.id.rl_course_history)
        val settingRl = mCurrentView!!.findViewById<RelativeLayout>(R.id.setting)
        userNameTV = mCurrentView!!.findViewById(R.id.tv_user_name)
        mCurrentView!!.visibility  = View.VISIBLE
        setLoginParams(readLoginStatus()) //设置登录时界面控件的状态
        headLl.setOnClickListener {
            Log.d("data","head")
            //判断是否已经登录
            if (!readLoginStatus()) {
                mContext.startActivityForResult(
                    Intent(
                        mContext,
                        LoginActivity::class.java
                    ), 1
                )
            }
        }
        courseHistoryRl.setOnClickListener {
            Log.d("data","courseHistoryRl")
            if (!readLoginStatus()) { //跳转到播放记录界面
                Toast.makeText(mContext, "您还没登录，请先登录", Toast.LENGTH_SHORT).show()
            }
        }
        settingRl.setOnClickListener {
            Log.d("data","settingRl")
            if (!readLoginStatus()) {
                    Toast.makeText(mContext, "您还没登录，请先登录", Toast.LENGTH_SHORT).show()
                }
        }
    }

    /**
     * 登录状态后设置我的界面
     */
    private fun setLoginParams(isLogin: Boolean) {
        if (isLogin) {
            Log.d("dame","登录后的字体")
            userNameTV!!.text = AnalysisUtils.readLoginUserName(mContext)
        } else {
            userNameTV!!.setText(R.string.tv_user_name)
        }
    }

    /**
     * 从 SharedPreferences 中读取登录状态
     */
    private fun readLoginStatus(): Boolean {
        val sp =
            mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        return sp.getBoolean("isLogin", false)
    }

    /**
     * 获取当前在导航栏上方对应的View
     */ //    /**
    val view: View?
        get() {
            if (mCurrentView == null) {
                initView()
            }
            return mCurrentView
        }

}