package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.TabFragmentAdapter;
import com.hsbank.luxclub.adapter.common.TabFragmentCallback;
import com.hsbank.luxclub.util.UIUtil;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.luxclub.view.main.my.event.OrderFunction;

import de.greenrobot.event.EventBus;

/**
 * Author:      chenliuchun
 * Date:        17/2/15
 * Description: 我的订单页面
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class MyOrderFragment extends ProjectBaseFragment implements TabFragmentCallback {

    private int[] mTitles = new int[]{R.string.txt_shop, R.string.txt_model};
    private static final String SELECTED_INDEX = "selected_index";
    // 当前订单显示状态(0:全部, 1:预约中, 2:已预约, 3:已结账, 4:已取消)
    private int state = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(view, getString(R.string.txt_my_order));
//        int selectedIndex = getIntent().getIntExtra(SELECTED_INDEX, 0);

        TabLayout tabLayout = mViewHelper.getView(R.id.tabLayout);
        ViewPager viewPager = mViewHelper.getView(R.id.viewPager);
        TabFragmentAdapter orderAdapter = new TabFragmentAdapter(getFragmentManager(), mTitles, this);
        viewPager.setAdapter(orderAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setCurrentItem(selectedIndex);

        //动态设置距离顶部padding值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout fly_toolbar = mViewHelper.getView(R.id.fly_toolbar);
            fly_toolbar.setPadding(0, UIUtil.getStatusBarHeight(mContext),0,0);
        }
    }

    @Override
    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return OrderListFragment.newInstance();

            case 1:
                return ModelOrderListFragment.newInstance();

            default:
                return OrderListFragment.newInstance();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                View parentView = mViewHelper.getView(R.id.parentView);
                MyPopupWindow.showFilterOrderState(mContext, parentView, orderFunction, state);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private OrderFunction orderFunction = new OrderFunction() {
        @Override
        public void popupWinFunction(View view, int which) {
            state = which;
            EventBus.getDefault().post(new MyEvent(OrderListFragment.BUS_ORDER_FILTER, which));
        }
    };

    public static void show(Context context, int selectIndex) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_INDEX, selectIndex);
        intent.setClass(context, MyOrderFragment.class);
        context.startActivity(intent);
    }
}
