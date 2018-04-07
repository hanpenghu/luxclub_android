package com.hsbank.util.java.type;

import android.util.Log;

import com.hsbank.util.java.JavaUtil;
import com.hsbank.util.java.constant.BasicTypeDefaultValue;
import com.hsbank.util.java.constant.BigDecimalFormat;
import com.hsbank.util.java.constant.CharSet;
import com.hsbank.util.java.constant.DatetimeFormat;
import com.hsbank.util.java.constant.StringConstant;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 * <p>
 * CreateDate 2006-12-28
 * @author wuyuan.xie
 */
public class StringUtil {
    /**
     * 处理传入的字符串变量
     * <p>
     * 如果是null，则返回"", 如果非null，则返回去掉前后两端的空格的传入字符串
     * <p>
     * 可扩展
     * @param s
     * @return String
     */
    public static String dealString(String s) {
        s = null == s ? "" : s.trim();
        s = "null".equals(s) ? "" : s;
        return s;
    }
    
    /**
     * 处理传入的字符串变量
     * <p>
     * 去掉前后两端的空格, 去掉字符串中的制表符(\t)、回车(\r)、换行符(\n)
     * <p>
     * @param s
     * @return
     */
    public static String dealStringForTRN(String s) {
        String resultValue = dealString(s);
        Pattern p = Pattern.compile("\t|\r|\n");
        Matcher m = p.matcher(s);
        resultValue = m.replaceAll("");
        return resultValue;
    }
    
    /**
     * 处理传入的字符串变量
     * <p>
     * 如果是null，则返回"", 如果非null，则返回则返回去掉前后两端的空格的传入字符串<br>
     * 新增对半角双引号的处理 
     * <p>
     * 可扩展
     * @param s
     * @return String
     */
    public static String dealStringForWeb(String s) {
        s = null == s ? "" : s.trim().replaceAll("\"", "&quot;");
        s = "null".equals(s) ? "" : s;
        return s;
    }
        
    /**
     * 得到给定字符串的字节数
     * @param s 给定字符串 
     * @return int
     */
    public static int getByteLength(String s) {
        return null == s ? 0 : s.getBytes().length;
    }
    
    /**
	 * 从指定字符串左边开始，截取定长字符串
	 * @param s				指定字符串
	 * @param iLength		指定长度
	 * @return
	 */
	public static String subString(String s, int iLength) {
		return s.length() > iLength ? s.substring(0, iLength) : s;
	}
	
	/**
	 * 从指定字符串左边开始，截取定长字符串(超过长充的字符串，截取后加附加字符串)
	 * @param s				指定字符串
	 * @param iLength		指定长度
	 * @param append 		附加字符串
	 * @return 
	 */
	public static String subString(String s, int iLength, String append) {
		return s.length() > iLength ? s.substring(0, iLength) + append : s;
	}
	
	/**
     * 得到一个“指定长度的指定字符”组成的字符串
     * @param c 	指定字符
     * @param length   指定个数
     * @return
     */
    public static String stringByRepeatChar(char c, int length) {
        NumberUtil.assertNotNegative(length);
        char[] charArray = new char[length];
        for (int i = 0;i < length;i++) {
            charArray[i] = c;
        }
        return new String(charArray);
    }
	
    /**
     * 将16进制值表示的字符串转换成byte数组，
     * <p>
     * @param strHex 需要转换的字符串
     * @return
     */
    public static byte[] hexStringToByteArray(String strHex) {
        byte[] arrTemp = strHex.getBytes();
        int iLength = arrTemp.length;
        //因为16进制中两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrByte = new byte[iLength / 2];
        String strTemp = null;
        for (int i = 0;i < iLength - 2;i = i + 2) {
            strTemp = new String(arrTemp, i, 2);
            arrByte[i / 2] = (byte) Integer.parseInt(strTemp, 16);
        }
        return arrByte;
    }
    
    /**
     * 把任意字符串转换成二进制表示的字符串
     * @param s         待转换的字符串
     * @return          二进制表示的字符串
     */
    public static String toBinaryString(String s) {
        StringBuffer sb = new StringBuffer();
        byte[] sourceArray = s.getBytes();
        int iLength = sourceArray.length;
        String strTemp = null;
        for (int i = 0; i < iLength; i++) {
            strTemp = Integer.toBinaryString(sourceArray[i]);
            //取最后8位
            strTemp = strTemp.substring(strTemp.length() - 8);
            sb.append(strTemp);
        }
        return sb.toString();
    }
        
