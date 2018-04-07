package com.hsbank.luxclub.view.main;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hsbank.luxclub.MyApp;
import com.hsbank.luxclub.R;
import com.hsbank.luxclub.mywidget.FloatingLayer;
import com.hsbank.luxclub.provider.ProviderFactory;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.SpanUtils;
import com.hsbank.luxclub.util.UIUtil;
import com.hsbank.luxclub.util.constants.GlobalData;
import com.hsbank.luxclub.util.popupwindow.CommonPopupWindow;
import com.hsbank.luxclub.util.popupwindow.MyPopupWindow;
import com.hsbank.luxclub.util.popupwindow.PopupWindowFunction;
import com.hsbank.luxclub.view.base.ProjectBaseFragment;
import com.hsbank.luxclub.view.main.my.AboutActivity;
import com.hsbank.luxclub.view.main.my.BindCardActivity;
import com.hsbank.luxclub.view.main.my.CoinPurseActivity;
import com.hsbank.luxclub.view.main.my.MessageActivity;
import com.hsbank.luxclub.view.main.my.RechargeHistoryActivity;
import com.hsbank.luxclub.view.main.my.RecordsHistoryActivity;
import com.hsbank.luxclub.view.main.my.SettingsActivity;
import com.hsbank.luxclub.view.main.my.UnbindCardActivity;
import com.hsbank.luxclub.view.main.my.event.MyEvent;
import com.hsbank.util.android.AndroidUtil;
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
import java.util.Locale;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * name：zhuzhenghua
 * create time:2015.11.19
 */
