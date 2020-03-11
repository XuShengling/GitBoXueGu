package com.xushengling.javaboxuegu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private String mUserName, mPsw, mSpPsw;
    private EditText mUserNameEt, mUserPswEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        TextView mMainTitleTv = findViewById(R.id.titleTv);
        mMainTitleTv.setText("登录");
        TextView backTv = findViewById(R.id.backTv);
        backTv.setOnClickListener(i -> this.finish());
        TextView register = findViewById(R.id.registerTv);
        register.setOnClickListener(i -> startActivityForResult(new Intent(this, RegisterActivity.class), 1));

        TextView loginBtn = findViewById(R.id.btn_login);
        mUserNameEt=findViewById(R.id.et_user_name);
        mUserPswEt=findViewById(R.id.et_psw);
        register.setOnClickListener(i ->startActivityForResult(new Intent(this,RegisterActivity.class),1));
        loginBtn.setOnClickListener(i ->{
            mUserName=mUserNameEt.getText().toString().trim();
            mPsw=mUserPswEt.getText().toString().trim();
            String md5=MD5Utils.md5(mPsw);
            mSpPsw=getSharedPreferences("loginInfo",MODE_PRIVATE).getString(mUserName,"");
            if (TextUtils.isEmpty(mUserName)){
                toast("请输入用户名");
            }else if (TextUtils.isEmpty(mPsw)){
                toast("请输入密码");
            }else if(md5.equals(mSpPsw)){
                toast("登陆成功");
                saveLoginStatus(true,mUserName);
                startActivity(new Intent(this,MainActivity.class));
                setResult(RESULT_OK,new Intent().putExtra("isLogin",true));
                this.finish();
            } else if (!TextUtils.isEmpty(mSpPsw)&&!md5.equals(mSpPsw)){
                toast("用户名密码不一致");
            }else {
                toast("此账户不存在");
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
