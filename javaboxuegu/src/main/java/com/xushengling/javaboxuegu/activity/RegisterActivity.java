package com.xushengling.javaboxuegu.activity;

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

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    private EditText mUserNameEt,mUserPswEt,mUserAgainEt;
    private String userName,psw,pswAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        TextView mTitleTv = findViewById(R.id.titleTv);
        mTitleTv.setText(R.string.user_login);
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
            if (TextUtils.isEmpty(userName)){
                toast(getString(R.string.userName));
            }else if (TextUtils.isEmpty(psw)){
                toast(getString(R.string.psw));
            }else if (TextUtils.isEmpty(pswAgain)){
                toast(getString(R.string.spPswPsw));
            }else if (!psw.equals(pswAgain)){
                toast(getString(R.string.spPswPsw));
            }else if (isExistUserName(userName)){
              toast(getString(R.string.noUser));
            }else {
                toast(getString(R.string.registerUser));
                saveRegisterInfo(userName,psw);
                setResult(RESULT_OK,new Intent().putExtra(getString(R.string.userNameLogin),userName));
                this.finish();
            }
        });
    }
    private void toast(String value){
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
    private void getEditString(){
        userName=mUserNameEt.getText().toString().trim();
        psw=mUserPswEt.getText().toString().trim();
        pswAgain=mUserAgainEt.getText().toString().trim();
    }
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences(getString(R.string.loginInfo),MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return has_userName;
    }
    private void saveRegisterInfo(String userName,String psw){
        String md5= MD5Utils.md5(psw);
        SharedPreferences sp = getSharedPreferences(getString(R.string.loginInfo),MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor=sp.edit();
        editor.putString(userName,md5);
        editor.apply();
        editor.commit();
    }
}
