package com.xushengling.myapplication.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.xushengling.myapplication.R
import com.xushengling.myapplication.view.MyInfoView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_title_bar.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(),View.OnClickListener{
    /**
     * 底部按钮栏
     */
    private lateinit var mButtonLayout: LinearLayout
    private var mMyInfoView:MyInfoView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initBottomBtn()
        setListener()
        setInitStarts()
    }

    private fun initBottomBtn() {
        mButtonLayout = findViewById(R.id.main_btn_bar)

    }

    private fun setInitStarts() {
        clearButtonImageState()
        setSelectedStatus(0)
        createView(0)
    }
    /**
     * 设置底部三个按钮的监听事件
     */
    private fun setListener() {
        for (i in 0 until mButtonLayout.childCount) {
            mButtonLayout.getChildAt(i).setOnClickListener(this)
        }
    }

    /**
     * 移除不需要视图
     */
    private fun removeAllView() {
        for (i in 0 until main_body.childCount) {
            main_body.getChildAt(i).visibility = View.GONE
        }
    }

    private fun initView() {
        mainTitleBar.setBackgroundColor(Color.parseColor("#30B4FF"))
        backTv.visibility = View.GONE
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.main_btn_bar_course_ll ->{
                clearButtonImageState()
                selectDisplayView(0)
            }
            R.id.main_btn_bar_exercises_ll ->{
                clearButtonImageState()
                selectDisplayView(1)
            }
            R.id.main_btn_bar_my_ll ->{
                clearButtonImageState()
                selectDisplayView(2)
            }
        }
    }

    /**
     * 显示对应的界面
     */
    private fun selectDisplayView(index: Int) {
        removeAllView()
        createView(index)
        setSelectedStatus(index)
    }

    /**
     * 清楚底部选中的按钮
     */
    private fun clearButtonImageState() {
        val color=getColor(R.color.colorText)
        main_btn_bar_course_tv.setTextColor(color)
        main_btn_bar_exercises_tv.setTextColor(color)
        main_btn_bar_my_tv.setTextColor(color)
        main_btn_bar_course_btn.setImageResource(R.drawable.main_course_icon)
        main_btn_bar_exercises_btn.setImageResource(R.drawable.main_exercises_icon)
        main_btn_bar_my_btn.setImageResource(R.drawable.main_my_icon)
        for (i in 0 until mButtonLayout.childCount) {
            mButtonLayout.getChildAt(i).isSelected = false
        }
    }


    private fun setSelectedStatus(index: Int) {
        when(index){
            0 ->{
                main_btn_bar_course_ll.isSelected = true
                main_btn_bar_course_btn.setImageResource(R.drawable.main_course_icon_selected)
                main_btn_bar_course_tv.setTextColor(Color.parseColor("#0097F7"))
                mainTitleBar.visibility=View.VISIBLE
                titleTv.text = "博学谷课程"
            }
            1 ->{
                main_btn_bar_exercises_ll.isSelected = true
                main_btn_bar_exercises_btn.setImageResource(R.drawable.main_exercises_icon_selected)
                main_btn_bar_exercises_tv.setTextColor(Color.parseColor("#0097F7"))
                mainTitleBar.visibility=View.VISIBLE
                titleTv.text = "博学谷习题"
            }
            2 ->{
                main_btn_bar_my_ll.isSelected = true
                main_btn_bar_my_btn.setImageResource(R.drawable.main_my_icon_selected)
                main_btn_bar_my_tv.setTextColor(Color.parseColor("#0097F7"))
                mainTitleBar.visibility=View.GONE
            }
        }
    }

    private fun createView(viewIndex: Int) {
        when(viewIndex){
            0 ->{
                //课程界面
            }
            1 ->{
                //习题界面
            }
            2 ->{
                //我的界面
                if (mMyInfoView == null) main_body.addView(MyInfoView(this).view)
                else mMyInfoView!!.view

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

