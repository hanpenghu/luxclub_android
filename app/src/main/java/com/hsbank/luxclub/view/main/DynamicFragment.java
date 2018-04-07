package com.hsbank.luxclub.view.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.DynamicItemBean;
import com.hsbank.luxclub.mywidget.swipe_refresh_loadmore.ListviewScrollListener;
import com.hsbank.luxclub.provider.apis.DynamicApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.UIUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.util.tool.ToastUtil;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.luxclub.view.main.joy.DynamicDetailActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/5/11
 * Description: 动态功能页
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class DynamicFragment extends ProjectBaseFragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    public static final int PAGE_SIZE = 10;
    private int pageNumber = 1;
    private boolean isRefresh = true; // 默认当前是下拉刷新状态
    private boolean hasMore; // 默认没有更多数据
    private SwipeToLoadLayout swipeToLoadLayout;
    private CommonAdapter<DynamicItemBean> dynamicAdapter;


    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(view, R.string.txt_dynamic);
        viewHandler(view);

        //动态设置距离顶部padding值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout fly_toolbar = mViewHelper.getView(R.id.fly_toolbar);
            fly_toolbar.setPadding(0, UIUtil.getStatusBarHeight(mContext),0,0);
        }
    }

    private void viewHandler(View view) {
        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        // 初始化listview适配器
        dynamicAdapter = new CommonAdapter<DynamicItemBean>(mContext, R.layout.item_dynamic) {
            @Override
            public void convert(ViewHolder holder, DynamicItemBean item, int position) {
                holder.setText(R.id.txt_title, item.getTitle());
                holder.setText(R.id.txt_label, item.getLabel());
                holder.setText(R.id.txt_create_date, item.getCreateDate());
                ImageView img_dynamic = holder.getView(R.id.img_dynamic);
                ImageView img_icon = holder.getView(R.id.img_icon);
                ImageLoader.getInstance().displayImage(item.getImageUlr(), img_dynamic, Constants.networkOptions);
                ImageLoader.getInstance().displayImage(item.getIconUrl(), img_icon, Constants.networkOptions);
            }
        };
        ListView listView = mViewHelper.getView(R.id.swipe_target);
        listView.setAdapter(dynamicAdapter);
        listView.setOnScrollListener(new ListviewScrollListener()); // 滑到底部自动加载更多
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onLoadMore() {
        if (!hasMore) {
            AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
            swipeToLoadLayout.setLoadingMore(false);
            return;
        }
        isRefresh = false;
        requestResultList(true);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        isRefresh = true;
        requestResultList(false);

    }

    private void requestResultList(boolean hasMore) {
        RetrofitHelper.getInstance()
                .create(DynamicApis.class, true)
                .pageList(PAGE_SIZE, hasMore ? ++pageNumber : pageNumber)
                .compose(RxUtil.<List<DynamicItemBean>>compose(mContext))
                .subscribe(new APISubscriber<List<DynamicItemBean>>() {
                    @Override
                    public void onNext(List<DynamicItemBean> itemBeanList) {
                        updateUi(itemBeanList);
                    }
                });
    }

    private void updateUi(List<DynamicItemBean> list) {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
        ViewAnimator vam_ticket = mViewHelper.getView(R.id.vam_list_data);
        if (list == null || list.size() == 0) {
            if (isRefresh) {
                vam_ticket.setDisplayedChild(1);
            } else {
                ToastUtil.show(getString(R.string.txt_no_data));
                swipeToLoadLayout.setLoadingMore(false);
            }
            return;
        }
        vam_ticket.setDisplayedChild(0);
        if (isRefresh) { // 下拉刷新状态
            dynamicAdapter.setDatas(list);
        } else { // 加载更多状态
            dynamicAdapter.addDatas(list, false);
        }
        hasMore = list.size() == PAGE_SIZE;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int dynamicId = dynamicAdapter.getItem(position).getDynamicId();
        DynamicDetailActivity.show(mContext,  dynamicId);
    }

}

