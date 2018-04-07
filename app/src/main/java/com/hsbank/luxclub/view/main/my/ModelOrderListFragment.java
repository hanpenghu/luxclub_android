package com.hsbank.luxclub.view.main.my;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.hsbank.luxclub.model.ModelOrderItemBean;
import com.hsbank.luxclub.mywidget.swipe_refresh_loadmore.ListviewScrollListener;
import com.hsbank.luxclub.provider.apis.ModelApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.util.tool.ToastUtil;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/2/16
 * Description: 我的订单--模特订单列表
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ModelOrderListFragment extends ProjectBaseFragment implements OnRefreshListener, OnLoadMoreListener {

    public static final String BUS_ORDER_FILTER = "bus_order_filter";

    public static final int PAGE_SIZE = 10;
    private int pageNumber = 1;
    private boolean isRefresh = true; // 默认当前是下拉刷新状态
    private boolean hasMore; // 默认没有更多数据
    private SwipeToLoadLayout swipeToLoadLayout;
    // 全部订单
    private List<ModelOrderItemBean> allList;
    // 当前筛选后的订单
    private List<ModelOrderItemBean> currentList;
    private CommonAdapter<ModelOrderItemBean> myOrderAdapter;
    // 当前订单显示状态(0:全部, 1:预约中, 2:已预约, 3:已结账, 4:已取消)
    private int state = 0;

    public static ModelOrderListFragment newInstance() {
        ModelOrderListFragment fragment = new ModelOrderListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if (event.getCommand().equals(BUS_ORDER_FILTER)) {
            if (getUserVisibleHint()) {
                state = (int) event.getMessage();
                filterOrder(state);
                updateUi();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHandler();
        swipeToLoadLayout.setRefreshing(true);
    }

    /**
     * 控件初始化
     */
    protected void viewHandler() {
        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        // 初始化listview适配器
        myOrderAdapter = new CommonAdapter<ModelOrderItemBean>(mContext, R.layout.item_my_order) {
            @Override
            public void convert(ViewHolder holder, ModelOrderItemBean item, int position) {
                holder.setText(R.id.txt_site_name, item.getNickname());
                holder.setText(R.id.txt_order_state, item.getOrderStatusName());
                holder.setText(R.id.txt_create_date, item.getCreateDate());
                ImageView img_site = holder.getView(R.id.img_site);
                ImageLoader.getInstance().displayImage(item.getModelHead(), img_site, Constants.networkOptions);
            }
        };
        ListView listView = mViewHelper.getView(R.id.swipe_target);
        listView.setAdapter(myOrderAdapter);
        listView.setOnScrollListener(new ListviewScrollListener()); // 滑到底部自动加载更多
        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);
    }

    private void updateUi() {
        List<ModelOrderItemBean> list;
        if (state == 0) {
            list = allList;
        } else {
            list = currentList;
        }

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

    private void filterOrder(int state) {
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        if (state == 0) return;
        for (ModelOrderItemBean bean : allList) {
            if (state == bean.getOrderStatus()) {
                currentList.add(bean);
            }
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        isRefresh = true;
        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .pageList(SharedPreferencesUtil.getInstance().getToken(), ProjectUtil.getCardNo(), state,
                        PAGE_SIZE, pageNumber)
                .compose(RxUtil.<List<ModelOrderItemBean>>compose(mContext))
                .subscribe(new APISubscriber<List<ModelOrderItemBean>>() {
                    @Override
                    public void onNext(List<ModelOrderItemBean> orderItemBeanList) {
                        allList = orderItemBeanList;
                        filterOrder(state);
                        updateUi();
                    }
                });
    }

    @Override
    public void onLoadMore() {
        if (!hasMore) {
            AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
            swipeToLoadLayout.setLoadingMore(false);
            return;
        }
        isRefresh = false;
        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .pageList(SharedPreferencesUtil.getInstance().getToken(), ProjectUtil.getCardNo(), state,
                        PAGE_SIZE, ++pageNumber)
                .compose(RxUtil.<List<ModelOrderItemBean>>compose(mContext))
                .subscribe(new APISubscriber<List<ModelOrderItemBean>>() {
                    @Override
                    public void onNext(List<ModelOrderItemBean> orderItemBeanList) {
                        allList = orderItemBeanList;
                        filterOrder(state);
                        updateUi();
                    }
                });
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ModelOrderItemBean item = myOrderAdapter.getItem(position);
            ModelOrderDetailActivity.show(mContext, item.getOrderId(), item.getOrderStatus());
        }
    };

    private AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            // 弹出删除菜单
            String[] items = {"删除订单"};
            new AlertDialog.Builder(mContext).setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ModelOrderItemBean item = myOrderAdapter.getItem(position);
                    if (item.getOrderStatus() == 3 || item.getOrderStatus() == 6) {
                        RetrofitHelper.getInstance().create(ModelApis.class, true)
                                .delete(SharedPreferencesUtil.getInstance().getToken(), item.getOrderId())
                                .compose(RxUtil.compose(mContext))
                                .subscribe(new APISubscriber<Object>() {
                                    @Override
                                    public void onNext(Object o) {
                                        ToastUtil.show("删除了订单：" + item.getNickname());
                                    }
                                });
                        myOrderAdapter.removeItem(position);
                        myOrderAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.show("该订单正在进行中，不可删除");
                    }
                }
            }).show();
            return true;
        }
    };

}