    /**
     * 把任意字符串转换成十六进制表示的字符串
     * @param s         待转换的字符串
     * @return          十六进制表示的字符串
     */
    public static String toHexString(String s) {
        StringBuffer sb = new StringBuffer();
        byte[] sourceArray = s.getBytes();
        int iLength = sourceArray.length;
        String strTemp = null;
        for (int i = 0; i < iLength; i++) {
            strTemp = Integer.toHexString(sourceArray[i]);
            //取最后2位
            strTemp = strTemp.substring(strTemp.length() - 2);
            sb.append(strTemp);
        }
        return sb.toString();
    }

    /**
     * String转换成boolean
     * 
     * @param s 				需要被转换的String值
     * @param defaultValue 		无效设置或缺省时，使用的默认的值
     * @return boolean 			对整数型的值，转换后大于0的认为是true,等于0为false。
     *     						字符串形式的值忽略大小写，y,yes,t,on,true转换为true，n,no,f,off,false转换为false。
     *     						其它情况取bDefault的值。
     */
    public static boolean toBoolean(String s, boolean defaultValue) {
        boolean bResult = defaultValue;
        if (s == null) {
            bResult = defaultValue;
        } else {
            s = s.toUpperCase(Locale.getDefault()).trim();
            if (s.equals("Y")
                || s.equals("T")
                || s.equals("YES")
                || s.equals("ON")
                || s.equals("TRUE")
                || (NumberUtil.isDigital(s) && NumberUtil.toInt(s, 0) > 0)) {
                bResult = true;
            } else if (s.equals("N")
                || s.equals("F")
                || s.equals("NO")
                || s.equals("OFF")
                || s.equals("FALSE")
                || (NumberUtil.isDigital(s) && NumberUtil.toInt(s, 0) == 0)) {
                bResult = false;
            }
        } 
        return bResult;
    }
    
    /**
     * String转换成boolean
     * @param s 				需要被转换的String值
     * @return boolean 			对整数型的值，转换后大于0的认为是true,等于0为false。
     *     						字符串形式的值忽略大小写，y,yes,t,on,true转换为true，n,no,f,off,false转换为false。
     *     						其它情况取bDefault的值。
     */
    public static boolean toBoolean(String s) {
        return toBoolean(s, BasicTypeDefaultValue.DEFAULT_BOOLEAN);
    }
    
    /**
     * String转换为Date
     * @param strDate 	字符串日期
     * @return 			日期
     */
    public static Date toDate(String strDate) {
        JavaUtil.assertNotNull(strDate);
        Date returnValue = null;
        SimpleDateFormat sdf = null;
        // 尝试按已知的格式转换
        for (DatetimeFormat pattern: StringConstant.DATE_TIME_FORMAT_ARRAY) {
            try {
                sdf =  new SimpleDateFormat(pattern.value(), Locale.getDefault());
                returnValue = sdf.parse(strDate);
                // 如果转换成功，则验证其结果的正确性
                if (strDate.equals(DatetimeUtil.datetimeToString(returnValue, pattern))) {
                    break;
                }
            } catch (ParseException e) {
            	returnValue = null;
            	//Log.d(StringUtil.class.getName(), "", e);
            }
        }
        return returnValue;
    }
    
    /**
	 * 把指定字符串转换成指定格式的日期时间
	 * @param strDate 格式化字符串, 如"yyyy-MM-dd HH:mm:ss:SSS"
	 * @param df
	 * @return
	 */
	public static Date toDate(String strDate, DatetimeFormat df) {
		Date resultDate = null;
		DateFormat format = new SimpleDateFormat(df.value(), Locale.getDefault());
		try {
		    resultDate = format.parse(strDate);
		} catch (ParseException e) {
			Log.e(StringUtil.class.getName(), e.getMessage());
		}
		return resultDate;
	}
	
	/**
	 * 把指定字符串转换成指定格式的日期时间
	 * @param strDate 格式化字符串, 如"yyyy-MM-dd HH:mm:ss:SSS"
	 * @param format
	 * @return
	 */
	public static Date toDate(String strDate, String format) {
		Date resultDate = null;
		DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
		try {
		    resultDate = df.parse(strDate);
		} catch (ParseException e) {
			Log.e(StringUtil.class.getName(), e.getMessage());
		}
		return resultDate;
	}

