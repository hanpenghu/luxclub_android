package com.hsbank.luxclub.provider;


import com.hsbank.luxclub.provider.custom.IProvider;
import com.hsbank.luxclub.provider.custom.impl.ProviderWifi;

/**
 * 工厂模式: IProvider构造工厂
 * @author wuyuan.xie
 * CreateDate 2014-09-11
 */
public class ProviderFactory {
	/**
	 * 得到一个工厂接口的实现类的实例
	 * @return
	 */
	public static IProvider getInstance() {
		IProvider instance = null;
		/*
		if (NetworkState.getInstance().getCurrentState() == NetworkState.TYPE_WIFI){
			//WIFI
			instance = new ProviderWifi();
		} else if (NetworkState.getInstance().getCurrentState() == NetworkState.TYPE_MOBILE) {
			//手机网络
			instance = new ProviderMobile();
		} else {
			//离线模式
			instance = new ProviderOffline();
		}
		*/
		instance = new ProviderWifi();
		return instance;
	}
}
