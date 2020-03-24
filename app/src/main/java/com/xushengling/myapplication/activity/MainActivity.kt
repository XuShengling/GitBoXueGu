package com.xushengling.myapplication.activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.xushengling.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_title_bar.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        setSelectedStatus(1)
        mainTitleBar.setBackgroundColor(Color.parseColor("#30B4FF"))
        backTv.visibility = View.GONE
        initBtnListener()
        clearButtonImageState()
        setSelectedStatus(1)
    }

    private fun initBtnListener() {
        /**
         * 课程
         */
        main_btn_bar_course_ll.setOnClickListener {
            clearButtonImageState()
            setSelectedStatus(1)
        }
        /**
         * 习题
         */
        main_btn_bar_exercises_ll.setOnClickListener {
            clearButtonImageState()
            setSelectedStatus(2)
        }
        /**
         * 我
         */
        main_btn_bar_my_ll.setOnClickListener {
            clearButtonImageState()
            setSelectedStatus(3)
        }
    }
    private fun clearButtonImageState() {
        main_btn_bar_course_tv.setTextColor(Color.parseColor("#666666"))
        main_btn_bar_exercises_tv.setTextColor(Color.parseColor("#666666"))
        main_btn_bar_my_tv.setTextColor(Color.parseColor("#666666"))
        main_btn_bar_course_btn.setImageResource(R.drawable.main_course_icon)
        main_btn_bar_exercises_btn.setImageResource(R.drawable.main_exercises_icon)
        main_btn_bar_my_btn.setImageResource(R.drawable.main_my_icon)
    }

    /**
     * 对应的界面
     */
    private fun setSelectedStatus(index:Int){
        when (index) {
            1 -> {
                main_btn_bar_course_btn.setImageResource(R.drawable.main_course_icon_selected)
                main_btn_bar_course_tv.setTextColor(Color.parseColor("#0097F7"))
                titleTv.text = getText(R.string.main_activity)
            }
            2 -> {
                main_btn_bar_exercises_btn.setImageResource(R.drawable.main_exercises_icon_selected)
                main_btn_bar_exercises_tv.setTextColor(Color.parseColor("#0097F7"))
                titleTv.text = getText(R.string.main_exercises_tv)
            }
            3 -> {
                main_btn_bar_my_btn.setImageResource(R.drawable.main_my_icon_selected)
                main_btn_bar_my_tv.setTextColor(Color.parseColor("#0097F7"))
                backTv.visibility=View.GONE
            }
        }

    }
    private var exitTime:Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000){
                toast("再次一次退出博学谷")
                exitTime=System.currentTimeMillis()
            }else{
                this.finish()
                if (readLoginStatus()){
                    clearLoginStatus()
                }
                exitProcess(0)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 获取 SharedPreference 中的登录情况
     */
    private fun readLoginStatus():Boolean {
        return  getSharedPreferences("loginInfo", Context.MODE_PRIVATE).getBoolean("isLogin",false)
    }

    /**
     * 清除 SharedPreference 中的登陆状态
     */
    private fun clearLoginStatus(){
        val sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        val editor=sp.edit()
        editor.putBoolean("isLogin",false)
        editor.putString("loginUserName","")
        editor.apply()
        editor.commit()
    }
    private fun toast(value:String){
        Toast.makeText(this,value,LENGTH_SHORT).show()
    }
}
