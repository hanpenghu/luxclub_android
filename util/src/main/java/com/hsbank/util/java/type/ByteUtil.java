package com.hsbank.util.java.type;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;

/**
 * 字节操作公共类
 * <p>
 * CreateDate 2006-12-28
 * 
 * @author wuyuan.xie
 * @version 1.0
 * 
 */
public class ByteUtil {
	/**十六进制的元素数组：字母小写*/
    public static final String[] ARRAY_HEX_ELEMENT_LOWER_CASE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    /**十六进制的元素数组：字母大写*/
    public static final String[] ARRAY_HEX_ELEMENT_UPPER_CASE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    
    /**
	 * 把二进制流转换为byte数组
	 * @param is
	 * @return
	 */
	public static byte[] inputStreamToByteArray(InputStream is) {
		byte[] returnValue = null;
		if (is != null) {
			try {
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				//创建数据读取缓存byte数组
				byte[] buffer = new byte[1024];
				int c;
				c = is.read(buffer);
				while (c != -1) {
					bytestream.write(buffer, 0, c);
					c = is.read(buffer);
				} 
				//将ByteArrayOutputStream转换为二进制数组
				returnValue = bytestream.toByteArray();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

    /**
     * 将字节数组转换为十六进制表示的字符串
     * <p>
     * 这个方法注释掉是因为下面那种写法更简洁
     * <p>
     * 若使用本函数转换则可得到返回结果的16进制表示，即数字字母混合的形式
     * @param byteArray 		字节数组
     * @return 					十六进制表示的字串
     */
    /*
    public static String toHexString(byte[] byteArray) {
        int iLength = byteArray.length;
        //每个byte用两个字符才能表示(因为是十六进制)，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLength * 2);
        int iTemp = 0;
        for (int i = 0; i < iLength; i++) {
            iTemp = byteArray[i];
            //把负数转换为正数
            while (iTemp < 0) {
                iTemp = 256 + iTemp;
            }
            int d1 = iTemp / 16;
            int d2 = iTemp % 16;
            sb.append(ARRAY_HEX_ELEMENT_UPPER_CASE[d1] + ARRAY_HEX_ELEMENT_UPPER_CASE[d2]);
        }
        return sb.toString();
    }
    */

    /**
     * 字节数组转换成十六进制表示的字符串
     * @param bytes			字节数组
     * @return				十六进制表示的字符串
     */
    public static String toHexString(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        //下面的输出，字母都是大写的
        return String.format("%0" + (bytes.length << 1) + "X", bi);
        //下面的输出，字母都是小写的
        //return String.format("%0" + (bytes.length << 1) + "x", bi);
    }
    
    /**
     * 将字节数组转换为十进制值表示的字符串
     * <p>
     * 使用本函数则可得到返回结果的10进制表示，即全数字形式
     * 
     * @param arrByte 字节数组
     * @return 十进制字串
     */
    public static String toDecString(byte[] arrByte) {
        StringBuffer sb = new StringBuffer();
        int iLength = arrByte.length;
        int iTemp = 0;
        for (int i = 0; i < iLength; i++) {
            iTemp = arrByte[i];
            //把负数转换为正数
            while (iTemp < 0) {
                iTemp = 256 + iTemp;
            }            
            sb.append(iTemp);
        }
        return sb.toString();
    }    
}
