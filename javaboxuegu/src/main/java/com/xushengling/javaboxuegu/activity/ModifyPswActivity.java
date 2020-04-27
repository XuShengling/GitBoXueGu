package com.xushengling.javaboxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;
import com.xushengling.javaboxuegu.utils.AnalysisUtils;
import com.xushengling.javaboxuegu.utils.MD5Utils;

public class ModifyPswActivity extends BaseActivity {
    private EditText et_original_psw, et_new_psw, et_new_psw_again;
    private String originalPsw, newPsw, newPswAgain;
    private String userName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_psw;
    }

    @Override
    protected void initView() {
        userName = AnalysisUtils.readLoginUserName(this);
        TextView tv_main_title = findViewById(R.id.titleTv);
        tv_main_title.setText("修改密码");
        findViewById(R.id.backTv).setOnClickListener(i -> finish());
        et_original_psw = findViewById(R.id.et_original_psw);
        et_new_psw = findViewById(R.id.et_psw_again);
        et_new_psw_again = findViewById(R.id.et_new_psw_again);
        findViewById(R.id.btn_save).setOnClickListener(i -> {
            getEditString();
            if (TextUtils.isEmpty(originalPsw)) {
                Toast("请输入原始密码");
            } else if (!MD5Utils.md5(originalPsw).equals(readPaw())) {
                Toast("输入的密码与原始密码不一致");
            } else if (!MD5Utils.md5(newPsw).equals(readPaw())) {
                Toast("输入新密码与原始密码不能一致");
            } else if (TextUtils.isEmpty(newPsw)) {
                Toast("请输入新密码");
            } else if (TextUtils.isEmpty(newPswAgain)) {
                Toast("请再次输入新密码");
            } else if (!newPsw.equals(newPswAgain)) {
                Toast("两次输入密码不一致");
            } else {
                Toast("新密码设置成功");
                modifyPsw(newPsw);
                startActivity(new Intent(this, LoginActivity.class));
                SettingActivity.instance.finish();
                this.finish();
            }
        });

    }

    /**
     * 获取控件上的字符串
     */
    private void getEditString() {
        originalPsw = et_original_psw.getText().toString().trim();
        newPsw = et_new_psw.getText().toString().trim();
        newPswAgain = et_new_psw_again.getText().toString().trim();
    }

    /**
     * 修改登录成功时保存在 SharedPreference 中的密码
     */
    private void modifyPsw(String newPsw) {
        String md5Psw = MD5Utils.md5(newPsw);
        SharedPreferences.Editor editor = getSharedPreferences("LoginInfo", MODE_PRIVATE).edit();
        editor.putString(userName, md5Psw);
        editor.apply();
    }

    /**
     * 从 SharedPreference 中读取原始密码
     */
    private String readPaw() {
        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        return sp.getString(userName, "");
    }
}
