package com.xushengling.javaboxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xushengling.javaboxuegu.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 中间内容狂
     */
    private FrameLayout mBodyLayout;

    /**
     * 底部按钮栏
     */
    private LinearLayout mButtonLayout;

    /**
     * 底部按钮
     */
    private View mCuresBtn;
    private View mExercisesBtn;
    private View mMyInfoBtn;
    private TextView tv_course;
    private TextView tv_exercise;
    private TextView tv_myInfo;
    private ImageView iv_course;
    private ImageView iv_exercise;
    private ImageView iv_myInfo;
    private TextView tv_main_titer;
    private ConstraintLayout rl_title_bar;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        initView();
        initBottomBtn();
        setListener();
        setInitStarts();
    }

    private void initView() {
        TextView tv_back = findViewById(R.id.backTv);
        tv_main_titer = findViewById(R.id.titleTv);
        tv_main_titer.setText(getText(R.string.main_activity));
        rl_title_bar = findViewById(R.id.mainTitleBar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back.setVisibility(View.GONE);
        initBodyLayout();
    }

    private void initBottomBtn() {
        mButtonLayout=findViewById(R.id.main_btn_bar);
        mCuresBtn=findViewById(R.id.main_btn_bar_course_ll);
        mExercisesBtn=findViewById(R.id.main_btn_bar_exercises_ll);
        mMyInfoBtn=findViewById(R.id.main_btn_bar_my_ll);
        tv_course=findViewById(R.id.main_btn_bar_course_tv);
        tv_exercise=findViewById(R.id.main_btn_bar_exercises_tv);
        tv_myInfo=findViewById(R.id.main_btn_bar_my_tv);
        iv_course=findViewById(R.id.main_btn_bar_course_btn);
        iv_exercise=findViewById(R.id.main_btn_bar_exercises_btn);
        iv_myInfo=findViewById(R.id.main_btn_bar_my_btn);
    }
    private void initBodyLayout(){
        mBodyLayout=findViewById(R.id.main_body);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.main_btn_bar_course_ll:
            clearBottomImageState();
            selectDisplayView(0);
            break;
        case R.id.main_btn_bar_exercises_ll:
            clearBottomImageState();
            selectDisplayView(1);
            break;
        case R.id.main_btn_bar_my_ll:
            clearBottomImageState();
            selectDisplayView(2);
            break;
    }
    }
    /**
     * 设置底部三个按钮的监听事件
     */
    private void setListener(){
        for(int i = 0 ; i <mButtonLayout.getChildCount(); i++){
            mButtonLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    /**
     * 清楚底部选中的按钮
     */
    private void clearBottomImageState(){
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercise.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercise.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);
        for (int i=0;i<mButtonLayout.getChildCount();i++){
            mButtonLayout.getChildAt(i).setSelected(false);
        }
    }
    /**
     * 设置底部按钮的选中状态
     */
    private void setSelectedStatus(int index){
        switch (index){
            case 0:
                mCuresBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_titer.setText(R.string.main_activity);
                break;
            case 1:
                mExercisesBtn.setSelected(true);
                iv_exercise.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercise.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_titer.setText(R.string.main_exercises_tv);
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myInfo.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.GONE);
                break;
        }
    }
    /**
     * 移除不需要视图
     */
    private void removeAllView(){
        for (int i = 0; i <mBodyLayout.getChildCount();i++){
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }
    /**
     * 设置界面view的初始化状态
     */
    private void setInitStarts(){
        clearBottomImageState();
        setSelectedStatus(0);
        createView(0);
    }
    /**
     * 显示对应的界面
     */
    private void selectDisplayView(int index){
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }
    /**
     * 选择视图
     */
    private void createView(int viewIndex){
        switch (viewIndex){
            case 0:
                //课程界面
                break;
            case 1:
                //习题界面
                break;
            case 2:
                //我的界面
                break;
        }
    }
    protected long exitTime;//记录第一次点击时的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(this, "再按一次退出博学谷", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else { this.finish();
            if (readLoginStatus()){
                clearLoginStatus();
            }
            System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 获取 SharedPreferences 中的登录信息
     */
    private boolean readLoginStatus(){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getBoolean("isLogin",false);
    }
    /**
     * 清除 SharedPreferences 中的登录状态
     */
    private void clearLoginStatus(){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.apply();
        editor.commit();
    }
}
