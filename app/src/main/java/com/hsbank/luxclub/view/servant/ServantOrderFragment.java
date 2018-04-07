package com.hsbank.luxclub.view.servant;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.ServantOrderItemBean;
import com.hsbank.luxclub.mywidget.swipe_refresh_loadmore.ListviewScrollListener;
import com.hsbank.luxclub.provider.apis.ServantApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.util.android.util.AlertUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        18/1/10
 * Description: 移动管家——订单列表页
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class ServantOrderFragment extends ProjectBaseFragment implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private static final String KEY_STATE = "key_state";
    private static final String KEY_TITLE = "key_title";

    public static final int PAGE_SIZE = 10;
    private int pageNumber = 1;
    private boolean isRefresh = true; // 默认当前是下拉刷新状态
    private boolean hasMore; // 默认没有更多数据

    private SwipeToLoadLayout swipeToLoadLayout;
    private CommonAdapter<ServantOrderItemBean> myOrderAdapter;
    // 当前状态(1:待接单, 2:已接单, 3:完成, 4:取消)
    private int state;
    // 订单集合
    private List<ServantOrderItemBean> orderList;

    public static ServantOrderFragment newInstance(int state, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_STATE, state);
        bundle.putString(KEY_TITLE, title);
        ServantOrderFragment fragment = new ServantOrderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_servant_order;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        state = getArguments().getInt(KEY_STATE, 1);
        viewHandler();
    }

    private void viewHandler() {
        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        // 初始化listview适配器
        myOrderAdapter = new CommonAdapter<ServantOrderItemBean>(mContext, R.layout.item_servant_order) {
            @Override
            public void convert(ViewHolder holder, ServantOrderItemBean item, int position) {
                holder.setText(R.id.txt_site_name, item.getSiteName());
                holder.setText(R.id.txt_order_state, item.getStateName());
                holder.setText(R.id.txt_member_name, item.getMemberName());
                holder.setText(R.id.txt_member_sex, item.getSexLabel());
                holder.setText(R.id.txt_reserve_date, item.getReserveDate());
                if (state == 3) {
                    holder.setVisible(R.id.txt_is_summary, true);
                    if (item.isFinishSummary()) {
                        holder.setText(R.id.txt_is_summary, "已上传服务总结");
                        holder.setTextColorRes(R.id.txt_is_summary, R.color.yellow);
                    } else {
                        holder.setText(R.id.txt_is_summary, "未上传服务总结");
                        holder.setTextColorRes(R.id.txt_is_summary, R.color.red);
                    }
                }
                ImageView img_site = holder.getView(R.id.img_site);
                ImageLoader.getInstance().displayImage(item.getSiteImageUrl(), img_site, Constants.networkOptions);
            }
        };
        ListView listView = mViewHelper.getView(R.id.swipe_target);
        listView.setAdapter(myOrderAdapter);
        listView.setOnScrollListener(new ListviewScrollListener()); // 滑到底部自动加载更多
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeToLoadLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        isRefresh = true;
        requestResultList(false);
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

    private void requestResultList(boolean hasMore) {
        RetrofitHelper.getInstance()
                .create(ServantApis.class, true)
                .pageList(ProjectUtil.getManagerToken(), state,
                        PAGE_SIZE, hasMore ? ++pageNumber : pageNumber)
                .compose(RxUtil.<List<ServantOrderItemBean>>compose(mContext))
                .subscribe(new APISubscriber<List<ServantOrderItemBean>>() {
                    @Override
                    public void onNext(List<ServantOrderItemBean> orderList) {
                        updateUi(orderList);
                    }
                });
    }

    private void updateUi(List<ServantOrderItemBean> list) {

        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
        ViewAnimator vam_ticket = mViewHelper.getView(R.id.vam_list_data);

        if (list == null || list.size() == 0) {
            if (isRefresh) {
                vam_ticket.setDisplayedChild(1);
            } else {
                AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
                swipeToLoadLayout.setLoadingMore(false);
            }
            return;
        }
        vam_ticket.setDisplayedChild(0);
        if (isRefresh) { // 下拉刷新状态
            myOrderAdapter.setDatas(list);
        } else { // 加载更多状态
            myOrderAdapter.addDatas(list, false);
        }
        hasMore = list.size() == PAGE_SIZE;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ServantOrderItemBean item = myOrderAdapter.getItem(position);
        if (item.getState() == 4) {
            ServantOrderCancelActivity.show(getActivity(), item.getOrderId(), item.getStewardOrderId());
        } else {
            ServantOrderDetailActivity.show(getActivity(), item.getOrderId(),
                    item.getStewardOrderId(), item.getState(), item.isFinishSummary());
        }
    }
}
