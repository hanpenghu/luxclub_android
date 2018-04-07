package com.hsbank.util.java.collection;

import com.hsbank.util.java.type.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数组_公共类
 * @author xwy
 * 2011-04-01
 */
public class ArrayUtil {
	/**
     * 判断指定对象是否为数组
     * @param obj 指定对象
     * @return 是则返回true，否则返回false
     */
    public static boolean isArray(Object obj) {
        return obj instanceof Object[];
    }
    
    /**
     * 判断指定数组是否为空
     * @param inputArray 指定数组
     * @return 是则返回true，否则返回false
     */
    public static boolean isEmpty(Object[] inputArray) {
        return null == inputArray ? true : 0 == inputArray.length;
    }
    
    /**
     * 判断给定的字符串数组中是否存在值为给定字符串的元素
     * @param stringArray
     * @param strSearch
     * @return boolean
     */
    public static boolean isExist(String[] stringArray, String strSearch) {
        boolean bResult = false;
        if (stringArray != null && strSearch != null) {
        	strSearch = strSearch.trim();
        	int iLength = stringArray.length;
            for (int i = 0; i < iLength; i++) {
                if (strSearch.equals(stringArray[i].trim())) {
                    bResult = true;
                    break;
                }                
            }
        }
        return bResult;
    }
    
    /**
     * Array转换成List
     * @param stringArray
     * @return List<String>
     */
    public static List<String> arrayToList(String[] stringArray) {
    	List<String> returnValue = new ArrayList<String>();
    	int iLength = stringArray.length;
    	for (int i = 0; i < iLength; i++) {
    		returnValue.add(StringUtil.dealString(stringArray[i]));
    	}
    	return returnValue;
    }
    
    /**
     * Array转换成Set
     * @param stringArray
     * @return Set<String>
     */
    public static Set<String> arrayToSet(String[] stringArray) {
    	Set<String> resultSet = new HashSet<String>();
    	int iLength = stringArray.length;
    	for (int i = 0; i < iLength; i++) {
    		resultSet.add(StringUtil.dealString(stringArray[i]));
    	}
    	return resultSet;
    }
    
    /**
     * 将一个字符串数组的所有元素的值组合成一个以指定分隔符分隔的字符串
     * @param stringArray
     * @param separator 	分隔符, 默认为""
     * @return
     */
    public static String arrayToString(String[] stringArray, String separator) {
    	separator = StringUtil.dealString(separator);
        StringBuffer sb = new StringBuffer();
        if (stringArray != null) {
            int iLength = stringArray.length;
            if (iLength > 0) {
            	sb.append(stringArray[0].trim());
            }
            for (int i = 1; i < iLength; i++) {
            	sb.append(separator).append(stringArray[i].trim());
            }
        }
        return sb.toString();
    }
}
