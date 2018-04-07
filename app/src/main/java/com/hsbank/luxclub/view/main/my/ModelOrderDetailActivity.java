package com.hsbank.luxclub.view.main.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.ModelOrderHandleBean;
import com.hsbank.luxclub.model.ModelOrderDetailBean;
import com.hsbank.luxclub.provider.apis.ModelApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.util.popupwindow.PopupWindowFunction;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.joy.ModelDetailActivity;
import com.hsbank.luxclub.view.manager.order.ConsumeCredentialsActivity;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/2/17
 * Description: 模特订单详情
 * 订单状态(0:提交,1:派单,2:确认, 3:取消, 4:异常,5:待结账,6:已结账)"
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class ModelOrderDetailActivity extends ProjectBaseActivity implements View.OnClickListener {
    private static final String ORDER_STATE = "order_state";
    private static final String ORDER_ID = "order_id";
    /**
     * 订单详情数据
     */
    private ModelOrderDetailBean orderBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_model_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        int orderState = getIntent().getIntExtra(ORDER_STATE, -1);
        if (orderState == 0 || orderState == 1) {
            inflater.inflate(R.menu.menu_order_cancel, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order_cancel:
                MyPopupWindow.showConfirmWin(mContext, mViewHelper.getContentView(), "",
                        getString(R.string.txt_confirm_cancel), getString(R.string.txt_ok), new PopupWindowFunction() {
                            @Override
                            public void popupWinFunction(Object o) {
                                RetrofitHelper.getInstance()
                                        .create(ModelApis.class, true)
                                        .orderCancel(SharedPreferencesUtil.getInstance().getToken(), orderBean.getOrderId())
                                        .compose(RxUtil.<ModelOrderHandleBean>compose(mContext))
                                        .subscribe(new APISubscriber<ModelOrderHandleBean>() {
                                            @Override
                                            public void onNext(ModelOrderHandleBean modelOrderHandleBean) {
                                                startNext();
                                            }
                                        });

                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 跳转到取消页面
     */
    private void startNext() {
        final AlertSuccess dialog = new AlertSuccess.Builder(this, getString(R.string.txt_cancel_success)).create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                show(mContext, orderBean.getOrderId(), 3);
            }
        }, 1500);
    }

    private void viewHandler() {
        String orderId = getIntent().getStringExtra(ORDER_ID);
        int orderState = getIntent().getIntExtra(ORDER_STATE, -1);
        if (orderState == 3){
            setToolbarTitle(R.string.txt_order_cancel);
        } else {
            setToolbarTitle(R.string.txt_model_order_detail);
        }
        mViewHelper.getView(R.id.lly_model_name).setOnClickListener(this);
        updateOrderState(orderState);

        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .orderDetails(SharedPreferencesUtil.getInstance().getToken(), orderId)
                .compose(RxUtil.<ModelOrderDetailBean>compose(this))
                .subscribe(new APISubscriber<ModelOrderDetailBean>() {
                    @Override
                    public void onNext(ModelOrderDetailBean modelOrderDetailBean) {
                        orderBean = modelOrderDetailBean;
                        updateUi();
                    }
                });
    }

    /**
     * 更新订单状态
     *
     * @param orderState
     */
    private void updateOrderState(int orderState) {
        // 设置进度指示器
        switch (orderState) {
            case 3:
                mViewHelper.getView(R.id.lly_order_state).setVisibility(View.GONE);
                break;
            case 6:
                CheckBox ckb_order_completed = mViewHelper.getView(R.id.ckb_order_completed);
                CheckBox ckb_line2 = mViewHelper.getView(R.id.ckb_line2);
                ckb_order_completed.setChecked(true);
                ckb_line2.setChecked(true);
            case 2:
            case 5:
                CheckBox ckb_order_appointment = mViewHelper.getView(R.id.ckb_order_appointment);
                CheckBox ckb_line1 = mViewHelper.getView(R.id.ckb_line1);
                ckb_order_appointment.setChecked(true);
                ckb_line1.setChecked(true);
            case 0:
            case 1:
                CheckBox ckb_order_commit = mViewHelper.getView(R.id.ckb_order_commit);
                ckb_order_commit.setChecked(true);
        }
        // 设置当前订单状态图标
        int drwRes;
        switch (orderState) {
            case 6:
                drwRes = R.drawable.img_order_submit_gold;
                break;
            case 2:
            case 5:
                drwRes = R.drawable.img_order_appoiment_gold;
                break;
            case 0:
            case 1:
                drwRes = R.drawable.img_order_completed_gold;
                break;
            case 3:
                drwRes = R.drawable.img_order_cancel;
                ImageView img_circle = mViewHelper.getView(R.id.img_circle);
                img_circle.setBackgroundResource(R.drawable.shape_circle_red);
                TextView txt_order_state_name = mViewHelper.getView(R.id.txt_order_state_name);
                txt_order_state_name.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                return;
        }
        mViewHelper.setImageResource(R.id.img_order_state, drwRes);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_model_name:
                ModelDetailActivity.show(this, orderBean.getOrderId());
                break;
        }
    }

    private void updateUi() {
        mViewHelper.setText(R.id.txt_model_name, orderBean.getNickname());
        mViewHelper.setText(R.id.txt_consume_money, orderBean.getConsumerMoney());
        mViewHelper.setText(R.id.txt_order_code, orderBean.getOrderCode());
        mViewHelper.setText(R.id.txt_order_date, orderBean.getCreateDate());
        mViewHelper.setText(R.id.txt_contact_mobile, orderBean.getContactNumber());
        mViewHelper.setText(R.id.txt_honour_code, orderBean.getLuxclubCode());
        mViewHelper.setText(R.id.txt_appointment_address, orderBean.getSiteAddress());
        mViewHelper.setText(R.id.txt_order_state_name, orderBean.getOrderStatusName());

        int orderState = getIntent().getIntExtra(ORDER_STATE, -1);
        updateConsumeAmount(orderState);
        updateConsumeList(orderState);
        updateConsumeRemark(orderState);
        updateRoomNum(orderState);
    }

    private void updateConsumeAmount(int orderState) {
        if (orderState == 6) {
            mViewHelper.getView(R.id.lly_coin_consume_amount).setVisibility(View.VISIBLE);
            mViewHelper.setText(R.id.txt_coin_comsume_money, orderBean.getWalletAmount());
        }
    }

    private void updateConsumeList(int orderState) {
        if (orderState == 6) {
            List<String> consumerVoucherUrl = orderBean.getConsumerVoucherUrl();
            if (consumerVoucherUrl == null) {
                return;
            }
            mViewHelper.getView(R.id.lly_consume_list).setVisibility(View.VISIBLE);
            GridView gridView = mViewHelper.getView(R.id.gridview);
            // 客户端消费凭证adapter
            CommonAdapter<String> commonAdapter = new CommonAdapter<String>(
                    this, R.layout.item_grid_pic, consumerVoucherUrl) {
                @Override
                public void convert(ViewHolder viewHolder, String item, int position) {
                    ImageView img = viewHolder.getView(R.id.img);
                    ImageLoader.getInstance().displayImage(item, img, Constants.networkOptions);
                }
            };
            gridView.setOnItemClickListener(itemClickListener);
            gridView.setAdapter(commonAdapter);
        }
    }

    /**
     * 零钱包凭证图片点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
            ConsumeCredentialsActivity.show(mContext, (ArrayList<String>) orderBean.getConsumerVoucherUrl(),
                    positionId, "imageUrl", orderBean.getOrderId());
        }
    };

    private void updateConsumeRemark(int orderState) {
        if (orderState == 6) {
            mViewHelper.getView(R.id.lly_coin_consume_remark).setVisibility(View.VISIBLE);
            mViewHelper.setText(R.id.txt_coin_consume_remark, orderBean.getWalletRemarks());
        }
    }

    private void updateRoomNum(int orderState) {
        if (orderState == 6) {
            mViewHelper.getView(R.id.lly_room_num).setVisibility(View.VISIBLE);
            mViewHelper.setText(R.id.txt_room_num, orderBean.getRoomNumber());
        }
    }

    public static void show(Context context, String orderId, int orderState) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(ORDER_STATE, orderState);
        intent.setClass(context, ModelOrderDetailActivity.class);
        context.startActivity(intent);
    }
}
