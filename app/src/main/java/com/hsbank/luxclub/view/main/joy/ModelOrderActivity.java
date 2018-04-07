package com.hsbank.luxclub.view.main.joy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.OrderSubmitedBean;
import com.hsbank.luxclub.model.SiteInfoBean;
import com.hsbank.luxclub.provider.apis.ModelApis;
import com.hsbank.luxclub.provider.apis.SiteInfoApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.tool.ToastUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.MainActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by chenliuchun on 17/2/13.
 */

public class ModelOrderActivity extends ProjectBaseActivity implements View.OnClickListener {

    private int[] siteIdArray;
    private String[] siteNameArray;
    public static final String MODEL_ID = "model_id";
    // 当前选择的地址索引
    private int currentSiteIndex = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_model_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHandler();
        RetrofitHelper.getInstance()
                .create(SiteInfoApis.class, true)
                .siteInfoPageList(11, 7, 10, 1)
                .compose(RxUtil.<List<SiteInfoBean>>compose(this))
                .subscribe(new APISubscriber<List<SiteInfoBean>>() {
                    @Override
                    public void onNext(List<SiteInfoBean> siteInfoBeanList) {
                        if (siteInfoBeanList.size() == 0) return;
                        siteIdArray = new int[siteInfoBeanList.size()];
                        siteNameArray = new String[siteInfoBeanList.size()];
                        for (int i = 0; i < siteInfoBeanList.size(); i++) {
                            siteIdArray[i] = siteInfoBeanList.get(i).getSiteId();
                            siteNameArray[i] = siteInfoBeanList.get(i).getSiteName();
                        }
                    }
                });
    }

    private void viewHandler() {
        setToolbarTitle("模特预约");
        mViewHelper.getView(R.id.txt_address_choose).setOnClickListener(this);
        mViewHelper.getView(R.id.btn_order).setOnClickListener(this);
        
        // 测试用
        mViewHelper.setText(R.id.edt_phone_num, "13812345678");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_address_choose:
                new AlertDialog.Builder(this)
                        .setTitle("请选择以下场所")
                        .setItems(siteNameArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                currentSiteIndex = which;
                                mViewHelper.setText(R.id.txt_address_choose, siteNameArray[which]);
                            }
                        }).show();
                break;
            case R.id.btn_order:
                if (!ProjectUtil.isLogin()) {
                    ToastUtil.show("您尚未绑定会员卡，请绑卡后再预定");
                    return;
                }
                String modelId = getIntent().getStringExtra(MODEL_ID);
                if (currentSiteIndex == -1){
                    AlertUtil.getInstance().alertMessage(this, "请选择地址");
                    return;
                }
                int siteId = siteIdArray[currentSiteIndex];
                String phoneNumber = mViewHelper.getText(R.id.edt_phone_num).trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    AlertUtil.getInstance().alertMessage(this, "请填写联系电话");
                    return;
                }
                String roomNumber = mViewHelper.getText(R.id.edt_room_num).trim();
                if (TextUtils.isEmpty(roomNumber)) {
                    AlertUtil.getInstance().alertMessage(this, "请填写门牌号");
                    return;
                }
                RetrofitHelper.getInstance()
                        .create(ModelApis.class, true)
                        .submit(SharedPreferencesUtil.getInstance().getToken(), modelId, phoneNumber, siteId, roomNumber)
                        .compose(RxUtil.<OrderSubmitedBean>compose(this))
                        .subscribe(new APISubscriber<OrderSubmitedBean>() {
                            @Override
                            public void onNext(OrderSubmitedBean orderSubmitedBean) {
                                MainActivity.show(mContext, 1);
                            }
                        });
                break;
        }
    }

    public static void show(Context context, String modelId) {
        Intent intent = new Intent();
        intent.putExtra(MODEL_ID, modelId);
        intent.setClass(context, ModelOrderActivity.class);
        context.startActivity(intent);
    }

}
