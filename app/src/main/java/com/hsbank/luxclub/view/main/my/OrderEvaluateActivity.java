package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.provider.apis.OrderApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.constants.GlobalData;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.java.collection.MapUtil;

import java.util.Map;

/**
 * 订单评价
 * Created by chenliuchun on 17/2/17.
 */

public class OrderEvaluateActivity extends ProjectBaseActivity implements View.OnClickListener{

    public static final String ORDER_ID = "order_id";
    public static final String ORDER_INFO = "order_info";
    public static final String EVALUATE = "evaluate";
    public static final String IS_EVALUATE = "is_evaluate";

    // 当前页面状态 T：评价过  F：未评价过(默认)
    private boolean isEvaluate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_evaluate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    private void viewHandler() {
        setToolbarTitle("我要评价");

        Bundle extra = getIntent().getBundleExtra(ORDER_INFO);
        String siteName = extra.getString("siteName", "");
        String orderCode = extra.getString("orderCode", "");
        String siteAddr = extra.getString("siteAddr", "");
        mViewHelper.setText(R.id.txt_site_name, siteName);
        mViewHelper.setText(R.id.txt_order_code, orderCode);
        mViewHelper.setText(R.id.txt_site_addr, siteAddr);
        Map<String, Object> myData = GlobalData.getInstance().getMyData();
        if (myData != null) {
            String memberName = MapUtil.getString(myData, "memberName");
            mViewHelper.setText(R.id.txt_serve_name, memberName);
        }

        isEvaluate = getIntent().getBooleanExtra(IS_EVALUATE, false);
        RatingBar ratbar_shop = mViewHelper.getView(R.id.ratbar_shop);
        RatingBar ratbar_waiter = mViewHelper.getView(R.id.ratbar_waiter);
        EditText edt_leave_msg = mViewHelper.getView(R.id.edt_leave_msg);
        if (isEvaluate) {
            String evaluateJson = getIntent().getStringExtra(EVALUATE);
            Map<String, Object> map = JsonUtil.toMap(evaluateJson);
            float clubEvaluate = MapUtil.getFloat(map, "clubEvaluate");
            float waiterEvaluate = MapUtil.getFloat(map, "waiterEvaluate");
            String message = MapUtil.getString(map, "message");

            ratbar_shop.setRating(clubEvaluate);
            ratbar_waiter.setRating(waiterEvaluate);
            mViewHelper.setText(R.id.edt_leave_msg, message);
            edt_leave_msg.setEnabled(false);
            ratbar_shop.setIsIndicator(true);
            ratbar_waiter.setIsIndicator(true);
            mViewHelper.getView(R.id.txt_submit).setVisibility(View.GONE);
        } else {
            mViewHelper.getView(R.id.txt_submit).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_submit){
            RatingBar ratbar_shop = mViewHelper.getView(R.id.ratbar_shop);
            RatingBar ratbar_waiter = mViewHelper.getView(R.id.ratbar_waiter);
            String ratingShop = ratbar_shop.getRating() + "";
            String ratingWaiter = ratbar_waiter.getRating() + "";
            String orderId = getIntent().getBundleExtra(ORDER_INFO).getString(ORDER_ID);
            String txt_leave_msg = mViewHelper.getText(R.id.edt_leave_msg).trim();
            RetrofitHelper.getInstance()
                    .create(OrderApis.class, true)
                    .submitEvaluate(SharedPreferencesUtil.getInstance().getToken(), orderId, ratingShop, ratingWaiter, txt_leave_msg)
                    .compose(RxUtil.compose(this))
                    .subscribe(new APISubscriber<Object>() {
                        @Override
                        public void onNext(Object o) {
                            finish();
                        }
                    });
        }
    }

    /**
     * 未评价的进入方法
     * @param context
     * @param orderInfo
     */
    public static void showEvaluating(Context context, Bundle orderInfo) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_INFO, orderInfo);
        intent.putExtra(IS_EVALUATE, false);
        intent.setClass(context, OrderEvaluateActivity.class);
        context.startActivity(intent);
    }

    /**
     * 已评价进入的方法
     * @param context
     * @param orderInfo
     * @param evaluateJson
     */
    public static void showEvaluated(Context context, Bundle orderInfo, String evaluateJson) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_INFO, orderInfo);
        intent.putExtra(EVALUATE, evaluateJson);
        intent.putExtra(IS_EVALUATE, true);
        intent.setClass(context, OrderEvaluateActivity.class);
        context.startActivity(intent);
    }
}
