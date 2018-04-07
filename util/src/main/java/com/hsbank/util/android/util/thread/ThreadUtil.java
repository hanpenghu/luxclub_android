package com.hsbank.util.android.util.thread;

import android.util.Log;

/**
 * 线程公共类
 * @author Administrator
 * 2016-03-25
 */
public class ThreadUtil {
	/**
     * 线程休眠（ms）
     * @param iSleepTime 	线程休眠的时间（ms）
     */
    public static void sleep(long iSleepTime) {
        try {
            Thread.sleep(iSleepTime);
        } catch (Exception e) {
            Log.e(ThreadUtil.class.getName(), e.getMessage());
        }
    }
}
