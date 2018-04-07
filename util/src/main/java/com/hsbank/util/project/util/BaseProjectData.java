package com.hsbank.util.project.util;

import android.util.Log;

import com.hsbank.util.java.collection.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 工程_数据类
 * 单例类
 * @author Arthur.Xie
 * 2009-10-25
 */
public class BaseProjectData {
	/**单例*/
	private static BaseProjectData instance = null;
	/**当前用户对象*/
	private Map<String, Object> currentUser = null;
	
	/**私有构造函数*/
	protected BaseProjectData() {
		Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
	}
	
	/**
     * 得到单例
     * @return  
     */
	public static synchronized BaseProjectData getInstance() {
		return instance == null ? instance = new BaseProjectData() : instance;
	}

	public Map<String, Object> getCurrentUser() {
		if (currentUser == null) {
			currentUser = new HashMap<String, Object>();
		}
		return currentUser;
	}

	public void setCurrentUser(Map<String, Object> currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * 得到当前用户Id
	 * @return
	 */
	public String getCurrentUserId() {
		return MapUtil.getString(currentUser, BaseProjectConstant.ID);
	}
	
	/**
	 * 得到当前用户token
	 * @return
	 */
	public String getCurrentUserToken() {
		return MapUtil.getString(currentUser, BaseProjectConstant.FLAG_TOKEN);
	}
	
	/**
	 * 得到当前用户姓名
	 * @return
	 */
	public String getCurrentUserName() {
		return MapUtil.getString(currentUser, BaseProjectConstant.NAME);
	}
}
