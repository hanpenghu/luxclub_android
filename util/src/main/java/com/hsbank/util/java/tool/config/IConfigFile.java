package com.hsbank.util.java.tool.config;

/**
 * 配置文件_接口类
 * @author xwy
 * 2011-04-20
 */
public interface IConfigFile {
	/**
	 * 加载配置文件
	 */
	public void load();
	
	/**
	 * 重新配置文件
	 */
	public void reload();
	
	/**
	 * 得到一个指定名称的String类型属性值
	 * @param key
	 * @return
	 */
	public String getString(String key);
	
	/**
	 * 得到一个指定名称的String类型属性值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue);
	
	/**
	 * 得到一个指定名称的int类型属性值
	 * @param key
	 * @return
	 */
	public int getInt(String key);
	
	/**
	 * 得到一个指定名称的int类型属性值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue);

	/**
	 * 得到一个指定名称的long类型属性值
	 * @param key
	 * @return
	 */
	public long getLong(String key);
	
	/**
	 * 得到一个指定名称的long类型属性值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public long getLong(String key, long defaultValue);
	
	/**
	 * 得到一个指定名称的boolean类型属性值
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key);
	
	/**
	 * 得到一个指定名称的boolean类型属性值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultValue);
}
