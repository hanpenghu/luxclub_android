package com.hsbank.util.android.util;

import android.os.Bundle;

import com.hsbank.util.java.collection.obj.SerializableMap;
import com.hsbank.util.java.type.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class BundleUtil {
	/**
	 * 设置指定key的参数值：Map
	 * @param bundle	Bundle对象
	 * @param key		键
	 */
	public static void setMap(Bundle bundle, String key, Map<String, Object> map) {
		key = StringUtil.dealString(key);
		if (bundle != null && !"".equals(key)) {
			final SerializableMap sMap = new SerializableMap();
			sMap.setMap(map);
			bundle.putSerializable(key, sMap);
		}
	}
	
	/**
	 * 得到指定key的参数值：Map
	 * @param bundle	Bundle对象
	 * @param key		键
	 * @return			值
	 */
	public static Map<String, Object> getMap(Bundle bundle, String key) {
		Map<String, Object> resultValue = new HashMap<String, Object>();
		key = StringUtil.dealString(key);
		if (bundle != null && !"".equals(key) && bundle.containsKey(key) && bundle.getSerializable(key) != null) {
			resultValue = ((SerializableMap)bundle.getSerializable(key)).getMap();
		}
		return resultValue;
	}
	
	/**
	 * 得到指定key的参数的值：String
	 * @param bundle	Bundle对象
	 * @param key		键
	 * @return			值
	 */
	public static String getString(Bundle bundle, String key) {
		String resultValue = "";
		key = StringUtil.dealString(key);
		if (bundle != null && !"".equals(key) && bundle.containsKey(key) && bundle.getSerializable(key) != null) {
			resultValue = bundle.getString(key);
			resultValue = StringUtil.dealString(resultValue);
		}
		return resultValue;
	}
}
