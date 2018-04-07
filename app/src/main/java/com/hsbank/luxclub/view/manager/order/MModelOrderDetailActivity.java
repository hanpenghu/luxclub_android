package com.hsbank.luxclub.view.manager.order;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.adapter.ImageAddAdapter2;
import com.hsbank.luxclub.adapter.common.CommonAdapter;
import com.hsbank.luxclub.model.MModelOrderDetailBean;
import com.hsbank.luxclub.model.ModelOrderHandleBean;
import com.hsbank.luxclub.model.UploadImageBean;
import com.hsbank.luxclub.mywidget.MyGridView;
import com.hsbank.luxclub.provider.apis.IndexApis;
import com.hsbank.luxclub.provider.apis.ModelApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ImageUtil;
import com.hsbank.luxclub.util.MathUtil2;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.java.arithmetic.Base64;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.ViewHolder;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      chenliuchun
 * Date:        17/2/20
 * Description: 经理端——模特订单详情
 * 订单状态(0:提交,1:派单,2:确认, 4:异常,5:待结账,6:已结账)
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public class MModelOrderDetailActivity extends ProjectBaseActivity implements View.OnClickListener {
    private static final String ORDER_STATE = "order_state";
    private static final String ORDER_ID = "order_id";
    /**
     * 订单详情数据
     */
    private MModelOrderDetailBean orderBean;

    private ArrayList<String> consumeImgNames;
    private ArrayList<String> coinImgNames;
    private ImageAddAdapter2 consumeAdapter;
    private ImageAddAdapter2 coinAdapter;
    private int addImgWhich;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_m_model_order_detail;
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

    private void viewHandler() {
        String orderId = getIntent().getStringExtra(ORDER_ID);
        int orderState = getIntent().getIntExtra(ORDER_STATE, -1);

        setTitleName(orderState);
        mViewHelper.getView(R.id.lly_menber_info).setOnClickListener(this);
        mViewHelper.getView(R.id.test02).setOnClickListener(this);

        if (orderState == 0 || orderState == 1 || orderState == 2) {
            mViewHelper.getView(R.id.lly_submit).setVisibility(View.VISIBLE);
            mViewHelper.getView(R.id.btn_cancel).setOnClickListener(this);
            mViewHelper.getView(R.id.btn_confirm).setOnClickListener(this);
        }
        updateOrderState(orderState);

        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .MOrderDetails(ProjectUtil.getManagerToken(), orderId)
                .compose(RxUtil.<MModelOrderDetailBean>compose(this))
                .subscribe(new APISubscriber<MModelOrderDetailBean>() {
                    @Override
                    public void onNext(MModelOrderDetailBean modelOrderDetailBean) {
                        orderBean = modelOrderDetailBean;
                        updateUi();
                    }
                });
    }

    /**
     * 订单状态(0:提交,1:派单,2:确认, 4:异常,5:待结账,6:已结账)
     * 3 取消订单页面 {@MModelOrderCancelActivity}
     * @param orderState
     */
    private void setTitleName(int orderState) {
        switch (orderState) {
            case 0:
            case 1:
                setToolbarTitle(R.string.txt_confirm_order);
                break;
            case 2:
                setToolbarTitle(R.string.txt_upload_proof);
                break;
            case 5:
                setToolbarTitle(R.string.txt_will_account);
                break;
            case 6:
                setToolbarTitle(R.string.txt_accounted);
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
            case 6:
                CheckBox ckb_accounted = mViewHelper.getView(R.id.ckb_accounted);
                CheckBox ckb_line2 = mViewHelper.getView(R.id.ckb_line2);
                ckb_accounted.setChecked(true);
                ckb_line2.setChecked(true);
            case 2:
            case 5:
                CheckBox ckb_account = mViewHelper.getView(R.id.ckb_account);
                CheckBox ckb_line1 = mViewHelper.getView(R.id.ckb_line1);
                ckb_account.setChecked(true);
                ckb_line1.setChecked(true);
            case 0:
            case 1:
                CheckBox ckb_comfirm = mViewHelper.getView(R.id.ckb_comfirm);
                ckb_comfirm.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_menber_info:
                MemberInformationActivity.show(mContext, orderBean.getCardno());
                break;
            case R.id.btn_cancel:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.txt_tip)
                        .setMessage("确认需要取消订单吗？")
                        .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    cancelOrder();
                                }
                            }
                        }).setNegativeButton(R.string.txt_cancel, null)
                        .show();

                break;
            case R.id.btn_confirm:
                int orderState = getIntent().getIntExtra(ORDER_STATE, -1);
                if (orderState == 0 || orderState == 1) {
                    submitOrder();
                    return;
                }
                if (orderState == 2) {
                    accountOrder();
                    return;
                }
                break;
        }
    }

    /**
     * 取消订单
     */
    private void cancelOrder() {
        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .MOrderCancel(ProjectUtil.getManagerToken(), orderBean.getOrderId(), "")
                .compose(RxUtil.<ModelOrderHandleBean>compose(this))
                .subscribe(new APISubscriber<ModelOrderHandleBean>() {
                    @Override
                    public void onNext(ModelOrderHandleBean modelOrderHandleBean) {
                        MModelOrderCancelActivity.show(mContext, orderBean.getOrderId());
                        finish();
                    }
                });
    }

    /**
     * 提交订单
     */
    private void submitOrder() {
        String roomNumber = mViewHelper.getText(R.id.edt_room_num).trim();
        // 确认订单
        RetrofitHelper.getInstance()
                .create(ModelApis.class, true)
                .MOrderConfirm(ProjectUtil.getManagerToken(), orderBean.getOrderId(), orderBean.getModelId(), roomNumber)
                .compose(RxUtil.<ModelOrderHandleBean>compose(this))
                .subscribe(new APISubscriber<ModelOrderHandleBean>() {
                    @Override
                    public void onNext(ModelOrderHandleBean modelOrderHandleBean) {
                        AlertUtil.getInstance().alertMessage(mContext, "订单确认成功");
                        finish();
                    }
                });
    }

    /**
     * 结账订单
     */
    private void accountOrder() {
        // 上传凭证
        String consumeAmount = mViewHelper.getText(R.id.edt_consume_amount).trim();
        if (consumeAmount.isEmpty() || MathUtil2.isEqual(consumeAmount, "0")){
            AlertUtil.getInstance().alertMessage(this, "请输入消费金额");
            return;
        }
        if (MathUtil2.isEqual(consumeAmount, "0")) {
            AlertUtil.getInstance().alertMessage(this, "请输入消费金额");
            return;
        }

        String coinAmount = mViewHelper.getText(R.id.edt_coin_consume_amount).trim();
        if (consumeAmount.isEmpty() || MathUtil2.isEqual(consumeAmount, "0")){
            if (coinAmount.isEmpty() || MathUtil2.isEqual(coinAmount, "0")){
                AlertUtil.getInstance().alertMessage(this, "请输入零钱包消费金额");
                return;
            }
            if (!NumberUtils.isNumber(coinAmount)) {
                AlertUtil.getInstance().alertMessage(this, "请输入零钱包消费金额的数字类型");
                return;
            }
        }

        consumeAmount = ProjectUtil.getFloatString(consumeAmount);
        coinAmount = ProjectUtil.getFloatString(coinAmount);

        if (consumeAdapter.getItem(0) == null ){
            AlertUtil.getInstance().alertMessage(this, "请添加消费凭证");
            return;
        }
        String[] strings = consumeImgNames.toArray(new String[]{});
        String vouchers = ArrayUtils.toString(strings).replace("{", "").replace("}", "");
        String[] strings2 = coinImgNames.toArray(new String[]{});
        String walletVouchers = ArrayUtils.toString(strings2).replace("{", "").replace("}", "");

        String coinConsumeRemarks = mViewHelper.getText(R.id.edt_coin_consume_remark).trim();

        RetrofitHelper.getInstance().create(ModelApis.class, true)
                .MOrderCheckout(ProjectUtil.getManagerToken(), orderBean.getOrderId(), consumeAmount,
                        vouchers, coinAmount, walletVouchers, coinConsumeRemarks)
                .compose(RxUtil.<ModelOrderHandleBean>compose(this))
                .subscribe(new APISubscriber<ModelOrderHandleBean>() {
                    @Override
                    public void onNext(ModelOrderHandleBean modelOrderHandleBean) {
                        AlertUtil.getInstance().alertMessage(mContext, "上传凭证成功");
                        finish();
                    }
                });
    }

    private void updateUi() {
        int orderState = getIntent().getIntExtra(ORDER_STATE, -1);

        mViewHelper.setText(R.id.txt_model_name, orderBean.getNickname());
        mViewHelper.setText(R.id.txt_order_code, orderBean.getOrderCode());
        mViewHelper.setText(R.id.txt_order_date, orderBean.getCreateDate());
        mViewHelper.setText(R.id.txt_honour_code, orderBean.getLuxclubCode());
        mViewHelper.setText(R.id.txt_appointment_address, orderBean.getSiteAddress());

        updateConsumeAmount(orderState);
        updateConsumeProof(orderState);
        updateCoinConsumeAmount(orderState);
        updateCoinProof(orderState);
        updateCoinConsumeRemark(orderState);
        updateRoomNum(orderState);
    }

    /**
     * 更新消费金额
     * @param orderState
     */
    private void updateConsumeAmount(int orderState) {
        if (orderState == 2 || orderState == 5 || orderState == 6) {
            mViewHelper.getView(R.id.lly_consume_amount).setVisibility(View.VISIBLE);
            if (orderState == 5 || orderState == 6) {
                mViewHelper.setText(R.id.edt_consume_amount, orderBean.getConsumerMoney());
                mViewHelper.getView(R.id.edt_consume_amount).setEnabled(false);
            }
        }
    }

    /**
     * 更新零钱包消费金额
     * @param orderState
     */
    private void updateCoinConsumeAmount(int orderState) {
        if (orderState == 2 || orderState == 5 || orderState == 6) {
            mViewHelper.getView(R.id.lly_coin_consume_amount).setVisibility(View.VISIBLE);
            if (orderState == 5 || orderState == 6) {
                mViewHelper.setText(R.id.edt_coin_consume_amount, orderBean.getConsumerMoney());
                mViewHelper.getView(R.id.edt_coin_consume_amount).setEnabled(false);
            }
        }
    }

    /**
     * 更新零钱包消费备注
     * @param orderState
     */
    private void updateCoinConsumeRemark(int orderState) {
        if (orderState == 2 || orderState == 5 || orderState == 6) {
            mViewHelper.getView(R.id.lly_coin_consume_remark).setVisibility(View.VISIBLE);
            if (orderState == 5 || orderState == 6) {
                mViewHelper.setText(R.id.edt_coin_consume_remark, orderBean.getConsumerMoney());
                mViewHelper.getView(R.id.edt_coin_consume_remark).setEnabled(false);
            }
        }
    }

    /**
     * 更新消费凭证
     * @param orderState
     */
    private void updateConsumeProof(int orderState) {
        if (orderState == 2) {
            mViewHelper.getView(R.id.lly_consume_proof).setVisibility(View.VISIBLE);
            MyGridView gridview_consume = mViewHelper.getView(R.id.gridview_consume);
            consumeImgNames = new ArrayList<>();

            consumeAdapter = new ImageAddAdapter2(this);
            gridview_consume.setAdapter(consumeAdapter);
            gridview_consume.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position) == null) {
                        // 弹窗添加图片
                        addImageDialog();
                        addImgWhich = 1;
                    } else {
                        ConsumeCredentialsActivity.show(mContext, consumeAdapter.getDatas(),
                                position, "network", orderBean.getOrderId());
                    }
                }
            });

        }
        if (orderState == 5 || orderState == 6) {
            final List<String> consumerVoucherUrl = orderBean.getConsumerVoucherUrl();
            if (consumerVoucherUrl == null) {
                return;
            }
            mViewHelper.getView(R.id.lly_consume_proof).setVisibility(View.VISIBLE);
            GridView gridview_consume = mViewHelper.getView(R.id.gridview_consume);
            // 客户端消费凭证adapter
            CommonAdapter<String> commonAdapter = new CommonAdapter<String>(
                    this, R.layout.item_grid_pic, consumerVoucherUrl) {
                @Override
                public void convert(ViewHolder viewHolder, String item, int position) {
                    ImageView img = viewHolder.getView(R.id.img);
                    ImageLoader.getInstance().displayImage(item, img, Constants.networkOptions);
                }
            };
            gridview_consume.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
                    ConsumeCredentialsActivity.show(mContext, new ArrayList<>(consumerVoucherUrl),
                            positionId, "network", orderBean.getOrderId());
                }
            });
            gridview_consume.setAdapter(commonAdapter);
        }
    }

    /**
     * 更新零钱包消费凭证
     * @param orderState
     */
    private void updateCoinProof(int orderState) {
        if (orderState == 2) {
            mViewHelper.getView(R.id.lly_coin_proof).setVisibility(View.VISIBLE);
            MyGridView gridview_coin = mViewHelper.getView(R.id.gridview_coin);
            coinImgNames = new ArrayList<>();
            coinAdapter = new ImageAddAdapter2(this);
            gridview_coin.setAdapter(coinAdapter);
            gridview_coin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position) == null) {
                        // 弹窗添加图片
                        addImageDialog();
                        addImgWhich = 2;
                    } else {
                        ConsumeCredentialsActivity.show(mContext, coinAdapter.getDatas(),
                                position, "network", orderBean.getOrderId());
                    }
                }
            });

        }
        if (orderState == 5 || orderState == 6) {
            final List<String> coinVoucherUrl = orderBean.getWalletVoucherUrl();
            if (coinVoucherUrl == null) {
                return;
            }
            mViewHelper.getView(R.id.lly_coin_proof).setVisibility(View.VISIBLE);
            GridView gridview_coin = mViewHelper.getView(R.id.gridview_coin);
            // 客户端消费凭证adapter
            CommonAdapter<String> commonAdapter = new CommonAdapter<String>(
                    this, R.layout.item_grid_pic, coinVoucherUrl) {
                @Override
                public void convert(ViewHolder viewHolder, String item, int position) {
                    ImageView img = viewHolder.getView(R.id.img);
                    ImageLoader.getInstance().displayImage(item, img, Constants.networkOptions);
                }
            };
            gridview_coin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int positionId, long rowId) {
                    ConsumeCredentialsActivity.show(mContext, new ArrayList<>(coinVoucherUrl),
                            positionId, "network", orderBean.getOrderId());
                }
            });
            gridview_coin.setAdapter(commonAdapter);
        }
    }

    /**
     * 弹窗添加图片
     */
    private void addImageDialog() {
        String[] array = new String[]{"拍照", "从手机相册选择"};
        new AlertDialog.Builder(this)
                .setTitle("请选择图片添加来源")
                .setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // 拍照
                                ImageUtil.imageChooseSwitch(mContext, ImageUtil.TAKE_BIG_PICTURE);
                                break;
                            case 1:
                                // 相册选取
                                ImageUtil.imageChooseSwitch(mContext, ImageUtil.CHOOSE_BIG_PICTURE);
                                break;
                        }
                    }
                }).show();
    }

    /**
     * 更新房间号
     * @param orderState
     */
    private void updateRoomNum(int orderState) {
        mViewHelper.setText(R.id.edt_room_num, orderBean.getRoomNumber());
        if (orderState == 5 || orderState == 6) {
            mViewHelper.getView(R.id.edt_room_num).setEnabled(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intentData) {
        super.onActivityResult(requestCode, resultCode, intentData);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageUtil.CHOOSE_BIG_PICTURE:
                case ImageUtil.TAKE_BIG_PICTURE:
                    ImageUtil.zipImage(ImageUtil.getImageFilePathName());
                    final String imageUrl = ImageUtil.getImageFilePathName();
                    if (!TextUtils.isEmpty(imageUrl)) {
                        RetrofitHelper.getInstance()
                                .create(IndexApis.class, true)
                                .uploadImage(ProjectUtil.getManagerToken(), Base64.encodeFile(imageUrl))
                                .compose(RxUtil.<UploadImageBean>compose(this))
                                .subscribe(new APISubscriber<UploadImageBean>() {
                                    @Override
                                    public void onNext(UploadImageBean uploadImageBean) {
                                        if (addImgWhich == 1) {
                                            consumeAdapter.addData(imageUrl);
                                            consumeImgNames.add(0, uploadImageBean.getImageName());
                                        } else {
                                            coinAdapter.addData(imageUrl);
                                            coinImgNames.add(0, uploadImageBean.getImageName());
                                        }

                                    }
                                });
                    }
                    break;
            }
        }
    }

    public static void show(Context context, String orderId, int orderState) {
        Intent intent = new Intent();
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(ORDER_STATE, orderState);
        intent.setClass(context, MModelOrderDetailActivity.class);
        context.startActivity(intent);
    }
}
