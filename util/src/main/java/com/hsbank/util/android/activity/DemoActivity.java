package com.hsbank.util.android.activity;

import android.os.Bundle;

import com.hsbank.util.android.activity.util.BaseActivity;
import com.hsbank.util.project.util.BaseProjectEvent;

/**
 * 示例Activity
 * 2016-01-24
 */
public class DemoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO：下面注释这句，根据需要设置
        //setKeyCodeBackFlag(true);
        //视图元素处理器
        viewHandler();
    }

    protected int getLayoutId() {
        //TODO：下面注释这句，要改成你想要的布局文件
        //return R.layout.activity_demo;
        return -1;
    }

    /**
     * 视图元素处理器
     */
    private void viewHandler() {
    }

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        //if (event.getCommand().equals(BaseProjectEvent.BASE_COMMAND_LOGIN)) {
        //}
    }
}
