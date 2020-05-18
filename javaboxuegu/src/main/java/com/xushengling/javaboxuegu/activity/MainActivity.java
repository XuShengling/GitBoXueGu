package com.xushengling.javaboxuegu.activity;

//import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.KeyEvent;
//import android.view.LayoutInflater;
import android.view.View;
//import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;
import com.xushengling.javaboxuegu.fragment.CourseFragment;
import com.xushengling.javaboxuegu.fragment.ExercisesFragment;
import com.xushengling.javaboxuegu.fragment.MyFragment;

//import java.util.ArrayList;
//import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
//    View view1, view2, view3;
//    ViewPager viewPager;
//    List<View> viewList;
    FrameLayout mBodyLayout;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBottomBtn();
        setListener();
        setInitStarts();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        initViewPager();
        TextView tv_back = $(R.id.backTv);
        tv_main_titer = $(R.id.titleTv);
        tv_main_titer.setText(getText(R.string.main_activity));
        rl_title_bar = $(R.id.mainTitleBar);
        rl_title_bar.setBackgroundColor(this.getColor(R.color.colorBar));
        tv_back.setVisibility(View.GONE);
        initBodyLayout();
    }

//    private void initViewPager() {
//        viewPager=findViewById(R.id.viewpager);
//        LayoutInflater inflater=getLayoutInflater();
//        view1=inflater.inflate(R.layout.course_fragment,null);
//        view2=inflater.inflate(R.layout.exercises_fragment,null);
//        view3=inflater.inflate(R.layout.my_fragment,null);
//
//        viewList=new ArrayList<>();
//        viewList.add(view1);
//        viewList.add(view2);
//        viewList.add(view3);
//
//        PagerAdapter pagerAdapter=new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return viewList.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//                return view==object;
//            }
//
//            @NonNull
//            @Override
//            public Object instantiateItem(@NonNull ViewGroup container, int position) {
//                container.addView(viewList.get(position));
//                return viewList.get(position);
//            }
//
//            @Override
//            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                container.removeView(viewList.get(position));
//            }
//        };
//        viewPager.setAdapter(pagerAdapter);
//    }

    private void initBottomBtn() {
        mButtonLayout=$(R.id.main_btn_bar);
        mCuresBtn=$(R.id.main_btn_bar_course_ll);
        mExercisesBtn=$(R.id.main_btn_bar_exercises_ll);
        mMyInfoBtn=$(R.id.main_btn_bar_my_ll);
        tv_course=$(R.id.main_btn_bar_course_tv);
        tv_exercise=$(R.id.main_btn_bar_exercises_tv);
        tv_myInfo=$(R.id.main_btn_bar_my_tv);
        iv_course=$(R.id.main_btn_bar_course_btn);
        iv_exercise=$(R.id.main_btn_bar_exercises_btn);
        iv_myInfo=$(R.id.main_btn_bar_my_btn);
    }
    private void initBodyLayout(){
        mBodyLayout=$(R.id.main_body);
        replaceFragment(new CourseFragment());
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
        tv_course.setTextColor(this.getColor(R.color.colorText));
        tv_exercise.setTextColor(this.getColor(R.color.colorText));
        tv_myInfo.setTextColor(this.getColor(R.color.colorText));
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercise.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);

    }

    /**
     * 设置底部按钮的选中状态
     */
    private void setSelectedStatus(int index){
        switch (index){
            case 0:
                mCuresBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(this.getColor(R.color.colorTextSelected));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_titer.setText(R.string.main_activity);
                break;
            case 1:
                mExercisesBtn.setSelected(true);
                iv_exercise.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercise.setTextColor(this.getColor(R.color.colorTextSelected));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_titer.setText(R.string.main_exercises_tv);
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myInfo.setTextColor(this.getColor(R.color.colorTextSelected));
                rl_title_bar.setVisibility(View.GONE);
                break;
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
                replaceFragment(new CourseFragment());
                break;
            case 1:
                //习题界面
                replaceFragment(new ExercisesFragment());
                break;
            case 2:
                //我的界面
                replaceFragment(new MyFragment());
                break;
        }
    }
    protected long exitTime;//记录第一次点击时的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - exitTime) > 2000){
               Toast(getString(R.string.keyDown));
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
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        return sp.getBoolean("isLogin",false);
    }
    /**
     * 清除 SharedPreferences 中的登录状态
     */

    private void clearLoginStatus(){
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            //从设置界面或登录界面传递过来的登陆状态
            boolean isLogin=data.getBooleanExtra("isLogin",false);
            if (isLogin){
                //登陆成功界面
                clearBottomImageState();
                selectDisplayView(0);
            }
        }
    }
    /**
     * 修改 Fragment界面
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.main_body,fragment);
        transaction.commit();
    }
}
