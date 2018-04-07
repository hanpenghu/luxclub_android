package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.ConsumeProofAdapter;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.AlertFail;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.LogUtil;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.util.popupwindow.PopupWindowFunction;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.joy.JoyDetailActivity;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.luxclub.view.manager.order.ConsumeCredentialsActivity;
import com.hsbank.luxclub.view.manager.order.event.OrderEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 我的订单详情
 * name：zhuzhenghua
 * create time:2015.11.19
 */

public class OrderDetailActivity extends ProjectBaseActivity implements View.OnClickListener {
    private static final String ORDER_STATE = "order_state";
    private static final String ORDER_ID = "order_id";

    /**
     * 订单ID
     */
    private String orderId = "";
    /**
     * 订单详情数据
     */
    private Map<String, Object> data = null;
    /**
     * 客户端消费凭证adapter
     */
    private ConsumeProofAdapter consumeProofAdapter;
    /**
     * 消费凭证图片url
     */
    private ArrayList<String> consumeImgList = new ArrayList<>();
    ;
    /**
     * 消费凭证名称(上传图片接口返回的imageName,多个用半角逗号","分隔)
     */
    private String[] voucherUrlList;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if (event.getCommand().equals(MyEvent.COMMAND_MY_ORDER_TO_DETAIL)) {
            orderId = (String) event.getMessage();
            if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getToken())) {
                ProviderFactory.getInstance().luxclub_orderDetails(mContext, SharedPreferencesUtil.getInstance().getToken(), orderId, orderDetailCallback);
            }
        } else if (event.getCommand().equals(MyEvent.COMMAND_MY_ORDER_DETAIL)) {
            String object = (String) event.getMessage();
            data = JsonUtil.toMap(object);
            updateUi();
        } else if (event.getCommand().equals(OrderEvent.COMMAND_ORDER_CANCEL_SUCCESS)) {
//            ProviderFactory.getInstance().luxclub_orderDetails(mContext,SharedPreferencesUtil.getInstance().getToken(), orderId, orderDetailCallback);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        int state = getIntent().getIntExtra(ORDER_STATE, -1);
        if (state == 0 || state == 1) {
            inflater.inflate(R.menu.menu_order_cancel, menu);
        }
        //        else if (state == 6) {
        //            inflater.inflate(R.menu.menu_order_evaluate, menu);
        //        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_cancel:
                MyPopupWindow.showConfirmWin(mContext, mViewHelper.getContentView(), "",
                        getString(R.string.txt_confirm_cancel), getString(R.string.txt_ok), orderCancle);
                return true;
            case R.id.action_order_evaluate:
                int isEvaluate = MapUtil.getInt(data, "isEvaluate");
                Bundle bundle = new Bundle();
                bundle.putString("orderId", MapUtil.getString(data, "orderId"));
                bundle.putString("orderCode", MapUtil.getString(data, "orderCode"));
                bundle.putString("siteName", MapUtil.getString(data, "siteName"));
                bundle.putString("siteAddr", MapUtil.getString(data, "siteAddr"));

                if (isEvaluate == 0) { // 未评价
                    OrderEvaluateActivity.showEvaluating(this, bundle);
                } else if (isEvaluate == 1) { // 已评价
                    OrderEvaluateActivity.showEvaluated(this, bundle, MapUtil.getString(data, "orderEvaluate"));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String orderId = getIntent().getStringExtra(ORDER_ID);
        ProviderFactory.getInstance().luxclub_orderDetails(mContext, SharedPreferencesUtil.getInstance().getToken(), orderId, orderDetailCallback);
    }

    /**
     * 订单取消事件
     */
    private PopupWindowFunction orderCancle = new PopupWindowFunction() {
        @Override
        public void popupWinFunction(Object o) {
            ProviderFactory.getInstance().manager_orderCancel(mContext, SharedPreferencesUtil.getInstance().getToken(), orderId,
                    "", orderCancelCallback);
        }
    };

    private void viewHandler() {
        String orderId = getIntent().getStringExtra(ORDER_ID);

        mViewHelper.getView(R.id.txt_site_name).setOnClickListener(this);
//        mViewHelper.getView(R.id.txt_menber_info).setOnClickListener(this);


        ProviderFactory.getInstance().luxclub_orderDetails(mContext,
                SharedPreferencesUtil.getInstance().getToken(), orderId, orderDetailCallback);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_site_name:
                JoyDetailActivity.show(OrderDetailActivity.this, MapUtil.getString(data, "siteId"));
                break;
//            case R.id.txt_menber_info:
//                MemberInformationActivity.show(mContext, MapUtil.getString(data, "cardno"));
//                break;
        }
    }

    /**
     * 将后台返回的数组转成集合
     *
     * @param str
     * @return
     */
    private ArrayList<String> toList(String[] str) {
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            list.add(str[i].substring(1, str[i].length() - 1));
        }
        return list;
    }

    /**
     * 更新界面
     */
    private void updateUi() {
//        mViewHelper.setText(R.id.txt_order_state_name, MapUtil.getString(data, "orderStateName"));
        mViewHelper.setText(R.id.txt_order_code, MapUtil.getString(data, "orderCode"));
        mViewHelper.setText(R.id.txt_create_date, MapUtil.getString(data, "createDate"));
        mViewHelper.setText(R.id.txt_card_no, MapUtil.getString(data, "cardno"));
        mViewHelper.setText(R.id.txt_contact_mobile, MapUtil.getString(data, "contactMobile"));
        mViewHelper.setText(R.id.txt_participate_number, MapUtil.getString(data, "reserveNumber"));
        mViewHelper.setText(R.id.txt_arrive_date, MapUtil.getString(data, "realDate"));
        mViewHelper.setText(R.id.txt_site_addr, MapUtil.getString(data, "siteAddr"));
        String scene = MapUtil.getString(data, "scene").replace(",", "  ");
        mViewHelper.setText(R.id.txt_scene, scene);
//        mViewHelper.setText(R.id.txt_coin_consume_remark, MapUtil.getString(data, "walletRemarks"));
        mViewHelper.setText(R.id.txt_remark, MapUtil.getString(data, "reserveRequire"));
        mViewHelper.setText(R.id.txt_cancel_reason, MapUtil.getString(data, "cancelReason"));
        String orderState = MapUtil.getString(data, "orderState");
        isShowHonerCode();
        enableEnterSite();
//        showCoinMoney();
        showOrderDetail(orderState);
    }

    /**
     * 根据state判断控件显示隐藏
     * 订单状态(0:提交,1:派单,2:确认,3:取消:4,异常,5:待结账,6:已结账)
     *
     * @param state
     */
    private void showOrderDetail(String state) {
        if (state.equals("6")) {
            completed();
        } else if (state.equals("0") || state.equals("1")) {
            submit();
        } else if (state.equals("2") || state.equals("5")) {
            appoiment();
        } else if (state.equals("3")) {
            cancel();
        } else {  // 4
//            mViewHelper.getView(R.id.img_order_state).setBackgroundResource(R.drawable.img_order_cancel_gold);
        }
    }

    /**
     * 订单提交派单状态 0 1
     */
    private void submit() {
        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_submit_gold, 0, 0);
