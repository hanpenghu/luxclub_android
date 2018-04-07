package com.hsbank.util.android.activity.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.hsbank.util.R;
import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.android.AndroidUtil;
import com.hsbank.util.android.util.helper.ViewFindHelper;
import com.hsbank.util.project.util.BaseProjectEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import de.greenrobot.event.EventBus;

/**
 * 基础Activity
 * <p/>
 * fragment是Android3.0以后的东西，为了在低版本中使用fragment就要用到android-support-v4.jar兼容包,
 * 而FragmentActivity就是这个兼容包里面的，它提供了操作fragment的一些方法，其功能跟3.0及以后
 * 的版本的Activity的功能一样
 * <p/>
 * 1、fragmentactivity 继承自activity，用来解决android3.0 之前没有fragment的api，所以在使用<br/>
 *    的时候需要导入support包，同时继承fragmentActivity，这样在activity中就能嵌入fragment来<br/>
 *    实现你想要的布局效果。
 * 2、Android3.0之后就可以直接继承自Activity，并且在其中嵌入使用fragment了。
 * 3、得到FragmentManager的方式：<br/>
 *    Android3.0以下：getSupportFragmentManager()；<br/>
 *    Android3.0以上：getFragmentManager()。<br/>
 * 4. Android5.0以后，提供了很多很多新东西，于是support v7也更新了，出现了AppCompatActivity。
 *    主要是为了使用一些较新的主题
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    /**视图辅助类*/
    protected ViewFindHelper mViewHelper;
    /**当前类的上下文对象*/
    protected Context mContext;
    /**是否响应退出键点击事件*/
    protected boolean mKeyCodeBackFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
        //1.设置屏幕方向为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //2.注册EventBus
        EventBus.getDefault().register(this);
        //3.类成员变量初始化
        mContext = this;
        mViewHelper = new ViewFindHelper(this, getLayoutId());
    }

    /**
     * 得到布局文件Id
     * @return 布局文件Id
     */
    protected abstract int getLayoutId();

    /**
     * EventBus消息处理器
     * @param event     事件
     */
    public void onEventMainThread(BaseProjectEvent event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
        //垃圾回收
        System.gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面跳转时候取消Toast对象的显示
//        AlertUtil.getInstance().cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //页面跳转时候取消Toast对象的显示
//        AlertUtil.getInstance().cancel();
        if (!AndroidUtil.isAppOnForeground(BaseActivity.this.getApplicationContext())) {
            //当前应用进入后台
            AndroidAppInfo.getInstance().setOnForeground(false);
            AndroidAppInfo.getInstance().setStartTimeOnBackground(System.currentTimeMillis());
            //当前应用从前端进入后台时，调用该方法
            onBackground();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!AndroidAppInfo.getInstance().isOnForeground()) {
            //当前应用从后台唤醒，进入前台
            AndroidAppInfo.getInstance().setOnForeground(true);
            //当前应用进入后台，超过指定时间以上，就需要验证手势密码：默认10秒
            if (System.currentTimeMillis() - AndroidAppInfo.getInstance().getStartTimeOnBackground() > AndroidAppInfo.AUTH_GESTURE_INTERVAL) {
                AndroidAppInfo.getInstance().setNeedAuthGesture(true);
            } else {
                AndroidAppInfo.getInstance().setNeedAuthGesture(false);
            }
            AndroidAppInfo.getInstance().setStartTimeOnBackground(0L);
            //当前应用从后台唤醒，进入前台时，调用该方法
            onForeground();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Intent intent = getIntent();
        //finish();
        //startActivity(intent);
    }

    /**当前应用从后台唤醒，进入前台时，调用该方法*/
    public void onForeground() {
    }

    /**当前应用从前端进入后台时，调用该方法*/
    public void onBackground() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isKeyCodeBackFlag()) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle(R.string.util_prompt_title);
                dialogBuilder.setMessage(R.string.util_prompt_system_exit);
                dialogBuilder.setPositiveButton(R.string.util_bt_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        System.exit(0);
                        finish();
                    }
                });
                dialogBuilder.setNegativeButton(R.string.util_bt_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**隐藏键盘*/
    public void hideKeyboard() {
        InputMethodManager im = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if (BaseActivity.this.getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean isKeyCodeBackFlag() {
        return mKeyCodeBackFlag;
    }

    public void setKeyCodeBackFlag(boolean keyCodeBackFlag) {
        this.mKeyCodeBackFlag = keyCodeBackFlag;
    }

    public void goBack() {
        this.finish();
    }
}
