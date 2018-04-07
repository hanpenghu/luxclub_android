package com.hsbank.util.java.collection;

import com.hsbank.util.java.constant.BasicTypeDefaultValue;
import com.hsbank.util.java.constant.DatetimeFormat;
import com.hsbank.util.java.type.DatetimeUtil;
import com.hsbank.util.java.type.NumberUtil;
import com.hsbank.util.java.type.StringUtil;

import java.util.Date;
import java.util.Map;

/**
 * Map集合_工具类
 * @author arthur_xie
 * 2013-05-27
 */
public class MapUtil {
    /**
     * 从指定map中得到指定key的值，并转换成字符串类型
     * @param map				集合
     * @param key				键
     * @param defaultValue		默认值
     * @return					值
     */
    public static String getString(Map<String, Object> map, String key, String defaultValue) {
        String returnValue = defaultValue;
        if (key != null && !"".equals(key) && map != null && map.get(key) != null) {
            returnValue = map.get(key).toString().trim();
            returnValue = "null".equals(returnValue) ? "" : returnValue;
        }
        return returnValue;
    }

    /**
     * 从指定map中得到指定key的值，并转换成字符串类型
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static String getString(Map<String, Object> map, String key) {
        return getString(map, key, BasicTypeDefaultValue.DEFAULT_STRING);
    }

    /**
     * 从指定map中得到指定key的值，并转换成布尔类型
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static boolean getBoolean(Map<String, Object> map, String key) {
        String s = getString(map, key, BasicTypeDefaultValue.DEFAULT_STRING);
        return StringUtil.toBoolean(s, BasicTypeDefaultValue.DEFAULT_BOOLEAN);
    }

    /**
     * 从指定map中得到指定key的值，并转换成整型
     * @param map				集合
     * @param key				键
     * @param defaultValue		默认值
     * @return					值
     */
    public static int getInt(Map<String, Object> map, String key, int defaultValue) {
        int returnValue = defaultValue;
        if (key != null && !"".equals(key) && map != null && map.get(key) != null) {
            returnValue = NumberUtil.toInt(map.get(key).toString().trim(), defaultValue);
        }
        return returnValue;
    }

    /**
     * 从指定map中得到指定key的值，并转换成整型
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static int getInt(Map<String, Object> map, String key) {
        return getInt(map, key, BasicTypeDefaultValue.DEFAULT_INT);
    }

    /**
     * 从指定map中得到指定key的值，并转换成长整型
     * @param map				集合
     * @param key				键
     * @param defaultValue		默认值
     * @return					值
     */
    public static long getLong(Map<String, Object> map, String key, long defaultValue) {
        long returnValue = defaultValue;
        if (key != null && !"".equals(key) && map != null && map.get(key) != null) {
            returnValue = NumberUtil.toLong(map.get(key).toString().trim(), defaultValue);
        }
        return returnValue;
    }

    /**
     * 从指定map中得到指定key的值，并转换成长整型
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static long getLong(Map<String, Object> map, String key) {
        return getLong(map, key, BasicTypeDefaultValue.DEFAULT_LONG);
    }

    /**
     * 从指定map中得到指定key的值，并转换成浮点型
     * @param map				集合
     * @param key				键
     * @param defaultValue		默认值
     * @return					值
     */
    public static float getFloat(Map<String, Object> map, String key, float defaultValue) {
        float returnValue = defaultValue;
        if (key != null && !"".equals(key) && map != null && map.get(key) != null) {
            returnValue = NumberUtil.toFloat(map.get(key).toString().trim(), defaultValue);
        }
        return returnValue;
    }

    /**
     * 从指定map中得到指定key的值，并转换成浮点型
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static float getFloat(Map<String, Object> map, String key) {
        return getFloat(map, key, BasicTypeDefaultValue.DEFAULT_FLOAT);
    }

    /**
     * 从指定map中得到指定key的值，并转换成浮点型
     * @param map				集合
     * @param key				键
     * @param defaultValue		默认值
     * @return					值
     */
    public static double getDouble(Map<String, Object> map, String key, double defaultValue) {
        double returnValue = defaultValue;
        if (key != null && !"".equals(key) && map != null && map.get(key) != null) {
            returnValue = NumberUtil.toDouble(map.get(key).toString().trim(), defaultValue);
        }
        return returnValue;
    }

    /**
     * 从指定map中得到指定key的值，并转换成浮点型
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static double getDouble(Map<String, Object> map, String key) {
        return getDouble(map, key, BasicTypeDefaultValue.DEFAULT_DOUBLE);
    }

    /**
     * 从指定map中得到指定key的值，并转换成日期
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static Date getDate(Map<String, Object> map, String key) {
        Date returnValue = null;
        if (key != null && !"".equals(key) && map != null && map.get(key) != null) {
            long longDate = NumberUtil.toLong(map.get(key).toString().trim(), BasicTypeDefaultValue.DEFAULT_LONG);
            returnValue = new Date(longDate);
        }
        return returnValue;
    }

    /**
     * 从指定map中得到指定key的值，并转换成格式化的日期"yyyy-MM-dd HH:mm:ss"
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static String getFormatDateTime(Map<String, Object> map, String key) {
        return getFormatDateTime(map, key, DatetimeFormat.DEFAULT_DATE_TIME.value());
    }

    /**
     * 从指定map中得到指定key的值，并转换成格式化的日期"yyyy-MM-dd"
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static String getFormatDate(Map<String, Object> map, String key) {
        return getFormatDateTime(map, key, DatetimeFormat.DEFAULT_DATE.value());
    }

    /**
     * 从指定map中得到指定key的值，并转换成格式化的日期"HH:mm:ss"
     * @param map				集合
     * @param key				键
     * @return					值
     */
    public static String getFormatTime(Map<String, Object> map, String key) {
        return getFormatDateTime(map, key, DatetimeFormat.DEFAULT_TIME.value());
    }

    /**
     * 从指定map中得到指定key的值，并转换成指定格式的日期
     * @param map				集合
     * @param key				键
     * @param df				日期时间格式
     * @return					值
     */
    public static String getFormatDateTime(Map<String, Object> map, String key, DatetimeFormat df) {
        return getFormatDateTime(map, key, df.value());
    }

    /**
     * 从指定map中得到指定key的值，并转换成指定格式的日期
     * @param map				集合
     * @param key				键
     * @param format			日期时间格式
     * @return					值
     */
    public static String getFormatDateTime(Map<String, Object> map, String key, String format) {
        String resultValue = "";
        Date d = getDate(map, key);
        if (d != null) {
            resultValue = DatetimeUtil.datetimeToString(d, format);
        }
        return resultValue;
    }

    /**
     * Demo方法：最简单的遍历Map的方法
     * @param map				集合
     */
    public static void traverseMap(Map<String, Long> map) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            System.out.println("key : " + entry.getKey());
            System.out.println("value : " + entry.getValue());
        }
    }
}
