package com.hsbank.util.java.tool;

import android.util.Log;

import com.hsbank.util.java.tool.config.IConfigFile;
import com.hsbank.util.java.type.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码操作类
 * <p>
 * CreateDate 2006-01-25
 * @author wuyuan.xie
 */
public class MobileUtil {
    /**默认的参数配置文件名称*/
    public static final String DEFAULT_CONFIG_FILE_NAME = "mobile.xml";
    /**手机号码长度*/
    private static final int LENGTH_OF_MOBILE = 11;
    //-----------------------------------------------------------------------------------------------------------------------
    /**正则表达式： 判断是不是中国移动的手机号码*/
    public static final String PATTERN_OF_CMCC_MOBILE = "13[4-9][0-9]{8}|14[07][0-9]{8}|15[0124789][0-9]{8}|165[0-9]{8}|170[356][0-9]{7}|178[0-9]{8}|18[23478][0-9]{8}";
    /**正则表达式： 判断是不是中国联通的手机号码*/
    public static final String PATTERN_OF_CUTC_MOBILE = "13[012][0-9]{8}|14[135][0-9]{8}|15[56][0-9]{8}|170[789][0-9]{7}|17[56][0-9]{8}|18[56][0-9]{8}";
    /**正则表达式： 判断是不是中国电信的手机号码*/
    public static final String PATTERN_OF_CTCC_MOBILE = "133[0-9]{8}|14[24689][0-9]{8}|153[0-9]{8}|170[012][0-9]{7}|17[037][0-9]{8}|18[019][0-9]{8}";
    //-----------------------------------------------------------------------------------------------------------------------
    /**正则表达式标签： 判断是不是中国移动的手机号码*/
    public static final String TAG_OF_PATTERN_CMCC_MOBILE = "pattern_cmcc_mobile";
    /**正则表达式标签： 判断是不是中国联通的手机号码*/
    public static final String TAG_OF_PATTERN_CUTC_MOBILE = "pattern_cutc_mobile";
    /**正则表达式标签： 判断是不是中国电信的手机号码*/
    public static final String TAG_OF_PATTERN_CTCC_MOBILE = "pattern_ctcc_mobile";
    //-----------------------------------------------------------------------------------------------------------------------
    /**手机邮箱后缀： 中国移动*/
    public static final String SUFFIX_OF_CMCC_MOBILE_MAIL = "@139.com";
    /**手机邮箱后缀： 中国联通*/
    public static final String SUFFIX_OF_CUTC_MOBILE_MAIL = "@156.cn";
    /**手机邮箱后缀： 中国电信*/
    public static final String SUFFIX_OF_CTCC_MOBILE_MAIL = "@189.cn";
    //-----------------------------------------------------------------------------------------------------------------------
    /**手机邮箱后缀标签： 中国移动*/
    public static final String TAG_OF_SUFFIX_MAIL_CMCC = "suffix_mail_cmcc";
    /**手机邮箱后缀标签： 中国联通*/
    public static final String TAG_OF_SUFFIX_MAIL_CUTC = "suffix_mail_cutc";
    /**手机邮箱后缀标签： 中国电信*/
    public static final String TAG_OF_SUFFIX_MAIL_CTCC = "suffix_mail_ctcc";
    //-----------------------------------------------------------------------------------------------------------------------
    /**参数配置文件接口*/
    private static IConfigFile configFile = null;