public class MyFragment extends ProjectBaseFragment implements View.OnClickListener {
    private FragmentActivity mActivity;
    /**
     * 会员卡信息
     */
    private Map<String, Object> memberInfoMap;

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if (event.getCommand().equals(MyEvent.COMMAND_MY)) {
            String object = (String) event.getMessage();
            memberInfoMap = JsonUtil.toMap(object);
            GlobalData.getInstance().setMyData(memberInfoMap);

            String businessMobile = MapUtil.getString(memberInfoMap, "businessMobile");
            ProjectUtil.setBusinessMobile(businessMobile);
            updateUi();
        } else if (event.getCommand().equals(MyEvent.BUS_UNREAD_COUNT)) {
            String obj = (String) event.getMessage();
            switchMessage(Integer.parseInt(obj) > 0);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHandler();

        //动态设置距离顶部padding值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            FrameLayout fly_toolbar = mViewHelper.getView(R.id.fly_toolbar);
            fly_toolbar.setPadding(0, UIUtil.getStatusBarHeight(mContext),0,0);
        }
    }

    private void viewHandler() {
        mViewHelper.setText(R.id.balance, getString(R.string.txt_balance));
        mViewHelper.getView(R.id.rly_bind_card).setOnClickListener(this);
        mViewHelper.getView(R.id.setitem_housekeeper).setOnClickListener(this);
        mViewHelper.getView(R.id.setitem_consumption).setOnClickListener(this);
        mViewHelper.getView(R.id.setitem_recharge).setOnClickListener(this);
        mViewHelper.getView(R.id.setitem_setting).setOnClickListener(this);
        mViewHelper.getView(R.id.setitem_about_us).setOnClickListener(this);
        mViewHelper.getView(R.id.setitem_coin_purse).setOnClickListener(this);
        mViewHelper.getView(R.id.img_recharge_activity).setOnClickListener(this);
        mViewHelper.getView(R.id.txt_menu_item).setOnClickListener(this);
        mViewHelper.getView(R.id.btn_exit).setOnClickListener(this);
        mViewHelper.setText(R.id.txt_menu_item, R.string.txt_message);
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle(mViewHelper.getView(R.id.fly_toolbar), getString(R.string.txt_member_center));

        if (ProjectUtil.isLogin()) {
            mViewHelper.getView(R.id.cardView).setVisibility(View.VISIBLE);
            mViewHelper.getView(R.id.rly_bind_card).setVisibility(View.GONE);
            mViewHelper.getView(R.id.card_number).setVisibility(View.VISIBLE);
            mViewHelper.setText(R.id.card_number, ProjectUtil.getCardNo().toUpperCase());
            ProviderFactory.getInstance().luxclub_memberInfo(mContext, SharedPreferencesUtil.getInstance().getToken(),
                    ProjectUtil.getCardNo(), memberInfoCallback);
            // 更新未读消息提示
            String cardNo = ProjectUtil.getCardNo();
            ProviderFactory.getInstance().message_unreadCount(mContext, SharedPreferencesUtil.getInstance().getToken(), cardNo, unreadCountCallback);
        } else {
            mViewHelper.getView(R.id.cardView).setVisibility(View.GONE);
            mViewHelper.getView(R.id.rly_bind_card).setVisibility(View.VISIBLE);
            mViewHelper.getView(R.id.card_number).setVisibility(View.GONE);
            mViewHelper.setText(R.id.txt_balance, "0.00");
        }

    }

    private void updateUi() {
        if (memberInfoMap != null && memberInfoMap.size() != 0) {
            /*未完成订单数量*/
            String orderNumber = MapUtil.getString(memberInfoMap, "orderNumber");
            BigDecimal priceBigDecimal = new BigDecimal(MapUtil.getString(memberInfoMap, "memberBalance"));
            String memberBalance = String.format(Locale.getDefault(), "%1$,.2f", priceBigDecimal.doubleValue());
            mViewHelper.setText(R.id.txt_balance, memberBalance);
        } else {
            mViewHelper.setText(R.id.txt_balance, "0.00");
        }
        final String businessMobile = ProjectUtil.getBusinessMobile();
        // 设置悬浮框点击
        FloatingLayer.getInstance(MyApp.getContext()).setListener(new FloatingLayer.FloatingLayerListener() {
            @Override
            public void onClick() {
                if (TextUtils.isEmpty(businessMobile)) {
                    AndroidUtil.callPhone(MyApp.getContext(), getString(R.string.txt_service_number));
                } else {
                    AndroidUtil.callPhone(MyApp.getContext(), businessMobile);
                }
            }

            @Override
            public void onShow() {

            }

            @Override
            public void onClose() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        String token = SharedPreferencesUtil.getInstance().getToken();
        switch (view.getId()) {
            case R.id.rly_bind_card:
                BindCardActivity.show(mActivity);
                break;

            case R.id.setitem_housekeeper:
                // 我的管家
                View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_houskeeper, null);
                CommonPopupWindow.Builder builder = new CommonPopupWindow.Builder(getContext())
                        .setView(contentView)
                        .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                final CommonPopupWindow popupWindow = builder.create();

                TextView txt_content = (TextView) contentView.findViewById(R.id.txt_content);
                String mobile = MapUtil.getString(memberInfoMap, "businessMobile");
                SpannableStringBuilder stringBuilder = new SpanUtils()
                        .append("尊敬的会员您好，我是您的私人管家")
                        .append(MapUtil.getString(memberInfoMap, "businessName"))
                        .setForegroundColor(getResources().getColor(R.color.yellow_lux))
                        .appendLine(",")
                        .appendLine("我将24小时竭诚为您服务")
                        .appendLine("为您和您家人的生活出行保驾护航")
                        .appendLine("")
                        .append("管家手机号：").appendLine(mobile)
                        .setForegroundColor(getResources().getColor(R.color.yellow_lux))
                        .append("微信号：").appendLine(mobile)
                        .setForegroundColor(getResources().getColor(R.color.yellow_lux))
                        .create();
                txt_content.setText(stringBuilder);
                contentView.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(mViewHelper.getContentView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.setitem_consumption:
                        /*0-消费*/
                if (!TextUtils.isEmpty(token)) {
                    RecordsHistoryActivity.show(mActivity);
                } else {
                    AlertUtil.getInstance().alertMessage(mActivity, getString(R.string.txt_no_bind_card));
                }
                break;

            case R.id.setitem_recharge:
                        /*1-充值*/
                if (!TextUtils.isEmpty(token)) {
                    RechargeHistoryActivity.show(mActivity);
                } else {
                    AlertUtil.getInstance().alertMessage(mActivity, getString(R.string.txt_no_bind_card));
                }
                break;

            case R.id.setitem_coin_purse:
                if (!TextUtils.isEmpty(token)) {
                    CoinPurseActivity.show(mContext);
                } else {
                    AlertUtil.getInstance().alertMessage(mActivity, getString(R.string.txt_no_bind_card));
                }
                break;

            case R.id.setitem_setting:
                SettingsActivity.show(mActivity);
                break;
            case R.id.setitem_about_us:
                AboutActivity.show(getContext());
                break;
            case R.id.img_recharge_activity:
                View parentView = mViewHelper.getView(R.id.parentView);
                if (!TextUtils.isEmpty(token)) {
                    MyPopupWindow.showRechargeDiscount(mActivity, parentView, getString(R.string.txt_recharge_discount), getString(R.string.txt_recharge_discount_activity));
                } else {
                    MyPopupWindow.showRechargeDiscount(mActivity, parentView, getString(R.string.txt_app_member),
                            String.format(getString(R.string.txt_company_introduction), getString(R.string.txt_service_number)));
                }
                break;
            case R.id.txt_menu_item:
                if (!TextUtils.isEmpty(token)) {
                    MessageActivity.show(mContext);
                } else {
                    AlertUtil.getInstance().alertMessage(mContext, getString(R.string.txt_no_bind_card));
                }
                break;
            case R.id.btn_exit:
                MyPopupWindow.showUnbindCard(getContext(), mViewHelper.getContentView(),
                        getString(R.string.txt_unbind_card_delete), getString(R.string.txt_unbind_card), popupwindowCallback);
                break;
        }
    }

    private PopupWindowFunction popupwindowCallback = new PopupWindowFunction() {
        @Override
        public void popupWinFunction(Object o) {
            UnbindCardActivity.show(getContext());
        }
    };

    /**
     * 是否有新消息, 默认为无
     * @param has
     */
    private void switchMessage(boolean has){
        TextView message = mViewHelper.getView(R.id.txt_menu_item);
        if (has){
            message.setTextColor(getResources().getColor(R.color.yellow_lux));
            message.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shape_circle_yellow_4, 0, 0, 0);
        } else {
            message.setTextColor(getResources().getColor(R.color.white));
            message.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * 未读消息数量的回调
     */
    private ApiCallback unreadCountCallback = new ApiCallback() {
        @Override
        public void onError(Request request, Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(MyEvent.BUS_UNREAD_COUNT, response.getData()));
            } else {
                AlertUtil.getInstance().alertMessage(mContext, response.getText());
            }
        }
    };

    private ApiCallback memberInfoCallback = new ApiCallback() {
        @Override
        public void onResponse(ApiResponseBean response) {
            if (response.getCode() == ApiCodeConstant.SUCCESS_CODE) {
                EventBus.getDefault().post(new MyEvent(MyEvent.COMMAND_MY, response.getData()));
                ProjectUtil.setCardNo(MapUtil.getString(memberInfoMap, "memberCardno"));
                ProjectUtil.setUserMobile(MapUtil.getString(memberInfoMap, "memberMobile"));
            } else {
                AlertUtil.getInstance().alertMessage(mActivity, response.getText());
                SharedPreferencesUtil.getInstance().setString(SharedPreferencesUtil.TOKEN, "");
                ProjectUtil.setCardNo("");
                ProjectUtil.setUserMobile("");
                BindCardActivity.show(mActivity);
            }
        }
    };
}

