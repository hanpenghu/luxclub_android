package com.hsbank.util.android.util.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 编辑时，用于保存数据的对象
 * @author Arthur.Xie
 * 2015-02-11
 */
public class EditBean {
	/**单例*/
	private static EditBean instance = null;
	/**Id*/
	private String id = "";
	
	/**私有构造函数*/
	private EditBean() {
	}
	
	/**
	 * 得到单例
	 * @return	单例
	 */
	public static synchronized EditBean getInstance() {
		return instance == null ? instance = new EditBean() : instance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