    /**
     * String转换为BigDecimal
     * @param strBigDecimal 	字符串金额
     * @return 					金额
     */
    public static BigDecimal toBigDecimal(String strBigDecimal) {
        JavaUtil.assertNotNull(strBigDecimal);
        DecimalFormat df = new DecimalFormat();
        // 设置返回BigDecimal
        df.setParseBigDecimal(true);
        BigDecimal returnValue = null;
        // 尝试按已知的格式转换
        for (BigDecimalFormat pattern : StringConstant.BIGDECIMAL_FORMAT_ARRAY) {
            try {
                df.applyPattern(pattern.value());
                returnValue = (BigDecimal) df.parse(strBigDecimal);
                if (strBigDecimal.equals(BigDecimalUtil.toString(returnValue, pattern))) {
                    break;
                }
            } catch (ParseException e) {
            	returnValue = null;
            	Log.d(StringUtil.class.getName(), "", e);
            }
        }
        return returnValue;
    }
    
    /**
     * 在尾部“按指定长度追加指定字符到指定字符串”
     * @param s 		指定字符串
     * @param c 		指定字符
     * @param length 	指定长度
     * @return 			追加字符后的字符串
     */
    public static String rAppend(String s, char c, int length) {
        JavaUtil.assertNotNull(s);
        NumberUtil.assertNotNegative(length);
        return s + stringByRepeatChar(c, length);
    }
    
    /**
     * 在头部“按指定长度追加指定字符到指定字符串”
     * @param s 		指定字符串
     * @param c 		指定字符
     * @param length 	指定长度
     * @return 			追加字符后的字符串
     */
    public static String lAppend(String s, char c, int length) {
        JavaUtil.assertNotNull(s);
        NumberUtil.assertNotNegative(length);
        return stringByRepeatChar(c, length) + s;
    }
    
    /**
     * 在头尾分别“按指定长度追加指定字符到指定字符串”
     * @param s 		指定字符串
     * @param c 		指定字符
     * @param length 	指定长度
     * @return 			追加字符后的字符串
     */
    public static String append(String s, char c, int length) {
        return rAppend(lAppend(s, c, length), c, length);
    }
    
    /**
     * 判断字符串变量是否为空
     * @param s 	字符串变量
     * @return 是则返回true，否则返回false
     */
    public static boolean isEmpty(String s) {
        return null == s ? true : StringConstant.EMPTY.equals(s);
    }

    /**
     * 格式化指定字符串的首字母: 把首字母转换成大写字母或小写字母
     * @param s			字符串变量
     * @param sfc 		处理类型
     * @return 			处理后的字符串
     */
    public static String firstLetterFormat(String s, StringConstant.StringFormatConstant sfc) {
        JavaUtil.assertNotNull(sfc);
        // 如果字符串变量为空，则直接返回
        if (isEmpty(s)) {
            return s;
        }
        String firstLetter = null;
        if (StringConstant.StringFormatConstant.UPPER_CASE.equals(sfc)) {
            // 获取字符串变量的首字母的大写字母
            firstLetter = s.substring(0, 1).toUpperCase(Locale.getDefault());
        } else if (StringConstant.StringFormatConstant.LOWER_CASE.equals(sfc)) {
            // 获取字符串变量的首字母的小写字母
            firstLetter = s.substring(0, 1).toLowerCase(Locale.getDefault());
        }
        // 如果字符串变量长度大于1，则返回首字母处理后的字符串变量，
        // 否则返回处理后的首字母
        if (1 < s.length()) {
            return firstLetter + s.substring(1);
        } else {
            return firstLetter;
        }
    }

    /**
     * 字符串左对齐
     * @param s 		字符串
     * @param c   		填充字符
     * @param length	填充长度
     * @return 			左对齐后的字符串
     */
    public static String lAlign(String s, char c, int length) {
        JavaUtil.assertNotNull(s);
        NumberUtil.assertNotNegative(length);
        return s.length() >= length ? s : s + stringByRepeatChar(c, length - s.length());
    }

    /**
     * 字符串左对齐(默认填充半角空格)
     * @param s 		字符串
     * @param length 	填充长度
     * @return 			左对齐后的字符串
     */
    public static String lAlign(String s, int length) {
        return lAlign(s, StringConstant.SPACE_OF_HALF, length);
    }
    
    /**
     * 字符串右对齐
     * @param s 		字符串
     * @param c 		填充字符
     * @param length 		长度
     * @return 			右对齐后的字符串
     */
    public static String rAlign(String s, char c, int length) {
        JavaUtil.assertNotNull(s);
        NumberUtil.assertNotNegative(length);
        return s.length() >= length ? s : stringByRepeatChar(c, length - s.length()) + s;
    }
    
    /**
     * 字符串右对齐(默认填充半角空格)
     * @param s 		字符串
     * @param length 		长度
     * @return 			右对齐后的字符串
     */
    public static String rAlign(String s, int length) {
        return rAlign(s, StringConstant.SPACE_OF_HALF, length);
    }
    
