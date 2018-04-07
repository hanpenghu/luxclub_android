package com.hsbank.util.android.util.thread;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 默认线程池
 * 2015-12-16
 */
public class DefaultThreadPool implements IThreadPool {
	/**单例*/
	private static DefaultThreadPool instance = null;
	/**线程池大小**/
	private static final int THREAD_POOL_SIZE = 30;
	/**线程池**/
	private final ExecutorService threadPool;
    
	/**构造函数*/
	public DefaultThreadPool() {
		Log.d(this.getClass().getName(), "..................." + this.getClass().getName() + ".................");
		threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		Log.i(this.getClass().getName(), "Thread pool init. threadPoolSize = " + THREAD_POOL_SIZE);
	}

	/**
	 * 得到单例
	 * @return  单例
	 */
	public static synchronized DefaultThreadPool getInstance() {
		return instance == null ? instance = new DefaultThreadPool() : instance;
	}
	
	@Override
	public void execute(Runnable runnable) {
		threadPool.execute(runnable);
	}
}

