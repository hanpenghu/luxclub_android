package com.hsbank.util.android.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class JsonUtil {
	private static final Gson gson = new Gson();

	/**
	 * Json转指定Class
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static Object toClass(String json, Class<?> clazz) {
		return gson.fromJson(json, clazz);
	}

	/**
	 * Json转Array
	 * @param json
	 * @param typeOfT
	 * @param <T>
	 * @return
	 */
	public static <T> T toArray(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

	/**
	 * Object转Json
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	/**
	 * Json转List
	 * @param json		json字符串
	 * @return			列表
	 */
	public static List<Map<String, Object>> toList(String json) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (json.length() > 2) {
			JSONArray jsonArray = JSONArray.parseArray(json);
			int iSize = jsonArray.size();
			for (int i = 0; i < iSize; i ++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				resultList.add(toMap(jsonObj.toString()));
			}
		}
		return resultList;
	}

	/**
	 * Json转Map
	 * @param json		json字符串
	 * @return 			集合
	 */
	public static Map<String, Object> toMap(String json) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (json.length() > 2) {
			JSONObject jsonObj = JSONObject.parseObject(json);
			for (Object key : jsonObj.keySet()) {
				Object value = jsonObj.get(key);
				if (value != null) {
					resultMap.put((String) key, value);
				} else {
					throw new IllegalStateException("Could not decode value for key: " + key);
				}
			}
		}
		return resultMap;
	}
}