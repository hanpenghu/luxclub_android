package com.hsbank.luxclub.view.manager.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.MessagePageListAdapter;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.my.event.MyEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.android.view.pull_to_refresh.OnFooterRefreshListener;
import com.hsbank.util.android.view.pull_to_refresh.OnHeaderRefreshListener;
import com.hsbank.util.android.view.pull_to_refresh.PullToRefreshView;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 个人消息
 * Created by pengwei on 2016/4/5.
 */
public class MMessageActivity extends ProjectBaseActivity {
    /**页容量*/
    public static final int PAGE_SIZE = 10;
    /**页码*/
    private int pageNumber = 1;
    /**个人消息列表数据*/
    private List<Map<String, Object>> data = null;
    /**个人消息列表适配器*/
    private MessagePageListAdapter messagePageListAdapter = null;
    /**分页加载是否还有更多数据*/
    private boolean hasMore = true;
    private String userId;

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_message_page;
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
        if(event.getCommand().equals(MyEvent.COMMAND_MANAGER_MESSAGE)){
            String obj = (String) event.getMessage();
            data = JsonUtil.toList(obj);
            updateUi();
        }
    }
    /**更新界面*/
    private void updateUi() {
        PullToRefreshView pullToRefreshView = mViewHelper.getView(R.id.pullToRefreshView);
        if (data != null && data.size() > 0) {
            hasMore = data.get(data.size() - 1) != null;
            pullToRefreshView.onHeaderRefreshComplete();
            pullToRefreshView.onFooterRefreshComplete();
            messagePageListAdapter.addDatas(data);
            mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.GONE);
            pullToRefreshView.setVisibility(View.VISIBLE);
        } else {
            boolean isListNoData = messagePageListAdapter.getCount() == 0;
            pullToRefreshView.onHeaderRefreshComplete();
            pullToRefreshView.onFooterRefreshComplete();
            if (isListNoData) {
                pullToRefreshView.setVisibility(View.INVISIBLE);
                mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.VISIBLE);
                mViewHelper.setText(R.id.txt_no_data, getString(R.string.txt_personal_no_message));
            } else {
                pullToRefreshView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**控件初始化*/
    protected void viewHandler() {
        ListView listView = mViewHelper.getView(R.id.listView);
        PullToRefreshView pullToRefreshView = mViewHelper.getView(R.id.pullToRefreshView);
        pullToRefreshView.setEnablePullToRefresh(true);
        pullToRefreshView.setEnablePullLoadMoreDataStatus(true);
        pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
        pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);
        messagePageListAdapter = new MessagePageListAdapter(this);
        listView.setAdapter(messagePageListAdapter);
        listView.setOnItemClickListener(listItemClickListener);
        getData();
    }

    /**接口请求数据*/
    private void getData() {
        userId = ProjectUtil.getManagerUserId();
        if(!TextUtils.isEmpty(ProjectUtil.getManagerToken())){
            ProviderFactory.getInstance().message_messagePageList(mContext,ProjectUtil.getManagerToken(), userId, PAGE_SIZE, pageNumber,messageCallback);
        }
    }

    /**下拉刷新*/
    private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {
        @Override
        public void onHeaderRefresh(PullToRefreshView view) {
            pageNumber = 1;
            messagePageListAdapter.clearDatas();
            ProviderFactory.getInstance().message_messagePageList(mContext,ProjectUtil.getManagerToken(), userId, PAGE_SIZE, pageNumber, messageCallback);
        }
    };

    /**上拉加载更多*/
    private OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {
        @Override
        public void onFooterRefresh(PullToRefreshView view) {
            if (hasMore) {
                ProviderFactory.getInstance().message_messagePageList(mContext,ProjectUtil.getManagerToken(), userId ,PAGE_SIZE, ++pageNumber, messageCallback);
            } else {
                view.onFooterRefreshComplete();
                view.onFooterFullScroll();
            }
        }
    };

    public AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
            try {
                Map<String, Object> dataItem = messagePageListAdapter.datas.get(positionId);
                String messageId=MapUtil.getString(dataItem, "messageId");
                String status = MapUtil.getString(dataItem, "status");
                if (!status.equals("2")) {
                    messagePageListAdapter.clearDatas();
                    ProviderFactory.getInstance().message_read(mContext,ProjectUtil.getManagerToken(), messageId, MessageReadCallback);
                    if(messagePageListAdapter.getSingleLine().equals(String.valueOf(positionId))){
                        messagePageListAdapter.setSingleLine("");
                    }else{
                        messagePageListAdapter.setSingleLine(String.valueOf(positionId));
                    }
                }else{
                    if(messagePageListAdapter.getSingleLine().equals(String.valueOf(positionId))){
                        messagePageListAdapter.setSingleLine("");
                        messagePageListAdapter.notifyDataSetChanged();
                    }else{
                        messagePageListAdapter.setSingleLine(String.valueOf(positionId));
                        messagePageListAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    /**个人消息列表的回调*/
    private ApiCallback messageCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }
        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_MANAGER_MESSAGE, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(MMessageActivity.this, response.getText());
            }
        }
    };
    /**已读消息的回调*/
    private ApiCallback MessageReadCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }
        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                ProviderFactory.getInstance().message_messagePageList(mContext,ProjectUtil.getManagerToken(), userId, PAGE_SIZE, pageNumber,messageCallback);
            } else {
                AlertUtil.getInstance().alertMessage(MMessageActivity.this, response.getText());
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MMessageActivity.class);
        context.startActivity(intent);
    }
}
