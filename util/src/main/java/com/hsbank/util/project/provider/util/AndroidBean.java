package com.hsbank.util.project.provider.util;

import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.android.AndroidSystemInfo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Android客户端统计参数
 * android:{					//Android参数
 *    channel:"",				//发布渠道
 *    md5:"",					//apk签名的md5值
 *    deviceModel:"",			//设备型号
 *    deviceNumber:"",			//设备号码
 *    systemVersion:"",	        //操作系统的版本，如：Android 4.3
 *    sdkVersion:""				//SDK的版本，如：19
 * }
 * 2015-11-19
 */
public class AndroidBean {
    /**发布渠道*/
    private String channel = null;
    /**当前应用的签名证书的md5指纹*/
    private String md5 = null;
    /**设备型号*/
    private String deviceModel = null;
    /**设备号码*/
    private String deviceNumber = null;
    /**操作系统的版本，如：Android 4.3*/
    private String systemVersion = null;
    /**SDK的版本，如：19*/
    private String sdkVersion = null;
    //----------------------------------
    /**单例*/
    private static AndroidBean instance = null;

    /**私有构造函数*/
    private AndroidBean() {
        init();
    }

    /**初始化*/
    public void init() {
        channel = AndroidAppInfo.getInstance().getChannel();
        md5 = AndroidAppInfo.getInstance().getMd5();
        deviceModel = AndroidSystemInfo.getInstance().getDeviceModel();
        deviceNumber = AndroidSystemInfo.getInstance().getDeviceNumber();
        systemVersion = AndroidSystemInfo.getInstance().getVersion();
        sdkVersion = AndroidSystemInfo.getInstance().getSdkVersion();
    }

    /**
     * 得到单例
     * @return	单例
     */
    public static synchronized AndroidBean getInstance() {
        return instance == null ? instance = new AndroidBean() : instance;
    }

    public String getChannel() {
        if (channel == null) {
            channel = AndroidAppInfo.getInstance().getChannel();
        }
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMd5() {
        if (md5 == null) {
            md5 = AndroidAppInfo.getInstance().getMd5();
        }
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDeviceModel() {
        if (deviceModel == null) {
            deviceModel = AndroidSystemInfo.getInstance().getDeviceModel();
        }
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceNumber() {
        if (deviceNumber == null) {
            deviceNumber = AndroidSystemInfo.getInstance().getDeviceNumber();
        }
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getSystemVersion() {
        if (systemVersion == null) {
            systemVersion = AndroidSystemInfo.getInstance().getVersion();
        }
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSdkVersion() {
        if (sdkVersion == null) {
            sdkVersion = AndroidSystemInfo.getInstance().getSdkVersion();
        }
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}