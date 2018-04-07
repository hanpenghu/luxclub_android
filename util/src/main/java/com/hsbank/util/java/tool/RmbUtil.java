package com.hsbank.util.java.tool;

/**
 * 人民币大写转换: 将阿拉伯数字表示的人民币金额转换为中文大写数字表示的形式 
 *  
 * 约束条件：
 * 1. 处理的最大数字不通超过千兆（1000,0000,0000,0000.00）
 * 2. 最小单位精确到分
 * 
 * @author Arthur.Xie
 * 2009-10-30
 */
public class RmbUtil {
	private static String[] DIGIT_ARRAY = new String[] {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	private static String[] UNIT_ARRAY = new String[] {"厘", "分", "角", "圆", "拾", "佰", "仟", "万", "亿", "兆" };
	/**特殊字符：整*/
	private static final String TAG_ZHENG = "整";
	
	/**
	 * 测试入口函数
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String strTemp = toChineseUppercase(2340912.23);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(2071234.00);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(100234.00);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(100000.60);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(10000000.05);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(0.00);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(0.10);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(1007.03);
			System.out.println(strTemp);
			strTemp = toChineseUppercase(1000000000000000.00);
			System.out.println(strTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 人民币大写转换
	 *  约束条件：
	 * 1. 处理的最大数字不能超过千兆（1000,0000,0000,0000.00）
	 * 2. 最小单位精确到分
	 * @param value
	 * @return
	 */
	public static String toChineseUppercase(double value) throws Exception {
		String returnValue = "";
		//转化成字符串: 当数字大于10个位的时候，也就是达亿的时候，如果直接转换成字符串，
		//会转换成科学计数法表示的字符串，解决方法就是把数字转化成整型long.
		String strValue = String.valueOf((long)(value * 100));		
		int iLength = strValue.length();
		if (strValue.equals("0")) {
			//金额为“零”，则直接输出结果
			returnValue = DIGIT_ARRAY[0] + UNIT_ARRAY[3];
		} else if (iLength < 3) {
			//只有小数部分
			returnValue = dealDotPart("", "0" + strValue);
		} else if (iLength > 18) {
			//超过千兆的问题，则抛出异常: 数值过大！
			throw new InternalError("The amount is too big.");
		} else {
			//取整数部分
			String intPart = strValue.substring(0, iLength - 2);
			//取小数部分
			String dotPart = strValue.substring(iLength - 2);
			
			//整数部分转化的结果
			String intResult = dealIntPart(intPart);
			//小数部分转化的结果
			String dotResult = dealDotPart(intResult, dotPart);
			
			if (dotResult.equals("")) {
				//只有整数部分
				returnValue = intResult + TAG_ZHENG;
			} else if (intResult.equals("")) {
				//只有小数部分
				returnValue = dotResult;
			} else {
				//既有整数部分，又有小数部分
				returnValue = intResult + dotResult;
			} 
		}
		return returnValue;
	}	
	
	/**
	 * 处理整数部分
	 * @param inttPart
	 * @return
	 */
	private static String dealIntPart(String intPart) {
		String returnValue = "";
		//把阿拉伯数字转换成大写中文数字
		StringBuffer sb = new StringBuffer();
		int iLength = intPart.length();
		for (int i = 0; i < iLength; i++) {
		    int digit = Integer.valueOf(intPart.substring(i, i + 1)).intValue();
		    sb.append(DIGIT_ARRAY[digit]);
		}
		intPart = sb.reverse().toString();
		
		StringBuffer resultSb = new StringBuffer();		
		for (int i = 0; i < iLength; i++) {
			if (i == 0) {
				resultSb.append(UNIT_ARRAY[3]);
			} else {				
				if (i % 12 == 0) {
					//兆位处理
					resultSb.append(UNIT_ARRAY[9]);
				} else if (i % 8 == 0) {
					//亿位处理
					resultSb.append(UNIT_ARRAY[8]);
				} else if (i % 4 == 0) {
					//万位处理
					resultSb.append(UNIT_ARRAY[7]);
				} else {
					resultSb.append(UNIT_ARRAY[i % 4 + 3]);
				}
			}
			//加面值
			String digit = intPart.substring(i, i + 1);
			resultSb.append(digit);
		}

		returnValue = resultSb.reverse().toString();

		returnValue = returnValue.replaceAll("零拾", "零");
		returnValue = returnValue.replaceAll("零佰", "零");
		returnValue = returnValue.replaceAll("零仟", "零");
		
		returnValue = returnValue.replaceAll("零零零零万", "零");
		returnValue = returnValue.replaceAll("零零零零亿", "零");

		returnValue = returnValue.replaceAll("[零]+", "零");

		returnValue = returnValue.replaceAll("零圆", "圆");
		returnValue = returnValue.replaceAll("零万", "万");
		returnValue = returnValue.replaceAll("零亿", "亿");
		returnValue = returnValue.replaceAll("零兆", "兆");
		
		returnValue = returnValue.replaceAll("壹拾", "拾");
		
		return returnValue;
	}	
	
	/**
	 * 处理小数部分
	 * @param dotPart
	 * @return
	 */
	private static String dealDotPart(String intResult, String dotPart) {
		String returnValue = "";
		if (dotPart.equals("00")) {
			returnValue = "";
		} else {
			if (dotPart.charAt(0) == '0') {
				//角为：'0'
				if (!intResult.equals("")) {
					//整数部分存在: 则变成如括号内形式（拾圆零叁分）
					returnValue = DIGIT_ARRAY[0];
				} 
			} else {
				returnValue = DIGIT_ARRAY[dotPart.charAt(0) - '0'] + UNIT_ARRAY[2];
			}
			returnValue += DIGIT_ARRAY[dotPart.charAt(1) - '0'] + UNIT_ARRAY[1];
		}
		return returnValue;
	}
}
