package com.hsbank.util.java.tool;

import com.hsbank.util.java.JavaUtil;
import com.hsbank.util.java.type.StringUtil;

/**
 * 定义了与性别有关的一些操作方法
 * @author Arthur.Xie
 * CreateDate 2008-11-11 
 */
public class SexUtil {
	/**键值对*/
	public static final String SEXS = "male|男,female|女,secret|保密";
    /**男*/
    public static final String SEX_MALE = "male";
    public static final String SEX_NAME_MALE = "男";
    /**女*/
    public static final String SEX_FEMALE = "female";
    public static final String SEX_NAME_FEMALE = "女";
    /**保密*/
    public static final String SEX_SECRET = "secret";
    public static final String SEX_NAME_SECRET = "保密";
    /**默认的表示性别的input对象的名称*/
    public static final String DEFAULT_INPUT_NAME = "sex";
	
	/**
	 * 得到性别名称: 如male-->男;female-->女;secret-->保密
	 * @param sex
	 * @return String
	 */
	public static String getName(String sex) {
		return StringUtil.dealString(JavaUtil.getMap(SEXS).get(sex));
	}
	
	/**
	 * 得到一个下拉框的OPTION列表
	 * @param selectedCode
	 * @return
	 */
	public static String getOptions(String selectedCode) {
		return JavaUtil.getOptions(SEXS, selectedCode);
	}
	
	/**
	 * 得到与性别有关的一组单选框
	 * @param inputName
	 * @param checkedValue
	 * @return String
	 */
	public static String getRadios(String inputName, String checkedValue) {
		return JavaUtil.getRadios(inputName, SEXS, checkedValue);
	}
	
	/**
	 * 得到与性别有关的一组单选框
	 * @param checkedValue
	 * @return String
	 */
	public static String getRadios(String checkedValue) {
		return JavaUtil.getRadios(DEFAULT_INPUT_NAME, SEXS, checkedValue);
	}
	
	/**
	 * 得到与性别有关的一组复选框
	 * @param inputName
	 * @param checkedValues
	 * @return String
	 */
	public static String getCheckBoxs(String inputName, String checkedValues) {
		return JavaUtil.getCheckBoxs(inputName, SEXS, checkedValues);
	}
	
	/**
	 * 得到与性别有关的一组单选框
	 * @param checkedValues
	 * @return String
	 */
	public static String getCheckBoxs(String checkedValues) {
		return JavaUtil.getCheckBoxs(DEFAULT_INPUT_NAME, SEXS, checkedValues);
	}
}
