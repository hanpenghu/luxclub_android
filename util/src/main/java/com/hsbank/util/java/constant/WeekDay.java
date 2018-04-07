package com.hsbank.util.java.constant;

import java.util.Calendar;

/**
 * 周_枚举类
 * @author xwy
 * 2011-04-01
 */
public enum WeekDay {
    /**星期一*/
	MONDAY(Calendar.MONDAY),
	/**星期二*/
	TUESDAY(Calendar.TUESDAY),
	/**星期三*/
	WEDNESDAY(Calendar.WEDNESDAY),
	/**星期四*/
	THURSDAY(Calendar.THURSDAY),
	/**星期五*/
	FRIDAY(Calendar.FRIDAY),
	/**星期六*/
	SATURDAY(Calendar.SATURDAY),
	/**星期日*/
	SUNDAY(Calendar.SUNDAY);
	
    /**值*/
    private int value;

    /**
     * 构造函数
     * @param value 值
     */
    WeekDay(int value) {
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
