package com.xushengling.myapplication.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


/**
 *
 * @ProjectName:    GitBoXueGu
 * @Package:        com.xushengling.myapplication
 * @ClassName:      BaseAvtivity
 * @Author:         徐圣领
 * @CreateDate:     2020/4/6 17:53
 */
abstract class BaseActivity : AppCompatActivity() {
protected var mHandler=Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(getLayoutId())
        initView()
    }

    abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected fun toast( value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
            mHandler.removeCallbacksAndMessages(null)
    }
}