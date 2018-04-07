package com.hsbank.util.java.type;

import android.util.Log;

import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Pattern;

public class NumberUtil {
    /**正则表达式：判断一个字符串是否是数字*/
    private static final String PATTERN_DIGIT = "[0-9]";
    /**正则表达式：判断一个字符串是否全是数字组成*/
    private static final String PATTERN_INT = "[0-9]*";
    
	/**
     * String转换成int
     * @param s 				需要被转换的String值，可以是10进制（默认）或16进制（前缀为0x）的表示。
     * @param defaultValue 		无效设置或缺省时，使用的默认值
     * @return int 				s表示的整数值，否则为defaultValue的值
     */
    public static int toInt(String s, int defaultValue) {
        int returnValue = defaultValue;
        if (s == null) {
            returnValue = defaultValue;
        } else {
            int radix = 10;
            if (s.startsWith("0x")) {
                s = s.substring(2).trim();
                radix = 16;
            }
            try {
                returnValue = Integer.parseInt(s, radix);
            } catch (Exception e) {
            	Log.e(NumberUtil.class.getName(), "s=" + s);
            	//Log.e(NumberUtil.class.getName(), e.getMessage());
            }
        }
        return returnValue;
    }
    
    /**
     * String转换成long
     * @param s 				需要被转换的String值，可以是10进制（默认）或16进制（前缀为0x）的表示。
     * @param defaultValue 		无效设置或缺省时，使用的默认值
     * @return long 			s表示的整数值，否则为defaultValue的值
     */
    public static long toLong(String s, long defaultValue) {
        long returnValue = defaultValue;
        if (s == null) {
            returnValue = defaultValue;
        } else {
            int radix = 10;
            if (s.startsWith("0x")) {
                s = s.substring(2).trim();
                radix = 16;
            }
            try {
                returnValue = Long.parseLong(s, radix);
            } catch (Exception e) {
                Log.e(NumberUtil.class.getName(), "s=" + s);
                //Log.e(NumberUtil.class.getName(), e.getMessage());
            }
        }
        return returnValue;
    }
    
    /**
     * String转换成Float
     * @param s                 需要被转换的String
     * @param defaultValue      无效设置或缺省时，使用的默认值
     * @return double           s表示的浮点数值，否则为defaultValue的值
     */
    public static float toFloat(String s, float defaultValue) {
        float fResult = defaultValue;
        if (s == null) {
            fResult = defaultValue;
        } else {
            try {
                fResult = Float.parseFloat(s);
            } catch (Exception e) {
                Log.e(NumberUtil.class.getName(), "s=" + s);
                //Log.e(NumberUtil.class.getName(), e.getMessage());
            }
        }
        return fResult;
    }
    
    /**
     * String转换成Double
     * @param s 				需要被转换的String
     * @param defaultValue 		无效设置或缺省时，使用的默认值
     * @return double 			s表示的浮点数值，否则为defaultValue的值
     */
    public static double toDouble(String s, double defaultValue) {
    	double dResult = defaultValue;
        if (s == null) {
            dResult = defaultValue;
        } else {
            try {
                dResult = Double.parseDouble(s);
            } catch (Exception e) {
                Log.e(NumberUtil.class.getName(), "s=" + s);
                //Log.e(NumberUtil.class.getName(), e.getMessage());
            }
        }
        return dResult;
    }
	
	/**
	 * 递归得到x的y次幂
	 * <p>
	 * 要求：x和y都是整数，且y>0
	 * @param x
	 * @param y
	 * @return
	 */
	public int cimi(int x, int y) {
		int sum = 1;
		if (y > 0) {
			sum = x * cimi(x, y - 1);
		}
		return sum;
	}
	
	/**
     * 得到指定范围[min, max]的随机整数：min <= result <= max
     * @param min
     * @param max
     * @return
     */
    public static int getRandomInt(int min, int max) {
    	Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
    
	/**
     * 负值判断
     * @param number 数字
     */
    public static void assertNotNegative(int number) {
        if (0 > number) {
            throw new InvalidParameterException();
        }
    }
    
    /**
     * 判断是否为浮点数，包括double和float 
     * @param str 传入的字符串 
     * @return 是浮点数返回true,否则返回false 
     */  
     public static boolean isDouble(String str) {  
         Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");  
         return pattern.matcher(str).matches();  
     }
    
    /**
     * 判断传入的字符串是不是数字
     * @param s
     * @return boolean true是数字，false非数字
     */
    public static boolean isDigital(String s) {
        s = StringUtil.dealString(s);
        return s.matches(PATTERN_DIGIT);
    }
    
    /**
     * 判断传入的字符串是不是整数
     * @param s
     * @return boolean
     */
    public static boolean isInt(String s) {
        s = StringUtil.dealString(s);
        return s.matches(PATTERN_INT);
    }
    
    /**
     * 得到指定最大小数位的百分比
     * @param partCount    分子
     * @param totalCount   分母
     * @param maxDigits    最大的小数位
     * @return
     */
	public final static String getRate(double partCount, double totalCount, int maxDigits) {
		String returnValue = "0%";
		if (totalCount != 0) {
			double p =  partCount  /  totalCount;
	        NumberFormat nf = NumberFormat.getPercentInstance();
	        //下面这个属性为false，就能避免把："10000"返回成"1000,0"
	        nf.setGroupingUsed(false);
	        nf.setMinimumFractionDigits(maxDigits);
	        returnValue = nf.format(p);
		}
		return returnValue;
	}
	
	/**
     * 格式化数值：保留指定的最大小数位
     * @param d			   待格式化的数值
     * @param maxDigits    最大的小数位
     * @return
     */
	public final static String format(double d, int maxDigits) {
		NumberFormat nf = NumberFormat.getNumberInstance() ;
		nf.setMaximumFractionDigits(maxDigits); 
		return nf.format(d) ; 
	}
}
