package com.hsbank.util.java.constant;

/**
 * 日期时间格式
 * @author xwy
 * 2011-04-02
 */
public enum DatetimeFormat {
	/** 
	 * 2011年01月01日 -> 2011-01-01 00:00:00 
	 */
    DEFAULT_DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    /** 
     * 2011年01月01日  00:00:00 -> 2011-01-01 
     */
    DEFAULT_DATE("yyyy-MM-dd"),
    /** 
     * 2011年01月01日  00:00:00 -> 00:00:00 
     */
    DEFAULT_TIME("HH:mm:ss"),
    /** 
     * 2011年01月01日 -> 2011年01月01日 
     */
    YEAR_MONTH_DAY("yyyy年MM月dd日"),
    /** 
     * 2011年01月01日 -> 2011年01月
     */
    YEAR_MONTH("yyyy年MM月"),
    /**
     * 2011年01月01日 -> 2011年01月
     */
    FOUR_YEAR_MONTH("yyyy年MM月"),
    /**
     * 2011年01月01日 -> 11年01月
     */
    TWO_YEAR_MONTH("yy年MM月"),
    /**
     * 2011年01月01日 -> 20110101000000
     */
    YYYYMMDDHHMMSSS("yyyyMMddHHmmsss"),
    /**
     * 2011年01月01日 -> 20110101000000
     */
    YYYYMMDDHHMMSS("yyyyMMddHHmmss"),
    /**
     * 2011年01月01日 -> 20110101
     */
    YYYYMMDD("yyyyMMdd"),
    /**
     * 2011年01月01日 -> 201101
     */
    YYYYMM("yyyyMM"),
    /**
     * 2011年01月 -> 1101
     */
    YYMM("yyMM"),
    /**
     * 2011年01月01日 -> 2011-01-01 00:00:00.000
     */
    YYYY_MM_DD_HH_MM_SS_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
    /**
     * 2011年01月01日 -> 2011-01-01
     */
    YYYY_MM_DD("yyyy-MM-dd"),
    /**
     * 2011年01月01日 -> 00:00:00
     */
    HH_MM_SS("HH:mm:ss"),
    /**
     * 2011年01月01日 -> 11-01-01 00:00:00.000
     */
    YY_MM_DD_HH_MM_SS_SSS("yy-MM-dd HH:mm:ss.SSS"),
    /**
     * 2011年01月01日 -> 11-1-1 00:00:00.000
     */
    YY_M_D_HH_MM_SS_SSS("yy-M-d HH:mm:ss.SSS"),
    /**
     * 2011年01月01日 -> 11-01-01
     */
    YY_MM_DD("yy-MM-dd"),    
    /**
     * 2011年01月01日 -> 11-1-1
     */
    YY_M_D("yy-M-d"),
    /**
     * 2011年01月01日 -> 2011/01/01 00:00:00.000
     */
    YYYY__MM__DD_HH_MM_SS_SSS("yyyy/MM/dd HH:mm:ss.SSS"),
    /**
     * 2011年01月01日 -> 2011/01/01
     */
    YYYY__MM__DD("yyyy/MM/dd"),
    /**
     * 2011年01月01日 -> 11/01/01 00:00:00.000
     */
    YY__MM__DD_HH_MM_SS_SSS("yy/MM/dd HH:mm:ss.SSS"),
    /**
     * 2011年01月01日 -> 11/1/1 00:00:00.000
     */
    YY__M__D_HH_MM_SS_SSS("yy/M/d HH:mm:ss.SSS"),
    /**
     * 2011年01月01日 -> 11/01/01
     */
    YY__MM__DD("yy/MM/dd"),    
    /**
     * 2011年01月01日 -> 11/1/1
     */
    YY__M__D("yy/M/d");
    
    /**日期时间格式*/
    private String format;

    /**
     * 构造函数
     * @param format 日期时间格式
     */
    DatetimeFormat(String format) {
        this.format = format;
    }

    /**
     * 返回日期时间格式
     * @return 		格式字符串
     */
    public String value() {
        return format;
    }
}
