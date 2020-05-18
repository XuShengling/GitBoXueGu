package com.xushengling.javaboxuegu.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;
import com.xushengling.javaboxuegu.bean.UserBean;
import com.xushengling.javaboxuegu.utils.AnalysisUtils;
import com.xushengling.javaboxuegu.utils.DBUtils;


public class UserInfoActivity extends BaseActivity {
    private static final int CHANGE_NICKNAME = 1;
    private static final int CHANGE_SIGNATURE = 2;
    private String spUserName;
    private TextView tv_nickName, tv_signature, tv_user_name, tv_sex;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        spUserName = AnalysisUtils.readLoginUserName(this);
        $(R.id.backTv).setOnClickListener(i -> finish());
        TextView title = findViewById(R.id.titleTv);
        title.setText("个人资料");
        $(R.id.mainTitleBar).setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_nickName = $(R.id.tv_nickName);
        tv_signature = $(R.id.tv_signature);
        tv_user_name = $(R.id.tv_user_name);
        tv_sex = $(R.id.tv_sex);
        initDate();
        setListener();
    }

    private void setListener() {
        $(R.id.rl_nickName).setOnClickListener(i -> {
            //昵称的单击事件
            String name = tv_nickName.getText().toString().trim();//获取昵称空间上的数据
            Bundle bdName = new Bundle();
            bdName.putString("content", name);
            bdName.putString("title", "昵称");
            bdName.putInt("flag", 1);
            enterActivityForResult(ChangeUserInfoActivity.class, CHANGE_NICKNAME, bdName);
        });
        $(R.id.rl_sex).setOnClickListener(i -> {
            //性别的单击事件
            sexDialog(tv_sex.getText().toString());
        });
        $(R.id.rl_signature).setOnClickListener(i -> {
            //签名的单击事件
            String signature = tv_signature.getText().toString().trim();
            Bundle bdSignature = new Bundle();
            bdSignature.putString("content", signature);
            bdSignature.putString("title", "签名");
            bdSignature.putInt("flag", 2);
            enterActivityForResult(ChangeUserInfoActivity.class, CHANGE_NICKNAME, bdSignature);
        });
    }

    /**
     * 弹出性别对话框
     */
    private void sexDialog(String sex) {
        int sexFlag;
        if ("男".equals(sex)) {
            sexFlag = 0;
        } else {
            sexFlag = 1;
        }
        final String[] items = {"男", "女"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("性别：");
        builder.setSingleChoiceItems(items, sexFlag, (dialog, which) -> {
            dialog.dismiss();
            Toast(items[which]);
            setSex(items[which]);
        });

    }

    private void setSex(String sex) {
        tv_sex.setText(sex);
        //更新数据库中的性别字段
        DBUtils.getInstance(this).updateUserInfo("sex", sex, spUserName);
    }

    /**
     * 获取数据
     */
    private void initDate() {
        UserBean bean;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        //首先判断一下数据库是否有数据
        if (bean == null) {
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = "问答精灵";
            bean.sex = "男";
            bean.signature = "问答精灵";
            //保存用户信息到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }

    /**
     * 为界面控件设置值
     */
    private void setValue(UserBean bean) {
        tv_nickName.setText(bean.nickName);
        tv_user_name.setText(bean.userName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
    }

    /**
     * 获取回传数据时需要使用的跳转方法
     * 第一个参数 to 表示需要跳转的界面
     * 第二个参数 requestCode 表示一个请求码
     * 第三个参数 b 表示跳转时传递的参数
     */
    public void enterActivityForResult(Class<?> to, int requestCode, Bundle b) {
        startActivityForResult(new Intent(this, to).putExtras(b), requestCode);
    }
    /**
     * 回传数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //最新数据
        String new_info;
        switch (requestCode) {
            case CHANGE_NICKNAME:
                if (data != null) {
                    new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)) return;
                    tv_nickName.setText(new_info);
                    DBUtils.getInstance(this).updateUserInfo("nickName", new_info, spUserName);
                }
            case CHANGE_SIGNATURE:
                if (data != null) {
                    new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)) return;
                    tv_nickName.setText(new_info);
                    DBUtils.getInstance(this).updateUserInfo("signature", new_info, spUserName);
                }
        }
    }

}