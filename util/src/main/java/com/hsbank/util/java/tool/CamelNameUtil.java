package com.hsbank.util.java.tool;

import com.hsbank.util.java.constant.StringConstant;
import com.hsbank.util.java.type.StringUtil;

import java.util.Locale;

/**
 * 命名转换工具类
 * @author Arthur.Xie
 * 2014-11-15
 */
public class CamelNameUtil {
	/**
	 * 驼峰法命名 转 下划线命名
	 * @param camelName		驼峰法命名的字符串
	 * @return				下划线命名的字符串
	 */
	public static String camel2underscore(String camelName){
		camelName = StringUtil.dealString(camelName);
		//先把第一个字母大写
		camelName = capitalize(camelName);
		String regex = "([A-Z][a-z]+)";
		String replacement = "$1_";
		String underscoreName = camelName.replaceAll(regex, replacement);
		//把最后一个_去掉，然后全部改小写
		underscoreName = underscoreName.toLowerCase(Locale.getDefault()).substring(0, underscoreName.length() - 1);
		return underscoreName;
	}

	/**
	 * 下划线命名 转 驼峰法命名
	 * @param underscoreName		下划线命名的字符串
	 * @return						驼峰法命名的字符串
	 */
	public static String underscore2camel(String underscoreName){
		String[] tempArray = underscoreName.split(StringConstant.UNDERLINE);
		StringBuilder sb = new StringBuilder();
		int iLength = tempArray.length;
		for(int i = 0; i < iLength; i ++){
			String s = tempArray[i];
			if (i == 0) {
				sb.append(s);
			} else {
				sb.append(capitalize(s));
			}
		}
		return sb.toString();
	}

	/**
	 * 把指定字符串的第一个字母转换成大写
	 * @param s			输入字符串
	 * @return			转换后的字符串
	 */
	public static String capitalize(String s) {
		int iLength;
		if (s == null || (iLength = s.length()) == 0) {
			return s;
		}
		return new StringBuilder(iLength)
				.append(Character.toTitleCase(s.charAt(0)))
				.append(s.substring(1))
				.toString();
	}
}