    /**
     * 去除指定字符串头部的重复空格(包括半角空格和全角空格)
     * @param s 	字符串
     * @return 		去除空格后的字符串
     */
    public static String lTrim(String s) {
        JavaUtil.assertNotNull(s);
        String returnValue = "";
        int iLength = s.length();
        for (int i = 0; i < iLength; i++) {
            if (StringConstant.SPACE_OF_HALF != s.charAt(i) 
            	&& StringConstant.SPACE_OF_FULL != s.charAt(i)) {
            	//从第一个非空的字符开始截取子字符串返回
            	returnValue = s.substring(i);
            }
        }
        return returnValue;
    }

    /**
     * 去除指定字符串头部的重复字符
     * @param s		指定字符串
     * @param c 	重复字符
     * @return 		去除重复字符后的字符串
     */
    public static String lTrim(String s, char c) {
        JavaUtil.assertNotNull(s);
        int iLength = s.length();
        String returnValue = "";
        for (int i = 0; i < iLength; i++) {
            if (s.charAt(i) != c) {
            	returnValue = s.substring(i);
            }
        }
        return returnValue;
    }

    /**
     * 去除指定字符串尾部的重复空格(包括半角空格和全角空格)
     * @param s 		指定字符串
     * @return 			去除空格后的字符串
     */
    public static String rTrim(String s) {
        JavaUtil.assertNotNull(s);
        int iLength = s.length();
        String returnValue = "";
        for (int i = iLength - 1;i >= 0;i--) {
            if (StringConstant.SPACE_OF_HALF != s.charAt(i) 
            	&& StringConstant.SPACE_OF_FULL != s.charAt(i)) {
            	returnValue = s.substring(0, i + 1);
            }
        }
        return returnValue;
    }

    /**
     * 去除指定字符串尾部的重复字符
     * @param s		指定字符串
     * @param c 	重复字符
     * @return 		去除重复字符后的字符串
     */
    public static String rTrim(String s, char c) {
        JavaUtil.assertNotNull(s);
        int iLength = s.length();
        String returnValue = "";
        for (int i = iLength - 1;i >= 0;i--) {
            if (s.charAt(i) != c) {
            	returnValue = s.substring(0, i + 1);
            }
        }
        return returnValue;
    }

    /**
     * 去除指定字符串头部和尾部的重复空格(包括半角空格和全角空格)
     * @param s 		指定字符串
     * @return 			去除空格后的字符串
     */
    public static String trim(String s) {
        return lTrim(rTrim(s));
    }

    /**
     * 去除指定字符串头部和尾部的重复字符
     * @param s 	对象字符串
     * @param c 	重复字符
     * @return 		去除重复字符后的字符串
     */
    public static String trim(String s, char c) {
        return rTrim(lTrim(s, c), c);
    }
        
    /**
     * 转义SQL字符串
     * @param sql 		SQL字符串
     * @return 			转义后的SQL字符串
     */
    public static String sqlEscaped(String sql) {
        JavaUtil.assertNotNull(sql);
        return sql.replace(StringConstant.LEFT_MIDDLE_BRACKET, StringConstant.ESCAPED_LEFT_MIDDLE_BRACKET)
            .replace(StringConstant.PERCENT, StringConstant.ESCAPED_PERCENT)
            .replace(StringConstant.UNDERLINE, StringConstant.ESCAPED_UNDERLINE)
            .replace(StringConstant.SINGLE_QUOTE, StringConstant.ESCAPED_SINGLE_QUOTE);
    }

    /**
     * 逆转义SQL字符串
     * @param sql 		SQL字符串
     * @return 			逆转义后的SQL字符串
     */
    public static String sqlUnescape(String sql) {
        JavaUtil.assertNotNull(sql);
        return sql.replace(StringConstant.ESCAPED_SINGLE_QUOTE, StringConstant.SINGLE_QUOTE)
            .replace(StringConstant.ESCAPED_UNDERLINE, StringConstant.UNDERLINE)
            .replace(StringConstant.ESCAPED_PERCENT, StringConstant.PERCENT)
            .replace(StringConstant.ESCAPED_LEFT_MIDDLE_BRACKET, StringConstant.LEFT_MIDDLE_BRACKET);
    }

