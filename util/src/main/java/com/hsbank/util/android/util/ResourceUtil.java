package com.hsbank.util.android.util;

import android.content.Context;
import android.util.Log;

import com.hsbank.util.java.type.StringUtil;

/**
 * 资源_公共类
 * Created by Administrator on 2016/1/10.
 */
public class ResourceUtil {
    /**单例*/
    private static ResourceUtil instance = null;
    //---------------------------------------------------
    //上下文对象
    private Context context;

    /**私有构造函数*/
    private ResourceUtil() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 得到单例
     * @return              单例
     */
    public static synchronized ResourceUtil getInstance() {
        return instance == null ? instance = new ResourceUtil() : instance;
    }

    /**
     * 设置上下文对象
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 得到指定资源Id对应的字符串
     * @param resourceId
     * @return
     */
    public String getString(int resourceId) {
        if (this.context == null) {
            return "";
        } else {
            return StringUtil.dealString(this.context.getResources().getString(resourceId));
        }
    }
}
