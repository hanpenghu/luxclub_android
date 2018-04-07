package com.hsbank.util.android;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Android应用信息
 * Created by Administrator on 2015/12/13.
 */
public class AndroidAppInfo {
    /**单例*/
    private static AndroidAppInfo instance = null;
    //---------------------------------------------------
    /**当前应用进入后台，超过指定时间以上，就需要验证手势密码：默认10秒*/
    public static int AUTH_GESTURE_INTERVAL = 10000;
    //---------------------------------------------------
    /**当前应用的包名*/
    private String packageName = null;
    /**当前应用的名称*/
    private String appName = null;
    /**当前应用的图标*/
    private Drawable appIcon = null;
    /**当前应用的版本名称：
     * versionName（展示给消费者，消费者会通过它认知自己安装的版本。一般我们说的版本号就是这个）
     * 发版时必须更新 */
    private String versionName = null;
    /**当前应用的版本编号：
     * versionCode（对消费者不可见，仅用于应用市场、程序内部识别版本，判断新旧等用途）
     * 发版时必须更新 */
    private int versionCode = 0;
    /**当前应用的权限*/
    private String[] appPermissions = null;
    /**当前应用的签名*/
    private String appSignature = null;
    /**当前应用的签名证书的md5指纹*/
    private String md5 = null;
    /**发布渠道*/
    private String channel = null;
    //---------------------------------------------------
    /**当前应用是否在前端运行*/
    private boolean onForeground = false;
    /**当前应用进入后台的时间（毫秒）*/
    private long startTimeOnBackground = 0L;
    /**当前应用从后台唤醒时，是否需要验证手势密码（即九宫格密码）*/
    private boolean needAuthGesture = false;

    /**私有构造函数*/
    protected AndroidAppInfo() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 得到单例
     * @return  单例
     */
    public static synchronized AndroidAppInfo getInstance() {
        return instance == null ? instance = new AndroidAppInfo() : instance;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String[] getAppPermissions() {
        return appPermissions;
    }

    public void setAppPermissions(String[] appPermissions) {
        this.appPermissions = appPermissions;
    }

    public String getAppSignature() {
        return appSignature;
    }

    public void setAppSignature(String appSignature) {
        this.appSignature = appSignature;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isOnForeground() {
        return onForeground;
    }

    public void setOnForeground(boolean onForeground) {
        this.onForeground = onForeground;
    }

    public long getStartTimeOnBackground() {
        return startTimeOnBackground;
    }

    public void setStartTimeOnBackground(long startTimeOnBackground) {
        this.startTimeOnBackground = startTimeOnBackground;
    }

    public boolean isNeedAuthGesture() {
        return needAuthGesture;
    }

    public void setNeedAuthGesture(boolean needAuthGesture) {
        this.needAuthGesture = needAuthGesture;
    }

    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}
