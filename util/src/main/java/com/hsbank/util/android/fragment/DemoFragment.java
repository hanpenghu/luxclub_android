package com.hsbank.util.android.fragment;

import android.os.Bundle;
import android.view.View;

import com.hsbank.util.android.fragment.util.BaseFragment;

/**
 * 示例Fragment
 * 2016-01-24
 */
public class DemoFragment extends BaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //<1>.视图元素处理器
        viewHandler();
    }

    protected int getLayoutId() {
        //TODO：下面注释这句，要改成你想要的布局文件
        //return R.layout.fragment_demo;
        return -1;
    }

    /**
     * 视图元素处理器
     */
    private void viewHandler() {
    }
}
