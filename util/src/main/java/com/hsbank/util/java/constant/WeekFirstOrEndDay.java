package com.hsbank.util.java.constant;

import java.util.Calendar;

/**
 * 周开始日期_或_结束日期_枚举类
 * @author xwy
 * 2011-04-01
 */
public enum WeekFirstOrEndDay {
    /**星期一*/
	MONDAY(Calendar.MONDAY),
	/**星期日*/
	SUNDAY(Calendar.SUNDAY);
	
    /**值*/
    private int value;

    /**
     * 构造函数
     * @param value 值
     */
    WeekFirstOrEndDay(int value) {
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
