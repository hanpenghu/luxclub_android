package com.hsbank.luxclub;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.hsbank.luxclub.mywidget.FloatingLayer;
import com.hsbank.luxclub.service.ServiceReceiver;
import com.hsbank.luxclub.util.LogUtil;
import com.hsbank.luxclub.util.ProjectConfig;
import com.hsbank.luxclub.util.ProjectConstant;
import com.hsbank.luxclub.util.ProjectUtil;
import com.hsbank.util.android.AndroidInit;
import com.hsbank.util.android.AndroidUtil;
import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.android.util.dialog.LoadingDialogConfig;
import com.hsbank.util.project.util.BaseProjectConstant;
import com.hsbank.xgpush.PushUtil;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.bugly.crashreport.CrashReport;

import static com.hsbank.xgpush.PushUtil.ID_ACCESS_PRDC;
import static com.hsbank.xgpush.PushUtil.ID_ACCESS_TEST;
import static com.hsbank.xgpush.PushUtil.KEY_ACCESS_PRDC;
import static com.hsbank.xgpush.PushUtil.KEY_ACCESS_TEST;

public class MyApp extends Application {

    private static MyApp myApp;

    public static Context getContext() {
        return myApp.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化应用级上下文
        myApp = this;
        //设置部署环境：发布时置为production
        setAppEnv();
        //初始化通用对象
        AndroidInit.getInstance().init(getApplicationContext());
        // 注册广播接收者
        initReceive(this);
        // 设置公共对象的上下文对象
        SharedPreferencesUtil.getInstance().setContext(this);
        // 注册信鸽推送
        boolean isPushMessage = SharedPreferencesUtil.getInstance().getBoolean(ProjectConstant.IS_PUSH_MESSAGE, true);
        if (isPushMessage) {
            XGPushConfig.enableDebug(this, BuildConfig.DEBUG);
            XGPushConfig.setAccessId(this, BuildConfig.DEBUG ? ID_ACCESS_TEST : ID_ACCESS_PRDC);
            XGPushConfig.setAccessKey(this, BuildConfig.DEBUG ? KEY_ACCESS_TEST : KEY_ACCESS_PRDC);
            String cardNo = SharedPreferencesUtil.getInstance().getString(ProjectConstant.CARDNO);
            PushUtil.INSTANCE.registerMyPush(this, cardNo);
            LogUtil.i2("cardNo: " + cardNo);
        }

        // bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "91e039abbd", BuildConfig.DEBUG);
        //初始化自定义的加载等待动画参数
        LoadingDialogConfig.getInstance().setLayoutId(R.layout.custom_loading_dialog);
        LoadingDialogConfig.getInstance().setStyleId(R.style.custom_loading_dialog);
        LoadingDialogConfig.getInstance().setAnimResourceId(R.anim.custom_loading_dialog);

        // 初始化悬浮框
        final String businessMobile = ProjectUtil.getBusinessMobile();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }
            @Override
            public void onActivityStarted(final Activity activity) {
                FloatingLayer.getInstance(myApp).show(activity, ProjectUtil.isLogin());
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
            public void onActivityResumed(Activity activity) {
            }
            @Override
            public void onActivityPaused(Activity activity) {
                FloatingLayer.getInstance(myApp).close();
            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    // 设置部署环境
    private void setAppEnv() {
        String env;
        String fav = BuildConfig.FLAVOR;
        if ("product".equals(fav)) { // 自有渠道为正式环境
            env = BaseProjectConstant.ENVIRONMENT_FLAG_PRODUCTION;
        } else if ("env_test".equals(fav)) { // 测试接口环境
            env = BaseProjectConstant.ENVIRONMENT_FLAG_TEST;
        } else if ("env_fake".equals(fav)) { // 模拟接口环境
            env = BaseProjectConstant.ENVIRONMENT_FLAG_DEVELOPMENT;
        } else { // 默认为production
            env = BaseProjectConstant.ENVIRONMENT_FLAG_PRODUCTION;
        }
        ProjectConfig.getInstance().setEnvironmentFlag(env);
    }

    private void initReceive(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(new ServiceReceiver(), filter);
    }

}