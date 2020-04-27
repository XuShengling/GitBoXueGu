package com.xushengling.myapplication.fragment

import android.content.Context
import android.content.Intent
import com.xushengling.myapplication.R
import com.xushengling.myapplication.activity.LoginActivity
import com.xushengling.myapplication.activity.SettingActivity
import com.xushengling.myapplication.base.BaseFragment
import com.xushengling.myapplication.utils.AnalysisUtils
import kotlinx.android.synthetic.main.my_fragment.*

/**
 *
 * @ProjectName:    GitBoXueGu
 * @Package:        com.xushengling.myapplication.fragment
 * @ClassName:      MyFragment
 * @Author:         徐圣领
 * @CreateDate:     2020/4/7 下午 9:08
 */
class MyFragment :BaseFragment(){
    override fun initView() {
        setLoginParams(readLoginStatus())
        ll_head.setOnClickListener {
            if(readLoginStatus()){
                //跳转至个人资料
            }else{
                startActivity(Intent(activity,LoginActivity::class.java))
            }
        }
        rl_course_history.setOnClickListener {
            if (!readLoginStatus()){

//            }else{
                toast("您还没登录，请先登录")
            }
        }
        setting.setOnClickListener {
            if (readLoginStatus()){
                startActivity(Intent(activity,SettingActivity::class.java))
            }else{
                toast("您还没登陆，请先登录")
            }
        }

    }

    private fun setLoginParams(isLogin: Boolean) {
        if (isLogin){
            tv_user_name.text = activity?.let { AnalysisUtils.readLoginUserName(it) }
        }else{
            tv_user_name.text = "点击登录"
        }
    }

    private fun readLoginStatus(): Boolean {
        val sp=activity?.getSharedPreferences("loginInfo",Context.MODE_PRIVATE)
        return sp!!.getBoolean("isLogin",false)
    }

    override fun setFragment(): Int {
    return R.layout.my_fragment
    }

    override fun onResume() {
        super.onResume()
        setLoginParams(readLoginStatus())
    }
}