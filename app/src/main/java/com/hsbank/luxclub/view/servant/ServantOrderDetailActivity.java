package com.hsbank.luxclub.view.servant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ViewAnimator;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.ContentBean;
import com.hsbank.luxclub.model.ServantOrderDetailBean;
import com.hsbank.luxclub.model.ViewSummaryBean;
import com.hsbank.luxclub.provider.apis.MemberApis;
import com.hsbank.luxclub.provider.apis.ServantApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.WebActivity;

/**
 * Author:      chenliuchun
 * Date:        2018-01-11
 * Description: 移动管家——订单详情
 * 订单状态( 1:待接单, 2:已接单, 3:已完成, 4:已取消)
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ServantOrderDetailActivity extends ProjectBaseActivity implements View.OnClickListener {
    private static final String ORDER_STATE = "order_state";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_STEWARD_ID = "order_steward_id";
    private static final String ORDER_IS_FINISH_SUMMARY = "order_is_finish_summary";

    /**
     * 订单详情数据
     */
    private ServantOrderDetailBean orderBean;
    private String stewardOrderId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_servant_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stewardOrderId = getIntent().getStringExtra(ORDER_STEWARD_ID);
        viewHandler();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void viewHandler() {
        String orderId = getIntent().getStringExtra(ORDER_ID);
        int orderState = getIntent().getIntExtra(ORDER_STATE, 1);

        setTitleName(orderState);
        mViewHelper.getView(R.id.lly_member_info).setOnClickListener(this);

        // 更新底部按钮
        ViewAnimator vam_bottom_btn = mViewHelper.getView(R.id.vam_bottom_btn);
        switch (orderState) {
            case 1:
                vam_bottom_btn.setDisplayedChild(orderState - 1);
                mViewHelper.setOnClickListener(R.id.btn_receive_order, this);
                break;
            case 2:
                vam_bottom_btn.setDisplayedChild(orderState - 1);
                mViewHelper.setOnClickListener(R.id.btn_finish_order, this);
                break;
            case 3:
                vam_bottom_btn.setDisplayedChild(orderState - 1);
                mViewHelper.setOnClickListener(R.id.btn_look_summary, this);

                boolean isFinishSummary = getIntent().getBooleanExtra(ORDER_IS_FINISH_SUMMARY, false);
                if (isFinishSummary) {
                    mViewHelper.setText(R.id.btn_look_summary, "查看服务总结");
                } else {
                    mViewHelper.setText(R.id.btn_look_summary, "上传服务总结");
                }
                break;
        }

        // 禁止订单修改
        mViewHelper.getView(R.id.edt_peop_num).setEnabled(false);
        mViewHelper.getView(R.id.txt_arrive_date).setEnabled(false);
        mViewHelper.getView(R.id.lly_servant).setEnabled(false);
        mViewHelper.getView(R.id.edt_other_remark).setEnabled(false);

        // 更新状态指示器
        updateOrderState(orderState);

        RetrofitHelper.getInstance()
                .create(ServantApis.class, true)
                .details(ProjectUtil.getManagerToken(), orderId, stewardOrderId)
                .compose(RxUtil.<ServantOrderDetailBean>compose(this))
                .subscribe(new APISubscriber<ServantOrderDetailBean>() {
                    @Override
                    public void onNext(ServantOrderDetailBean detailBean) {
                        orderBean = detailBean;
                        updateUi();
                    }
                });
    }

    private void updateUi() {
        mViewHelper.setText(R.id.txt_site_name, orderBean.getSiteName());
        mViewHelper.setText(R.id.txt_order_code, orderBean.getOrderCode());
        mViewHelper.setText(R.id.txt_order_date, orderBean.getCreateDate());
        mViewHelper.setText(R.id.txt_honour_code, orderBean.getLuxclubCode());
        mViewHelper.setText(R.id.txt_member_name, orderBean.getMemberName());
        mViewHelper.setText(R.id.txt_member_sex, orderBean.getSexLabel());
        mViewHelper.setText(R.id.edt_peop_num, orderBean.getReserveNumber());
        mViewHelper.setText(R.id.txt_arrive_date, orderBean.getReserveDate());
        mViewHelper.setText(R.id.txt_servant_name, orderBean.getMobileStewardName());
        mViewHelper.setText(R.id.edt_other_remark, orderBean.getReserveRequire());
    }

    /**
     * 订单状态(0:提交,1:派单,2:确认, 4:异常,5:待结账,6:已结账)
     * 3 取消订单页面 {@MOrderCancelActivity}
     *
     * @param orderState
     */
    private void setTitleName(int orderState) {
        switch (orderState) {
            case 1:
                setToolbarTitle("待结单");
                break;
            case 2:
                setToolbarTitle("已接单");
                break;
            case 3:
                setToolbarTitle("已完成");
                break;
        }
    }

    /**
     * 更新订单状态指示器
     *
     * @param orderState
     */
    private void updateOrderState(int orderState) {
        // 设置进度指示器
        switch (orderState) {
            case 3:
                CheckBox ckb_accounted = mViewHelper.getView(R.id.ckb_accounted);
                CheckBox ckb_line2 = mViewHelper.getView(R.id.ckb_line2);
                ckb_accounted.setChecked(true);
                ckb_line2.setChecked(true);
            case 2:
                CheckBox ckb_account = mViewHelper.getView(R.id.ckb_account);
                CheckBox ckb_line1 = mViewHelper.getView(R.id.ckb_line1);
                ckb_account.setChecked(true);
                ckb_line1.setChecked(true);
            case 1:
                CheckBox ckb_comfirm = mViewHelper.getView(R.id.ckb_comfirm);
                ckb_comfirm.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_member_info:
                if (orderBean != null) {
                    final ProgressDialog dialog = new ProgressDialog(this);
                    dialog.show();
                    RetrofitHelper.getInstance()
                            .create(MemberApis.class, true)
                            .memberDetails(ProjectUtil.getManagerToken(), orderBean.getCardno())
                            .compose(RxUtil.<ContentBean>compose(this))
                            .subscribe(new APISubscriber<ContentBean>() {
                                @Override
                                public void onNext(ContentBean contentBean) {
                                    dialog.cancel();
                                    WebActivity.show(mContext, Constants.WebType.WEB_TYPE_HTML_CODE,
                                            "", "", contentBean.getContent(), "会员信息");
                                }
                            });

                }
                break;
            case R.id.btn_receive_order:
                if (orderBean == null) return;
                RetrofitHelper.getInstance()
                        .create(ServantApis.class, true)
                        .accept(ProjectUtil.getManagerToken(), orderBean.getOrderId(), stewardOrderId)
                        .compose(RxUtil.compose(this))
                        .subscribe(new APISubscriber<Object>() {
                            @Override
                            public void onNext(Object o) {
                                finish();
                            }
                        });
                break;
            case R.id.btn_finish_order:
                if (orderBean == null) return;
                RetrofitHelper.getInstance()
                        .create(ServantApis.class, true)
                        .finish(ProjectUtil.getManagerToken(), orderBean.getOrderId(), stewardOrderId)
                        .compose(RxUtil.compose(this))
                        .subscribe(new APISubscriber<Object>() {
                            @Override
                            public void onNext(Object o) {
                                FinishServiceActivity.show(mContext, orderBean.getOrderId());
                                finish();
                            }
                        });
                break;
            // 上传和查看服务总结
            case R.id.btn_look_summary:
                if (orderBean == null) return;
                boolean isFinishSummary = getIntent().getBooleanExtra(ORDER_IS_FINISH_SUMMARY, false);
                if (isFinishSummary) {
                    RetrofitHelper.getInstance()
                            .create(ServantApis.class, true)
                            .viewSummary(ProjectUtil.getManagerToken(), orderBean.getOrderId())
                            .compose(RxUtil.<ViewSummaryBean>compose(this))
                            .subscribe(new APISubscriber<ViewSummaryBean>() {
                                @Override
                                public void onNext(ViewSummaryBean summaryBean) {
                                    ViewSummaryActivity.show(mContext, orderBean.getOrderId());
                                }
                            });
                } else {
                    UploadSummaryActivity.show(this, orderBean.getOrderId());
                    finish();
                }
                break;
        }
    }

    public static void show(Context context, String orderId, String stewardOrderId, int orderState, boolean isFinishSummary) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(ORDER_STEWARD_ID, stewardOrderId);
        intent.putExtra(ORDER_STATE, orderState);
        intent.putExtra(ORDER_IS_FINISH_SUMMARY, isFinishSummary);
        intent.setClass(context, ServantOrderDetailActivity.class);
        context.startActivity(intent);
    }
}
