package com.xushengling.javaboxuegu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ProjectName: GitBoXueGu
 * @Package: com.xushengling.javaboxuegu.utils
 * @ClassName: AnalysisUtils
 * @Author: 徐圣领
 * @CreateDate: 2020/3/25 19:40
 */
public class AnalysisUtils {
    /**
     * 从 SharedPreferences 中读取登录用户名
     */
    public static String readLoginUserName(Context context){
        SharedPreferences sp=context.getSharedPreferences("LoginInfo",Context.MODE_PRIVATE);
        return sp.getString("loginUserName","");
    }
}
