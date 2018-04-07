package com.hsbank.luxclub.mywidget.swipe_refresh_loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;
import com.hsbank.luxclub.R;

/**
 * Author:      chen_liuchun
 * Date:        2016/5/19
 * Description: 下拉刷新头部视图
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------
 */
public class MyRefreshHeaderView extends SwipeRefreshHeaderLayout {


    public MyRefreshHeaderView(Context context) {
        super(context);
    }

    public MyRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView img_logo = (ImageView) findViewById(R.id.img_logo);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
    }
}
