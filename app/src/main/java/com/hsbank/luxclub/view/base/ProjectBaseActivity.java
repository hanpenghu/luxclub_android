package com.hsbank.luxclub.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.tool.LogUtil;
import com.hsbank.luxclub.view.manager.lock_pattern.LockPatternUnlockActivity;
import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.android.activity.util.BaseActivity;

/**
 * 工程级基类
 * 2016-3-8
 */
public abstract class ProjectBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化沉浸式状态栏
        initStatusBar();
        // 初始化toolbar
        initToolbar();
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void initStatusBar() {
        // 沉浸式状态栏，取消通知栏空间
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置toolbar，
     * 1. 使标题与标题菜单解耦，对于不同的标题菜单，只需添加对应的menu文件即可，不需创建多个title XML文件；
     * 2. 使title返回按钮与finish方法区分开，增强页面跳转灵活性；
     * 3. 页面标题可以在manifest.xml文件的label标签里面预定义，增强可视性
     * 4. 可以利用android官方侧滑菜单栏
     */
    protected void initToolbar() {
        // 设置toolbar生效
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            // 使原生title可见
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            // 设置自己的title文本
            String title = getSupportActionBar().getTitle().toString();
            TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
            if (toolbar_title != null) {
                toolbar_title.setText(title);
            }
            // 设置是否显示返回箭头
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isKeyCodeBackFlag());
            // 使原生title隐藏
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } else {
            LogUtil.w3("当前页面找不到Toolbar，是否遗漏？");
        }
    }

    @Override
    public void onForeground() {
        super.onForeground();
        if (AndroidAppInfo.getInstance().isNeedAuthGesture()) {
            LockPatternUnlockActivity.show(this.getApplicationContext(), "");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置Toolbar自定义的标题，一般用于动态标题
     *
     * @param title 标题名称
     */
    protected void setToolbarTitle(CharSequence title) {
        ((TextView) findViewById(R.id.toolbar_title)).setText(title);
    }

    /**
     * 设置Toolbar自定义的标题，一般用于动态标题
     *
     * @param resid 标题名称资源id
     */
    protected void setToolbarTitle(int resid) {
        ((TextView) findViewById(R.id.toolbar_title)).setText(resid);
    }

}