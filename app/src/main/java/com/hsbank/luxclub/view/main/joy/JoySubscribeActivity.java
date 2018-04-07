package com.hsbank.luxclub.view.main.joy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.pickerview_ios_style.TimePickerView;
import com.hsbank.luxclub.mywidget.setting_item.item.BasicItemViewH;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.provider.apis.OrderApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.AlertSuccess;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.constants.Constants;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.joy.event.JoyEvent;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.http.api.ApiCodeConstant;
import com.hsbank.util.android.util.http.api.ApiResponseBean;
import com.hsbank.util.android.util.http.okhttp.callback.ApiCallback;
import com.hsbank.util.java.collection.ListUtil;
import com.hsbank.util.java.type.DatetimeUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.squareup.okhttp.Request;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * 场所预定页面
 * Created by chen_liuchun on 2016/3/15.
 */
public class JoySubscribeActivity extends ProjectBaseActivity implements View.OnClickListener {

    // 请求数据集合
    private Map<String, Object> mDatas;
    // 场所ID
    private int mSiteId;
    // 场所名称
    private String mSiteName;
    // 区域名称
    private String areaName;
    // 消费预定时间
    String reserveDate;
    final String weekArray[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    // 选择的预定时间
    private String mSelectedTime;
    // 选择的预定日期
    private String mSelectedDate;
    // 日期时间选择item
    BasicItemViewH mBasicItemViewH;
    private String mToken;
    private String mCardNo;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        if (event.getCommand().equals(JoyEvent.COMMAND_JOY_SUBSCRIBE)) {
            String obj = (String) event.getMessage();
            mDatas = JsonUtil.toMap(obj);
            updateUi();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_joy_subscribe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 界面传值
        mSiteId = getIntent().getIntExtra(Constants.SITE_ID, -1);
        mSiteName = getIntent().getStringExtra(Constants.SITE_NAME);
        areaName = getIntent().getStringExtra(Constants.CITY_NAME);
        super.onCreate(savedInstanceState);
        viewHandler();
    }

    protected void viewHandler() {
        String mobile = SharedPreferencesUtil.getInstance().getString(ProjectConstant.MOBILE);
        if (!mobile.isEmpty()) {
            mViewHelper.setText(R.id.edt_contact_num, mobile);
        }
        if (mSiteName != null && !mSiteName.isEmpty()) {
            mViewHelper.setText(R.id.txt_site_name, mSiteName);
        } else {
            mViewHelper.setText(R.id.txt_site_name, getString(R.string.txt_manager_contact));
        }
//        mViewHelper.setText(R.id.txt_site_city, areaName);

        // 设定当前日期
        Date currentDate = DatetimeUtil.getCurrentDate();
        String monthDay = DatetimeUtil.datetimeToString(currentDate, "MM-dd");
        int week = DatetimeUtil.getCurrenDayOfWeek();
        mBasicItemViewH = mViewHelper.getView(R.id.setitem_subscribe_time);
        mBasicItemViewH.setTitle("日期 " + monthDay + "  " + weekArray[week - 1]);

        ((EditText) mViewHelper.getView(R.id.edt_contact_num)).addTextChangedListener(textWacher);
        mViewHelper.getView(R.id.setitem_subscribe_time).setOnClickListener(this);
        mViewHelper.getView(R.id.img_delete).setOnClickListener(this);
        mViewHelper.getView(R.id.txt_comfirm).setOnClickListener(this);
    }

    private void updateUi() {
        final AlertSuccess dialog = new AlertSuccess.Builder(mContext, getString(R.string.txt_subscribe_success)).create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 关闭当前页面，跳转到订单列表页
                dialog.cancel();
                finish();
                SubscribeSuccessActivity.show(mContext, true);
            }
        }, 1300);
    }

    private TextWatcher textWacher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String edt_contact_num_str = mViewHelper.getText(R.id.edt_contact_num).trim();
            if (!edt_contact_num_str.isEmpty()) {
                mViewHelper.getView(R.id.img_delete).setVisibility(View.VISIBLE);
            } else {
                mViewHelper.getView(R.id.img_delete).setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setitem_subscribe_time:
                hideKeyboard();
                // 弹出TimePicker
                TimePickerView pvTime = new TimePickerView(mContext, TimePickerView.Type.MONTH_DAY_HOUR_MIN);// 设置时间样式
                pvTime.setCyclic(true);// 设置表盘是否循环
                pvTime.setCancelable(true);
                //时间选择后回调
                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        // 判断选择的日期时间是否为过去
                        if (date.before(new Date())) {
                            AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_tip_choose_time_wrong));
                            return;
                        }
                        mSelectedDate = DatetimeUtil.datetimeToString(date, "yyyy-MM-dd");
                        mSelectedTime = DatetimeUtil.datetimeToString(date, "HH:mm");
                        int week = DatetimeUtil.getDayOfWeek(date);
                        String monthDay = DatetimeUtil.datetimeToString(date, "MM-dd");
                        mBasicItemViewH.setTitle("日期 " + monthDay + "  " + weekArray[week - 1]);
                        mBasicItemViewH.setSubTitle(mSelectedTime);
                    }
                });
                pvTime.show();
                break;
            case R.id.txt_comfirm:
                // 提交订单
                mViewHelper.getView(R.id.txt_comfirm).setEnabled(false); // 防止连击
                mToken = SharedPreferencesUtil.getInstance().getString(SharedPreferencesUtil.TOKEN);
                mCardNo = ProjectUtil.getCardNo();
                String phoneNum = mViewHelper.getText(R.id.edt_contact_num).trim();
                reserveDate = mSelectedDate + " " + mSelectedTime;
                String reserveNumber = mViewHelper.getText(R.id.edt_peop_num).trim();
                if (!NumberUtils.isDigits(reserveNumber)) {
                    AlertUtil.getInstance().alertMessage(this, "请输入数字类型的人数");
                    return;
                }
                if (Integer.parseInt(reserveNumber) == 0) {
                    AlertUtil.getInstance().alertMessage(this, "参与人数不可为 0");
                    return;
                }

                // 场景
                ArrayList<String> sceneList = new ArrayList<>();
                String scene = "";
                LinearLayout lly_site_choice = mViewHelper.getView(R.id.lly_site_choice);
                for (int i = 0; i < lly_site_choice.getChildCount(); i++) {
                    View childView = lly_site_choice.getChildAt(i);
                    if (!(childView instanceof Checkable)){
                        throw new IllegalArgumentException("lly_site_choice 子控件必须是 CompoundButton 的子类");
                    }
                    boolean checked = ((CompoundButton) childView).isChecked();
                    if (checked){
                        sceneList.add(((CompoundButton) childView).getText().toString());
                    }
                }
                if (sceneList.size() != 0){
                    scene = ListUtil.listToString(sceneList, ",");
                }

                // 订单备注(最多100字)
                String remarks = mViewHelper.getText(R.id.edt_remark);

                if (verifyData()) {
                    if (ProjectUtil.isLogin()) {
                        ProviderFactory.getInstance().luxclub_submitOrder(mContext, mToken, mCardNo, mSiteId + "", phoneNum,
                                reserveNumber, reserveDate, "", remarks, scene, submitCallBack);
                    } else {
                        // 未绑卡用户订单提交
                        final ProgressDialog progressDialog = ProgressDialog.show(this, null, "加载中...", false, true);
                        RetrofitHelper.getInstance()
                                .create(OrderApis.class, true)
                                .submitOrder(mSiteId, "", "", phoneNum,
                                        reserveNumber, reserveDate, "", scene, remarks)
                                .compose(RxUtil.compose(this))
                                .subscribe(new APISubscriber<Object>() {
                                    @Override
                                    public void onNext(Object o) {
                                        progressDialog.dismiss();
                                        SubscribeSuccessActivity.show(mContext, false);
                                        finish();
                                    }
                                });
                    }
                } else {
                    mViewHelper.getView(R.id.txt_comfirm).setEnabled(true);
                    return;
                }
                break;
            case R.id.img_delete:
                mViewHelper.setText(R.id.edt_contact_num, "");
                break;
        }
    }

    /**
     * 校验手机号码合法性
     */
    private boolean verifyData() {
        String errorInfo = "";
        // 校验手机号
        String phoneNum = mViewHelper.getText(R.id.edt_contact_num);
        if (phoneNum.equals("")) {
            errorInfo = getString(R.string.txt_phone_null_tip);
            Toast.makeText(mContext, errorInfo, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Pattern.matches(getString(R.string.txt_regular_phone), phoneNum)) {
            errorInfo = getString(R.string.info_need_mobile_format_error);
            Toast.makeText(mContext, errorInfo, Toast.LENGTH_SHORT).show();
            return false;
        }
        // 校验时间
        if (TextUtils.isEmpty(mSelectedTime)) {
            errorInfo += TextUtils.isEmpty(errorInfo) ? getString(R.string.txt_tip_choose_reserve_time) : "\n" + getString(R.string.txt_tip_choose_reserve_time);
            Toast.makeText(mContext, errorInfo, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (errorInfo.equals("")) {
            return true;
        } else {
            Toast.makeText(mContext, errorInfo, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 提交订单回调
     */
    private ApiCallback submitCallBack = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
            mViewHelper.getView(R.id.txt_comfirm).setEnabled(true);
            Toast.makeText(mContext, R.string.txt_tip_subscribe_fail, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(JoyEvent.COMMAND_JOY_SUBSCRIBE, response.getData()));
                SharedPreferencesUtil.getInstance().setString(ProjectConstant.MOBILE, ((EditText) mViewHelper.getView(R.id.edt_contact_num)).getText().toString());
            } else {
                Toast.makeText(mContext, response.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 打开此预订页面
     *
     * @param context
     * @param siteId
     * @param siteName
     * @param areaName
     */
    public static void show(Context context, int siteId, String siteName, String areaName) {
        Intent intent = new Intent();
        intent.putExtra(Constants.SITE_ID, siteId);
        intent.putExtra(Constants.SITE_NAME, siteName);
        intent.putExtra(Constants.CITY_NAME, areaName);
        intent.setClass(context, JoySubscribeActivity.class);
        context.startActivity(intent);
    }

}

