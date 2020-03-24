package com.xushengling.myapplication.utils

import android.content.Context

/**
 *
 * @ProjectName:    GitBoXueGu
 * @Package:        com.xushengling.myapplication.utils
 * @ClassName:      AnaltsisUtils
 * @Author:         徐圣领
 * @CreateDate:     2020/3/17 13:49
 */
object AnalysisUtils {
    /**
     * 从SharedPreferences中读取登录用户名
     */
    fun readLoginUserName(context: Context): String? {
        val sp=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE)
        return sp.getString("loginUserName","")
    }
}