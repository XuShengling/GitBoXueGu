package com.xushengling.javaboxuegu.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;
import com.xushengling.javaboxuegu.utils.AnalysisUtils;
import com.xushengling.javaboxuegu.utils.MD5Utils;

public class FindPswActivity extends BaseActivity {
    private EditText etValidateName, et_user_name;
    private String From;
    private TextView tv_reset_psw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_psw;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        From = getIntent().getStringExtra("From");
        TextView tv_main_title = $(R.id.titleTv);
        TextView tv_back = $(R.id.backTv);
        etValidateName = $(R.id.et_validate_name);
        Button btn_validate = $(R.id.btn_validate);
        tv_reset_psw = $(R.id.tv_validate_psw);
        et_user_name = $(R.id.et_user_name);
        TextView tv_user_name = $(R.id.tv_user_name);
        if ("security".equals(From)) {
            tv_main_title.setText("设置密保");
        } else {
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }
        tv_back.setOnClickListener(i -> finish());
        btn_validate.setOnClickListener(i -> {
            String validateName = etValidateName.getText().toString().trim();
            if ("security".equals(From)) {
                if (TextUtils.isEmpty(validateName)) {
                    Toast("请输入要验证的姓名");
                } else {
                    Toast("密保设置成功");
                    saveSecurity(validateName);
                    finish();
                }
            } else {
                String userName = et_user_name.getText().toString().trim();
                String sp_security = readSecurity(validateName);
                if (TextUtils.isEmpty(userName)) {
                    Toast("请输入您的用户名");
                } else if (!isExistUserName(userName)) {
                    Toast("您输入的用户名不存在");
                } else if (TextUtils.isEmpty(validateName)) {
                    Toast("请输入要验证的姓名");
                }
                if (!validateName.equals(sp_security)) {
                    Toast("输入的密保不正确");
                } else {
                    tv_reset_psw.setVisibility(View.VISIBLE);
                    tv_reset_psw.setText("初始密码:123456");
                    savePsw(userName);
                }
            }
        });
    }

    /**
     * 保存初始化的密码
     */
    private void savePsw(String userName) {
        String md5Psw = MD5Utils.md5("123456");
        SharedPreferences.Editor editor = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
        editor.putString(userName, md5Psw);
        editor.apply();
    }

    /**
     * 保存密保到 SharedPreferences 中
     */
    private void saveSecurity(String validateName) {
        SharedPreferences.Editor editor = getSharedPreferences("loginInfo", MODE_PRIVATE).edit();
        editor.putString(AnalysisUtils.readLoginUserName(this) + "_security", validateName);
        editor.apply();
    }

    /**
     * 从 SharedPreferences 中读取密保
     */
    private String readSecurity(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName + "_security", "");
    }

    /**
     * 从 SharedPreferences
     */
    private boolean isExistUserName(String userName) {
        boolean hasUserName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");
        if (!TextUtils.isEmpty(spPsw)) {
            hasUserName = true;
        }
        return hasUserName;
    }
}
