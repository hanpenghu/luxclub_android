package com.hsbank.util.java.constant;

import java.util.Calendar;

/**
 * 日期时间区间_枚举类
 * @author xwy
 * 2011-04-01
 */
public enum DatetimeField {
    /**field: 年*/
	YEAR(Calendar.YEAR),
	/**field: 月*/
	MONTH(Calendar.MONTH),
	/**field: 日*/
	DAY(Calendar.DATE),
	/**field: 时(12小时制)*/
	HOUR(Calendar.HOUR),
	/**field: 时(24小时制)*/
	HOUR_OF_DAY(Calendar.HOUR_OF_DAY),
	/**field: 分*/
	MINUTE(Calendar.MINUTE),
	/**field: 秒*/
	SECOND(Calendar.SECOND);
	
    /**值*/
    private int value;

    /**
     * 构造函数
     * @param value 值
     */
    DatetimeField(int value) {
        this.value = value;
    }

    /**
     * 获取值
     * @return 值
     */
    public int value() {
        return value;
    }
}
