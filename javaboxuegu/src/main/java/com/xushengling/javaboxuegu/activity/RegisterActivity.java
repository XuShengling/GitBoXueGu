package com.xushengling.javaboxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;
import com.xushengling.javaboxuegu.utils.MD5Utils;

public class RegisterActivity extends BaseActivity {
    private EditText mUserNameEt,mUserPswEt,mUserAgainEt;
    private String userName,psw,pswAgain;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        TextView mTitleTv = findViewById(R.id.titleTv);
        mTitleTv.setText(R.string.register_activity);
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
                Toast(getString(R.string.userName));
            }else if (TextUtils.isEmpty(psw)){
                Toast(getString(R.string.psw));
            }else if (TextUtils.isEmpty(pswAgain)){
                Toast(getString(R.string.spPswPsw));
            }else if (!psw.equals(pswAgain)){
                Toast(getString(R.string.spPswPsw));
            }else if (isExistUserName(userName)){
                Toast(getString(R.string.userNameRl));
            }else {
                Toast(getString(R.string.registerUser));
                saveRegisterInfo(userName,psw);
                setResult(RESULT_OK,new Intent().putExtra(getString(R.string.userNameLogin),userName));
                this.finish();
            }
        });
    }

    private void getEditString(){
        userName=mUserNameEt.getText().toString().trim();
        psw=mUserPswEt.getText().toString().trim();
        pswAgain=mUserAgainEt.getText().toString().trim();
    }
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return has_userName;
    }
    private void saveRegisterInfo(String userName,String psw){
        String md5= MD5Utils.md5(psw);
        SharedPreferences sp = getSharedPreferences("LoginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(userName,md5);
        editor.apply();
    }
}