    /**
     * 判断是不是正确的中国移动的手机号码 
     * <p>
     * CMCC: China Mobile Communication Corporation. 中国移动的英文简称。
     * @param mobile 手机号码
     * @return boolean 如果是则返回true，否则返回false 
     */
    public static boolean isCmccMobile(String mobile) {
        boolean bResult = false;
        Pattern p;
        Matcher m;
        try {
        	mobile = StringUtil.dealString(mobile);
            if (mobile.length() == LENGTH_OF_MOBILE) {
                //用正则表达式判断手机号码的正确性
            	String strPattern = PATTERN_OF_CMCC_MOBILE;
            	if (configFile != null) {
            		strPattern = configFile.getString(TAG_OF_PATTERN_CMCC_MOBILE, "");
            	}
                p = Pattern.compile(strPattern);
                m = p.matcher(mobile);
                bResult = m.matches();
            }
        } catch (Exception e) {
            Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return bResult;
    }
    
    /**
     * 判断是不是正确的中国移动的手机号码 
     * <p>
     * CMCC: China Mobile Communication Corporation. 中国移动的英文简称。
     * @param mobile 手机号码
     * @param pattern 判断手机号码的正则表达式
     * @return boolean 如果是则返回true，否则返回false 
     */
    public static boolean isCmccMobile(String mobile, String pattern) {
        boolean bResult = false;
        Pattern p;
        Matcher m;
        try {
        	mobile = StringUtil.dealString(mobile);
            if (mobile.length() == LENGTH_OF_MOBILE) {
                //用正则表达式判断手机号码的正确性          
                p = Pattern.compile(pattern);
                m = p.matcher(mobile);
                bResult = m.matches();
            }
        } catch (Exception e) {
            Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return bResult;
    }
    
    /**
     * 判断是不是正确的中国联通的手机号码 
     * CUTC: China United Telecommunications Co.Ltd. 中国联通的英文简称。
     * @param mobile 手机号码
     * @return boolean 如果是则返回true，否则返回false 
     */
    public static boolean isCutcMobile(String mobile) {
        boolean bResult = false;
        Pattern p;
        Matcher m;
        try {
        	mobile = StringUtil.dealString(mobile);
            if (mobile.length() == LENGTH_OF_MOBILE) {
                //用正则表达式判断手机号码的正确性
            	String strPattern = PATTERN_OF_CUTC_MOBILE;
            	if (configFile != null) {
            		strPattern = configFile.getString(TAG_OF_PATTERN_CUTC_MOBILE, "");
            	}
                p = Pattern.compile(strPattern);
                m = p.matcher(mobile);
                bResult = m.matches();
            }
        } catch (Exception e) {
            Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return bResult;
    }
    
    /**
     * 判断是不是正确的中国联通的手机号码 
     * CUTC: China United Telecommunications Co.Ltd. 中国联通的英文简称。
     * @param mobile 手机号码
     * @param pattern 判断手机号码的正则表达式
     * @return boolean 如果是则返回true，否则返回false 
     */
    public static boolean isCutcMobile(String mobile, String pattern) {
        boolean bResult = false;
        Pattern p;
        Matcher m;
        try {
        	mobile = StringUtil.dealString(mobile);
            if (mobile.length() == LENGTH_OF_MOBILE) {
                //用正则表达式判断手机号码的正确性          
                p = Pattern.compile(pattern);
                m = p.matcher(mobile);
                bResult = m.matches();
            }
        } catch (Exception e) {
            Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return bResult;
    }
    
    /**
     * 判断是不是正确的中国电信的手机号码 
     * CTCC: China TeleCommunications Corporation. 中国电信的英文简称。
     * @param mobile 手机号码
     * @return boolean 如果是则返回true，否则返回false 
     */
    public static boolean isCtccMobile(String mobile) {
        boolean bResult = false;
        Pattern p;
        Matcher m;
        try {
        	mobile = StringUtil.dealString(mobile);
            if (mobile.length() == LENGTH_OF_MOBILE) {
                //用正则表达式判断手机号码的正确性
            	String strPattern = PATTERN_OF_CTCC_MOBILE;
            	if (configFile != null) {
            		strPattern = configFile.getString(TAG_OF_PATTERN_CTCC_MOBILE, "");
            	}
                p = Pattern.compile(strPattern);
                m = p.matcher(mobile);
                bResult = m.matches();
            }
        } catch (Exception e) {
            Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return bResult;
    }
    
    /**
     * 判断是不是正确的中国电信的手机号码 
     * CTCC: China TeleCommunications Corporation. 中国电信的英文简称。
     * @param mobile 手机号码
     * @param pattern 判断手机号码的正则表达式
     * @return boolean 如果是则返回true，否则返回false 
     */
    public static boolean isCtccMobile(String mobile, String pattern) {
        boolean bResult = false;
        Pattern p;
        Matcher m;
        try {
        	mobile = StringUtil.dealString(mobile);
            if (mobile.length() == LENGTH_OF_MOBILE) {
                //用正则表达式判断手机号码的正确性
                p = Pattern.compile(pattern);
                m = p.matcher(mobile);
                bResult = m.matches();
            }
        } catch (Exception e) {
            Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return bResult;
    }

    /**
     * 判断是不是正确的手机号码 
     * @param mobile 手机号码
     * @return boolean 如果是正确的手机号码，则返回true，否则返回false 
     */
    public static boolean isMobile(String mobile) {
    	return isCmccMobile(mobile) 
        	|| isCutcMobile(mobile)
        	|| isCtccMobile(mobile);
    }
    
    /**
     * 判断是不是正确的手机号码 
     * @param mobile 手机号码
     * @param cmccPattern 判断中国移动手机号码的正则表达式
     * @param cutcPattern 判断中国联通手机号码的正则表达式
     * @param ctccPattern 判断中国电信手机号码的正则表达式
     * @return 如果是正确的手机号码，则返回true，否则返回false
     */
    public static boolean isMobile(String mobile, String cmccPattern, 
    		String cutcPattern, String ctccPattern) {
    	return isCmccMobile(mobile, cmccPattern) 
        	|| isCutcMobile(mobile, cutcPattern)
        	|| isCtccMobile(mobile, ctccPattern);
    }

    /**
     * 根据手机号码得到手机邮箱
     * @param mobile	手机号码
     * @return			手机邮箱
     */
    public static String getMail(String mobile) {
        mobile = StringUtil.dealString(mobile);
        //得到手机邮箱的后缀
        String returnValue = getMailSuffix(mobile);
        //得到手机邮箱
        returnValue = returnValue.equals("") ? "" : mobile + returnValue;
        return returnValue;
    }

    /**
     * 根据手机号码得到手机邮箱的后缀
     * @param mobile	手机号码
     * @return			手机邮箱的后缀
     */
    public static String getMailSuffix(String mobile) {
        String returnValue = "";
        if (isCmccMobile(mobile)) {
            returnValue = SUFFIX_OF_CMCC_MOBILE_MAIL;
            if (configFile != null) {
                returnValue = configFile.getString(TAG_OF_SUFFIX_MAIL_CMCC, "");
            }
        } else if (isCutcMobile(mobile)) {
            returnValue = SUFFIX_OF_CUTC_MOBILE_MAIL;
            if (configFile != null) {
                returnValue = configFile.getString(TAG_OF_SUFFIX_MAIL_CUTC, "");
            }
        } else if (isCtccMobile(mobile)) {
            returnValue = SUFFIX_OF_CTCC_MOBILE_MAIL;
            if (configFile != null) {
                returnValue = configFile.getString(TAG_OF_SUFFIX_MAIL_CTCC, "");
            }
        }
        return returnValue;
    }

    /**
     * 得到手机号码所属的运营商代码
     * @param mobile	手机号码
     * @return			手机号码所属的运营商代码
     */
    public static String getCarrierCode(String mobile) {
        String returnValue = "";
        if (isCmccMobile(mobile)) {
            returnValue = CarrierUtil.CARRIER_CODE_OF_CMCC;
        } else if (isCutcMobile(mobile)) {
            returnValue = CarrierUtil.CARRIER_CODE_OF_CUTC;
        } else if (isCtccMobile(mobile)) {
            returnValue = CarrierUtil.CARRIER_CODE_OF_CTCC;
        }
        return returnValue;
    }

    /**
     * 得到手机号码所属的运营商名称
     * @param mobile	手机号码
     * @return			手机号码所属的运营商名称
     */
    public static String getCarrierName(String mobile) {
        String returnValue = "";
        if (isCmccMobile(mobile)) {
            returnValue = CarrierUtil.CARRIER_NAME_OF_CMCC;
        } else if (isCutcMobile(mobile)) {
            returnValue = CarrierUtil.CARRIER_NAME_OF_CUTC;
        } else if (isCtccMobile(mobile)) {
            returnValue = CarrierUtil.CARRIER_NAME_OF_CTCC;
        }
        return returnValue;
    }

    /**
     * 得到手机号码的前三位
     * @param mobile		手机号码
     * @return				手机号码的前三位
     */
    public static String get3SegmentByMobile(String mobile) {
        mobile = StringUtil.dealString(mobile);
        if (mobile.startsWith("+86")) {
            return mobile.substring(3, 6);
        } else {
            if (mobile.startsWith("86")) {
                return mobile.substring(2, 5);
            } else {
                if (mobile.startsWith("1")) {
                    return mobile.substring(0, 3);
                } else {
                    return mobile;
                }
            }
        }
    }

    /**
     * 得到手机号码的前七位
     * @param mobile		手机号码
     * @return				手机号码的前七位
     */
    public static String get7SegmentByMobile(String mobile) {
        mobile = StringUtil.dealString(mobile);
        if (mobile.startsWith("+86")) {
            return mobile.substring(3, 10);
        } else {
            if (mobile.startsWith("86")) {
                return mobile.substring(2, 9);
            } else {
                if (mobile.startsWith("1")) {
                    return mobile.substring(0, 7);
                } else {
                    return mobile;
                }
            }
        }
    }

    /**
     * 得到不带86的手机号码
     * @param mobile		手机号码
     * @return				不带86的手机号码
     */
    public static String getMobileWithout86(String mobile) {
        String strResult = null;
        try {
            strResult = StringUtil.dealString(mobile);
            if (mobile.startsWith("+86")) {
                strResult = mobile.substring(3);
            } else if (mobile.startsWith("86")) {
                strResult = mobile.substring(2);
            } else {
                strResult = mobile;
            }
        } catch (Exception e) {
             Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return strResult;
    }

    /**
     * 得到带86的手机号码
     * @param mobile		手机号码
     * @return				带86的手机号码
     */
    public static String getMobileWith86(String mobile) {
        String strResult = null;
        try {
            mobile = StringUtil.dealString(mobile);
            if (mobile.startsWith("+86")) {
                strResult = mobile.substring(1);
            } else if (mobile.startsWith("1")) {
                strResult = "86" + mobile;
            } else {
                strResult = mobile;
            }
        } catch (Exception e) {
             Log.e(MobileUtil.class.getName(), e.getMessage());
        }
        return strResult;
    }
}
