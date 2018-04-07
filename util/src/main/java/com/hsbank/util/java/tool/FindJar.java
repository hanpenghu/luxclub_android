package com.hsbank.util.java.tool;

import android.util.Log;

import java.net.URL;

/**
 * 常用公共方法类
 * <p>
 * CreateDate 2007-01-25
 * 
 * @author wuyuan.xie
 * @version 1.0
 */
public class FindJar {

    /**
     * 入口函数
     * @param args
     */
    public static void main(String[] args) {
        Log.d(FindJar.class.getName(), "...................begin....................");
        //Log.d(this.getClass().getName(), getClassPathName());
        Log.d(FindJar.class.getName(), "...................end......................");
    }
        
    /**
     * 得到类文件路径名称
	 * <p>
	 * 通过这个方法可以很容易的看出这个类来自哪个jar包
	 * <p>
     * @param c
     * @return
     */
    public static String getClassPathName(Class<?> c) {
    	String className = c.getName();
    	className = className.replace('.', '/');
    	String resource = "/" + className + ".class";
    	URL url = c.getResource(resource);
    	return url.getFile();
    }
}
