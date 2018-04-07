package com.hsbank.util.android.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * 手机震动工具类
 * <p>
 * 使用前提：
 * 首先要在AndroidManifest.xml中添加震动权限：<uses-permission android:name="android.permission.VIBRATE" />
 * </p>
 * Created by Administrator on 2016/4/20.
 */
public class VibratorUtil {
    /**
     * 让手机震动
     * <p>
     * 示例：让手机震动200毫秒
     * VibratorUtil.vibrate(mContext, 200);
     * </p>
     * @param context           上下文对象
     * @param milliseconds      震动时长
     */
    public static Vibrator vibrate(final Context context, long milliseconds) {
        Vibrator resultValue = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        resultValue.vibrate(milliseconds);
        return resultValue;
    }

    /**
     * 让手机震动
     * <p>
     * 示例：让手机震动反复震动3次
     * VibratorUtil.vibrate(mContext, new long[]{100, 500, 200, 500, 300, 500}, false);
     * </p>
     * @param context           上下文对象
     * @param pattern           自定义震动模式。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长...]时长的单位是毫秒
     * @param isRepeat          是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static Vibrator vibrate(final Context context, long[] pattern, boolean isRepeat) {
        Vibrator resultValue = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        resultValue.vibrate(pattern, isRepeat ? 1 : -1);
        return resultValue;
    }
}
