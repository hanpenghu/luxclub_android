package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.main.my.OrderListFragment;
import com.hsbank.luxclub.view.main.my.event.MyEvent;

import de.greenrobot.event.EventBus;

/**
 * 预约成功页面
 * Created by chen_liuchun on 2016/3/15.
 */
public class SubscribeSuccessActivity extends ProjectBaseActivity {

    public static final String IS_BIND = "is_bind";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subscribe_success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("预约详情");
        boolean isBind = getIntent().getBooleanExtra(IS_BIND, true);
        TextView txt_look_detail = mViewHelper.getView(R.id.txt_look_detail);
        if (isBind) {
            txt_look_detail.setVisibility(View.VISIBLE);
            txt_look_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.show(mContext, MainActivity.MENU_INDEX_02);
                    EventBus.getDefault().post(new MyEvent(OrderListFragment.BUS_ORDER_REFRESH));
                }
            });
        }
    }

    public static void show(Context context, boolean isBind) {
        Intent intent = new Intent();
        intent.putExtra(IS_BIND, isBind);
        intent.setClass(context, SubscribeSuccessActivity.class);
        context.startActivity(intent);
    }
}
