package com.hsbank.luxclub.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.manager.ManagerMainActivity;
import com.hsbank.util.android.activity.util.BaseActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;

/**
 * 启动页
 * name：zhuzhenghua
 * create time:2016.3.29
 */
public class LaunchActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    private void viewHandler() {
        // 是否需要跳转经理端
        if (!TextUtils.isEmpty(ProjectUtil.getManagerToken())){
            ManagerMainActivity.show(mContext);
        } else {
            boolean firstEnter = SharedPreferencesUtil.getInstance().getBoolean(ProjectConstant.IS_FIRST_ENTER, true);
            if (firstEnter) {
                GuideActivity.show(this);
            } else {
                // 从启动页进入主页如果设置了手势密码就会显示解锁手势密码页面
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("entrance", "LaunchActivity");
                startActivity(intent);
            }
        }
        finish();
    }
}
