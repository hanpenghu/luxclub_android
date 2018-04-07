package com.hsbank.luxclub.adapter.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.hsbank.util.android.util.ResourceUtil;

/**
 * Author:      chen_liuchun
 * Date:        2016/5/19
 * Description: TabLayout指示器通用Fragment适配器
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------
 * 2016-5-26    clc         2
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    private int[] mTitles;
    private TabFragmentCallback mTabFragmentCallback;

    /**
     * 构造实例
     *
     * @param fm                  v4包下
     * @param titles              tab标题
     * @param tabFragmentCallback Fragment传入接口
     */
    public TabFragmentAdapter(FragmentManager fm, int[] titles, TabFragmentCallback tabFragmentCallback) {
        super(fm);
        mTitles = titles;
        mTabFragmentCallback = tabFragmentCallback;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabFragmentCallback.getFragment(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ResourceUtil.getInstance().getString(mTitles[position]);
    }

    public void setTabFragmentCallback(TabFragmentCallback tabFragmentCallback) {
        this.mTabFragmentCallback = tabFragmentCallback;
    }

}