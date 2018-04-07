package com.hsbank.luxclub.view.manager.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.MOrderDetailBean;
import com.hsbank.luxclub.provider.apis.ManagerApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;

/**
 * Author:      chenliuchun
 * Date:        17/2/27
 * Description: 经理端——订单取消页面详情
 * 订单状态(0:提交,1:派单,2:确认, 3:取消, 4:异常,5:待结账,6:已结账)"
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class MOrderCancelActivity extends ProjectBaseActivity {

    private static final String ORDER_ID = "order_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_order_cancel;
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
                .create(ManagerApis.class, true)
                .orderDetails(ProjectUtil.getManagerToken(), orderId)
                .compose(RxUtil.<MOrderDetailBean>compose(this))
                .subscribe(new APISubscriber<MOrderDetailBean>() {
                    @Override
                    public void onNext(MOrderDetailBean modelOrderDetailBean) {
                        updateUi(modelOrderDetailBean);
                    }
                });
    }

    private void updateUi(MOrderDetailBean orderBean) {
        mViewHelper.setText(R.id.txt_order_state_name, orderBean.getOrderStateName());
        mViewHelper.setText(R.id.txt_site_name, orderBean.getSiteName());
        mViewHelper.setText(R.id.txt_order_code, orderBean.getOrderCode());
        mViewHelper.setText(R.id.txt_order_date, orderBean.getCreateDate());
        mViewHelper.setText(R.id.txt_site_addr, orderBean.getSiteAddr());
        mViewHelper.setText(R.id.txt_cancel_reason, orderBean.getCancelReason());
    }

    public static void show(Context context, String orderId) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.setClass(context, MOrderCancelActivity.class);
        context.startActivity(intent);
    }
}
