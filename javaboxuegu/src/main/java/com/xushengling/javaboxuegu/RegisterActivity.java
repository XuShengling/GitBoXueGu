package com.xushengling.javaboxuegu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RegisterActivity extends AppCompatActivity {
    private EditText mUserNameEt,mUserPswEt,mUserAgainEt;
    private String mUserName,mUserPsw,mUserAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        TextView mTitleTv = findViewById(R.id.titleTv);
        mTitleTv.setText("注册");
        TextView mBackBtn = findViewById(R.id.backTv);
        TextView mRegisterBtn = findViewById(R.id.registerBtn);
        mUserNameEt=findViewById(R.id.userNameEt);
        mUserPswEt=findViewById(R.id.pswEt);
        mUserAgainEt=findViewById(R.id.pswAgainEt);
        ConstraintLayout mMainTitleBarCl = findViewById(R.id.mainTitleBar);
        mMainTitleBarCl.setBackgroundColor(Color.TRANSPARENT);
        mBackBtn.setOnClickListener(i ->this.finish());
        mRegisterBtn.setOnClickListener(i ->{
            getEditString();
            if (TextUtils.isEmpty(mUserName)){
                toast("请输入用户名");
            }else if (TextUtils.isEmpty(mUserPsw)){
                toast("请输入密码");
            }else if (TextUtils.isEmpty(mUserAgain)){
                toast("请再次输入密码");
            }else if (!mUserPsw.equals(mUserAgain)){
                toast("两次密码不一致");
            }if (isExistUserName(mUserName)){
              toast("此用户名已存在");
            } else {
                toast("注册成功");
                saveRegisterInfo(mUserName,mUserPsw);
                setResult(RESULT_OK,new Intent().putExtra("mUserName",mUserName));
                this.finish();
            }
        });
    }
    private void toast(String value){
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
    private void getEditString(){
        mUserName=mUserNameEt.getText().toString().trim();
        mUserPsw=mUserPswEt.getText().toString().trim();
        mUserAgain=mUserAgainEt.getText().toString().trim();
    }
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return has_userName;
    }
    private void saveRegisterInfo(String userName,String psw){
        String md5=MD5Utils.md5(psw);
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=sp.edit();
        editor.putString(userName,md5);
        editor.apply();
        editor.commit();
    }
}