package com.hsbank.util.java;

import android.util.Log;

import com.hsbank.util.java.collection.ArrayUtil;
import com.hsbank.util.java.collection.MapUtil;
import com.hsbank.util.java.constant.DatetimeFormat;
import com.hsbank.util.java.type.DatetimeUtil;
import com.hsbank.util.java.type.NumberUtil;
import com.hsbank.util.java.type.StringUtil;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用公共方法类
 * <p>
 * CreateDate 2007-01-25
 * 
 * @author wuyuan.xie
 * @version 1.0
 */
public class JavaUtil {

    /**
     * Null值判断
     * @param obj 对象
     */
    public static void assertNotNull(Object obj) {
        if (null == obj) {
            throw new InvalidParameterException();
        }
    }
    
    /**
     * 线程休眠（ms）
     * @param iSleepTime 线程休眠的时间（ms）
     */
    public static void threadSleep(long iSleepTime) {
        try {
            Thread.sleep(iSleepTime);
        } catch (Exception e) {
			Log.e(JavaUtil.class.getName(), e.getMessage());
        }
    }

	/**
	 * 得到一个下拉框的option列表
	 * @param recordList 列表中的每一条记录为一个HashMap
	 * @param textField
	 * @param valueField
	 * @param selectedValue 选中的值
	 * @return String
	 */
	public static String getOptions(List<Map<String, String>> recordList,
			String textField, String valueField, String selectedValue) {
		String resultStr = "";
		int iLength = recordList.size();
		Map<String, String> tempMap = null;
		String key = null;
		String value = null;
		String isSelected = "";
		for (int i = 0; i < iLength; i++) {
			tempMap = recordList.get(i);
			key = tempMap.get(textField);
			value = tempMap.get(valueField);
			isSelected = (value != null && value.equals(selectedValue)) ? " selected" : "";
			resultStr += "<option value='" + value + "'" + isSelected + ">" + key + "</option>";
		}
		return resultStr;
	}
	
	/**
	 * 从指定格式的字符串中得到一个下拉框的option列表
	 * <br>
	 * 格式示例："male|男,female|女,unknown|保密"
	 * 每个键值对以半角逗号“,”间隔，键值之间以“|”间隔
	 * @param strValueTexts
	 * @param selectedValue
	 * @return String
	 */
	public static String getOptions(String strValueTexts, String selectedValue) {
		StringBuffer sb = new StringBuffer();
		String arrValueText[] = strValueTexts.split(",");
		int iLength = arrValueText.length;			
		String strTemp = "";
		String value = "";
		String text = "";
		String isSelected = "";
		for (int i = 0; i < iLength; i++) {
			strTemp = arrValueText[i].trim();
			int j =  strTemp.indexOf("|");
			if (j > 0) {
				value = strTemp.substring(0, j).trim();
				text = strTemp.substring(j + 1).trim();
			} else {
				value = strTemp;
				text = strTemp;
			}
			isSelected = selectedValue.equals(value) ? " selected" : "";
			sb.append("<option value='" + value + "'" + isSelected + ">" + text + "</option>");
		}
		return sb.toString();
	} 
	
