package com.hsbank.luxclub.view.manager.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.manager.order.event.OrderEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import java.math.BigDecimal;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 会员信息
 * name：zhuzhenghua
 * create time:2016.4.13
 *
 */
public class MemberInformationActivity extends ProjectBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_information;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    private void viewHandler() {
        String cardNo = getIntent().getStringExtra("cardNo");
        ProviderFactory.getInstance().luxclub_memberInfo(mContext, ProjectUtil.getManagerToken(),cardNo,memberInfoCallback);
    }

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if(event.getCommand().equals(OrderEvent.COMMAND_MEMBER_INFO)){
            String obj = (String) event.getMessage();
            Map<String, Object> data = JsonUtil.toMap(obj);
            mViewHelper.setText(R.id.txt_card_balance, stringFormat(MapUtil.getString(data,"memberBalance")));
            mViewHelper.setText(R.id.txt_wallet_balance, stringFormat(MapUtil.getString(data,"walletBalance")));
        }
    }

    private String stringFormat(String str){
        if(str==null||str.equals("")){
            return str= "0.00";
        }
        BigDecimal priceBigDecimal = new BigDecimal(str);
        String str1 = String.format("%1$,.2f",
                priceBigDecimal.doubleValue());
        return str1;
    }

    private ApiCallback memberInfoCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new OrderEvent(OrderEvent.COMMAND_MEMBER_INFO, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(MemberInformationActivity.this, response.getText());
            }
        }
    };

    public static void show(Context context,String cardNo) {
        Intent intent = new Intent();
        intent.putExtra("cardNo",cardNo);
        intent.setClass(context, MemberInformationActivity.class);
        context.startActivity(intent);
    }
}
