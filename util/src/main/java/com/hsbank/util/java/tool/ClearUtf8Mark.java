package com.hsbank.util.java.tool;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

/**
 * 去掉UTF8格式的文件的BOM头
 * <p>
 * 一般用UE或记事本编辑过的UTF-8的文件头会加入BOM标识，该标识由3个char组成。
 * 在UTF－8的标准里该BOM标识是可有可无的。但是Sun的javac在编译带有BOM的UTF-8的格式
 * 的文件时会出现“非法字符：\\65279”的错误。用Eclipse进行编译没有问题，
 * 原因在于Eclipse使用的是自己的JDT，而非javac。
 * <p>
 * CreateDate: 2011-01-26
 * @author xwy
 */
public class ClearUtf8Mark {
	/*文件过滤器：只处理.java文件*/
	private static FileFilter fileFilter = new FileFilter() {
        public boolean accept(File file) {
        	boolean bResult = false;
            String fileName = file.getName().toLowerCase(Locale.getDefault());
            if (file.isDirectory() || fileName.endsWith(".java")) {
                bResult = true;
            }
            return bResult;
        }
    };
    	
	public static void main(String[] args) {
		File f = new File("C:\\mayland_workspace\\wmios_workspace\\wmios_util");
		ClearUtf8Mark.clear(f);
		System.out.println("It's finished !");
	}
	
	/**
	 * 去掉UTF8格式的Java源文件的BOM头
	 * @param filePathName
	 */
    public static void clear(String filePathName) {
    	File f = new File(filePathName);
    	clear(f);
    }
	
	/**
	 * 去掉UTF8格式的Java源文件的BOM头
	 * @param file
	 */
    public static void clear(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles(ClearUtf8Mark.fileFilter)) {
            	clear(f);
            }
        } else {
            FileInputStream fis = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            OutputStream out = null;
            try {
                fis = new FileInputStream(file);
                is = new BufferedInputStream(fis);
                baos = new ByteArrayOutputStream();
                byte b[] = new byte[3];
                is.read(b);
                if (-17 == b[0] && -69 == b[1] && -65 == b[2]) {
                    System.out.println(file.getAbsolutePath());
                    b = new byte[1024];
                    while (true) {
                        int bytes = 0;
                        try {
                            bytes = is.read(b);
                        } catch (IOException e) {
                        }
                        if (bytes == -1) {
                            break;
                        }
                        baos.write(b, 0, bytes);

                        b = baos.toByteArray();
                    }                    
                    file.delete();
                    out = new FileOutputStream(file);
                    baos.writeTo(out);
                }
            } catch (Exception e) {
               e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
        }
    }
}