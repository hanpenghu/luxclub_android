package com.hsbank.util.java.collection.util;

import java.util.Comparator;

/**
 * 排序规则类：按整型数据排序
 * 实现Comparator接口，可以定义任何排序规则
 * @author Arthur.Xie
 * 2009-12-29
 */
public class SortByInteger implements Comparator<Integer> {
	/**升序*/
	public static final String UP = "up";
	/**降序*/
    public static final String DOWN = "down";
    /**标识：升序，还是降序*/
    private String tag;

    public SortByInteger(String tag) {
    	this.tag = tag;
    }

	public int compare(Integer n01, Integer n02) {
		if (this.tag == SortByInteger.DOWN) {
            return sortDown(n01, n02);
		} else {
			return sortUp(n01, n02);
		}
    }
	
	private int sortUp(Integer n01, Integer n02) {
        if (n01 < n02) {
        	return -1;
        } else if (n01 > n02) {
            return 1;
        } else {
        	return 0;
        }
	}

	private int sortDown(Integer n01, Integer n02) {
        if (n01 > n02) {
        	return -1;
        } else if (n01 < n02) {
            return 1;
        } else {
        	return 0;
        }
	}
}
