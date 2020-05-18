package com.xushengling.javaboxuegu.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.activity.LoginActivity;
import com.xushengling.javaboxuegu.activity.SettingActivity;
import com.xushengling.javaboxuegu.activity.UserInfoActivity;
import com.xushengling.javaboxuegu.base.BaseFragment;
import com.xushengling.javaboxuegu.utils.AnalysisUtils;

import java.util.Objects;

/**
 * @ProjectName: GitBoXueGu
 * @Package: com.xushengling.javaboxuegu.fragment
 * @ClassName: MyFragment
 * @Author: 徐圣领
 * @CreateDate: 2020/4/7 下午 4:03
 */
public class MyFragment extends BaseFragment {
    private TextView tv_user_name;

    @Override
    protected int setFragment() {
        return R.layout.my_fragment;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        LinearLayout ll_head = $(R.id.ll_head);
        RelativeLayout rl_setting = $(R.id.setting);
        RelativeLayout rl_course_history = $(R.id.rl_course_history);
        tv_user_name=$(R.id.tv_user_name);
        setLoginParams(readLoginStatus());
        ll_head.setOnClickListener(i ->{
            if (readLoginStatus()) {
                //跳转至个人资料
                startActivity(new Intent(getActivity(),UserInfoActivity.class));
            }else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        rl_course_history.setOnClickListener(i ->{
            if (!readLoginStatus()){
                //跳转至课程观看历史
//            }else {
                Toast("您还没登录,请先登录");
            }
        });
        rl_setting.setOnClickListener(i ->{
            if (readLoginStatus()){
                startActivityForResult(new Intent(getActivity(),SettingActivity.class),1);
            }else {
                Toast("您还没登录,请先登录");
            }
        });
    }

    private void setLoginParams(boolean isLogin) {
        if (isLogin){
            tv_user_name.setText(AnalysisUtils.readLoginUserName(Objects.requireNonNull(getActivity())));
        }else {
            tv_user_name.setText("点击登录");
        }
    }

    private boolean readLoginStatus() {
        SharedPreferences sp= Objects.requireNonNull(getActivity()).getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin",false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setLoginParams(readLoginStatus());
    }
}
