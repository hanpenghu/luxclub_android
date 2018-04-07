package com.hsbank.luxclub.view.servant;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.TabFragmentAdapter;
import com.hsbank.luxclub.adapter.common.TabFragmentCallback;
import com.hsbank.luxclub.util.ReflectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;

/**
 * Author:      chenliuchun
 * Date:        18/01/09
 * Description: 经理端——店家订单Tab
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class ServantOrderTabFragment extends ProjectBaseFragment implements TabFragmentCallback {

    private final int[] mTitleArray = new int[]{R.string.wait_receive_order, R.string.received_order, R.string.have_finished, R.string.have_canceled};
    private ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_order_tab;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHandler();
    }

    private void viewHandler() {
        viewPager = mViewHelper.getView(R.id.viewPager);
        TabLayout tabLayout = mViewHelper.getView(R.id.tabLayout);
        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), mTitleArray, this);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public Fragment getFragment(int position) {
        // 防止内存重启导致的 Fragment 重复加载
        Fragment fragment = getChildFragmentManager().findFragmentByTag(
                ReflectUtil.getFragmentName(viewPager.getId(), position));
        if (fragment == null) {
            fragment = ServantOrderFragment.newInstance(position + 1, getString(mTitleArray[position]));
        }
        return fragment;
    }

}
