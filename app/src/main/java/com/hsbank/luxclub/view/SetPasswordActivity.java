package com.hsbank.luxclub.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.manager.ManagerMainActivity;
import com.hsbank.luxclub.view.manager.lock_pattern.LockPatternSetActivity;

/**
 * 刚安装app时跳转这个页面，是否设置手势密码
 * name：zhuzhenghua
 * create time:2016.5.24
 */
public class SetPasswordActivity extends ProjectBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
        mViewHelper.getView(R.id.fly_toolbar).setVisibility(View.GONE);
    }

    /**
     * 控件初始化
     */
    private void viewHandler() {
        mViewHelper.getView(R.id.btn_set_gesture_password).setOnClickListener(clickListener);
        mViewHelper.getView(R.id.btn_temporarily_no).setOnClickListener(clickListener);
    }

    /**
     * 界面所有控件的点击事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_set_gesture_password:
                    LockPatternSetActivity.show(mContext,"true");
                    break;
                case R.id.btn_temporarily_no:
                    gotoMain();
                    break;
            }
        }
    };

    /**跳转到主页*/
    private void gotoMain() {
        // 是否需要跳转经理端
        if (!TextUtils.isEmpty(ProjectUtil.getManagerToken())){
            ManagerMainActivity.show(mContext);
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        /*从启动页进入主页如果设置了手势密码就会显示解锁手势密码页面*/
        intent.putExtra("entrance","LaunchActivity");
        startActivity(intent);
        finish();
    }

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SetPasswordActivity.class);
        context.startActivity(intent);
    }
}
