package com.hsbank.luxclub.view.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.GlobalData;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.servant.ServantOrderTabFragment;

/**
 * 经理-主页面
 * name：zhuzhenghua
 * create time:2016.4.5
 */
public class ManagerMainActivity extends ProjectBaseActivity implements RadioGroup.OnCheckedChangeListener {
    /**
     * 菜单项序号
     */
    private final int MENU_INDEX_01 = 0;
    private final int MENU_INDEX_02 = 1;
    /**
     * 当前菜单项序号
     */
    private int mCurrentMenuIndex = -1;
    /**
     * 子视图集合
     */
    private SparseArray<Fragment> mFragmentMap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 是否响应退出键点击事件
        setKeyCodeBackFlag(true);
        super.onCreate(savedInstanceState);

        mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_manager_order));
        viewHandler();
        if (GlobalData.getInstance().getManagerCurrentIndex() == 0) {
            GlobalData.getInstance().setManagerCurrentIndex(0);
            switchContent(MENU_INDEX_01, true);
            mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_manager_order));
            ((RadioButton) mViewHelper.getView(R.id.rbtn_manager_order)).setChecked(true);
        } else if (GlobalData.getInstance().getManagerCurrentIndex() == 1) {
            GlobalData.getInstance().setManagerCurrentIndex(1);
            switchContent(MENU_INDEX_02, true);
            mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_my));
            ((RadioButton) mViewHelper.getView(R.id.rbtn_manager_my)).setChecked(true);
        }
    }

    /**
     * 控件初始化
     */
    protected void viewHandler() {
        mFragmentMap = new SparseArray<>();
        if (ProjectUtil.isServantType()) {
            mFragmentMap.put(MENU_INDEX_01, new ServantOrderTabFragment());
        } else {
            mFragmentMap.put(MENU_INDEX_01, new ManagerOrderFragment());
        }
        mFragmentMap.put(MENU_INDEX_02, new ManagerMyFragment());
        ((RadioGroup) mViewHelper.getView(R.id.rgroup_manager_otab_bottom)).setOnCheckedChangeListener(this);
    }

    /**
     * tab切换事件监听器
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_manager_order:
                GlobalData.getInstance().setManagerCurrentIndex(0);
                switchContent(MENU_INDEX_01, true);
                mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_manager_order));
                break;
            case R.id.rbtn_manager_my:
                GlobalData.getInstance().setManagerCurrentIndex(1);
                switchContent(MENU_INDEX_02, true);
                mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_my));
                break;
        }
    }

    /**
     * 切换fragment视图
     *
     * @param menuIndex 切换视图对应的菜单项序号
     * @param animFlag  是否设置切换动画
     */
    private void switchContent(int menuIndex, boolean animFlag) {
        if (menuIndex != mCurrentMenuIndex) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            Fragment fragmentCurrent = mCurrentMenuIndex < 0 ? null : mFragmentMap.get(mCurrentMenuIndex);
            Fragment fragmentNew = mFragmentMap.get(menuIndex);

            if (animFlag) {
                if (menuIndex > mCurrentMenuIndex) {
                    fragmentTransaction.setCustomAnimations(R.anim.util_enter_from_right, R.anim.util_exit_to_left);
                } else {
                    fragmentTransaction.setCustomAnimations(R.anim.util_enter_from_left, R.anim.util_exit_to_right);
                }
            }
            if (fragmentCurrent != null) {
                fragmentCurrent.onPause();
            }
            if (fragmentNew.isAdded()) {
                fragmentNew.onResume();
            } else {
                fragmentTransaction.add(R.id.manager_main_content, fragmentNew);
            }
            if (fragmentCurrent != null) {
                fragmentTransaction.hide(fragmentCurrent);
            }
            fragmentTransaction.show(fragmentNew);
            fragmentTransaction.commit();
            mCurrentMenuIndex = menuIndex;
        }
    }

    /**
     * 用于fragment更改标题
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        mViewHelper.setText(R.id.toolbar_title, title);
    }

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ManagerMainActivity.class);
        context.startActivity(intent);
    }
}
