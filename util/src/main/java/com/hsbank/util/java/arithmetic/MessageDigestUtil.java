package com.hsbank.util.java.arithmetic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法
 */
public class MessageDigestUtil {
	public static final String MD5 = "MD5";
	public static final String SHA_1 = "SHA-1";
	public static final String SHA_256 = "SHA-256";
	
	public static void main(String[] args) {
		String s = "加密前";
		System.out.println("Source String:" + s);
		System.out.println("Encrypted String:");
		System.out.println("Use MD5:" + MessageDigestUtil.encode(s, null));
		System.out.println("Use MD5:" + MessageDigestUtil.encode(s, MD5));
		System.out.println("Use SHA:" + MessageDigestUtil.encode(s, SHA_1));
		System.out.println("Use SHA-256:" + MessageDigestUtil.encode(s, SHA_256));
	}
 
	public static String encode(String s, String encodeType) {
		MessageDigest md = null;
		String resultValue = null;
		byte[] bt = s.getBytes();
		try {
			if (encodeType == null || "".equals(encodeType)) {
				//默认使用MD5
				encodeType = MD5;
			}
			md = MessageDigest.getInstance(encodeType);
			md.update(bt);
			resultValue = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return s;
		}
		return resultValue;
	}

	private static String bytes2Hex(byte[] byteArray) {
		String resultValue = "";
		String strTemp = null;
		for (int i = 0; i < byteArray.length; i++) {
			strTemp = (Integer.toHexString(byteArray[i] & 0xFF));
			if (strTemp.length() == 1) {
				resultValue += "0";
			}
			resultValue += strTemp;
		}
		return resultValue;
	}
}