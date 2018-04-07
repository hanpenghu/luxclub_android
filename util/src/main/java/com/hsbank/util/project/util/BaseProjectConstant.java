package com.hsbank.util.project.util;


/**
 * 工程_常量类
 * @author Arthur.Xie
 * 2014-12-16
 */
public interface BaseProjectConstant {
	/**工程_标识*/
	String APP_FLAG = "mayland_android";
	//---------------------------------------------
	/**部署环境_标识：开发*/
	String ENVIRONMENT_FLAG_DEVELOPMENT = "development";
	/**部署环境_标识：测试*/
	String ENVIRONMENT_FLAG_TEST = "test";
	/**部署环境_标识：生产*/
	String ENVIRONMENT_FLAG_PRODUCTION = "production";
	//---------------------------------------------
	/**元数据：发布渠道*/
	String META_DATA_APP_CHANNEL = "APP_CHANNEL";
	//---------------------------------------------
	/**根节点父ID*/
	int ROOT_PARENT_ID = 0;
	/**根节点ID*/
	int ROOT_ID = 0;
	/**根节点名称*/
	String ROOT_NAME = "全部";
	/**根节点编码*/
	String ROOT_CODE = "0";
	/**根节点备注*/
	String ROOT_MEMO = "根节点";
	//---------------------------------------------
	/**第一个节点的前置节点Id*/
	int FIRST_PREVIOUS_ID = 0;
	/**最后一个节点的后置节点Id*/
	int LAST_NEXT_ID = 0;
	//---------------------------------------------
	/**记录Id*/
	String ID = "id";
	/**记录名称*/
	String NAME = "name";
	/**组织Id*/
	String ORG_ID = "org_id";
	/**父节点Id*/
	String PARENT_ID = "parent_id";
	/**前置节点Id*/
	String PREVIOUS_ID = "previous_id";
	/**后置节点Id*/
	String NEXT_ID = "next_id";
	//---------------------------------------------
	/**下拉刷新：取第一页*/
	String FLAG_GET_LIST_NEW = "new";
	/**上拉加载更多：取下一页*/
	String FLAG_GET_LIST_NEXT = "next";
	//---------------------------------------------
	/**每次登录成功，都自动生成一个已登录用户的唯一标识：token*/
	String FLAG_TOKEN = "token";
	//---------------------------------------------
	/**日志级别：all(打开全部日志)*/
	String LOG_LEVEL_ALL = "all";
	/**日志级别：debug(用于调试)*/
	String LOG_LEVEL_DEBUG = "debug";
	/**日志级别：info(用于运行过程)*/
	String LOG_LEVEL_INFO = "info";
	/**日志级别：warn(用于潜在的错误)*/
	String LOG_LEVEL_WARN = "warn";
	/**日志级别：error(用于错误事件)*/
	String LOG_LEVEL_ERROR = "error";
	/**日志级别：fatal(用于严重错误事件)*/
	String LOG_LEVEL_FATAL = "fatal";
	/**日志级别：off(关闭日志)*/
	String LOG_LEVEL_OFF = "off";
	//----------------------------------------------
	/**加密策略：
	 <1>.default：	用默认加密方法加密
	 <2>.base64：	用base64方法加密
	 <3>.md5：		用md5算法加密
	 <4>.""：		为空时，不加密，明文保存
	 */
	String ENCRYPTION_POLICY_DEFAULT = "default";
	String ENCRYPTION_POLICY_BASE64 = "base64";
	String ENCRYPTION_POLICY_MD5 = "md5";
	String ENCRYPTION_POLICY_EMPTY = "";
}