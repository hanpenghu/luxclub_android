package com.hsbank.util.java.collection.util;

import java.util.Comparator;

/**
 * 排序规则类：按字符串型数据排序
 * 实现Comparator接口，可以定义任何排序规则
 * @author Arthur.Xie
 * 2009-12-29
 */
public class SortByString implements Comparator<String> {
	/**升序*/
	public static final String UP = "up";
	/**降序*/
    public static final String DOWN = "down";
    /**标识：升序，还是降序*/
    private String tag;

    public SortByString(String tag) {
    	this.tag = tag;
    }

	public int compare(String str01, String str02) {
		if (this.tag == SortByInteger.DOWN) {
            return sortDown(str01, str02);
		} else {
			return sortUp(str01, str02);
		}
    }
	
	/**
	 * 升序
	 * @param str01
	 * @param str02
	 * @return
	 */
	private int sortUp(String str01, String str02) {
		return str01.compareTo(str02);
	}

	/**
	 * 降序
	 * @param str01
	 * @param str02
	 * @return
	 */
	private int sortDown(String str01, String str02) {
		return str02.compareTo(str01);
	}
}
