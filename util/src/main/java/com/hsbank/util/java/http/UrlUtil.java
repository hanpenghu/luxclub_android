package com.hsbank.util.java.http;

import android.util.Log;

import com.hsbank.util.java.constant.CharSet;
import com.hsbank.util.java.type.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UrlUtil {
	/**
	 * 处理url
	 * @param url		链接
	 * @return			处理后的链接
	 */
	public static String dealUrl(String url) {
		url = url == null ? "" : url;
		if (url.startsWith("http://")) {
			url = url.replaceFirst("http://", "");
			url = url.replaceAll("\\\\", "/").replaceAll("//", "/");
			url = "http://" + url;
		} else if (url.startsWith("https://")) {
			url = url.replaceFirst("https://", "");
			url = url.replaceAll("\\\\", "/").replaceAll("//", "/");
			url = "https://" + url;
		} else {
			url = url.replaceAll("\\\\", "/").replaceAll("//", "/");
		}
		return url;
	}

	/**
	 * 添加参数到指定的url
	 * @param url
	 * @return
	 */
	public static String addParameter(String url, String paramaters) {
		url = StringUtil.dealString(url);
		url += url.indexOf("?") > -1 ? "&" : "?";
		paramaters = StringUtil.dealString(paramaters);
		while (paramaters.startsWith("?") || paramaters.startsWith("&")) {
			paramaters = paramaters.substring(1);
		}
		return url + paramaters;
	}

	/**
	 * 得到url中不带参数的部分
	 * @param url
	 * @return
	 */
	public static String getUrlWithoutParameter(String url) {
		url = StringUtil.dealString(url);
		return StringUtil.dealString(url.split("\\?")[0]);
	}

	/**
	 * 得到url中的参数的部分
	 * @param url
	 * @return
	 */
	public static String getUrlParameters(String url) {
		url = StringUtil.dealString(url);
		return url.indexOf("?") > -1 ? StringUtil.dealString(url.substring(url.indexOf("?") + 1)) : "";
	}

	/**
	 * 得到url中的参数的部分
	 * @param url
	 * @return
	 */
	public static Map<String, Object> getUrlParameterMap(String url) {
		Map<String, Object> resultValue = new HashMap<String, Object>();
		String parameters = getUrlParameters(url);
		String[] parameterArray = parameters.split("&");
		for (String parameter : parameterArray){
			String[] tempArray = parameter.split("=");
			if (tempArray.length >= 2) {
				resultValue.put(tempArray[0], tempArray[1]);
			}
		}
		return resultValue;
	}

	/**
	 * Url编码：utf-8
	 * @param s			字符串
	 * @return			编码后的字符串
	 */
	public static String encodeUTF8(String s) {
		return encode(s, CharSet.UTF_8.value());
	}

	/**
	 * Url编码：gbk
	 * @param s			字符串
	 * @return			编码后的字符串
	 */
	public static String encodeGBK(String s) {
		return encode(s, CharSet.GBK.value());
	}

	/**
	 * Url编码
	 * @param s			字符串
	 * @param encoding 编码
	 * @return			编码后的字符串
	 */
	public static String encode(String s, String encoding) {
		String resultValue = "";
		try {
			resultValue = URLEncoder.encode(s, encoding);
		} catch (UnsupportedEncodingException e) {
			Log.e(UrlUtil.class.getName(), "s : " + s);
			Log.e(UrlUtil.class.getName(), "s : " + "encoding : " + encoding);
			Log.e(UrlUtil.class.getName(), e.getMessage());
		}
		return resultValue;
	}

	/**
	 * Url解码：utf-8
	 * @param s			字符串
	 * @return			解码后的字符串
	 */
	public static String decodeUTF8(String s) {
		return decode(s, CharSet.UTF_8.value());
	}

	/**
	 * Url解码：gbk
	 * @param s			字符串
	 * @return			解码后的字符串
	 */
	public static String decodeGBK(String s) {
		return decode(s, CharSet.GBK.value());
	}

	/**
	 * Url解码
	 * @param s			字符串
	 * @param encoding	编码
	 * @return			解码后的字符串
	 */
	public static String decode(String s, String encoding) {
		String resultValue = "";
		try {
			resultValue = URLDecoder.decode(s, encoding);
		} catch (UnsupportedEncodingException e) {
			Log.e(UrlUtil.class.getName(), "s : " + s);
			Log.e(UrlUtil.class.getName(), "s : " + "encoding : " + encoding);
			Log.e(UrlUtil.class.getName(), e.getMessage());
		}
		return resultValue;
	}

	/**
	 * 从url中得到文件名称
	 * @param url
	 * @return
	 */
	public static String getFileName(String url) {
		url = url == null ? "" : url;
		if (url.startsWith("http://")) {
			url = url.replaceFirst("http://", "");
			url = url.replaceAll("\\\\", "/").replaceAll("//", "/");
		} else if (url.startsWith("https://")) {
			url = url.replaceFirst("https://", "");
			url = url.replaceAll("\\\\", "/").replaceAll("//", "/");
		} else {
			url = url.replaceAll("\\\\", "/").replaceAll("//", "/");
		}
		if (url.contains("?")) {
			url = url.split("\\?")[0];
		}
		int lastIndex = url.lastIndexOf("/");
		String fileName = url.substring(lastIndex + 1);
		return decodeUTF8(fileName);
	}

	/**
	 * 得到指定文件的扩展名
	 * @param url
	 * @return 如“jpg”、“png”、“gif”
	 */
	public static String getFileExt(String url) {
		String resultValue = "";
		String fileName = getFileName(url);
		if (fileName.contains(".")) {
			int lastIndex = fileName.lastIndexOf(".");
			resultValue = fileName.substring(lastIndex + 1);
		}
		return decodeUTF8(resultValue);
	}
}
