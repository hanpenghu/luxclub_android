package com.hsbank.luxclub.view.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hsbank.luxclub.R;
import com.hsbank.luxclub.model.CheckUpdateBean;
import com.hsbank.luxclub.provider.apis.EventApis;
import com.hsbank.luxclub.provider.http.APISubscriber;
import com.hsbank.luxclub.provider.http.RetrofitHelper;
import com.hsbank.luxclub.provider.http.RxUtil;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.luxclub.util.check_update.CheckUpdateHelper;
import com.hsbank.luxclub.util.constants.GlobalData;
import com.hsbank.luxclub.util.dialog.CustomDialogUtil;
import com.hsbank.luxclub.util.dialog.listener.DialogFragmentListener;
import com.hsbank.luxclub.util.tool.LogUtil;
import com.hsbank.luxclub.view.base.ProjectBaseActivity;
import com.hsbank.luxclub.view.main.joy.DynamicDetailActivity;
import com.hsbank.luxclub.view.main.joy.JoyDetailActivity;
import com.hsbank.luxclub.view.main.joy.ModelDetailActivity;
import com.hsbank.luxclub.view.main.my.BindCardActivity;
import com.hsbank.luxclub.view.main.my.ModelOrderDetailActivity;
import com.hsbank.luxclub.view.main.my.OrderDetailActivity;
import com.hsbank.luxclub.view.main.my.OrderListFragment;
import com.hsbank.luxclub.view.manager.LoginActivity;
import com.hsbank.luxclub.view.manager.ManagerMainActivity;
import com.hsbank.luxclub.view.manager.lock_pattern.LockPatternUnlockActivity;
import com.hsbank.util.android.util.AlertUtil;
import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.hsbank.xgpush.TPushEvent;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Author:      chenliuchun
 * Date:        2016/3/9
 * Description: 客户端首页
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */
public class MainActivity extends ProjectBaseActivity implements RadioGroup.OnCheckedChangeListener {
    /**
     * 菜单项序号
     */
    public static final int MENU_INDEX_01 = 0; // 娱乐
    public static final int MENU_INDEX_02 = 1; // 订单
    public static final int MENU_INDEX_03 = 2; // 动态
    public static final int MENU_INDEX_04 = 3; // 我的
    private static final String MENU_INDEX = "menu_index";
    /**
     * 当前菜单项序号
     */
    private int mCurrentMenuIndex = -1;
    /**
     * 子视图集合
     */
    private SparseArray<Fragment> mFragmentMap;
    private Handler handler;
    private CheckUpdateHelper mCheckUpdateHelper;

