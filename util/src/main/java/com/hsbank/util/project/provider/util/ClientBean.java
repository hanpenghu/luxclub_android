package com.hsbank.util.project.provider.util;

import com.hsbank.util.android.AndroidAppInfo;
import com.hsbank.util.android.AndroidSystemInfo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 客户端统计参数：对应api中的client
 * {
 *    android:{						//Android参数
 *      channel:"",					//发布渠道
 *      md5:"",						//apk签名的md5值
 *      deviceModel:"",				//设备型号
 *      deviceNumber:"",			//设备号码
 *      platformVersion:"",			//操作系统的版本，如：Android 4.3
 *      sdkVersion:""				//SDK的版本，如：19
 *    },
 *    ios:{							//iOS参数
 *      deviceModel:"",				//设备型号，如：iPhone4、iphone6s
 *      systemVersion:""			//操作系统版本，如：9.0.1
 *    },
 *    language:"",					//客户端语言
 *    type:"",           			//客户端类型：website、wechat、android、iOS
 *    version:"",					//客户端版本
 *    website:{},					//网站参数
 *    wechat:{}						//微信参数
 * }
 * 2015-12-20
 */
public class ClientBean {
    /**客户端类型：android*/
    public static final String CLIENT_TYPE_ANDROID = "android";
    /**客户端类型：iOS*/
    public static final String CLIENT_TYPE_IOS = "iOS";
    /**客户端类型：wechat*/
    public static final String CLIENT_TYPE_WECHAT = "wechat";
    /**客户端类型：website*/
    public static final String CLIENT_TYPE_WEBSITE = "website";
    //----------------------------------
    /**Android参数*/
    private AndroidBean android = null;
    /**客户端语言*/
    private String language = null;
    /**客户端类型*/
    private String type = null;
    /**客户端版本*/
    private String version = null;
    //----------------------------------
    /**单例*/
    private static ClientBean instance = null;

    /**私有构造函数*/
    private ClientBean() {
        init();
    }

    /**初始化*/
    public void init() {
        android = AndroidBean.getInstance();
        language = AndroidSystemInfo.getInstance().getLanguage();
        type = CLIENT_TYPE_ANDROID;
        version = AndroidAppInfo.getInstance().getVersionName();
    }

    /**
     * 得到单例
     * @return	单例
     */
    public static synchronized ClientBean getInstance() {
        return instance == null ? instance = new ClientBean() : instance;
    }

    public AndroidBean getAndroid() {
        return android;
    }

    public void setAndroid(AndroidBean android) {
        this.android = android;
    }

    public String getLanguage() {
        if (language == null) {
            language = AndroidSystemInfo.getInstance().getLanguage();
        }
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        if (type == null) {
            type = CLIENT_TYPE_ANDROID;
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        if (version == null) {
            version = AndroidAppInfo.getInstance().getVersionName();
        }
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String toString(){
        return ReflectionToStringBuilder.toString(this);
    }
}