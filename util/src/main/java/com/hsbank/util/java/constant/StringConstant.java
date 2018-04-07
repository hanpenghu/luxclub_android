package com.hsbank.util.java.constant;

/**
 * 字符串常量_枚举类
 * @author xwy
 * 2011-04-01
 */
public class StringConstant {
	/**大写*/
	public static final StringFormatConstant UPPER_CASE = StringFormatConstant.UPPER_CASE;
	/**小写*/
    public static final StringFormatConstant LOWER_CASE = StringFormatConstant.LOWER_CASE;
    /**字符串处理常量枚举*/
    public enum StringFormatConstant {
        /** 小写 */
        LOWER_CASE,
        /** 大写 */
        UPPER_CASE;
    }
    /**空字符串 */
    public static final String EMPTY = "";
    /**true */
    public static final String TURE = "true";
    /**false */
    public static final String FALSE = "false";
    /**全角字符*/
    public static final String CHAR_S_OF_FULL_WIDTH = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ１２３４５６７８９０";
    /**半角字符*/
    public static final String CHAR_S_OF_HALF_WIDTH = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    /**全角空格*/
    public static final char SPACE_OF_FULL = 12288;
    /**半角空格*/
    public static final char SPACE_OF_HALF = 32;
    /**左中括号*/
    public static final String LEFT_MIDDLE_BRACKET = "[";
    /**转义括号*/
    public static final String ESCAPED_LEFT_MIDDLE_BRACKET = "[[]";
    /**下划线*/
    public static final String UNDERLINE = "_";
    /**转义下划线*/
    public static final String ESCAPED_UNDERLINE = "[_]";
    /**百分号*/
    public static final String PERCENT = "%";
    /**转义百分号*/
    public static final String ESCAPED_PERCENT = "[%]";
    /**单引号*/
    public static final String SINGLE_QUOTE = "'";
    /**转义单引号*/
    public static final String ESCAPED_SINGLE_QUOTE = "''";
    /**日期时间格式*/
    public static DatetimeFormat[] DATE_TIME_FORMAT_ARRAY;
    /**金额格式*/
    public static BigDecimalFormat[] BIGDECIMAL_FORMAT_ARRAY;
    
    static {
        DATE_TIME_FORMAT_ARRAY = new DatetimeFormat[] {
    		DatetimeFormat.DEFAULT_DATE_TIME,
    		DatetimeFormat.DEFAULT_DATE_TIME,
    		DatetimeFormat.DEFAULT_DATE,
    		DatetimeFormat.DEFAULT_TIME,
    		DatetimeFormat.YEAR_MONTH_DAY,
    		DatetimeFormat.YEAR_MONTH,
    		DatetimeFormat.FOUR_YEAR_MONTH,
    		DatetimeFormat.TWO_YEAR_MONTH,
    		DatetimeFormat.YYYYMMDDHHMMSSS,
    		DatetimeFormat.YYYYMMDDHHMMSS,
    		DatetimeFormat.YYYYMMDD,
    		DatetimeFormat.YYYYMM,
    		DatetimeFormat.YYMM,
    		DatetimeFormat.YYYY_MM_DD_HH_MM_SS_SSS,
    		DatetimeFormat.YYYY_MM_DD,
    		DatetimeFormat.HH_MM_SS,
    		DatetimeFormat.YY_MM_DD_HH_MM_SS_SSS,
    		DatetimeFormat.YY_M_D_HH_MM_SS_SSS,
    		DatetimeFormat.YY_MM_DD, 
    		DatetimeFormat.YY_M_D,
    		DatetimeFormat.YYYY__MM__DD_HH_MM_SS_SSS,
    		DatetimeFormat.YYYY__MM__DD,
    		DatetimeFormat.YY__MM__DD_HH_MM_SS_SSS,
    		DatetimeFormat.YY__M__D_HH_MM_SS_SSS,
    		DatetimeFormat.YY__MM__DD,   
    		DatetimeFormat.YY__M__D
        };
        BIGDECIMAL_FORMAT_ARRAY = new BigDecimalFormat[] {
            BigDecimalFormat.INTEGER_NUMBER,
            BigDecimalFormat.MONEY,
            BigDecimalFormat.NUMBER,
            BigDecimalFormat.PER_NUMBER,
            BigDecimalFormat.NORMAL_NUMBER,
            BigDecimalFormat.TOW_00_NUMBER 
        };
    }
}
