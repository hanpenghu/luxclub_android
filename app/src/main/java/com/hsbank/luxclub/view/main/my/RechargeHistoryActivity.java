package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.MyRechargeHistoryAdapter;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.android.view.pull_to_refresh.OnFooterRefreshListener;
import com.hsbank.util.android.view.pull_to_refresh.OnHeaderRefreshListener;
import com.hsbank.util.android.view.pull_to_refresh.PullToRefreshView;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.java.type.DatetimeUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * name：zhuzhenghua
 * create time:2015.11.19
 * 我的充值记录
 */
public class RechargeHistoryActivity extends ProjectBaseActivity {
    /**页容量*/
    public static final int PAGE_SIZE = 10;
    /**页码*/
    private int pageNumber = 1;
    /**我的消费记录列表数据*/
    private List<Map<String, Object>> data = null;
    /**我的消费记录列表适配器*/
    private MyRechargeHistoryAdapter myRecordsConsumptionAdapter = null;
    /**分页加载是否还有更多数据*/
    private boolean hasMore = true;
    /**算出每个月的消费/充值总额*/
    private List<Map<String, Object>> dataList = null;

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_recharge_history;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    /**
     * EventBus消息处理器
     * @param event
     */
    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if(event.getCommand().equals(MyEvent.COMMAND_MY_RECHARGE_CONSUMPTION)){
            String obj = (String) event.getMessage();
            data = JsonUtil.toList(obj);
            dataList.addAll(data);
            computeData(dataList);
        }
    }

    /**
     * 计算每个月消费/充值的总额
     * @param data
     */
    private void computeData(List<Map<String, Object>> data) {
        Collections.sort(data, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
                int flag = MapUtil.getString(rhs, "createDate").compareTo(MapUtil.getString(lhs, "createDate"));
                return flag;
            }
        });
        ArrayList<Map<String, Double>> list = new ArrayList<>();
        double money = 0;
        boolean again = false;
        if(data.size()==1){
            Map<String, Double> map = new HashMap<>();
            map.put(DatetimeUtil.formatDate(MapUtil.getString(data.get(0), "createDate"),
                    getString(R.string.txt_from_format), getString(R.string.txt_account_date)), MapUtil.getDouble(data.get(0),"feeMoney"));
            list.add(map);
        }else{
            for(int i=0;i<data.size()-1;i++) {
                String createDate1 = DatetimeUtil.formatDate(MapUtil.getString(data.get(i), "createDate"),
                        getString(R.string.txt_from_format), getString(R.string.txt_account_date));
                String createDate2 = DatetimeUtil.formatDate(MapUtil.getString(data.get(i + 1), "createDate"),
                        getString(R.string.txt_from_format), getString(R.string.txt_account_date));
                if (createDate1.equals(createDate2)) {
                    double money1 = MapUtil.getDouble(data.get(i), "feeMoney");
                    if(i==0||again){
                        again =false;
                        money +=  money1 +MapUtil.getDouble(data.get(i + 1), "feeMoney");
                    }else{
                        money += MapUtil.getDouble(data.get(i + 1), "feeMoney");
                    }
                    if(i==data.size()-2){
                        Map<String, Double> map = new HashMap<>();
                        map.put(DatetimeUtil.formatDate(MapUtil.getString(data.get(i), "createDate"),
                                getString(R.string.txt_from_format), getString(R.string.txt_account_date)), money);
                        list.add(map);
                    }
                } else {
                    Map<String, Double> map = new HashMap<>();
                    map.put(DatetimeUtil.formatDate(MapUtil.getString(data.get(i), "createDate"),
                            getString(R.string.txt_from_format), getString(R.string.txt_account_date)), money);
                    list.add(map);
                    money =0.00;
                    again = true;
                    if(again){
                        again =false;
                        money =MapUtil.getDouble(data.get(i + 1), "feeMoney");
                    }
                    if(i==data.size()-2){
                        map.put(DatetimeUtil.formatDate(MapUtil.getString(data.get(i + 1), "createDate"),
                                getString(R.string.txt_from_format), getString(R.string.txt_account_date)), money);
                        list.add(map);
                    }
                }
            }
        }
        myRecordsConsumptionAdapter.setList(list);
        Log.i("---------月份总消费--------", list.toString());
        updateUi();
    }

    /**
     * 更新界面
     */
    private void updateUi() {
        PullToRefreshView pullToRefreshView = mViewHelper.getView(R.id.pullToRefreshView);
        boolean isListNoData = myRecordsConsumptionAdapter.getCount() == 0;
        if (data != null && data.size() > 0) {
            hasMore = data.get(data.size() - 1) != null;
            pullToRefreshView.onHeaderRefreshComplete();
            pullToRefreshView.onFooterRefreshComplete();
            myRecordsConsumptionAdapter.addDatas(data);
            mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.GONE);
            pullToRefreshView.setVisibility(View.VISIBLE);
        } else {
            pullToRefreshView.onHeaderRefreshComplete();
            pullToRefreshView.onFooterRefreshComplete();
            if (isListNoData) {
                pullToRefreshView.setVisibility(View.INVISIBLE);
                mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.VISIBLE);
                mViewHelper.setText(R.id.txt_no_data, getString(R.string.txt_no_data2));
            } else {
                pullToRefreshView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 控件初始化
     */
    protected void viewHandler() {
        dataList = new ArrayList<>();
        ListView listView = mViewHelper.getView(R.id.listView);
        PullToRefreshView pullToRefreshView = mViewHelper.getView(R.id.pullToRefreshView);
        pullToRefreshView.setEnablePullToRefresh(true);
        pullToRefreshView.setEnablePullLoadMoreDataStatus(true);
        pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
        pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);
        myRecordsConsumptionAdapter = new MyRechargeHistoryAdapter(this);
        listView.setAdapter(myRecordsConsumptionAdapter);
        getData();
    }

    /**
     * 接口请求数据
     */
    private void getData() {
        if(!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getToken())){
            ProviderFactory.getInstance().luxclub_feeRecordPageList(mContext,SharedPreferencesUtil.getInstance().getToken(), ProjectUtil.getCardNo(),
                    "1",PAGE_SIZE, pageNumber,feeRecordCallback);
        }
    }


    /**
     * 下拉刷新
     */
    private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {
        @Override
        public void onHeaderRefresh(PullToRefreshView view) {
            pageNumber = 1;
            myRecordsConsumptionAdapter.clearDatas();
            dataList.clear();
            ProviderFactory.getInstance().luxclub_feeRecordPageList(mContext,SharedPreferencesUtil.getInstance().getToken(), ProjectUtil.getCardNo(),
                    "1", PAGE_SIZE, pageNumber, feeRecordCallback);
        }
    };

    /**
     * 上拉加载更多
     */
    private OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {
        @Override
        public void onFooterRefresh(PullToRefreshView view) {
            if (hasMore) {
                ProviderFactory.getInstance().luxclub_feeRecordPageList(mContext,SharedPreferencesUtil.getInstance().getToken(), ProjectUtil.getCardNo(),
                        "1", PAGE_SIZE, ++pageNumber, feeRecordCallback);
            } else {
                view.onFooterRefreshComplete();
                view.onFooterFullScroll();
            }
        }
    };

    private ApiCallback feeRecordCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }
        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_MY_RECHARGE_CONSUMPTION, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(RechargeHistoryActivity.this, response.getText());
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RechargeHistoryActivity.class);
        context.startActivity(intent);
    }
}
