package com.hsbank.util.project.util;

import android.util.Log;

import com.hsbank.util.android.util.JsonUtil;
import com.hsbank.util.java.arithmetic.Base64;
import com.hsbank.util.project.provider.util.ClientBean;

/**
 * Api公共类
 * Created by Administrator on 2015/12/21.
 */
public class BaseProjectApi {
    /**单例*/
    private static BaseProjectApi instance = null;
    //---------------------------------------------------
    /**客户端统计参数：key*/
    public static final String CLIENT_KEY = "client";
    /**客户端统计参数：value*/
    private String client = null;

    /**私有构造函数*/
    protected BaseProjectApi() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 得到单例
     * @return  单例
     */
    public static synchronized BaseProjectApi getInstance() {
        return instance == null ? instance = new BaseProjectApi() : instance;
    }

    /**
     * 设置用于Api请求的client参数
     * @param client
     * @return
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * 得到用于Api请求的client参数：Base64.encode(JsonUtil.toJson(ClientBean.getInstance()));
     * @return
     */
    public String getClient() {
        if (client == null) {
            client = Base64.encodeUTF8(JsonUtil.toJson(ClientBean.getInstance()));
        }
        return client;
    }
}
