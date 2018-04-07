package com.hsbank.luxclub.util;

import java.lang.reflect.Method;

/**
 * Author:      chenliuchun
 * Date:        2017/7/17
 * Description: Android 类库方法的反射
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 */

public final class ReflectUtil {

    /**
     * 获取 FragmentPagerAdapter 添加 Fragment 时候所需的 TAG
     * 反射调用 android.support.v4.app.FragmentPagerAdapter#makeFragmentName(int, long) 方法
     *
     * @param viewId
     * @param id
     * @return
     */
    public static String getFragmentName(int viewId, long id) {
        try {
            Class<?> clazz = Class.forName("android.support.v4.app.FragmentPagerAdapter");
            Method method = clazz.getDeclaredMethod("makeFragmentName", int.class, long.class);
            method.setAccessible(true);
            return (String) method.invoke(null, viewId, id);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
