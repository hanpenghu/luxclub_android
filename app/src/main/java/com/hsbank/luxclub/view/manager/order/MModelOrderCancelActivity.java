package com.hsbank.luxclub.view.manager.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.ModelOrderDetailBean;
import com.hsbank.luxclub.provider.apis.ModelApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;

/**
 * Author:      chenliuchun
 * Date:        17/2/17
 * Description: 经理端——模特订单取消页面详情
 * 订单状态(0:提交,1:派单,2:确认, 3:取消, 4:异常,5:待结账,6:已结账)"
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class MModelOrderCancelActivity extends ProjectBaseActivity {

    private static final String ORDER_ID = "order_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_model_order_cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    private void viewHandler() {
        String orderId = getIntent().getStringExtra(ORDER_ID);
        setToolbarTitle(R.string.txt_order_cancel);

        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .orderDetails(SharedPreferencesUtil.getInstance().getToken(), orderId)
                .compose(RxUtil.<ModelOrderDetailBean>compose(this))
                .subscribe(new APISubscriber<ModelOrderDetailBean>() {
                    @Override
                    public void onNext(ModelOrderDetailBean modelOrderDetailBean) {
                        updateUi(modelOrderDetailBean);
                    }
                });
    }

    private void updateUi(ModelOrderDetailBean orderBean) {
        mViewHelper.setText(R.id.txt_model_name, orderBean.getNickname());
        mViewHelper.setText(R.id.txt_order_code, orderBean.getOrderCode());
        mViewHelper.setText(R.id.txt_order_date, orderBean.getCreateDate());
        mViewHelper.setText(R.id.txt_appointment_address, orderBean.getSiteAddress());
        mViewHelper.setText(R.id.txt_honour_code, orderBean.getLuxclubCode());
        mViewHelper.setText(R.id.txt_order_state_name, orderBean.getOrderStatusName());
    }

    public static void show(Context context, String orderId) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.setClass(context, MModelOrderCancelActivity.class);
        context.startActivity(intent);
    }
}
