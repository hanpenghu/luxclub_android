package com.hsbank.util.java.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置_常量类 
 * @author Arthur.Xie
 * 2009-11-09
 */
public class SystemConfigConstant {
	/**公司网址*/
	public static final String COMPANY_WEBSITE_KEY = "company_website";
	public static final String COMPANY_WEBSITE_VALUE = "http://www.maylandstudio.com";
	public static final String COMPANY_WEBSITE_MEMO = "公司网址";
	/**公司名称*/
	public static final String COMPANY_NAME_KEY = "company_name";
	public static final String COMPANY_NAME_VALUE = "mayland_studio";
	public static final String COMPANY_NAME_MEMO = "公司名称";
    /**项目名称*/
	public static final String PROJECT_NAME_KEY = "project_name";
	public static final String PROJECT_NAME_VALUE = "mayland rbac platform";
	public static final String PROJECT_NAME_MEMO = "项目名称";
	/**项目标识*/
	public static final String PROJECT_FLAG_KEY = "project_flag";
	public static final String PROJECT_FLAG_VALUE = "mayland_rbac";
	public static final String PROJECT_FLAG_MEMO = "项目标识";
    /**版权申请*/
	public static final String COPYRIGHT_KEY = "copyright";
	public static final String COPYRIGHT_VALUE = "mayland studio 版权所有 &#169; 2013";
	public static final String COPYRIGHT_MEMO = "版权申明";
	/**IP鉴权*/
	public static final String IP_AUTH_KEY = "ip_auth";
	public static final String IP_AUTH_VALUE = StatusConstant.DISABLE;
	public static final String IP_AUTH_MEMO = "Ip鉴权( enable | disable )";
	
    /**把上面定义的默认值都放到一个列表里面去*/
    public static List<Map<String, Object>> getDefaultList() {
    	List<Map<String, Object>> returnValue = new ArrayList<Map<String, Object>>();
    	Map<String, Object> companyNameMap = new HashMap<String, Object>();
    	//公司网址
    	companyNameMap.put("config_key", COMPANY_WEBSITE_KEY);
    	companyNameMap.put("config_value", COMPANY_WEBSITE_VALUE);
    	companyNameMap.put("remark", COMPANY_WEBSITE_MEMO);
    	returnValue.add(companyNameMap);
    	//公司名称
    	companyNameMap.put("config_key", COMPANY_NAME_KEY);
    	companyNameMap.put("config_value", COMPANY_NAME_VALUE);
    	companyNameMap.put("remark", COMPANY_NAME_MEMO);
    	returnValue.add(companyNameMap);
    	//项目名称
    	Map<String, Object> projectNameMap = new HashMap<String, Object>();
    	projectNameMap.put("config_key", PROJECT_NAME_KEY);
    	projectNameMap.put("config_value", PROJECT_NAME_VALUE);
    	projectNameMap.put("remark", PROJECT_NAME_MEMO);
    	returnValue.add(projectNameMap);
    	//项目标识
    	Map<String, Object> projectTagMap = new HashMap<String, Object>();
    	projectTagMap.put("config_key", PROJECT_FLAG_KEY);
    	projectTagMap.put("config_value", PROJECT_FLAG_VALUE);
    	projectTagMap.put("remark", PROJECT_FLAG_MEMO);
    	returnValue.add(projectTagMap);
    	//版权申请
    	Map<String, Object> copyrightMap = new HashMap<String, Object>();
    	copyrightMap.put("config_key", COPYRIGHT_KEY);
    	copyrightMap.put("config_value", COPYRIGHT_VALUE);
    	copyrightMap.put("remark", COPYRIGHT_MEMO);
    	returnValue.add(copyrightMap);
    	//Ip鉴权
    	Map<String, Object> ipAuthMap = new HashMap<String, Object>();
    	ipAuthMap.put("config_key", IP_AUTH_KEY);
    	ipAuthMap.put("config_value", IP_AUTH_VALUE);
    	ipAuthMap.put("remark", IP_AUTH_MEMO);
    	returnValue.add(ipAuthMap);
    	return returnValue;
    }
}
