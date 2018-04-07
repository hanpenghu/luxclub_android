package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;

/**
 * 公司简介
 * name：zhuzhenghua
 * create time:2016.3.10
 */
public class IntroduceActivity extends ProjectBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduce;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.txt_luxclub_introduce);
    }

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, IntroduceActivity.class);
        context.startActivity(intent);
    }
}
