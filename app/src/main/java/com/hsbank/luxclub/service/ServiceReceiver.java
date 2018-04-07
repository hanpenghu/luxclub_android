package com.hsbank.luxclub.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.hsbank.util.android.AndroidAppInfo;

/**
 * 广播接收者
 * 在开机、wifi环境变化等情况下启动ServiceSystem，保证解锁功能正常
 */
public class ServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String strIntentAction = intent.getAction();
        if (strIntentAction.equals(Intent.ACTION_BOOT_COMPLETED)) {
            startService(context);
        } else if (strIntentAction.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            startService(context);
        } else if (strIntentAction.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            startService(context);
        } else if (strIntentAction.equals(Intent.ACTION_USER_PRESENT)) {
            startService(context);
        } else if (strIntentAction.equals(AudioManager.RINGER_MODE_CHANGED_ACTION)) {
            startService(context);
        } else if (strIntentAction.equals(Intent.ACTION_BATTERY_CHANGED)) {
            startService(context);
        } else if (strIntentAction.equals(Intent.ACTION_SCREEN_OFF)) {
            AndroidAppInfo.getInstance().setOnForeground(false);
            AndroidAppInfo.getInstance().setStartTimeOnBackground(System.currentTimeMillis());
            startService(context);
        }
    }

    private void startService(Context context) {
//        Intent serviceIntent = new Intent(context, ServiceSystem.class);
//        context.startService(serviceIntent);
    }
}