package com.hsbank.luxclub.view.manager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewAnimator;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.MOrderItemBean;
import com.hsbank.luxclub.mywidget.swipe_refresh_loadmore.ListviewScrollListener;
import com.hsbank.luxclub.provider.apis.ManagerApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.luxclub.view.manager.order.MOrderCancelActivity;
import com.hsbank.luxclub.view.manager.order.MOrderDetailActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/2/20
 * Description: 经理端——店家订单
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class ManagerOrderFragment extends ProjectBaseFragment implements RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    public static final int PAGE_SIZE = 10;
    private int pageNumber = 1;
    private boolean isRefresh = true; // 默认当前是下拉刷新状态
    private boolean hasMore; // 默认没有更多数据
    private SwipeToLoadLayout swipeToLoadLayout;
    private CommonAdapter<MOrderItemBean> myOrderAdapter;
    // 当前订单状态(1 待确认 2 待结账 3 已完成)
    private int state = 1;
    // 待确认订单
    private List<MOrderItemBean> comfirmList;
    // 待结账订单
    private List<MOrderItemBean> accountList;
    // 已完成订单
    private List<MOrderItemBean> completedList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manager_order;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHandler();
    }

    private void viewHandler() {
        ((RadioGroup) mViewHelper.getView(R.id.radiogp_status)).setOnCheckedChangeListener(this);

        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        // 初始化listview适配器
        myOrderAdapter = new CommonAdapter<MOrderItemBean>(mContext, R.layout.item_manager_order) {
            @Override
            public void convert(ViewHolder holder, MOrderItemBean item, int position) {
                holder.setText(R.id.txt_site_name, item.getSiteName());
                holder.setText(R.id.txt_order_state, item.getOrderStateName());
                holder.setText(R.id.txt_reserve_date, item.getCreateDate());
                holder.setText(R.id.txt_member_code, item.getCardno());
                holder.setText(R.id.txt_member_name, item.getMemberName());
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

    private void updateUi(List<MOrderItemBean> list) {
        if (isRefresh) {
            switch (state) {
                case 1:
                    comfirmList = list;
                    break;
                case 2:
                    accountList = list;
                    break;
                case 3:
                    completedList = list;
                    break;
            }
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
                .create(ManagerApis.class, true)
                .pageList(ProjectUtil.getManagerToken(), state,
                        PAGE_SIZE, hasMore ? ++pageNumber : pageNumber)
                .compose(RxUtil.<List<MOrderItemBean>>compose(mContext))
                .subscribe(new APISubscriber<List<MOrderItemBean>>() {
                    @Override
                    public void onNext(List<MOrderItemBean> orderItemBeanList) {
                        updateUi(orderItemBeanList);
                    }
                });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radiobtn_comfirm = mViewHelper.getView(R.id.radiobtn_comfirm);
        RadioButton radiobtn_account = mViewHelper.getView(R.id.radiobtn_account);
        RadioButton radiobtn_completed = mViewHelper.getView(R.id.radiobtn_completed);
        ViewAnimator vam_ticket = mViewHelper.getView(R.id.vam_list_data);

        switch (checkedId) {
            case R.id.radiobtn_comfirm:
                if (state == 1) return;
                state = 1;
                radiobtn_comfirm.setChecked(true);
                if (comfirmList == null || comfirmList.size() == 0){
                    swipeToLoadLayout.setRefreshing(true);
                } else {
                    vam_ticket.setDisplayedChild(0);
                    myOrderAdapter.setDatas(comfirmList);
                }
                break;
            case R.id.radiobtn_account:
                if (state == 2) return;
                state = 2;
                radiobtn_account.setChecked(true);
                if (accountList == null || accountList.size() == 0){
                    swipeToLoadLayout.setRefreshing(true);
                } else {
                    vam_ticket.setDisplayedChild(0);
                    myOrderAdapter.setDatas(accountList);
                }
                break;
            case R.id.radiobtn_completed:
                if (state == 3) return;
                state = 3;
                radiobtn_completed.setChecked(true);
                if (completedList == null || completedList.size() == 0){
                    swipeToLoadLayout.setRefreshing(true);
                } else {
                    vam_ticket.setDisplayedChild(0);
                    myOrderAdapter.setDatas(completedList);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MOrderItemBean item = myOrderAdapter.getItem(position);
        if (item.getOrderState() == 3) {
            MOrderCancelActivity.show(getActivity(), item.getOrderId()+"");
        } else {
            MOrderDetailActivity.show(getActivity(), item.getOrderId()+"", item.getOrderState());
        }
    }
}
