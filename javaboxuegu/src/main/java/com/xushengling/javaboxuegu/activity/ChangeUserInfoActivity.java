package com.xushengling.javaboxuegu.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;


/**
 * @author 徐圣领
 */
public class ChangeUserInfoActivity extends BaseActivity {
    private int flag;
    private String title, content;
    private EditText et_content;
    private ImageView iv_delete;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_user_info;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        $(R.id.backTv).setOnClickListener(i -> finish());
        content = getIntent().getStringExtra("content");
        flag = getIntent().getIntExtra("flag", 0);
        TextView tvTitle = $(R.id.titleTv);
        tvTitle.setText(title);
        $(R.id.mainTitleBar).setBackgroundColor(Color.parseColor("#30B4FF"));
        $(R.id.tv_save).setVisibility(View.VISIBLE);
        et_content=$(R.id.et_content);
        iv_delete=$(R.id.iv_delete);
        if (!TextUtils.isEmpty(content)){
            et_content.setText(content);
            et_content.setSelection(content.length());
        }
        contentListener();
        $(R.id.tv_save).setOnClickListener(i->{
            String etContent=et_content.getText().toString().trim();
            Intent intent=new Intent();
            switch (flag){
                case 1:
                    if (!TextUtils.isEmpty(etContent)){
                        intent.putExtra("nickName",etContent);
                        Toast("设置成功");
                        this.finish();
                    }else {
                        Toast("昵称不能为空");
                    }
                case 2:
                    if (!TextUtils.isEmpty(etContent)){
                        intent.putExtra("signature",etContent);
                        Toast("保存成功");
                        this.finish();
                    }else {
                        Toast("签名不能为空");
                    }
            }
        });
    }
    /**
     * 监听个人资料修改界面输入的文字
     */
    private void contentListener(){
        et_content.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Editable editable=et_content.getText();
                int len=editable.length();
                if (len>0){
                    iv_delete.setVisibility(View.VISIBLE);
                }else {
                    iv_delete.setVisibility(View.GONE);
                }
                switch (flag){
                    case 1://昵称
                        //昵称限制最多八个文字，超过八个需要截掉多余的文字
                        if (len>8){
                            int selEndIndex= Selection.getSelectionEnd(editable);
                            String str = editable.toString();
                            //截取新字符串
                            String newStr =str.substring(0,8);
                            et_content.setText(newStr);
                            editable=et_content.getText();
                            //新字符串的长度
                            int newLen=editable.length();
                            //旧光标位置超过新字符串的长度
                            if (selEndIndex>newLen){
                                selEndIndex=editable.length();
                            }
                            //设置新光标所在的位置
                            Selection.setSelection(editable,selEndIndex);
                        }
                        break;
                    case 2://签名
                        //签名最多16分文字，超过16个文字截取掉多余的文字
                        if (len>16){
                            int selEndIndex=Selection.getSelectionEnd(editable);
                            String str = editable.toString();
                            //截取新字符串
                            String newStr = str.substring(0,16);
                            et_content.setText(newStr);
                            editable=et_content.getText();
                            //新字符串的长度
                            int newLen=editable.length();
                            //旧光标的位置超过了新字符串的长度
                            if (selEndIndex>newLen){
                                selEndIndex=editable.length();
                            }
                            //设置鑫光标所在的位置
                            Selection.setSelection(editable,selEndIndex);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