	/**
	 * 从指定列表中取得一个Map
	 * @param recordList 源数据列表
	 * @param keyFieldName   作为key的字段名称
	 * @param valueFieldName 作为value的字段名称
	 * @return 
	 */
	public static Map<String, Object> getMap(List<Map<String, Object>> recordList,
			String keyFieldName, String valueFieldName) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		String key = null;
		Object value = null;
		for (Map<String, Object> recordMap: recordList) {
			key = MapUtil.getString(recordMap, keyFieldName);
			value = recordMap.get(valueFieldName);
			returnValue.put(key, value);
		}
		return returnValue;
	}
	
	/**
	 * 从指定列表中取得一个Map
	 * @param recordList 		源数据列表
	 * @param keyFieldName   	作为key的字段名称
	 * @param valueFieldName1 	作为value的字段名称1
	 * @param valueFieldName2 	作为value的字段名称2
	 * @return 
	 */
	public static Map<String, Object> getMap(List<Map<String, Object>> recordList,
			String keyFieldName, String valueFieldName1, String valueFieldName2) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		String key = null;
		Object value = null;
		for (Map<String, Object> recordMap: recordList) {
			key = MapUtil.getString(recordMap, keyFieldName);
			value = MapUtil.getString(recordMap, valueFieldName1) + MapUtil.getString(recordMap, valueFieldName2);;
			returnValue.put(key, value);
		}
		return returnValue;
	}
		
	/**
	 * 从指定格式的字符串中取得一个Map
	 * <br>
	 * 格式示例："male|男,female|女,unknown|保密"
	 * 每个键值对以半角逗号“,”间隔，键值之间以“|”间隔
	 * @param strKeyValues 
	 * @return 
	 */
	public static Map<String, String> getMap(String strKeyValues) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String[] keyValueArray = strKeyValues.split(",");
		int iSize = keyValueArray.length;
		String strTemp = "";
		String value = "";
		String text = "";
		for (int i = 0; i < iSize; i++) {
			strTemp = keyValueArray[i].trim();
			int j = strTemp.indexOf("|");
			if (j > 0) {
				value = strTemp.substring(0, j).trim();
				text = strTemp.substring(j + 1).trim();
			} else {
				value = strTemp;
				text = strTemp;
			}
			returnValue.put(value, text);				
		}	
		return returnValue;
	}	
	
	/**
	 * 从指定格式的字符串中得到一组单选框
	 * <br>
	 * 格式示例："male|男,female|女,unknown|保密"
	 * 每个键值对以半角逗号“,”间隔，键值之间以“|”间隔
	 * @param inputName 单选框的名称
	 * @param strValueTexts
	 * @param checkedValue 选中的input的值
	 * @return String
	 */
	public static String getRadios(String inputName, String strValueTexts, String checkedValue) {
		StringBuffer sb = new StringBuffer();
		String[] valueTextArray = strValueTexts.split(",");
		int iLength = valueTextArray.length;			
		String strTemp = "";
		String value = "";
		String text = "";
		String isChecked = "";
		for (int i = 0; i < iLength; i++) {
			strTemp = valueTextArray[i].trim();
			int j =  strTemp.indexOf("|");
			if (j > 0) {
				value = strTemp.substring(0, j).trim();
				text = strTemp.substring(j + 1).trim();
			} else {
				value = strTemp;
				text = strTemp;
			}
			isChecked = StringUtil.dealString(checkedValue).equals(value) ? " checked" : "";
			sb.append("<input type='radio' name='" + inputName + "' value='" + value + "'" + isChecked + "/>" + text);
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString();
	}
	
	/**
	 * 从指定格式的字符串中得到一组复选框
	 * <br>
	 * 格式示例："male|男,female|女,unknown|保密"
	 * 每个键值对以半角逗号“,”间隔，键值之间以“|”间隔
	 * @param inputName 单选框的名称
	 * @param strValueTexts
	 * @param checkedValues 选中的input的值。多个选中时，以“,”间隔
	 * @return String
	 */
	public static String getCheckBoxs(String inputName, String strValueTexts, String checkedValues) {
		StringBuffer sb = new StringBuffer();
		String[] valueTextArray = strValueTexts.split(",");		
		int iLength = valueTextArray.length;			
		String strTemp = "";
		String value = "";
		String text = "";
		String isChecked = "";
		String[] checkedValueArray = checkedValues.split(",");
		for (int i = 0; i < iLength; i++) {
			strTemp = valueTextArray[i].trim();
			int j =  strTemp.indexOf("|");
			if ( j > 0 ) {
				value = strTemp.substring(0, j).trim();
				text = strTemp.substring(j + 1).trim();
			} else {
				value = strTemp;
				text = strTemp;
			}
			isChecked = ArrayUtil.isExist(checkedValueArray, value) ? " checked" : "";
			sb.append("<input type='checkbox' name='" + inputName + "' value='" + value + "'" + isChecked + "/>" + text);
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString();
	}
	
	/**
	 * 判断是不是正确的e_mail
	 * @param s
	 * @return
	 */
	public static boolean isEmail(String s) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.find();
	}
	
	/**
	 * 判断是不是正确的IP地址
	 * @param ip
	 * @return
	 */
	public static boolean isIpAddress(String ip) {
		boolean returnValue = false;
		if (!StringUtil.isEmpty(ip)) {
			String[] ipArray = ip.split("\\.");
			if (ipArray.length == 4) {
				try{
					int ipa = Integer.parseInt(ipArray[0]);
					int ipb = Integer.parseInt(ipArray[1]);
					int ipc = Integer.parseInt(ipArray[2]);
					int ipd = Integer.parseInt(ipArray[3]);
					returnValue = ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 
							   && ipc >= 0	&& ipc <= 255 && ipd >= 0 && ipd <= 255;
				} catch (Exception e) {
				}
			}
		}
		return returnValue;
	}
	
	/**
     * 得到文件名称的前缀
     * <p>
     * 取当前时间的“年-月-日_时分秒” + “3位随机数”作为实际保存的文件名称的前缀
     * @return
     */
    public static String getFileNamePrefix() {
    	final int begin = 100;
    	final int end = 999;
		return DatetimeUtil.datetimeToString(DatetimeFormat.YYYYMMDDHHMMSS) + "_" + NumberUtil.getRandomInt(begin, end);
    }
}
