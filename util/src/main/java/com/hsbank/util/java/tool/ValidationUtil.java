package com.hsbank.util.java.tool;

import com.hsbank.util.java.type.StringUtil;

import java.math.BigDecimal;

/**
 * 校验_公共类
 * @author xwy
 * 2011-04-02
 */
public class ValidationUtil {
    /**
     * 忽略检查
     */
    public static final int IGNORE = Integer.MIN_VALUE;
    /**
     * 密码规则表达式分隔符
     */
    public static final String APPEND_CHAR = ",";
    /**
     * 至少有一个大写字母
     */
    public static final String AT_LEAST_UPPER_CHAR = "(?=.*?\\p{Upper})";
    /**
     * 至少有一个小写字母
     */
    public static final String AT_LEAST_LOWER_CHAR = "(?=.*?\\p{Lower})";
    /**
     * 至少有一个数字
     */
    public static final String AT_LEAST_NUMBER_CHAR = "(?=.*?\\d)";
    /**
     * 至少有一个符号
     */
    public static final String AT_LEAST_SIGN_CHAR =
        "(?=.*?[-:;!\"$%^&*()_+=|\\\\?,./{}\\[\\]~'<>`@])";
    
    /**
     * 密码最小长度
     */
    public static final int MIN_PWD_LENGTH = 6;

    /**
     * 密码最大长度
     */
    public static final int MAX_PWD_LENGTH = 18;
    
    /**
     * 至少有一个符号
     */
    public static final String LEN_REGX_CHAR = ".{"
        + MIN_PWD_LENGTH + APPEND_CHAR
        + MAX_PWD_LENGTH + "}";
    
    /**
     * 密码规则表达式
     */
    public static final String[] PWD_REGEX_ARR = {
        AT_LEAST_UPPER_CHAR + AT_LEAST_LOWER_CHAR + AT_LEAST_NUMBER_CHAR + LEN_REGX_CHAR,
        AT_LEAST_UPPER_CHAR + AT_LEAST_LOWER_CHAR + AT_LEAST_SIGN_CHAR + LEN_REGX_CHAR,
        AT_LEAST_UPPER_CHAR + AT_LEAST_NUMBER_CHAR + AT_LEAST_SIGN_CHAR + LEN_REGX_CHAR,
        AT_LEAST_LOWER_CHAR + AT_LEAST_NUMBER_CHAR + AT_LEAST_SIGN_CHAR + LEN_REGX_CHAR
    };
       
    
    /**
     * 金额小数位数
     */
    public static final int MONEY_FRACTION = 2;
    
    /**
     * 金额整数位数
     */
    public static final int MONEY_INTEGRAL = 13;

    /**
     * 校验字符串字符数
     * 
     * @param str
     *            字符串
     * @param min
     *            最小长度，ValidationUtil.IGNORE时忽略检查
     * @param max
     *            最大长度，ValidationUtil.IGNORE时忽略检查
     * @return true时字符串长度有效
     */
    public static boolean validateLength(String str, int min, int max) {
        boolean retval = true;
        // 检查最小长度
        if (IGNORE != min) {
            retval = retval && str.length() >= min;
        }
        // 检查最大长度
        if (IGNORE != max) {
            retval = retval && str.length() <= max;
        }
        return retval;
    }

    /**
     * 校验日期字符串
     * @param strDate   日期字符串
     * @return 			
     */
    public static boolean validateDate(String strDate) {
        return null != StringUtil.toDate(strDate);
    }

    /**
     * 校验数字字符串最大值最小值
     * 
     * @param s
     *            字符串
     * @param min
     *            最小值
     * @param max
     *            最大值
     * @return true时数字字符串有效
     */
    public static boolean validateDoubleRange(String s, BigDecimal min, BigDecimal max) {
        boolean returnValue = true;
        // 转换数字
        BigDecimal bd = StringUtil.toBigDecimal(s);
        // 如果无法转换，则返回false
        if (null == bd) {
            return false;
        }
        // 检查最小值
        returnValue = returnValue && bd.compareTo(min) != -1;
        // 检查最大值
        returnValue = returnValue && bd.compareTo(max) != 1;
        return returnValue;
    }

    /**
     * 校验金额字符串
     * @param s 	金额字符串
     * @return true时金额字符串表示的是一个有效的金额（13位整数，2位小数）
     */
    public static boolean validateMoney(String s) {
        return validateNumber(s, MONEY_INTEGRAL, MONEY_FRACTION, false);
    }

    /**
     * 校验数字字符串
     * 
     * @param s 			数字字符串
     * @param integral 		整数位数
     * @param fraction 		小数位数
     * @param negativable 	是否可以为负数
     * @return
     */
    public static boolean validateNumber(String s, int integral,
        int fraction, boolean negativable) {
        boolean returnValue = false;

        // 如果无法转换为数字，则直接返回false
        if (null == StringUtil.toBigDecimal(s)) {
            return false;
        }
        // 如果数字不能为负数，则直接返回false
        if ((!negativable) && s.startsWith("-")) {
            return false;
        }
        // 过滤逗号
        String numstr = s.replace(APPEND_CHAR, "");
        // 小数点位置以及整数位数、小数位数
        int dotIdx = numstr.indexOf('.');
        int fracLen = (-1 != dotIdx) ? numstr.length() - dotIdx - 1 : 0;
        int inteLen = (-1 != dotIdx) ? dotIdx : numstr.length();
        // 如果小数点位置小于等于最大小数点位置且小数位数小于等于2时，返回true
        returnValue = (inteLen <= integral) && (fracLen <= fraction);
        return returnValue;
    }

    /**
     * 校验数字字符串
     * 
     * @param s 			数字字符串
     * @param integral 		整数位数
     * @param fraction 		小数位数
     * @return
     */
    public static boolean validateNumberLength(String s, int integral,
        int fraction) {
        return validateNumber(s, integral, fraction, true);
    }    
}
