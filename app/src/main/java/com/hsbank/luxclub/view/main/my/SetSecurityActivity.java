package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.setting_item.item.SwitchView;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.lock_pattern.LockPatternUnlockActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;

/**
 * 应用安全页面
 * name：zhuzhenghua
 * create time:2015.11.19
 */
public class SetSecurityActivity extends ProjectBaseActivity {
    /**手势密码开关*/
    private SwitchView set_item_safe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    /**
     * 视图元素处理器
     */
    protected void viewHandler() {
        set_item_safe = mViewHelper.getView(R.id.set_item_safe);
        mViewHelper.getView(R.id.set_item_update_password).setOnClickListener(clickListener);
        set_item_safe.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                /**参数：代表需要验证身份才能设置手势*/
                VerifyPasswordActivity.show(mContext, "cardPassword");
            }

            @Override
            public void toggleToOff(View view) {
                /**参数：为了关闭手势密码需验证手势密码*/
                LockPatternUnlockActivity.show(mContext, "toOffPassword");
            }
        });
    }

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_set_security;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SharedPreferencesUtil.getInstance().getString(ProjectConstant.MD_5_STRING)!=null&&!SharedPreferencesUtil.getInstance().getString(ProjectConstant.MD_5_STRING).isEmpty()){
            set_item_safe.setOpened(true);
            mViewHelper.getView(R.id.set_item_update_password).setVisibility(View.VISIBLE);
            mViewHelper.getView(R.id.lineView).setVisibility(View.VISIBLE);
        }else{
            set_item_safe.setOpened(false);
            mViewHelper.getView(R.id.set_item_update_password).setVisibility(View.GONE);
            mViewHelper.getView(R.id.lineView).setVisibility(View.GONE);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.set_item_update_password:
                    /**参数：为了修改手势密码需验证手势密码*/
                    LockPatternUnlockActivity.show(SetSecurityActivity.this,"updatePassword");
                    break;
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SetSecurityActivity.class);
        context.startActivity(intent);
    }
}
