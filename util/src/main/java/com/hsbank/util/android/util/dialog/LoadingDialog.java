package com.hsbank.util.android.util.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.hsbank.util.java.collection.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于网络请求的_加载等待对话框
 * Created by Administrator on 2016/4/18.
 */
public class LoadingDialog {
    /**单例*/
    private static LoadingDialog mInstance = null;
    //---------------------------------------------------
    /**【Context类名，加载等待对话框对象】集合*/
    private static Map<String, Dialog> mDialogMap = null;
    /**【Context类名，请求计数】集合*/
    private static Map<String, Object> mRequestCountMap = null;
    //---------------------------------------------------
    /**当前Context对象的类名*/
    private String mClassName = null;

    /**私有构造函数*/
    protected LoadingDialog() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
        mDialogMap = new HashMap<String, Dialog>();
        mRequestCountMap = new HashMap<String, Object>();
    }

    /**
     * 得到单例
     * @return  单例
     */
    public static synchronized LoadingDialog getInstance() {
        return mInstance == null ? mInstance = new LoadingDialog() : mInstance;
    }

    /**
     * 显示_加载等待对话框
     * @param context
     */
    public void show(Context context) {
        if (context == null) {
            return;
        }
        Dialog dialog = null;
        String className = context.getClass().getName();
        if (mDialogMap.containsKey(className)) {
            dialog = mDialogMap.get(className);
            mRequestCountMap.put(className, MapUtil.getInt(mRequestCountMap, className) + 1);
        } else {
            dialog = LoadingDialogUtil.getDialog(context,
                    LoadingDialogConfig.getInstance().getLayoutId(),
                    LoadingDialogConfig.getInstance().getStyleId(),
                    LoadingDialogConfig.getInstance().getImageDrawableId(),
                    LoadingDialogConfig.getInstance().getTip(),
                    LoadingDialogConfig.getInstance().getType(),
                    LoadingDialogConfig.getInstance().getAnimResourceId(),
                    LoadingDialogConfig.getInstance().isCanceledOnTouchOutside());
            mDialogMap.put(className, dialog);
            mRequestCountMap.put(className, 0);
        }
        dialog.show();
    }

    /**
     * 取消_加载等待对话框
     * @param context
     */
    public void cancel(Context context) {
        if (context == null) {
            return;
        }
        Dialog dialog = null;
        String className = context.getClass().getName();
        if (mDialogMap.containsKey(className)) {
            int requestCount = MapUtil.getInt(mRequestCountMap, className) - 1;
            requestCount = requestCount < 0 ? 0 : requestCount;
            mRequestCountMap.put(className, requestCount);
            if (requestCount == 0) {
                dialog = mDialogMap.remove(className);
                if (dialog != null) {
                    dialog.cancel();
                }
                mRequestCountMap.remove(className);
            }
        }
    }
}
