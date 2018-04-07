package com.hsbank.util.android.util.dialog;

import android.util.Log;

import com.hsbank.util.R;
import com.hsbank.util.android.util.AnimType;

/**
 * 用于网络请求的_加载等待对话框_配置参数
 * Created by Administrator on 2016/4/18.
 */
public class LoadingDialogConfig {
    /**单例*/
    private static LoadingDialogConfig mInstance = null;
    //---------------------------------------------------
    /**加载等待对话框布局Id*/
    private int layoutId = R.layout.util_custom_loading_dialog;
    /**加载等待对话框样式Id*/
    private int styleId = R.style.util_custom_loading_dialog;
    /**加载图片资源Id*/
    private int imageDrawableId = -1;
    /**加载提示语*/
    private String tip = "";
    /**加载动画类型, 默认补间动画*/
    private AnimType type = AnimType.TWEEN;
    /**加载动画资源Id*/
    private int animResourceId = -1;
    /**触摸对话框外部区域时，对话框是否关闭*/
    private boolean canceledOnTouchOutside = false;

    /**私有构造函数*/
    protected LoadingDialogConfig() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 得到单例
     * @return  单例
     */
    public static synchronized LoadingDialogConfig getInstance() {
        return mInstance == null ? mInstance = new LoadingDialogConfig() : mInstance;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public int getImageDrawableId() {
        return imageDrawableId;
    }

    public void setImageDrawableId(int imageDrawableId) {
        this.imageDrawableId = imageDrawableId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public AnimType getType() {
        return type;
    }

    public void setType(AnimType type) {
        this.type = type;
    }

    public int getAnimResourceId() {
        return animResourceId;
    }

    public void setAnimResourceId(int animResourceId) {
        this.animResourceId = animResourceId;
    }

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }
}
