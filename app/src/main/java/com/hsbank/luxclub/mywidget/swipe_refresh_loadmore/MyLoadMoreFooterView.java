package com.hsbank.luxclub.mywidget.swipe_refresh_loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreFooterLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.util.UIUtil;

/**
 * Author:      chen_liuchun
 * Date:        2016/5/19
 * Description: 下拉刷新尾部视图
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------
 */
public class MyLoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView txtLoadMore;
    private ImageView imgSuccess;
    private ProgressBar progressBar;

    private int mFooterHeight;

    public MyLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public MyLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = (int) UIUtil.dpToPx(48);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        txtLoadMore = (TextView) findViewById(R.id.txt_load_more);
        imgSuccess = (ImageView) findViewById(R.id.img_success);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onPrepare() {
        imgSuccess.setVisibility(GONE);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            imgSuccess.setVisibility(GONE);
            progressBar.setVisibility(GONE);
            if (-y >= mFooterHeight) {
                txtLoadMore.setText(R.string.ptr_release_load);
            } else {
                txtLoadMore.setText(R.string.ptr_pull_load);
            }
        }
    }

    @Override
    public void onLoadMore() {
        txtLoadMore.setText(R.string.ptr_loading);
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(GONE);
        imgSuccess.setVisibility(VISIBLE);
    }

    @Override
    public void onReset() {
        imgSuccess.setVisibility(GONE);
    }
}
