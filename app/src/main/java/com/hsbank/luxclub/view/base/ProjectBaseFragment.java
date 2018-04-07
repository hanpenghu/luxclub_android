package com.hsbank.luxclub.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.tool.LogUtil;
import com.hsbank.util.android.fragment.util.BaseFragment;

/**
 * 工程级基类
 * 2016-3-8
 */
public abstract class ProjectBaseFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // 初始化toolbar
        initToolbar(view);
        // 初始化沉浸式状态栏
        initStatusBar();
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void initStatusBar() {
        // 沉浸式状态栏，取消通知栏空间
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置toolbar，
     * 1. 使标题与标题菜单解耦，对于不同的标题菜单，只需添加对应的menu文件即可，不需创建多个title XML文件；
     * 2. 使title返回按钮与finish方法区分开，增强页面跳转灵活性；
     * 3. 页面标题可以在manifest.xml文件的label标签里面预定义，增强可视性
     * 4. 可以利用android官方侧滑菜单栏
     */
    protected void initToolbar(View view){
        // 设置toolbar生效
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
            // 使原生title隐藏
            getToolbar().setDisplayShowTitleEnabled(false);
            // 不显示返回箭头
            getToolbar().setDisplayHomeAsUpEnabled(false);
        } else {
            LogUtil.w3("当前页面找不到Toolbar，请仔细检查！");
        }
    }

    /**
     * 获取Toolbar，用于自定义返回箭头和其可见性
     * @return Toolbar代理对象
     */
    protected ActionBar getToolbar() {
        return ((AppCompatActivity) mContext).getSupportActionBar();
    }

    /**
     * 设置Toolbar自定义的标题，一般用于动态标题
     * @param title 标题名称
     */
    protected void setToolbarTitle(View view, CharSequence title) {
        ((TextView) view.findViewById(R.id.toolbar_title)).setText(title);
    }

    /**
     * 设置Toolbar自定义的标题，一般用于动态标题
     * @param resId 标题名称
     */
    protected void setToolbarTitle(View view, int resId) {
        ((TextView) view.findViewById(R.id.toolbar_title)).setText(resId);
    }
}