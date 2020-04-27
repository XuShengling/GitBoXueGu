package com.xushengling.javaboxuegu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.TextView;

import com.xushengling.javaboxuegu.R;
import com.xushengling.javaboxuegu.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    protected Handler mHandler = new Handler();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        TextView mVersionTv = findViewById(R.id.versionTv);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            mVersionTv.setText("V" + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mVersionTv.setText("V");
        }

        mHandler=new Handler();
        mHandler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        },3000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler !=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

}
