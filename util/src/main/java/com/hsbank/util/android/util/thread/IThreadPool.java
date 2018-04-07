package com.hsbank.util.android.util.thread;

/**
 * 线程池。这里使用java.util.concurrency包里面的类实现，暴露一个容易使用的接口。
 * 
 * java.util.concurrent.ThreadPoolExecutor相关基础介绍和使用示例。 
 * 
 * (一)、常用线程池 
 * 
 * 最常用构造方法为：
 * 
 * ThreadPoolExecutor(
 * 		int corePoolSize,
 *		int maximumPoolSize,
 * 		long keepAliveTime,
 * 		TimeUnit unit,
 *		BlockingQueue&lt;Runnable&gt; workQueue,
 *		RejectedExecutionHandler handler)
 * 
 * JDK自带的配置好的线程池： 
 * 
 *		// 固定工作线程数量的线程池
 *      ExecutorService executorService1 = Executors.newFixedThreadPool(3);
 * 
 *      // 一个可缓存的线程池
        ExecutorService executorService2 = Executors.newCachedThreadPool();
 * 
 *      // 单线程化的Executor
 *      ExecutorService executorService3 = Executors.newSingleThreadExecutor();
 * 
 *      // 支持定时的以及周期性的任务执行
 *      ExecutorService executorService4 = Executors.newScheduledThreadPool(3);
 *      
 * 这些预定义好的线程池服务也是基于ThreadPoolExecutor配置的，所以我们应该从最基本的参数着手了解，如下：
 * 
 * 参数详细说明：集装箱运费
 * (1)、corePoolSize： 线程池维护线程的最少数量
 * (2)、maximumPoolSize：线程池维护线程的最大数量
 * (3)、keepAliveTime： 线程池维护线程所允许的空闲时间
 * (4)、unit： 线程池维护线程所允许的空闲时间的单位，unit可选的参数为java.util.concurrent.TimeUnit中的几个静态属性：
 * 		  		 NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS
 * (5)、workQueue： 线程池所使用的缓冲队列，常用的是：java.util.concurrent.ArrayBlockingQueue 
 * (6)、handler： 线程池对拒绝任务的处理策略，有四个选择如下： 
 * 				ThreadPoolExecutor.AbortPolicy()：抛出java.util.concurrent.RejectedExecutionException异常
 * 				ThreadPoolExecutor.CallerRunsPolicy()：重试添加当前的任务，他会自动重复调用execute()方法
 * 				ThreadPoolExecutor.DiscardOldestPolicy()：抛弃旧的任务
 * 				ThreadPoolExecutor.DiscardPolicy()：抛弃当前的任务
 * 
 * (二)、详细说明 
 * 
 * (1)、当一个任务通过execute(Runnable)方法欲添加到线程池时： 
 * 		如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
 * 		如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
 * 		如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
 * 		如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于 maximumPoolSize，那么通过 handler所指定的策略来处理此任务。
 * 
 * 		也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程 maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
 * 		当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
 * 
 * @author Administrator
 *
 */
public interface IThreadPool {
	/**
	 * 处理一个线程
	 * @param runnable	Runnable对象
	 */
	public void execute(Runnable runnable);
}
