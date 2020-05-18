package com.xushengling.javaboxuegu.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xushengling.javaboxuegu.R;


/**
 * @ProjectName: GitBoXueGu
 * @Package: com.xushengling.javaboxuegu.base
 * @ClassName: BaseActivity
 * @Author: 徐圣领
 * @CreateDate: 2020/4/6 20:07
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected <T extends View> T $(@IdRes int id) {
        return findViewById(id);
    }

    protected void Toast(String value) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }

}
