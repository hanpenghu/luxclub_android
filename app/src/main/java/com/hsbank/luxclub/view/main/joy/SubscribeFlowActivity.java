package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;

/**
 * 预约页面
 * Created by chen_liuchun on 2016/3/15.
 */
public class SubscribeFlowActivity extends ProjectBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subscribe_flow;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("预约流程");
    }

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SubscribeFlowActivity.class);
        context.startActivity(intent);
    }
}
