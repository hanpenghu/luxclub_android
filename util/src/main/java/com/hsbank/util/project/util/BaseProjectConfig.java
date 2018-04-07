package com.hsbank.util.project.util;

import android.util.Log;

/**
 * 工程_配置类
 * 单例类
 * @author Arthur.Xie
 * 2009-10-25
 */
public class BaseProjectConfig {
	/**单例*/
	private static BaseProjectConfig instance = null;
	//---------------------------------------------------
	/**调试_标识*/
	private boolean debug = false;
	/**部署环境_标识*/
	private String environmentFlag = BaseProjectConstant.ENVIRONMENT_FLAG_DEVELOPMENT;
	/**加密策略*/
	private String encryptionPolicy = null;

    /**私有构造函数*/
	protected BaseProjectConfig() {
		Log.d(this.getClass().getName(), "................" + this.getClass().getName() + "..................");
	}

	/**
     * 得到单例
     * @return  单例
     */
	public static synchronized BaseProjectConfig getInstance() {
		return instance == null ? instance = new BaseProjectConfig() : instance;
	}

	public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

	public String getEnvironmentFlag() {
		return environmentFlag;
	}

	public void setEnvironmentFlag(String environmentFlag) {
		this.environmentFlag = environmentFlag;
	}

	public String getEncryptionPolicy() {
		if (this.encryptionPolicy == null) {
			this.encryptionPolicy = BaseProjectConstant.ENCRYPTION_POLICY_EMPTY;
		}
		return encryptionPolicy;
	}

	public void setEncryptionPolicy(String encryptionPolicy) {
		this.encryptionPolicy = encryptionPolicy;
	}
}
