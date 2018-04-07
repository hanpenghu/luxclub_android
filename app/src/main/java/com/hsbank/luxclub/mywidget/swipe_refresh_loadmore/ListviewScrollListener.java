package com.hsbank.luxclub.mywidget.swipe_refresh_loadmore;

import android.support.v4.view.ViewCompat;
import android.widget.AbsListView;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Author:      chen_liuchun
 * Date:        2016/5/20
 * Description: Listview滑动状态默认监听实现类，用于滑动到底部自动加载更多
 *  如果需要个性化，请自行实现AbsListView.OnScrollListener接口
 * Attention：仅当listview的父控件是SwipeToLoadLayout的时候才适用，否则重写OnScrollListener接口
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------
 */
public class ListviewScrollListener implements AbsListView.OnScrollListener {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1 && !ViewCompat.canScrollVertically(view, 1)) {
                ((SwipeToLoadLayout) view.getParent()).setLoadingMore(true); // 启动下拉加载更多
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 如果有页面轮播图，在这里应该控制轮播的切换开始和终止
    }
}
