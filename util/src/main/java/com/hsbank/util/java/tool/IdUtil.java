package com.hsbank.util.java.tool;

import android.util.Log;

/**
 * Id生成器
 * <p>
 * @author xwy
 * 2011-02-28
 */
public class IdUtil {
	/**单例*/
	private static IdUtil instance = null;
	//---------------------------------------------------
	/**当前Id*/
	private int curId = 0;

	/**私有构造函数*/
	private IdUtil() {
		Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
	}

	/**
	 * 得到单例
	 * @return  单例
	 */
	public static synchronized IdUtil getInstance() {
		return instance == null ? instance = new IdUtil() : instance;
	}

	/**
	 * 得到一个新的Id
	 * @return
	 */
	public int getId() {
		return nextId();
	}

	/**
	 * 生成一个新的Id
	 * @return
	 */
	private synchronized int nextId() {
		return ++ curId;
	}
}
