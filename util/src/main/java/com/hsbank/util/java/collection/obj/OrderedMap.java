package com.hsbank.util.java.collection.obj;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 有序的Map
 * @author Administrator
 * 2011-11-18
 * @param <K>
 * @param <V>
 */
public class OrderedMap<K, V> {
	/**键列表*/
	private Collection<K> keyList = new LinkedList<K>();
	/**<key, value>集合*/
	private Map<K, V> dataMap = new HashMap<K, V>();

	/**
	 * 添加键值对
	 * @param key 		键
	 * @param value 	值
	 * @return 	
	 */
	public V put(K key, V value) {
		if (!this.keyList.contains(key)) {
			this.keyList.add(key);
		}
		return this.dataMap.put(key, value);
	}

	/**
	 * 得到指定键对应的值
	 * @param key 		键
	 * @return 		值
	 */
	public V get(K key) {
		return this.dataMap.get(key);
	}

	/**
	 * 删除指定的键对应的键值对
	 * @param key		键
	 * @return 		值
	 */
	public V remove(K key) {
		this.keyList.remove(key);
		return this.dataMap.remove(key);
	}

	/**清空*/
	public void clear() {
		this.keyList.clear();
		this.dataMap.clear();
	}

	/**
	 * 检查Map是否为空
	 * @return 	如果为空返回true，否则返回false
	 */
	public boolean isEmpty() {
		return this.dataMap.isEmpty();
	}

	/**
	 * 检查Map是否包含指定的键
	 * @param key 		键
	 * @return 		如果包含指定的键则返回true,否则返回false
	 */
	public boolean containsKey(K key) {
		return this.dataMap.containsKey(key);
	}

	/**
	 * 检查Map是否包含指定的值
	 * @param value 	值
	 * @return 		如果包含指定的值则返回true,否则返回false
	 */
	public boolean containsValue(V value) {
		return this.dataMap.containsValue(value);
	}

	/**
	 * 得到键列表
	 * 
	 * @return 	键列表
	 */
	public Collection<K> getAllKeys() {
		return this.keyList;
	}

	/**
	 * 得到值列表
	 * @return 	值列表
	 */
	public Collection<V> values() {
		Collection<V> results = new LinkedList<V>();
		for (K key : this.keyList) {
			results.add(this.dataMap.get(key));
		}
		return results;
	}

	/**
	 * 得到键值对的数量
	 * @return
	 */
	public int size() {
		return dataMap.size();
	}
}
