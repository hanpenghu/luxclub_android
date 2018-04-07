package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.MessageAdapter;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.tool.LogUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.luxclub.view.main.joy.DynamicDetailActivity;
import com.hsbank.luxclub.view.main.joy.JoyDetailActivity;
import com.hsbank.luxclub.view.main.joy.ModelDetailActivity;
import com.hsbank.luxclub.view.manager.my.event.MyEvent;
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
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 客户端消息
 * name：zhuzhenghua
 * create time:2016.5.16
 */
public class MessageActivity extends ProjectBaseActivity {
    /**
     * 页容量
     */
    public static final int PAGE_SIZE = 10;
    /**
     * 页码
     */
    private int pageNumber = 1;
    /**
     * 个人消息列表数据
     */
    private List<Map<String, Object>> data;
    /**
     * 个人消息列表适配器
     */
    private MessageAdapter messageAdapter;
    /**
     * 分页加载是否还有更多数据
     */
    private boolean hasMore = true;
    private String cardNo;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if (event.getCommand().equals(MyEvent.COMMAND_MANAGER_MESSAGE)) {
            String obj = (String) event.getMessage();
            data = JsonUtil.toList(obj);
            updateUi();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_message_read, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_marked_read:
                if (data != null && data.size() != 0) {
                    StringBuilder messageSb = new StringBuilder();
                    for (int i = 0; i < data.size(); i++) {
                        Map<String, Object> map = data.get(i);
                        if (MapUtil.getInt(map, "status") != 2) {
                            map.put("status", 2);
                            messageSb.append(MapUtil.getString(map, "messageId") + ",");
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
                    if (!messageSb.toString().isEmpty()) {
                        String messageId = messageSb.substring(0, messageSb.length() - 1); // 去掉尾部逗号
                        ProviderFactory.getInstance().message_read(mContext, SharedPreferencesUtil.getInstance().getToken(), messageId, readedCallback);
                    } else {
                        AlertUtil.getInstance().alertMessage(mContext, getString(R.string.user_user_read_message));
                    }
                } else {
                    AlertUtil.getInstance().alertMessage(mContext, getString(R.string.user_user_read_message));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void viewHandler() {
        ListView listView = mViewHelper.getView(R.id.listView);
        PullToRefreshView pullToRefreshView = mViewHelper.getView(R.id.pullToRefreshView);
        pullToRefreshView.setEnablePullToRefresh(true);
        pullToRefreshView.setEnablePullLoadMoreDataStatus(true);
        pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
        pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);
        messageAdapter = new MessageAdapter(this);
        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(itemClickListener);
        getData();
    }

    /**
     * 接口请求数据
     */
    private void getData() {
        String token = SharedPreferencesUtil.getInstance().getToken();
        cardNo = ProjectUtil.getCardNo();
        if (!TextUtils.isEmpty(token)) {
            ProviderFactory.getInstance().message_messagePageList(mContext, token, cardNo, PAGE_SIZE, pageNumber, messageCallback);
        } else {
            PullToRefreshView pullToRefreshView = mViewHelper.getView(R.id.pullToRefreshView);
            pullToRefreshView.setVisibility(View.GONE);
            mViewHelper.getView(R.id.data).setVisibility(View.GONE);
            mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.VISIBLE);
            mViewHelper.setText(R.id.txt_no_data, getString(R.string.txt_personal_no_message));
        }
    }

    private void updateUi() {
        PullToRefreshView pullToRefreshView = mViewHelper.getView(R.id.pullToRefreshView);
        if (data != null && data.size() > 0) {
            hasMore = data.get(data.size() - 1) != null;
            pullToRefreshView.onHeaderRefreshComplete();
            pullToRefreshView.onFooterRefreshComplete();
            messageAdapter.addDatas(data);
            mViewHelper.getView(R.id.NoDataIsShow).setVisibility(View.GONE);
            pullToRefreshView.setVisibility(View.VISIBLE);
        } else {
            boolean isListNoData = messageAdapter.getCount() == 0;
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

    /**
     * 下拉刷新
     */
    private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {
        @Override
        public void onHeaderRefresh(PullToRefreshView view) {
            pageNumber = 1;
            messageAdapter.clearDatas();
            ProviderFactory.getInstance().message_messagePageList(mContext, SharedPreferencesUtil.getInstance().getToken(), cardNo, PAGE_SIZE, pageNumber, messageCallback);
        }
    };

    /**
     * 上拉加载更多
     */
    private OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {
        @Override
        public void onFooterRefresh(PullToRefreshView view) {
            if (hasMore) {
                ProviderFactory.getInstance().message_messagePageList(mContext, SharedPreferencesUtil.getInstance().getToken(), cardNo, PAGE_SIZE, ++pageNumber, messageCallback);
            } else {
                view.onFooterRefreshComplete();
                view.onFooterFullScroll();
            }
        }
    };

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
            Map<String, Object> dataItem = messageAdapter.datas.get(positionId);
            String messageId = MapUtil.getString(dataItem, "messageId");
            String status = MapUtil.getString(dataItem, "status");
            if (!status.equals("2")) {
                messageAdapter.clearDatas();
                ProviderFactory.getInstance().message_read(mContext, SharedPreferencesUtil.getInstance().getToken(), messageId, readedCallback);
                if (messageAdapter.getSingleLine().equals(String.valueOf(positionId))) {
                    messageAdapter.setSingleLine("");
                } else {
                    messageAdapter.setSingleLine(String.valueOf(positionId));
                }
            } else {
                if (messageAdapter.getSingleLine().equals(String.valueOf(positionId))) {
                    messageAdapter.setSingleLine("");
                    messageAdapter.notifyDataSetChanged();
                } else {
                    messageAdapter.setSingleLine(String.valueOf(positionId));
                    messageAdapter.notifyDataSetChanged();
                }
            }

            String target = MapUtil.getString(dataItem, "target");
            if (TextUtils.isEmpty(target)) return;
            jumpPage(target);

        }
    };

    /**
     * 推送页面跳转
     */
    private void jumpPage(String json) {
        Map<String, Object> map = JsonUtil.toMap(json);
        String type = MapUtil.getString(map, "type");
        switch (type) {
            case "home": // 跳转首页
                int value = MapUtil.getInt(map, "value");
                MainActivity.show(this, value - 1);
                break;
            case "detail": // 详情页面
                int siteType = MapUtil.getInt(map, "siteType");
                String detail = MapUtil.getString(map, "detail");
                if (siteType == 7) {  // 模特页面
                    switch (detail) {
                        case "site_detail":
                            String modelId = MapUtil.getString(map, "site_detail");
                            ModelDetailActivity.show(this, modelId);
                            break;
                        case "order_detail":
                            String orderId = MapUtil.getString(map, "order_detail");
                            int order_status = MapUtil.getInt(map, "order_status");
                            ModelOrderDetailActivity.show(this, orderId, convertStatus(order_status));
                            break;
                    }
                } else {  // 其他页面
                    switch (detail) {
                        case "site_detail":
                            String siteId = MapUtil.getString(map, "site_detail");
                            JoyDetailActivity.show(this, siteId);
                            break;
                        case "order_detail":
                            String orderId = MapUtil.getString(map, "order_detail");
                            int order_status = MapUtil.getInt(map, "order_status");
                            OrderDetailActivity.show(this, orderId, convertStatus(order_status));
                            break;
                    }
                }
                break;
            case "dynamic":
                int dynamic_id = MapUtil.getInt(map, "dynamic_id", -1);
                if (dynamic_id != -1) {
                    DynamicDetailActivity.show(this, dynamic_id);
                }
                break;
            default:
                LogUtil.e3("推送消息的类型值不存在");
                break;
        }
    }

    /**
     * state 状态(0:全部, 1:预约中, 2:已预约, 3:已结账, 4:已取消)
     * 转成 orderState 订单状态(0:提交,1:派单,2:确认,3:取消:4,异常,5:待结账,6:已结账)
     *
     * @param state
     * @return
     */
    private int convertStatus(int state) {
        switch (state) {
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
                return 6;
            case 4:
                return 3;
            default:
                LogUtil.e3("跳转页面不存在");
                return 0;
        }
    }

    /**
     * 个人消息列表的回调
     */
    private ApiCallback messageCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_MANAGER_MESSAGE, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(MessageActivity.this, response.getText());
            }
        }
    };

    /**
     * 已读消息的回调
     */
    private ApiCallback readedCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                messageAdapter.clearDatas();
                ProviderFactory.getInstance().message_messagePageList(mContext, SharedPreferencesUtil.getInstance().getToken(), cardNo, PAGE_SIZE, pageNumber, messageCallback);
            } else {
                Toast.makeText(getApplicationContext(), response.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MessageActivity.class);
        context.startActivity(intent);
    }
}
