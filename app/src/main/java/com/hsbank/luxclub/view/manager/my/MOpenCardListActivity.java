package com.hsbank.luxclub.view.manager.my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSONArray;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.CardItemBean;
import com.hsbank.luxclub.model.CardOpenBean;
import com.hsbank.luxclub.mywidget.swipe_refresh_loadmore.ListviewScrollListener;
import com.hsbank.luxclub.provider.apis.ActivateCardApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.tool.ToastUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/5/19
 * Description: 推荐开卡记录列表
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class MOpenCardListActivity extends ProjectBaseActivity implements AdapterView.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    public static final int PAGE_SIZE = 10;
    private static final String KEY_RULE_ID = "key_rule_id";
    private int pageNumber = 1;
    private boolean isRefresh = true; // 默认当前是下拉刷新状态
    private boolean hasMore; // 默认没有更多数据
    private SwipeToLoadLayout swipeToLoadLayout;
    private CommonAdapter<CardItemBean> cardItemAdapter;

    private List<CardOpenBean.CardRuleListBean> ruleList; // 开卡规则筛选列表
    private int index; // 当前开卡规则

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_open_card_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.txt_open_card_record));

        String ruleJson = getIntent().getStringExtra(KEY_RULE_ID);
        ruleList = JSONArray.parseArray(ruleJson, CardOpenBean.CardRuleListBean.class);
        CardOpenBean.CardRuleListBean bean = new CardOpenBean.CardRuleListBean();
        bean.setRuleId(0);
        bean.setRuleName("全部");
        ruleList.add(0, bean);

        viewHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                String[] strings = parseRuleList(ruleList);
                new AlertDialog.Builder(this)
                        .setItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                index = which;
                                requestResultList(false, which);
                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void viewHandler() {
        swipeToLoadLayout = mViewHelper.getView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        // 初始化listview适配器
        cardItemAdapter = new CommonAdapter<CardItemBean>(mContext, R.layout.item_my_card) {
            @Override
            public void convert(ViewHolder holder, CardItemBean item, int position) {
                holder.setText(R.id.txt_card_num, item.getCardNo());
                holder.setText(R.id.txt_actual_amount, item.getActualAmount());
                holder.setText(R.id.txt_create_date, item.getCreateDate());
            }
        };
        ListView listView = mViewHelper.getView(R.id.swipe_target);
        listView.setAdapter(cardItemAdapter);
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
        requestResultList(false, index);
    }

    @Override
    public void onLoadMore() {
        if (!hasMore) {
            AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_data));
            swipeToLoadLayout.setLoadingMore(false);
            return;
        }
        isRefresh = false;
        requestResultList(true, index);
    }

    private void requestResultList(boolean hasMore, int index) {
        RetrofitHelper.getInstance()
                .create(ActivateCardApis.class, true)
                .pageList(ProjectUtil.getManagerToken(), ruleList.get(index).getRuleId(),
                        PAGE_SIZE, hasMore ? ++pageNumber : pageNumber)
                .compose(RxUtil.<List<CardItemBean>>compose(mContext))
                .subscribe(new APISubscriber<List<CardItemBean>>() {
                    @Override
                    public void onNext(List<CardItemBean> itemBeanList) {
                        updateUi(itemBeanList);
                    }
                });
    }

    private void updateUi(List<CardItemBean> list) {
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
            cardItemAdapter.setDatas(list);
        } else { // 加载更多状态
            cardItemAdapter.addDatas(list, false);
        }
        hasMore = list.size() == PAGE_SIZE;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int recordIdId = cardItemAdapter.getItem(position).getRecordId();
        MOpenCardDetailActivity.show(mContext,  recordIdId);
    }

    /**
     * 解析返回售卡规则列表
     * @param ruleBean
     * @return
     */
    private String[] parseRuleList(List<CardOpenBean.CardRuleListBean> ruleBean){
        List<String> list = new ArrayList<>();
        for (CardOpenBean.CardRuleListBean bean : ruleBean) {
            list.add(bean.getRuleName());
        }
        return list.toArray(new String[]{});
    }

    public static void show(Context context, String ruleIds) {
        Intent intent = new Intent();
        intent.putExtra(KEY_RULE_ID, ruleIds);
        intent.setClass(context, MOpenCardListActivity.class);
        context.startActivity(intent);
    }
}
