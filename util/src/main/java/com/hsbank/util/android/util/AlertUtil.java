package com.hsbank.util.android.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.hsbank.util.R;
import com.hsbank.util.android.util.helper.ActivityViewHelper;

/**
 * 提示消息（包括普通消息、警告消息、错误消息）公共类
 * Created by Administrator on 2016/1/14.
 */
public class AlertUtil {
    /**单例*/
    private static AlertUtil mInstance = null;
    //---------------------------------------------------
    /**Toast对象*/
    private Toast mToast = null;
    /**主线程的handler，避免子线程吐司吐不出来*/
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /**私有构造函数*/
    protected AlertUtil() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 得到单例
     * @return  单例
     */
    public static synchronized AlertUtil getInstance() {
        return mInstance == null ? mInstance = new AlertUtil() : mInstance;
    }

    /**
     * 在屏幕中央弹出一个自动消失的提示框：普通消息
     * @param context
     * @param message
     */
    public void alertMessage(final Context context, final String message) {
        cancel();
        //居中
        //mToast.setGravity(Gravity.CENTER, 0, 0);
        if (Looper.myLooper() == Looper.getMainLooper()){
            //下面5句要写在处理线程里面
            ActivityViewHelper mViewHelper = new ActivityViewHelper(context, R.layout.util_alert_message_view, false);
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.setView(mViewHelper.getRootView());
            mViewHelper.setText(R.id.util_alert_message, message);
            mToast.show();
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                //下面5句要写在处理线程里面
                ActivityViewHelper mViewHelper = new ActivityViewHelper(context, R.layout.util_alert_message_view, false);
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                mToast.setView(mViewHelper.getRootView());
                mViewHelper.setText(R.id.util_alert_message, message);
                mToast.show();
                }
            });
        }
    }

    /**
     * 在屏幕中央弹出一个自动消失的提示框：警告消息
     * @param context
     * @param message
     */
    public void alertWarning(final Context context, final String message) {
        cancel();
        //居中
        //mToast.setGravity(Gravity.CENTER, 0, 0);
        if (Looper.myLooper() == Looper.getMainLooper()){
            //下面5句要写在处理线程里面
            ActivityViewHelper mViewHelper = new ActivityViewHelper(context, R.layout.util_alert_warning_view, false);
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.setView(mViewHelper.getRootView());
            mViewHelper.setText(R.id.util_alert_warning, message);
            mToast.show();
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                //下面5句要写在处理线程里面
                ActivityViewHelper mViewHelper = new ActivityViewHelper(context, R.layout.util_alert_warning_view, false);
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                mToast.setView(mViewHelper.getRootView());
                mViewHelper.setText(R.id.util_alert_warning, message);
                mToast.show();
                }
            });
        }
    }

    /**
     * 在屏幕中央弹出一个自动消失的提示框：错误消息
     * @param context
     * @param message
     */
    public void alertError(final Context context, final String message) {
        cancel();
        //居中
        //mToast.setGravity(Gravity.CENTER, 0, 0);
        if (Looper.myLooper() == Looper.getMainLooper()){
            //下面5句要写在处理线程里面
            ActivityViewHelper mViewHelper = new ActivityViewHelper(context, R.layout.util_alert_error_view, false);
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.setView(mViewHelper.getRootView());
            mViewHelper.setText(R.id.util_alert_error, message);
            mToast.show();
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                //下面5句要写在处理线程里面
                ActivityViewHelper mViewHelper = new ActivityViewHelper(context, R.layout.util_alert_error_view, false);
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                mToast.setView(mViewHelper.getRootView());
                mViewHelper.setText(R.id.util_alert_error, message);
                mToast.show();
                }
            });
        }
    }

    /**
     * 取消当前提示框
     */
    public void cancel(){
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
