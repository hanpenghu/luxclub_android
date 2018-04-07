package com.hsbank.util.java.collection.obj;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 实现了序列化接口的List
 * @author Administrator
 * 2011-12-20
 * @param <E>
 */
public class SerializableList<E> implements Serializable {
	private static final long serialVersionUID = 1L;
	private java.util.List<E> list = new ArrayList<E>();

	public java.util.List<E> getList() {
		return list;
	}

	public void setList(java.util.List<E> list) {
		this.list = list;
	}

	public E get(int index) {
		return list.get(index);
	}

	public void add(E e) {
		this.list.add(e);
	}

	public void remove(int index) {
		list.remove(index);
	}
}
