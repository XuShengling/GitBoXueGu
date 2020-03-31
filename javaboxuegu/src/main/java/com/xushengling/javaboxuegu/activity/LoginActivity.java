package com.xushengling.javaboxuegu.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.xushengling.javaboxuegu.utils.MD5Utils;
import com.xushengling.javaboxuegu.R;

public class LoginActivity extends AppCompatActivity {
    private String userName, psw, spPsw;
    private EditText mUserNameEt, mUserPswEt;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        TextView mMainTitleTv = findViewById(R.id.titleTv);
        mMainTitleTv.setText(R.string.user_login);
        TextView backTv = findViewById(R.id.backTv);
        backTv.setOnClickListener(i -> this.finish());
        TextView register = findViewById(R.id.registerTv);
        register.setOnClickListener(i -> startActivityForResult(new Intent(this, RegisterActivity.class), 1));
        //注册  findPswTv
        TextView loginBtn = findViewById(R.id.btn_login);
        mUserNameEt=findViewById(R.id.et_user_name);
        mUserPswEt=findViewById(R.id.et_psw);
        register.setOnClickListener(i ->startActivityForResult(new Intent(this,RegisterActivity.class),1));
        loginBtn.setOnClickListener(i ->{
            userName=mUserNameEt.getText().toString().trim();
            psw=mUserPswEt.getText().toString().trim();
            String md5= MD5Utils.md5(psw);
            spPsw=getSharedPreferences("loginInfo",MODE_PRIVATE).getString(userName,"");
            if (TextUtils.isEmpty(userName)){
                toast(getString(R.string.userName));
            }else if (TextUtils.isEmpty(psw)){
                toast(getString(R.string.psw));
            }else if(md5.equals(spPsw)){
                toast(getString(R.string.spPsw));
                saveLoginStatus(true,userName);
                startActivity(new Intent(this, MainActivity.class));
                setResult(RESULT_OK,new Intent().putExtra("isLogin",true));
                this.finish();
            } else if (!TextUtils.isEmpty(spPsw)&&!md5.equals(spPsw)){
                toast(getString(R.string.spPswPsw));
            }else {
                toast(getString(R.string.noUser));
            }
        });
    }
    private void saveLoginStatus(boolean status,String userName){
        SharedPreferences.Editor editor=getSharedPreferences("loginInfo",MODE_PRIVATE).edit();
        editor.putBoolean("isLogin",status);
        editor.putString("loginUserName",userName);
        editor.apply();
        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String userName=data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)){
                mUserNameEt.setText(userName);
                mUserNameEt.setSelection(userName.length());
            }
        }
    }

    private void toast(String value){
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
}
