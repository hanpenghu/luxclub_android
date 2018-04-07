package com.hsbank.util.java.collection.obj;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 同步队列，实现数据访问的同步。在包队列和命令队列中使用
 * 主要实现功能：
 * 1）队列容量控制
 * 2）在添加数据时，如果操作容量，可以等待
 * 3）在取数据时，如果为空，可以等待
 * 4）取指定数量数据出来进行处理
 * 
 * @author xwy
 * 2011-04-18
 * @param <E>
 */
public class SyncQueue<E> {
	/**私有队列*/
	private E[] queue;
	/**队列容量*/
	private volatile int capacity;
	/**队列中对象的数量*/
	private volatile int size;
	/**队列头指针*/
	private volatile int head;
	/**队列尾指针*/
	private volatile int tail;
	
	/**
	 * 构造函数
	 * @param cap	队列容量
	 */
	@SuppressWarnings("unchecked")
	public SyncQueue(int cap) {
		capacity = (cap > 0) ? cap : 1;
		queue = (E[])new Object[capacity];
		size = 0;
		head = 0;
		tail = 0;
	}
	
	/**
	 * 得到队列中对象的数量
	 * @return
	 */
	public int  getSize() {
		return size;
	}
	
	/**
	 * 队列是否满了
	 * @return
	 */
	public synchronized boolean isFull() {
		return (size == capacity);
	}

	/**
	 * 同步地往队列添加一个元素
	 * <p>
	 * 如果队列已满，则等待，一直等到添加成功才返回
	 * <p>
	 * @param obj
	 * @throws InterruptedException
	 */
	public synchronized void add(E obj) throws InterruptedException {
		if (obj == null) {
			return;		
		}
		while(size == capacity) {
			wait();
		}		
		queue[head] = obj;
		head = (head + 1) % capacity;
		size++;
		notifyAll();
	}
	
	/**
	 * 同步地从队列尾部删除一个元素，并且返回该元素
	 * <p>
	 * 其实就是一个取操作
	 * <p>
	 * 如果队列为空，则会等待，一直取到数据才返回。
	 * 
	 * @return 被删除的元素
	 * @throws InterruptedException
	 */
	public synchronized E remove() throws InterruptedException {
		while(size == 0) {
			wait();
		}
		E obj = queue[tail];
		queue[tail] = null;
		tail = (tail + 1) % capacity;
		size--;
		notifyAll();
		return obj;
	}

	/**
	 * 同步地往队列中添加多个元素（元素列表）
	 * <p>
	 * 如果队列已满，则等待，一直到所有元素添加成功才返回
	 * <p>
	 * @param list
	 * @throws InterruptedException
	 */
	public synchronized void add(List<E> list) throws InterruptedException {
		while(size == capacity) {
			wait();
		}
		Iterator<E> itr = list.iterator();
		while(itr.hasNext()) {
			while(size == capacity) {
				wait();
			}
			E obj = itr.next();
			itr.remove();
			if (obj == null) continue;
			queue[head] = obj;
			head = (head + 1) % capacity;
			size++;
		}
		notifyAll();		
	}

	/**
	 * 同步地从队列中取出多个元素。参考remove()
	 * @param limit
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized List<E> remove(int limit) throws InterruptedException {
		while(size == 0) {
			wait();
		}
		if (limit > size || limit <= 0) {
			limit = size;
		}
		LinkedList<E> list = new LinkedList<E>();
		for (int i = 0;i < limit;i++) {
			E obj = queue[tail];
			queue[tail] = null;
			tail = (tail + 1) % capacity;
			size--;
			list.add(obj);
		}
		notifyAll();
		return list;
	}
	
	@SuppressWarnings("unused")
	private synchronized List<E> addNoBlock(List<E> list) {
		if (size == capacity) {
			return list;
		}
		Iterator<E> itr = list.iterator();
		while(itr.hasNext()) {
			if (size == capacity) {
				return list;
			}
			E obj = itr.next();
			itr.remove();
			if (obj == null) {
				continue;
			}
			queue[head] = obj;
			head = (head + 1) % capacity;
			size++;
		}
		notifyAll();		
		return null;
	}

	@SuppressWarnings("unused")
	private synchronized List<E> removeNoBlock(int limit) {
		if (size == 0) {
			return null;
		}
		if (limit > size || limit <= 0)
			limit = size;
		LinkedList<E> list = new LinkedList<E>();
		for (int i = 0;i < limit;i++) {
			E obj = queue[tail];
			queue[tail] = null;
			tail = (tail + 1) % capacity;
			size--;
			list.add(obj);
		}
		notifyAll();
		return list;
	}
}
