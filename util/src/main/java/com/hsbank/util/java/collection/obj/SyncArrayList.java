package com.hsbank.util.java.collection.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 同步列表: 实现了增删等操作方法的同步
 * @author xwy
 * 2011-04-18
 * @param <E>
 */
public class SyncArrayList<E> extends ArrayList<E> {	
	static final long serialVersionUID = 0L;
	/**存放SyncArrayList对象的集合*/
    private static Map<String, SyncArrayList<?>> instanceMap = new HashMap<String, SyncArrayList<?>>();
	
	/**
     * 得到一个指定名称SyncArrayList实例
     * @return SyncArrayList 实例
     */
    @SuppressWarnings("unchecked")
	public static <T> SyncArrayList<T> getInstance(String name, Class<T> bean) {
    	SyncArrayList<T> resultObj = null;
        if (instanceMap.containsKey(name)) {
            resultObj = (SyncArrayList<T>)instanceMap.get(name);
        } else {
            resultObj = new SyncArrayList<T>();
            instanceMap.put(name, resultObj);
        }
        return resultObj;
    }
	
	public synchronized boolean add(E o) {
		return super.add(o);
	}
	
	public synchronized E remove(int index) {
		return super.remove(index);
	}
	
	public synchronized int size() {
		return super.size();
	}
}
