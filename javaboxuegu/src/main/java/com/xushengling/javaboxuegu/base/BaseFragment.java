package com.xushengling.javaboxuegu.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

/**
 * @ProjectName: GitBoXueGu
 * @Package: com.xushengling.javaboxuegu.base
 * @ClassName: Base
 * @Author: 徐圣领
 * @CreateDate: 2020/4/7 下午 5:28
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setFragment(),container,false);
    }
    protected abstract int setFragment();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    protected abstract void initView();

    protected void Toast(String value){
        Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
    }

    protected <T extends View> T $(int id) {
        return Objects.requireNonNull(getActivity()).findViewById(id);
    }
}