    private boolean isJumpPage; // 启动后是否调到其他页面
    private String mPushJson; //推送消息类型
    private RadioGroup radioGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onEventMainThread(BaseProjectEvent event) {
        super.onEventMainThread(event);
        if (event.getCommand().equals(TPushEvent.COMMAND_TPUSH)) {
            // 信鸽消息点击跳转参数接收
            mPushJson = (String) event.getMessage();
            isJumpPage = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  必须位于父类初始化前
        setKeyCodeBackFlag(true);
        super.onCreate(savedInstanceState);
        // 首先反注册父类的的eventbus，再注册粘性事件（用于接收消息推送），避免冲突
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().registerSticky(this);
        // 是否响应退出键点击事件
        // 初始化页面视图
        viewHandler();
        // 是否显示手势解锁视图
        String entrance = getIntent().getStringExtra("entrance");
        if ("LaunchActivity".equals(entrance)) {
            LockPatternUnlockActivity.show(this, "");
        }
        // 检查应用是否有更新
        mCheckUpdateHelper = new CheckUpdateHelper(this);
        mCheckUpdateHelper.check(true, new CheckUpdateHelper.CheckUpdateCallback() {
            @Override
            public void success() {
                AlertUtil.getInstance().alertMessage(mContext, getString(R.string.str_download_backgroung));
            }

            @Override
            public void fail() {

            }

            @Override
            public void noUpdate() {
                checkPromotion();
            }
        });

//        final String businessMobile = GlobalData.getInstance().getManagerMobile();
        // 设置悬浮框点击
/*
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
*/
    }

    protected void viewHandler() {
        mFragmentMap = new SparseArray<>();
        mFragmentMap.put(MENU_INDEX_01, new JoyFragment());
        mFragmentMap.put(MENU_INDEX_02, new OrderListFragment());
        mFragmentMap.put(MENU_INDEX_03, new DynamicFragment());
        mFragmentMap.put(MENU_INDEX_04, new MyFragment());

        radioGroup = mViewHelper.getView(R.id.rgroup_tab_bottom);
        radioGroup.setOnCheckedChangeListener(this);

        // 设置 "我的" 图标长按3s跳转经理端
        RadioButton myRadioButton = mViewHelper.getView(R.id.rbtn_my);
        myRadioButton.setOnTouchListener(OnTouchListener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int index = getIntent().getIntExtra(MENU_INDEX, -1);
        radioGroup.check(radioGroup.getChildAt(index).getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 显示当前子视图，默认显示第一个
        switch (GlobalData.getInstance().getCurrentSelectItem()) {
            case 0:
                ((RadioButton) mViewHelper.getView(R.id.rbtn_joy)).setChecked(true);
                break;
            case 1:
                ((RadioButton) mViewHelper.getView(R.id.rbtn_order)).setChecked(true);
                break;
            case 2:
                ((RadioButton) mViewHelper.getView(R.id.rbtn_dynamic)).setChecked(true);
                break;
            case 3:
                ((RadioButton) mViewHelper.getView(R.id.rbtn_my)).setChecked(true);
                break;
        }
        // 检查是否有推送的消息跳转
        if (isJumpPage) {
            jumpPage(mPushJson);
            isJumpPage = false; // 跳转后清除消息跳转信息
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCheckUpdateHelper.cancelDownload();
    }

    /**
     * 推送页面跳转
     */
    private void jumpPage(String json) {
        Map<String, Object> map = JsonUtil.toMap(json);
        String type = MapUtil.getString(map, "type");
        switch (type) {
            case "home": // 跳转首页
                int value = MapUtil.getInt(map, "value");
                switch (value) {
                    case 1:
                        ((RadioButton) mViewHelper.getView(R.id.rbtn_joy)).setChecked(true);
                        break;
                    case 2:
                        ((RadioButton) mViewHelper.getView(R.id.rbtn_order)).setChecked(true);
                        break;
                    case 3:
                        ((RadioButton) mViewHelper.getView(R.id.rbtn_dynamic)).setChecked(true);
                        break;
                    case 4:
                        ((RadioButton) mViewHelper.getView(R.id.rbtn_my)).setChecked(true);
                        break;
                }
                break;
            case "detail": // 详情页面
                int siteType = MapUtil.getInt(map, "siteType");
                String detail = MapUtil.getString(map, "detail");
                if (siteType == 7) {  // 模特页面
                    switch (detail) {
                        case "site_detail":
                            String modelId = MapUtil.getString(map, "site_detail");
                            ModelDetailActivity.show(this, modelId);
                            break;
                        case "order_detail":
                            String orderId = MapUtil.getString(map, "order_detail");
                            int order_status = MapUtil.getInt(map, "order_status");
                            ModelOrderDetailActivity.show(this, orderId, convertStatus(order_status));
                            break;
                    }
                } else {  // 其他页面
                    switch (detail) {
                        case "site_detail":
                            String siteId = MapUtil.getString(map, "site_detail");
                            JoyDetailActivity.show(this, siteId);
                            break;
                        case "order_detail":
                            String orderId = MapUtil.getString(map, "order_detail");
                            int order_status = MapUtil.getInt(map, "order_status");
                            OrderDetailActivity.show(this, orderId, convertStatus(order_status));
                            break;
                    }
                }
                break;
            case "dynamic":
                int dynamic_id = MapUtil.getInt(map, "dynamic_id", -1);
                if (dynamic_id != -1) {
                    DynamicDetailActivity.show(this, dynamic_id);
                }
                break;
            default:
                LogUtil.e3("推送消息的类型值不存在");
                break;
        }
    }

    /**
     * state 状态(0:全部, 1:预约中, 2:已预约, 3:已结账, 4:已取消)
     * 转成 orderState 订单状态(0:提交,1:派单,2:确认,3:取消:4,异常,5:待结账,6:已结账)
     *
     * @param state
     * @return
     */
    private int convertStatus(int state) {
        switch (state) {
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:
                return 6;
            case 4:
                return 3;
            default:
                LogUtil.e3("订单状态值的类型值不存在");
                return 0;
        }
    }

    /**
     * 检查首页活动广告
     */
    private void checkPromotion() {
        RetrofitHelper.getInstance()
                .create(EventApis.class, true)
                .getAdPosition("1002")
                .compose(RxUtil.<CheckUpdateBean>compose(this))
                .subscribe(new APISubscriber<CheckUpdateBean>() {
                    @Override
                    public void onNext(CheckUpdateBean checkUpdateBean) {
                        if (checkUpdateBean.getImageUrl() != null) {
                            showPromotionDialog(checkUpdateBean);
                        }
                    }
                });

    }

    private void showPromotionDialog(final CheckUpdateBean checkUpdateBean) {
        CustomDialogUtil.showPromotionDialog(this, checkUpdateBean.getImageUrl(), new DialogFragmentListener() {
            @Override
            public void onDialogClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        WebActivity.show(mContext, checkUpdateBean.getTarget());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        CustomDialogUtil.dismiss(mContext);
                        break;
                }
            }
        });
    }

    /**
     * 我的按钮的touch时间超过三秒跳转经理登录页面
     */
    View.OnTouchListener OnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                handler = checkMyLongClick(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getString(ProjectConstant.MANAGER_TOKEN))) {
                            ManagerMainActivity.show(mContext);
                        } else {
                            LoginActivity.show(mContext);
                        }
                    }
                }, 2500);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                handler.removeMessages(0);
            }
            return false;
        }
    };

    public Handler checkMyLongClick(final Runnable runnable, int time) {
        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                runOnUiThread(runnable);
            }
        };
        handler.sendEmptyMessageDelayed(0, time);
        return handler;
    }

    /**
     * tab切换事件监听器
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_joy:
                switchContent(MENU_INDEX_01, true);
                GlobalData.getInstance().setCurrentSelectItem(0);
                break;
            case R.id.rbtn_order:
                if (ProjectUtil.isLogin()) {
                    switchContent(MENU_INDEX_02, true);
                    GlobalData.getInstance().setCurrentSelectItem(1);
                } else {
                    BindCardActivity.show(this);
                }
                break;
            case R.id.rbtn_dynamic:
                switchContent(MENU_INDEX_03, true);
                GlobalData.getInstance().setCurrentSelectItem(2);
                break;
            case R.id.rbtn_my:
                switchContent(MENU_INDEX_04, true);
                GlobalData.getInstance().setCurrentSelectItem(3);
                break;
        }
    }

    /**
     * 切换fragment视图
     *
     * @param menuIndex 切换视图对应的菜单项序号
     * @param animFlag  是否设置切换动画
     */
    private void switchContent(int menuIndex, boolean animFlag) {
        if (menuIndex == mCurrentMenuIndex) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment currentFragment = mCurrentMenuIndex < 0 ? null : mFragmentMap.get(mCurrentMenuIndex);
        Fragment newFragment = mFragmentMap.get(menuIndex);

        if (animFlag) {
            if (menuIndex > mCurrentMenuIndex) {
                transaction.setCustomAnimations(R.anim.util_enter_from_right, R.anim.util_exit_to_left);
            } else {
                transaction.setCustomAnimations(R.anim.util_enter_from_left, R.anim.util_exit_to_right);
            }
        }
        if (currentFragment != null) {
            currentFragment.onPause();
        }
        if (newFragment.isAdded()) {
            newFragment.onResume();
        } else {
            transaction.add(R.id.main_content, newFragment, newFragment.getClass().getSimpleName());
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.show(newFragment);
        transaction.commit();

        mCurrentMenuIndex = menuIndex;
    }

    public static void show(Context context, int index) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra(MENU_INDEX, index);
        context.startActivity(intent);
    }

}
