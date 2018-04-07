package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 我的消费记录详情
 * name：zhuzhenghua
 * create time:2016.3.17
 */
public class RecordsHistoryDetailActivity extends ProjectBaseActivity {
    /**我的消费详情数据*/
    private Map<String, Object> data = null;

    /**
     * 得到布局文件Id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_records_history_detail;
    }

    /**
     * EventBus消息处理器
     * @param event
     */
    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if(event.getCommand().equals(MyEvent.COMMAND_MY_RECORDS_TO_DETAIL)){
            String feeId = (String)event.getMessage();
            if(!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getToken())){
                ProviderFactory.getInstance().luxclub_feeRecordDetails(mContext,SharedPreferencesUtil.getInstance().getToken(), feeId, recordDetailCallback);
            }
        }else if(event.getCommand().equals(MyEvent.COMMAND_MY_RECORDS_DETAIL)){
            String object = (String)event.getMessage();
            data = JsonUtil.toMap(object);
            updateUi();
        }
    }

    /**
     * 更新界面
     */
    private void updateUi() {
        mViewHelper.setText(R.id.txt_site_name, MapUtil.getString(data,"siteName"));
        mViewHelper.setText(R.id.mTxtFeeMoney, MapUtil.getString(data,"feeMoney"));
        mViewHelper.setText(R.id.txt_order_code, MapUtil.getString(data,"orderCode"));
        mViewHelper.setText(R.id.mTxtOrderTime, MapUtil.getString(data,"createDate"));
        mViewHelper.setText(R.id.txt_pay_way, MapUtil.getString(data,"payModel"));
    }

    private ApiCallback recordDetailCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_MY_RECORDS_DETAIL, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(RecordsHistoryDetailActivity.this, response.getText());
            }
        }
    };

    public static void show(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RecordsHistoryDetailActivity.class);
        context.startActivity(intent);
    }
}
