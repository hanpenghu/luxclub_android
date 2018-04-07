package com.hsbank.util.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hsbank.util.java.arithmetic.Base64;
import com.hsbank.util.java.type.StringUtil;
import com.hsbank.util.project.util.BaseProjectConstant;

/**
 * 本地缓存变量_公共类
 * Created by Administrator on 2015/12/13.
 */
public class SharedPreferencesUtil {
    /**单例*/
    private static SharedPreferencesUtil instance = null;
    //---------------------------------------------------
    /**当前用户登录凭证*/
    public static final String TOKEN = "token";
    /**当前用户头像*/
    public static final String AVATAR = "avatar";
    /**当前用户帐号*/
    public static final String ACCOUNT = "account";
    /**Push消息时关联的用户标识：可以唯一Id、手机号码、帐号、邮箱等*/
    public static final String PUSH_USRE_FLAG = "push_user_flag";
    //---------------------------------------------------
    //上下文对象
    private Context context;
    //数据存储
    private SharedPreferences sp;
    //存数据editor
    private SharedPreferences.Editor editor;

    /**私有构造函数*/
    private SharedPreferencesUtil() {
        Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
    }

    /**
     * 得到单例
     * @return              单例
     */
    public static synchronized SharedPreferencesUtil getInstance() {
        return instance == null ? instance = new SharedPreferencesUtil() : instance;
    }

    /**
     * 设置上下文对象
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
        if (sp == null) {
            //Context.MODE_PRIVATE：         默认模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
            //Context.MODE_APPEND：          会检查文件是否存在,存在就往文件追加内容,否则就创建新文件
            //Context.MODE_WORLD_READABLE：  表示当前文件可以被其他应用读取
            //Context.MODE_WORLD_WRITEABLE： 表示当前文件可以被其他应用写入
            sp = context.getSharedPreferences(BaseProjectConstant.APP_FLAG, Context.MODE_PRIVATE);
            editor = sp.edit();
            editor.apply();
        }
    }

    /**
     * 保存【键,值】对到本地缓存：字符串类型
     * @param key           键
     * @param value         值
     * @return
     */
    public boolean setString(String key, String value) {
        if (editor == null) {
            Log.e(this.getClass().getName(), "editor is null");
            return false;
        } else {
            value = encode(value);
            editor.putString(key, value);
            return editor.commit();
        }
    }

    /**
     * 保存【键,值】对到本地缓存：布尔类型
     * @param key           键
     * @param value         值
     * @return
     */
    public boolean setBoolean(String key, boolean value) {
        if (editor == null) {
            Log.e(this.getClass().getName(), "editor is null");
            return false;
        } else {
            editor.putBoolean(key, value);
            return editor.commit();
        }
    }

    public boolean setInt(String key, int value) {
        if (editor == null) {
            Log.e(this.getClass().getName(), "editor is null");
            return false;
        } else {
            editor.putInt(key, value);
            return editor.commit();
        }
    }

    /**
     * 得到本地保存的【键,值】对：字符串类型
     * @param key               键
     * @return
     */
    public String getString(String key) {
        return getString(key, "");
    }

    /**
     * 得到本地保存的【键,值】对：字符串类型
     * @param key               键
     * @param defaultValue      默认值
     * @return
     */
    public String getString(String key, String defaultValue) {
        String resultValue = sp.getString(key, defaultValue);
        if (resultValue != null && !resultValue.equals(defaultValue)) {
            resultValue = decode(resultValue);
        }
        return StringUtil.dealString(resultValue);
    }

    /**
     * 得到本地保存的【键,值】对：布尔类型
     * @param key               键
     * @return
     */
    public Boolean getBoolean(String key) {
        boolean resultValue = false;
        try {
            resultValue = getBoolean(key, false);
        } catch (Exception e) {
            setBoolean(key, false);
        }
        return resultValue;
    }

    /**
     * 得到本地保存的【键,值】对：布尔类型
     * @param key               键
     * @param defaultValue      默认值
     * @return
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * 移除指定的【键】对应的本地保存的【键,值】对
     */
    public boolean remove(String key) {
        if (key == null || "".equals(key)) {
            return false;
        }
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 得到当前用户登录凭证
     * @return
     */
    public String getToken() {
        return getString(TOKEN, "");
    }

    /**
     * 字符串编码：反转--Base64--再反转
     * @param s
     * @return
     */
    private String encode(String s) {
        String resultValue = StringUtil.dealString(s);
        resultValue = StringUtil.reverse(resultValue);
        resultValue = Base64.encodeUTF8(resultValue);
        return StringUtil.reverse(resultValue);
    }

    /**
     * 字符串编码：反转--Base64--再反转
     * @param s
     * @return
     */
    private String decode(String s) {
        String resultValue = StringUtil.dealString(s);
        resultValue = StringUtil.reverse(resultValue);
        resultValue = Base64.decodeUTF8(resultValue);
        return StringUtil.reverse(resultValue);
    }
}