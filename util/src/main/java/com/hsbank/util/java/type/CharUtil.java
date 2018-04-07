package com.hsbank.util.java.type;

import com.hsbank.util.java.JavaConstant;

public class CharUtil {
	/**
     * 判断传入的字符串是不是全是由大小写字母组成
     * @param strInput  传入的字符串
     * @return          是则返回true,否则返回false
     */
    public static boolean isChars(String strInput) {
        strInput = StringUtil.dealString(strInput);
        return strInput.matches(JavaConstant.PATTERN_CHAR);
    }
}
