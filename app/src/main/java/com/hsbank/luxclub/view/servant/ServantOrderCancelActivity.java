package com.hsbank.luxclub.view.servant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.ServantOrderDetailBean;
import com.hsbank.luxclub.provider.apis.ServantApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;

/**
 * Author:      chenliuchun
 * Date:        2018-01-16
 * Description: 移动管家——订单取消页面详情
 * 订单状态 4
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ServantOrderCancelActivity extends ProjectBaseActivity {

    private static final String ORDER_ID = "order_id";
    private static final String ORDER_STEWARD_ID = "order_steward_id";
    private String stewardOrderId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_servant_order_cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stewardOrderId = getIntent().getStringExtra(ORDER_STEWARD_ID);
        viewHandler();
    }

    private void viewHandler() {
        setToolbarTitle(R.string.txt_order_cancel);
        String orderId = getIntent().getStringExtra(ORDER_ID);

        RetrofitHelper.getInstance()
                .create(ServantApis.class, true)
                .details(ProjectUtil.getManagerToken(), orderId, stewardOrderId)
                .compose(RxUtil.<ServantOrderDetailBean>compose(this))
                .subscribe(new APISubscriber<ServantOrderDetailBean>() {
                    @Override
                    public void onNext(ServantOrderDetailBean detailBean) {
                        updateUi(detailBean);
                    }
                });
    }

    private void updateUi(ServantOrderDetailBean orderBean) {
        mViewHelper.setText(R.id.txt_order_state_name, orderBean.getStateName());
        mViewHelper.setText(R.id.txt_site_name, orderBean.getSiteName());
        mViewHelper.setText(R.id.txt_order_code, orderBean.getOrderCode());
        mViewHelper.setText(R.id.txt_order_date, orderBean.getCreateDate());
        mViewHelper.setText(R.id.txt_site_addr, orderBean.getSiteAddr());
        mViewHelper.setText(R.id.txt_cancel_reason, orderBean.getCancelReason());
    }

    public static void show(Context context, String orderId, String stewardOrderId) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(ORDER_STEWARD_ID, stewardOrderId);
        intent.setClass(context, ServantOrderCancelActivity.class);
        context.startActivity(intent);
    }
}
