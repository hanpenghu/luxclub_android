package com.hsbank.util.java.collection;

import com.hsbank.util.java.collection.util.SortByInteger;
import com.hsbank.util.java.collection.util.SortByMapField;
import com.hsbank.util.java.collection.util.SortByString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * List_公共类
 * @author xwy
 * 2011-04-01
 */
public final class ListUtil {
	/**
     * 判断对象是否为List
     * @param obj 指定对象
     * @return 是则返回true，否则返回false
     */
    public static boolean isList(Object obj) {
        return obj instanceof List;
    }
    
	/**
     * 判断指定列表是否为空
     * @param inputList 指定列表
     * @return 是则返回true，否则返回false
     */
    public static boolean isEmpty(List<?> inputList) {
        return null == inputList ? true: inputList.isEmpty();
    }
    
    /**
     * 判断指定对象是否在指定列表中
     * @param inputList 目标List
     * @param <E> 		对象类型
     * @param element 	对象
     * @return 是则返回true，否则返回false
     */
    public static <E> boolean isExist(E element, List<E> inputList) {
        return isEmpty(inputList) ? false : inputList.contains(element);
    }
    
    /**
     * 把指定列表的所有元素的值组合成一个以指定分隔符分隔的字符串
     * @param list 字符列表
     * @return
     */
    public static String listToString(List<String> list, String strTag) {
    	StringBuffer sb = new StringBuffer();
    	if (list != null) {
            int iSize = list.size();
            if (iSize > 0) {
            	sb.append(list.get(0).trim());
            	for (int i = 1; i < iSize; i++) {
            		sb.append(strTag).append(list.get(i).trim());
            	}
            }
        }        
        return sb.toString();
    }
    
    /**
     * 列表按顺序去重：用linked保持原来的顺序，用set去掉重复元素
     * @param <E>
     * @param list
     * @return
     */
    public static <E> List<E> filterRepeat(List<E> list) {
        return new ArrayList<E>(new LinkedHashSet<E>(list));
    }
    
    /**
     * 按整型数据给列表排序：升序
     * @param list
     */
    public static void sortUpByInteger(List<Integer> list) {
        if (list.size() > 0) {
            SortByInteger sort = new SortByInteger(SortByInteger.UP);
            Collections.sort(list, sort);
        }
    }
    
    /**
     * 按整型数据给列表排序：降序
     * @param list
     */
    public static void sortDownByInteger(List<Integer> list) {
        if (list.size() > 0) {
            SortByInteger sort = new SortByInteger(SortByInteger.DOWN);
            Collections.sort(list, sort);
        }
    }
    
    /**
     * 按指定字符串型数据给列表排序：升序
     * @param list
     */
    public static void sortUpByString(List<String> list) {
        if (list.size() > 0) {
            Collections.sort(list, new SortByString(SortByString.UP));
        }
    }
    
    /**
     * 按指定字符串型数据给列表排序：降序
     * @param list
     */
    public static void sortDownByString(List<String> list) {
        if (list.size() > 0) {
            Collections.sort(list, new SortByString(SortByString.DOWN));
        }
    }
    
    /**
     * 按指定字段给列表排序：升序
     * @param list
     */
    public static void sortUpByMapField(List<Map<String, Object>> list, String key) {
        if (list.size() > 0) {
            SortByMapField sort = new SortByMapField(key, SortByMapField.UP);
            Collections.sort(list, sort);
        }
    }
    
    /**
     * 按指定字段给列表排序：降序
     * @param list
     * @param key
     */
    public static void sortDownByMapField(List<Map<String, Object>> list, String key) {
        if (list.size() > 0) {
            SortByMapField sort = new SortByMapField(key, SortByMapField.DOWN);
            Collections.sort(list, sort);
        }
    }
}
