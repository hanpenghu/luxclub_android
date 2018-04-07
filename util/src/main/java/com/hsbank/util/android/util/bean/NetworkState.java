package com.hsbank.util.android.util.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 网络状态
 * @author Arthur.Xie
 * 2014-12-16
 */
public class NetworkState {
	/**离线状态*/
	public static final int TYPE_NONE = -0x1;
	/**WiFi网络*/
	public static final int TYPE_WIFI = 0x1;
	/**手机网络*/
	public static final int TYPE_MOBILE = 0x2;
	/**单例*/
	private static NetworkState instance = null;
	/**当前网络状态*/
	private int currentState = TYPE_NONE;

	/**得到单例*/
	public static NetworkState getInstance() {
		return instance == null ? instance = new NetworkState() : instance;
	}

	/**私有构造函数*/
	private NetworkState() {
	}
	
	/**得到当前网络状态*/
	public int getCurrentState() {
		return currentState;
	}
	
	/**
	 * 设置当前网络状态
	 * @param currentState
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
