package com.hsbank.util.project.util;

import android.text.TextUtils;
import android.util.Log;

import com.hsbank.util.android.util.SharedPreferencesUtil;
import com.hsbank.util.java.arithmetic.Base64;
import com.hsbank.util.java.arithmetic.MessageDigestUtil;
import com.hsbank.util.java.constant.DatetimeFormat;
import com.hsbank.util.java.type.DatetimeUtil;
import com.hsbank.util.java.type.NumberUtil;

/**
 * 工程_工具类
 * 单例类
 * @author Arthur.Xie
 * 2009-10-25
 */
public class BaseProjectUtil {
    /**Welcome*/
    public static void welcome() {
        Log.d(BaseProjectUtil.class.getName(), "|::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::|");
        Log.d(BaseProjectUtil.class.getName(), "|                                                            |");
        Log.d(BaseProjectUtil.class.getName(), "|              ...:::  Mayland Base Framework :::...         |");
        Log.d(BaseProjectUtil.class.getName(), "|              -------------------------------------         |");
        Log.d(BaseProjectUtil.class.getName(), "|                (c) 2009 - 2016 Mayland Studio              |");
        Log.d(BaseProjectUtil.class.getName(), "|                     www.maylandstudio.com                  |");
        Log.d(BaseProjectUtil.class.getName(), "|                                                            |");
        Log.d(BaseProjectUtil.class.getName(), "|::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::|\n");
    }

    /**
     * 当前用户是否已登录
     * @return
     */
    public static boolean isLogin() {
        String token = SharedPreferencesUtil.getInstance().getToken();
        return !TextUtils.isEmpty(token);
    }

    /**
     * 根据系统配置的加密策略进行加密
     * @param strInput
     * @return
     */
    public static String encrypt(String strInput) {
        String returnValue = "";
        if (BaseProjectConstant.ENCRYPTION_POLICY_DEFAULT.equals(BaseProjectConfig.getInstance().getEncryptionPolicy())) {
            //<1>.默认加密策略：先Base64加密，再Md5加密
            returnValue = MessageDigestUtil.encode(Base64.encodeUTF8(strInput), MessageDigestUtil.MD5);
        } else if (BaseProjectConstant.ENCRYPTION_POLICY_BASE64.equals(BaseProjectConfig.getInstance().getEncryptionPolicy())) {
            //<2>.Base64加密
            returnValue = Base64.encodeUTF8(strInput);
        } else if (BaseProjectConstant.ENCRYPTION_POLICY_MD5.equals(BaseProjectConfig.getInstance().getEncryptionPolicy())) {
            //<3>.Md5加密
            returnValue = MessageDigestUtil.encode(strInput, MessageDigestUtil.MD5);
        } else {
            //<4>.其它：不加密，明文保存
            returnValue = strInput;
        }
        return returnValue;
    }

    /**
     * 根据指定的加密策略进行加密
     * @param strInput
     * @param encryptionPolicy
     * @return
     */
    public static String encrypt(String strInput, String encryptionPolicy) {
        String returnValue = "";
        if (BaseProjectConstant.ENCRYPTION_POLICY_DEFAULT.equals(encryptionPolicy)) {
            //<1>.默认加密策略：先Base64加密，再Md5加密
            returnValue = MessageDigestUtil.encode(Base64.encodeUTF8(strInput), MessageDigestUtil.MD5);
        } else if (BaseProjectConstant.ENCRYPTION_POLICY_BASE64.equals(encryptionPolicy)) {
            //<2>.Base64加密
            returnValue = Base64.encodeUTF8(strInput);
        } else if (BaseProjectConstant.ENCRYPTION_POLICY_MD5.equals(encryptionPolicy)) {
            //<3>.Md5加密
            returnValue = MessageDigestUtil.encode(strInput, MessageDigestUtil.MD5);
        } else {
            //<4>.其它：不加密，明文保存
            returnValue = strInput;
        }
        return returnValue;
    }

    /**
     * 得到文件名称的前缀
     * 取当前时间的“年-月-日_时分秒” + “3位随机数”作为实际保存的文件名称的前缀
     * @return
     */
    public static String getFileNamePrefix() {
        final int begin = 100;
        final int end = 999;
        return DatetimeUtil.datetimeToString(DatetimeFormat.YYYYMMDDHHMMSS) + "_" + NumberUtil.getRandomInt(begin, end);
    }

    /**
     * 得到一个随机的用户姓名
     * @return
     */
    public static String getRandomName() {
        final int begin = 10000;
        final int end = 99999;
        return "u" + NumberUtil.getRandomInt(begin, end);
    }
}
