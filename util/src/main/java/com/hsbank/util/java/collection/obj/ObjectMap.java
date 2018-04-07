package com.hsbank.util.java.collection.obj;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义Map集合类
 * <p>
 * 在HashMap基础上进行封装，针对不同的数据类型，提供了setXXX/putXXX方法对。
 * @author xwy
 * 2011-04-18
 */
public class ObjectMap extends ConcurrentHashMap<Object, Object> {
	/**序列化Id*/
	private static final long serialVersionUID = -2415142660975265415L;
	
	public Object get(String key) {
		return super.get(key);
	}

	public void set(String key, Object value) {
		super.put(key, value);
	}

	public String getString(String key) {
		Object value = get(key);
		if (value != null) {
			return value.toString();
		} else {
			return "";
		}
	}

	public void setString(String key, String value) {
		set(key, value);
	}
	
	public boolean getBool(String key) {
		Object value = get(key);
		if (value instanceof Boolean) {
			return ((Boolean)value).booleanValue();
		} else if (value instanceof String) {
			return Boolean.parseBoolean(value.toString());
		} else {
			return false;
		}
	}
	
	public void setBool(String key, boolean value) {
		set(key, value);
	}

	public int getInt(String key) {
		Object value = get(key);
		if (value instanceof Number) {
			return ((Number) value).intValue();
		} else if (value instanceof String) {
			return Integer.parseInt(value.toString());
		} else {
			return 0;
		}
	}

	public void setInt(String key, int value) {
		set(key, value);
	}

	public long getLong(String key) {
		Object value = get(key);
		if (value instanceof Number) {
			return ((Number)value).longValue();
		} else if (value instanceof Long) {
			return Long.parseLong(value.toString());
		} else {
			return 0L;
		}
	}
	
	public void setLong(String key, long value) {
		set(key, value);
	}
}
