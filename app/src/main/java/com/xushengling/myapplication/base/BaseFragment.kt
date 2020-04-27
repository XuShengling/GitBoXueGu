package com.xushengling.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment

/**
 *
 * @ProjectName:    GitBoXueGu
 * @Package:        com.xushengling.myapplication.base
 * @ClassName:      BaseFragment
 * @Author:         徐圣领
 * @CreateDate:     2020/4/7 下午 8:56
 */
abstract class BaseFragment :Fragment(){
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setFragment(),container,false)
    }

    abstract fun setFragment(): Int

    protected fun toast(value:String){
        Toast.makeText(activity,value,LENGTH_SHORT).show()
    }


}