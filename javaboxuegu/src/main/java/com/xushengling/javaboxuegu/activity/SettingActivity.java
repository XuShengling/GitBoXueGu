package com.xushengling.javaboxuegu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;

public class SettingActivity extends BaseActivity {
    @SuppressLint("StaticFieldLeak")
    public static SettingActivity instance=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    /**
     * 清除 SharedPreferences 中的登录状态和登陆时的用户名
     */

    private void clearLoginStatus(){
        SharedPreferences sp=getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.apply();
    }

    @Override
    public void initView() {
        TextView tv_main_title = findViewById(R.id.titleTv);
        tv_main_title.setText(R.string.setting);
        findViewById(R.id.mainTitleBar).setBackgroundColor(Color.parseColor("#30B4FF"));
        findViewById(R.id.backTv).setOnClickListener(i ->this.finish());
        //修改密码
        findViewById(R.id.rl_modify_psw).setOnClickListener(i ->{
            //跳转到修改密码界面
            startActivity(new Intent(this,ModifyPswActivity.class));
        });
        //设置密保
        findViewById(R.id.rl_security_setting).setOnClickListener(i -> startActivity(new Intent(this,FindPswActivity.class).putExtra("From","security")));

        findViewById(R.id.rl_exit_login).setOnClickListener(i ->{
            Toast("退出登录");
            clearLoginStatus();
            setResult(RESULT_OK,new Intent().putExtra("isLogin",false));
            this.finish();
        });
    }
}

