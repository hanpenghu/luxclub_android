package com.hsbank.util.java.collection.util;

import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.java.type.NumberUtil;

import java.util.Comparator;
import java.util.Map;

/**
 * 排序规则类：按指定名称的属性值排序
 * 实现Comparator接口，可以定义任何排序规则
 * @author Arthur.Xie
 * 2009-12-29
 */
public class SortByMapField implements Comparator<Map<String, Object>> {
	/**升序*/
	public static final String UP = "up";
	/**降序*/
    public static final String DOWN = "down";
    /**排序字段*/
    private String orderField;
    /**标识：升序，还是降序*/
    private String tag;

    public SortByMapField(String orderField, String tag) {
    	this.orderField = orderField;
    	this.tag = tag;
    }

	public int compare(Map<String, Object> map01, Map<String, Object> map02) {
		if (this.tag == SortByInteger.DOWN) {
            return sortDown(map01, map02, this.orderField);
		} else {
			return sortUp(map01, map02, this.orderField);
		}
    }
	
	/**
	 * 升序
	 * @param map01
	 * @param map02
	 * @param orderField
	 * @return
	 */
	private int sortUp(Map<String, Object> map01, Map<String, Object> map02, String orderField) {
	    Object obj01 = map01.get(orderField);
	    Object obj02 = map02.get(orderField);
	    if (obj01 instanceof Integer && obj02 instanceof Integer) {
	        //整型
	        int int01 = NumberUtil.toInt(String.valueOf(obj01), 0);
	        int int02 = NumberUtil.toInt(String.valueOf(obj02), 0);
	        if (int01 < int02) {
	            return -1;
	        } else if (int01 > int02) {
	            return 1;
	        } else {
	            return 0;
	        }
	    } else if (obj01 instanceof Float && obj02 instanceof Float){
            //浮点型
            float f01 = NumberUtil.toFloat(String.valueOf(obj01), 0);
            float f02 = NumberUtil.toFloat(String.valueOf(obj02), 0);
            if (f01 < f02) {
                return -1;
            } else if (f01 > f02) {
                return 1;
            } else {
                return 0;
            }
        } else if (obj01 instanceof Double && obj02 instanceof Double){
            //浮点型
            double d01 = NumberUtil.toDouble(String.valueOf(obj01), 0);
            double d02 = NumberUtil.toDouble(String.valueOf(obj02), 0);
            if (d01 < d02) {
                return -1;
            } else if (d01 > d02) {
                return 1;
            } else {
                return 0;
            }
        } else {
            //字符串型
            String str01 = MapUtil.getString(map01, orderField);
            String str02 = MapUtil.getString(map02, orderField);
            return str01.compareTo(str02);
        }
	}

	/**
	 * 降序
	 * @param map01
	 * @param map02
	 * @param orderField
	 * @return
	 */
	private int sortDown(Map<String, Object> map01, Map<String, Object> map02, String orderField) {
	    Object obj01 = map01.get(orderField);
        Object obj02 = map02.get(orderField);
        if (obj01 instanceof Integer && obj02 instanceof Integer) {
            //整型
            int int01 = NumberUtil.toInt(String.valueOf(obj01), 0);
            int int02 = NumberUtil.toInt(String.valueOf(obj02), 0);
            if (int01 > int02) {
                return -1;
            } else if (int01 < int02) {
                return 1;
            } else {
                return 0;
            }
        } else if (obj01 instanceof Float && obj02 instanceof Float){
            //浮点型
            float f01 = NumberUtil.toFloat(String.valueOf(obj01), 0);
            float f02 = NumberUtil.toFloat(String.valueOf(obj02), 0);
            if (f01 > f02) {
                return -1;
            } else if (f01 < f02) {
                return 1;
            } else {
                return 0;
            }
        } else if (obj01 instanceof Double && obj02 instanceof Double){
            //浮点型
            double d01 = NumberUtil.toDouble(String.valueOf(obj01), 0);
            double d02 = NumberUtil.toDouble(String.valueOf(obj02), 0);
            if (d01 > d02) {
                return -1;
            } else if (d01 < d02) {
                return 1;
            } else {
                return 0;
            }
        } else {
            //字符串型
            String str01 = MapUtil.getString(map01, orderField);
            String str02 = MapUtil.getString(map02, orderField);
            return str02.compareTo(str01);
        }
	}
}
