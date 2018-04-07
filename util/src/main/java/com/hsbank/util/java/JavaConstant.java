package com.hsbank.util.java;

/**
 * 常量类
 * <p>
 * CreateDate 2006-12-28
 * <p>
 * @author wuyuan.xie
 * @version 1.0
 */
public class JavaConstant {
    /**本地文件分隔符*/
    public static final String LOCAL_FILE_SEPARATOR = System.getProperty("file.separator");
    /**本地行分隔符*/
    public static final String LOCAL_LINE_SEPARATOR = System.getProperty("line.separator");
	/**换行符*/
	public static final String NEW_LINE = "\r\n";
    //--------------------------------------------------------------
    /**十六进制的元素数组*/
    public static final String[] ARRAY_HEX_ELEMENT = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    //--------------------------------------------------------------
    /**正则表达式：判断一个字符串是否是数字*/
    public static final String PATTERN_DIGIT = "[0-9]";
    /**正则表达式：判断一个字符串是否全是数字组成*/
    public static final String PATTERN_INT = "[0-9]*";
    /**正则表达式：判断一个字符串是否全是大小写字母组成*/
    public static final String PATTERN_CHAR = "[a-zA-Z]*";
}