    /**
     * 全角转半角
     * @param s 	指定字符串
     * @return 		半角字符串 
     */
    public static String fullToHalf(String s) {
    	JavaUtil.assertNotNull(s);
        StringBuffer sb = new StringBuffer();
        int iLength = s.length();
        for (int i = 0; i < iLength; i++) {
            int index = StringConstant.CHAR_S_OF_FULL_WIDTH.indexOf(s.charAt(i));
            if (index != -1) {
                sb.append(StringConstant.CHAR_S_OF_HALF_WIDTH.charAt(index));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
    
    /**
     * 半角转全角
     * @param s 	指定字符串
     * @return 		全角字符串 
     */
    public static String halfToFull(String s) {
    	JavaUtil.assertNotNull(s);
        StringBuffer sb = new StringBuffer(s.length());
        int iLength = s.length();
        for (int i = 0; i < iLength; i++) {
            int index = StringConstant.CHAR_S_OF_HALF_WIDTH.indexOf(s.charAt(i));
            if (index != -1) {
                sb.append(StringConstant.CHAR_S_OF_FULL_WIDTH.charAt(index));
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
    
    /**
	 * 得到UTF-8的BOM标记
	 * @return
	 */
    public static String getUtf8BomString() {
		String s = "";
		byte[] utf8BomBytes = new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf};
		try {
			s = new String(utf8BomBytes, CharSet.UTF_8.value());
		} catch (UnsupportedEncodingException e) {
		   e.printStackTrace();
		}
		return s;
	}
    
    /**
     * 从源字符串strSource中，找出符合正则表达式strPattern的子字符串列表
     * @param strSource         源字符串
     * @param strPattern        正则表达式
     * @return
     */
    public static List<String> findList(String strSource, String strPattern) {
        List<String> resultList = new ArrayList<String>();
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strSource);
        while(m.find()) {
            resultList.add(m.group());
        }
        return resultList;
    }
    
    /**
     * 把【指定字符串】以【指定分隔符】分隔后的多个字符串放到一个列表中
     * @param s
     * @param strTag
     * @return
     */
    public static List<String> toList(String s, String strTag) {
    	List<String> resultValue = new ArrayList<String>();
    	s = dealString(s);
    	if (!"".equals(s)) {
    		String[] a = s.split(strTag);
    		for (String tempS : a) {
    			resultValue.add(tempS);
    		}
    	}
        return resultValue;
    }
    
    /**
     * 把【指定字符串】以【指定分隔符】分隔后的多个字符串放到一个集合中
     * @param s
     * @param strTag
     * @return
     */
    public static Map<String, String> toMap(String s, String strTag) {
    	Map<String, String> resultValue = new HashMap<String, String>();
    	s = dealString(s);
    	if (!"".equals(s)) {
    		String[] a = s.split(strTag);
    		for (String tempS : a) {
    			resultValue.put(tempS, tempS);
    		}
    	}
        return resultValue;
    }
    
    /**
     * ISO-8859-1 转 GBK
     * @param s
     * @return
     */
    public static String iso_8859_1_to_gbk(String s) {
    	String resultValue = s;
    	try {
    		resultValue = new String(s.getBytes(CharSet.ISO_8859_1.value()), CharSet.GBK.value());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return resultValue;
    }
    
    /**
     * ISO-8859-1 转 UTF-8
     * @param s
     * @return
     */
    public static String iso_8859_1_to_utf_8(String s) {
    	String resultValue = s;
    	try {
    		resultValue = new String(s.getBytes(CharSet.GBK.value()), CharSet.UTF_8.value());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return resultValue;
    }
    
    /**
     * GBK 转 ISO-8859-1
     * @param s
     * @return
     */
    public static String gbk_to_iso_8859_1(String s) {
    	String resultValue = s;
    	try {
    		resultValue = new String(s.getBytes(CharSet.GBK.value()), CharSet.ISO_8859_1.value());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return resultValue;
    }
    
    /**
     * GBK 转 UTF-8
     * @param s
     * @return
     */
    public static String gbk_to_utf_8(String s) {
    	String resultValue = s;
    	try {
    		resultValue = new String(s.getBytes(CharSet.GBK.value()), CharSet.UTF_8.value());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return resultValue;
    }
    
    /**
     * UTF-8 转 ISO-8859-1
     * @param s
     * @return
     */
    public static String utf_8_to_iso_8859_1(String s) {
    	String resultValue = s;
    	try {
    		resultValue = new String(s.getBytes(CharSet.UTF_8.value()), CharSet.ISO_8859_1.value());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return resultValue;
    }
    
    /**
     * UTF-8 转 GBK
     * @param s
     * @return
     */
    public static String utf_8_to_gbk(String s) {
    	String resultValue = s;
    	try {
    		resultValue = new String(s.getBytes(CharSet.UTF_8.value()), CharSet.GBK.value());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return resultValue;
    }

    /**
     * 反转字符串
     * @param s
     * @return
     */
    public static String reverse(String s) {
        return new StringBuffer(s).reverse().toString();
    }
}
