package com.hsbank.util.java.collection;

import java.util.HashSet;
import java.util.Set;

public class SetUtil {	
	/**
	 * 得到两个指定集合的“交集”: set1和set2都有的元素的集合
	 * <p>
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static Set<Object> getJiaoJi(Set<Object> set1, Set<Object> set2) {
		Set<Object> returnValue = new HashSet<Object>();
		returnValue.addAll(set1);
		returnValue.retainAll(set2);
		return returnValue;
	}
	
	/**
	 * 得到两个指定集合的“差集”： set1有set2没有的元素的集合
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static Set<Object> getChaJi(Set<Object> set1, Set<Object> set2) {
		Set<Object> returnValue = new HashSet<Object>();
		returnValue.addAll(set1);
		returnValue.removeAll(set2);
		return returnValue;
	}
	
	/**
	 * 得到两个指定集合的“并集”： set1或set2有的所有元素的集合
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static Set<Object> getBingJi(Set<Object> set1, Set<Object> set2) {
		Set<Object> returnValue = new HashSet<Object>();
		returnValue.addAll(set1);
		returnValue.addAll(set2);
		return returnValue;
	}
	
	/**
     * 把指定集合的所有元素的值组合成一个以指定分隔符分隔的字符串
     * @param list 字符列表
     * @return
     */
    public static String setToString(Set<String> set, String strTag) {
        StringBuffer sb = new StringBuffer();
        if (set != null) {
            for (String s : set) {
                sb.append(strTag).append(s.trim());
            }
        }        
        String strResult = sb.toString();
        strResult = strResult == null ? "" : strResult.trim();
        return strResult.startsWith(strTag) ? strResult.replaceFirst(strTag, "") : strResult;
    }
}
