package com.xushengling.javaboxuegu.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.activity.LoginActivity;
import com.xushengling.javaboxuegu.activity.SettingActivity;
import com.xushengling.javaboxuegu.utils.AnalysisUtils;

/**
 * @ProjectName: GitBoXueGu
 * @Package: com.xushengling.javaboxuegu.view
 * @ClassName: MyInfoView
 * @Author: 徐圣领
 * @CreateDate: 2020/3/25 20:36
 */
public class MyInfoView {
    private TextView tv_user_name;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;

    public MyInfoView(Activity content) {
        mContext = content;
        //为之后将 Layout 转化为 view 时用
        mInflater = LayoutInflater.from(mContext) ;
    }

    private void createView() {
        initView();
    }

    /**
     * 获取界面控件
     */
    @SuppressLint("InflateParams")
    private void initView() {
        //设置布局文件
        mCurrentView = mInflater.inflate(R.layout.main_view_myinfo, null);
        LinearLayout ll_head = mCurrentView.findViewById(R.id.ll_head);
//        ImageView iv_head = mCurrentView.findViewById(R.id.iv_head_icon);
        RelativeLayout rl_course_history = mCurrentView.findViewById(R.id.rl_course_history);
        RelativeLayout rl_setting = mCurrentView.findViewById(R.id.setting);
        tv_user_name = mCurrentView.findViewById(R.id.tv_user_name);
        mCurrentView.setVisibility(View.VISIBLE);
        setLoginParams(readLoginStatus()); //设置登录时界面控件的状态
        ll_head.setOnClickListener(i -> {
            //判断是否已经登录
            if (!readLoginStatus()) {
                //已登录状态

                //未登录状态
                mContext.startActivityForResult(new Intent(mContext, LoginActivity.class),1);
            }
        });
        rl_course_history.setOnClickListener(i -> {
            if (!readLoginStatus()) {
                //跳转到播放记录界面

                Toast.makeText(mContext, "您还没登录，请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        rl_setting.setOnClickListener(i -> {
            if (readLoginStatus()) mContext.startActivityForResult(new Intent(mContext, SettingActivity.class),1);
            else Toast.makeText(mContext, "您还没登录，请先登录", Toast.LENGTH_SHORT).show();

        });
    }

    /**
     * 登录状态后设置我的界面
     */
    private void setLoginParams(boolean isLogin) {
        if (isLogin) {
            tv_user_name.setText(AnalysisUtils.readLoginUserName(mContext));
        } else {
            tv_user_name.setText(R.string.tv_user_name);
        }
    }

    /**
     * 从 SharedPreferences 中读取登录状态
     */
    private boolean readLoginStatus() {
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin", false);
    }

    /**
     * 获取当前在导航栏上方对应的View
     */
    public View getView() {
        if (mCurrentView == null) {
            createView();
        }
        return mCurrentView;
    }


    /**
     * 显示当前导航栏上方对应的 view 界面
     */
    public void showView(){
        if (mCurrentView == null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}
