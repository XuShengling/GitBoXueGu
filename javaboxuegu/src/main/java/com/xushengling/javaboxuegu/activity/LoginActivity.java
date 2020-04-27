package com.xushengling.javaboxuegu.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;


import com.xushengling.javaboxuegu.base.BaseActivity;
import com.xushengling.javaboxuegu.utils.MD5Utils;
import com.xushengling.javaboxuegu.R;


public class LoginActivity extends BaseActivity {
    private String userName, psw, spPsw;
    private EditText et_user_name, et_psw;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        TextView mMainTitleTv = $(R.id.titleTv);
        mMainTitleTv.setText(R.string.user_login);
        TextView backTv = $(R.id.backTv);
        backTv.setOnClickListener(i -> this.finish());
        TextView register = $(R.id.registerTv);
        register.setOnClickListener(i -> startActivityForResult(new Intent(this, RegisterActivity.class), 1));
        TextView findPswTV=$(R.id.findPswTv);
        findPswTV.setOnClickListener(i->startActivity(new Intent(this,FindPswActivity.class)));
        TextView loginBtn = $(R.id.btn_login);
        et_user_name = $(R.id.et_user_name);
        et_psw = $(R.id.et_psw);
        register.setOnClickListener(i -> startActivityForResult(new Intent(this, RegisterActivity.class), 1));
        loginBtn.setOnClickListener(i -> {
            userName = et_user_name.getText().toString().trim();
            psw = et_psw.getText().toString().trim();
            String md5 = MD5Utils.md5(psw);
            spPsw = readPsw(userName);
            if (TextUtils.isEmpty(userName)) {
                Toast(getString(R.string.userName));
            } else if (TextUtils.isEmpty(psw)) {
                Toast(getString(R.string.psw));
            } else if (!TextUtils.isEmpty(spPsw) && !md5.equals(spPsw)) {
                Toast(getString(R.string.spPswPsw));
            }else if(spPsw==null){
              Toast("未注册");
            } else if (md5.equals(spPsw)) {
                Toast(getString(R.string.spPsw));
                saveLoginStatus(true, userName);
                startActivity(new Intent(this, MainActivity.class));
                setResult(RESULT_OK, new Intent().putExtra("isLogin", true));
                this.finish();
            }else {
                Toast(getString(R.string.noUser));
            }
        });
    }

    /**
     * 从 SharedPreferences 中根据用户名读取密码
     */
    private String readPsw(String userName) {
        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
            return  sp.getString(userName,"");
    }

    /**
     * 保存登陆状态
     */
    private void saveLoginStatus(boolean status, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences("LoginInfo", MODE_PRIVATE).edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", userName);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());
            }
        }
    }

}