//        mViewHelper.getView(R.id.img_order_state).setBackgroundResource(R.drawable.img_order_submit_gold);
        mViewHelper.getView(R.id.view7).setVisibility(View.GONE);
    }

    /**
     * 订单确认待结账状态 2 5
     */
    private void appoiment() {
        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_submit_gold, 0, 0);
        ((TextView) mViewHelper.getView(R.id.txt_order_appointment)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_appointment)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_appoiment_gold, 0, 0);
        mViewHelper.getView(R.id.view1).setBackgroundColor(getResources().getColor(R.color.yellow_lux));
//        mViewHelper.getView(R.id.img_order_state).setBackgroundResource(R.drawable.img_order_appoiment_gold);
    }

    /**
     * 已结账 6
     */
    private void completed() {
        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_submit_gold, 0, 0);
        ((TextView) mViewHelper.getView(R.id.txt_order_appointment)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_appointment)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_appoiment_gold, 0, 0);
        ((TextView) mViewHelper.getView(R.id.txt_order_completion)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_completion)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_completed_gold, 0, 0);
        mViewHelper.getView(R.id.view1).setBackgroundColor(getResources().getColor(R.color.yellow_lux));
        mViewHelper.getView(R.id.view2).setBackgroundColor(getResources().getColor(R.color.yellow_lux));

        // 只有在已结账时才显示消费金额
        mViewHelper.setVisible(R.id.lly_consume_money, true);
        BigDecimal priceBigDecimal = new BigDecimal(MapUtil.getString(data, "consumerMoney"));
        String consumerMoney = String.format(Locale.getDefault(), "%1$,.2f",
                priceBigDecimal.doubleValue());
        mViewHelper.setText(R.id.txt_consume_money, "¥" + consumerMoney);

        // 显示
        mViewHelper.getView(R.id.lly_consume_proof).setVisibility(View.VISIBLE);
        showWalletVoucher();
    }

    /**
     * 订单取消状态 4
     */
    private void cancel() {
        mViewHelper.setText(R.id.toolbar_title, getString(R.string.txt_order_cancle));

        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_commit)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_submit_gold, 0, 0);
        ((TextView) mViewHelper.getView(R.id.txt_order_appointment)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_appointment)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_appoiment_gold, 0, 0);
        ((TextView) mViewHelper.getView(R.id.txt_order_completion)).setTextColor(getResources().getColor(R.color.yellow_lux));
        ((TextView) mViewHelper.getView(R.id.txt_order_completion)).setText("已取消");
        ((TextView) mViewHelper.getView(R.id.txt_order_completion)).setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_order_completed_gold, 0, 0);
        mViewHelper.getView(R.id.view1).setBackgroundColor(getResources().getColor(R.color.yellow_lux));
        mViewHelper.getView(R.id.view2).setBackgroundColor(getResources().getColor(R.color.yellow_lux));

        String cancelReason = MapUtil.getString(data, "cancelReason");
        mViewHelper.getView(R.id.lly_cancel_reason).setVisibility(View.VISIBLE);
        mViewHelper.setText(R.id.txt_cancel_reason, cancelReason);
    }

    /**
     * 零钱包图片显示
     */
    private void showWalletVoucher() {
        GridView gridView = mViewHelper.getView(R.id.gridview);
        consumeProofAdapter = new ConsumeProofAdapter(this);
        gridView.setOnItemClickListener(itemClickListener);
        gridView.setAdapter(consumeProofAdapter);

        String voucherUrl = MapUtil.getString(data, "voucherUrl");
        List<String> list = JSONArray.parseArray(voucherUrl, String.class);
        if (list.isEmpty()) return;
        consumeImgList = new ArrayList<>(filterNum(list, 5));
        consumeProofAdapter.addDatas(consumeImgList);
    }

    /**
     * 消费凭证图片点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
            ConsumeCredentialsActivity.show(mContext, consumeImgList, positionId, "imageUrl", MapUtil.getString(data, "orderId"));
        }
    };

    /**
     * 店家名称是否能点击进入
     */
    private void enableEnterSite() {
        String siteName = MapUtil.getString(data, "siteName");
        TextView txt_site_name = mViewHelper.getView(R.id.txt_site_name);
        if (siteName != null && !siteName.isEmpty()) {
            mViewHelper.setText(R.id.txt_site_name, MapUtil.getString(data, "siteName"));
            txt_site_name.setEnabled(true);
            txt_site_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.setting_view_arrow, 0);
        } else {
            mViewHelper.setText(R.id.txt_site_name, getString(R.string.txt_call_customer_for_manager));
            txt_site_name.setEnabled(false);
            txt_site_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * 尊享码更新，无则隐藏
     */
    private void isShowHonerCode() {
        String luxclubCode = MapUtil.getString(data, "luxclubCode");
        if (luxclubCode != null && !luxclubCode.isEmpty()) {
            mViewHelper.setText(R.id.txt_honour_code, luxclubCode);
        } else {
            mViewHelper.getView(R.id.rly_honour_code).setVisibility(View.GONE);
        }
    }

    /**
     * 凭证图片取前四张
     */
    private List<String> filterNum(List<String> list, int limit) {
        if (limit <= 0) throw new IllegalArgumentException("limit 必须大于0");

        if (limit < list.size()) {
            return list.subList(0, limit);
        } else {
            return list;
        }
    }

    /**
     * 订单取消回调
     */
    private ApiCallback orderCancelCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(final ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                final AlertSuccess dialog = new AlertSuccess.Builder(mContext, getString(R.string.txt_cancel_success)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        EventBus.getDefault().post(new OrderEvent(OrderEvent.COMMAND_ORDER_CANCEL_SUCCESS, response.getData()));
                        finish();
                    }
                }, 1500);
            } else {
                final AlertFail dialog = new AlertFail.Builder(mContext, getString(R.string.txt_cancel_fail)).create();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                }, 1500);
            }
        }
    };

    /**
     * 订单详情回调
     */
    private ApiCallback orderDetailCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                LogUtil.i2(response.getData());
                EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_MY_ORDER_DETAIL, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(OrderDetailActivity.this, response.getText());
            }
        }
    };

    public static void show(Context context, String orderId, int orderState) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(ORDER_STATE, orderState);
        intent.setClass(context, OrderDetailActivity.class);
        context.startActivity(intent);
    }
}
