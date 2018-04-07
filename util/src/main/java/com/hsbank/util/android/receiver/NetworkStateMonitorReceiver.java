package com.hsbank.util.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.hsbank.util.android.AndroidUtil;

/**
 * 网络状态监听服务
 * @author Arthur.Xie
 * 2014-12-16
 */
public class NetworkStateMonitorReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(this.getClass().getName(), ".................onReceive...............");
		String action = intent.getAction();
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			Log.d(this.getClass().getName(), "Network state changed.");
			AndroidUtil.setNetworkState(context);
		}
	}


}
