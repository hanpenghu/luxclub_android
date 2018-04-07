package com.hsbank.util.java.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态常量类
 * @author arthur_xie
 * 2013-07-09
 */
public class StatusConstant {
    /**操作成功*/
    public static final String SUCCESS = "200";
    public static final String SUCCESS_TEXT = "操作成功";
    /**操作失败*/
    public static final String FAIL = "400";
    public static final String FAIL_TEXT = "操作失败";
    
    /**启用*/
    public static final String ENABLE = "enable";
    public static final String ENABLE_TEXT = "启用";
    /**禁用*/
    public static final String DISABLE = "disable";
    public static final String DISABLE_TEXT = "禁用";
    
    /**公共*/
    public static final String PUBLIC = "public";
    public static final String PUBLIC_TEXT = "公共";
    /**私有*/
    public static final String PRIVATE = "private";
    public static final String PRIVATE_TEXT = "私有";
    
    /**未读*/
    public static final String NEW = "new";
    public static final String NEW_TEXT = "未读";
    /**已读*/
    public static final String READ = "read";   
    public static final String READ_TEXT = "已读";
    
    /**有效*/
    public static final String VALID = "valid";
    public static final String VALID_TEXT = "有效";
    /**无效*/
    public static final String INVALID = "invalid";
    public static final String INVALID_TEXT = "无效";
    
    /**是*/
    public static final String TRUE = "true";
    public static final String TRUE_TEXT = "是";
    /**否*/
    public static final String FALSE = "false";
    public static final String FALSE_TEXT = "否";
    
    /**待审核*/
    public static final String AUDIT = "audit";
    public static final String AUDIT_TEXT = "待审核";
    /**审核中*/
    public static final String AUDITING = "auditing";
    public static final String AUDITING_TEXT = "审核中";
    /**审核通过*/
    public static final String AUDIT_SUCCESS = "audit_response";
    public static final String AUDIT_SUCCESS_TEXT = "审核通过";
    /**审核不通过*/
    public static final String AUDIT_FAIL = "audit_fail";
    public static final String AUDIT_FAIL_TEXT = "审核没有通过";
    
    /**待处理*/
    public static final String DEAL = "deal";
    public static final String DEAL_TEXT = "待处理";
    /**处理中*/
    public static final String DEALING = "dealing";
    public static final String DEALING_TEXT = "处理中";
    /**处理完成*/
    public static final String DEALED = "dealed";
    public static final String DEALED_TEXT = "处理完成";
    
    /**待发送*/
    public static final String SEND = "send";
    public static final String SEND_TEXT = "待发送";
    /**发送中*/
    public static final String SENDING = "sending";
    public static final String SENDING_TEXT = "发送中";
    /**暂停中*/    
    public static final String PAUSED = "paused";
    public static final String PAUSED_TEXT = "暂停中";
    /**发送完成*/
    public static final String SENT = "sent";
    public static final String SENT_TEXT = "已发送";
    
    /**已删除*/
    public static final String DELETED = "deleted";
    public static final String DELETED_TEXT = "已删除";
    
    /**把上面定义的默认值都放到一个集合里面去*/
    public static Map<String, Object> getMap() {
    	Map<String, Object> returnValue = new HashMap<String, Object>();
    	returnValue.put(SUCCESS, SUCCESS_TEXT);
    	returnValue.put(FAIL, FAIL_TEXT);
    	returnValue.put(ENABLE, ENABLE_TEXT);
    	returnValue.put(DISABLE, DISABLE_TEXT);
    	returnValue.put(PUBLIC, PUBLIC_TEXT);
    	returnValue.put(PRIVATE, PRIVATE_TEXT);
    	returnValue.put(NEW, NEW_TEXT);
    	returnValue.put(READ, READ_TEXT);
    	returnValue.put(VALID, VALID_TEXT);
    	returnValue.put(INVALID, INVALID_TEXT);
    	returnValue.put(TRUE, TRUE_TEXT);
    	returnValue.put(FALSE, FALSE_TEXT);
    	returnValue.put(AUDIT, AUDIT_TEXT);
    	returnValue.put(AUDITING, AUDITING_TEXT);
    	returnValue.put(AUDIT_SUCCESS, AUDIT_SUCCESS_TEXT);
    	returnValue.put(AUDIT_FAIL, AUDIT_FAIL_TEXT);
    	returnValue.put(DEAL, DEAL_TEXT);
    	returnValue.put(DEALING, DEALING_TEXT);
    	returnValue.put(DEALED, DEALED_TEXT);
    	returnValue.put(SEND, SEND_TEXT);
    	returnValue.put(SENDING, SENDING_TEXT);
    	returnValue.put(PAUSED, PAUSED_TEXT);
    	returnValue.put(SENT, SENT_TEXT);
    	returnValue.put(DELETED, DELETED_TEXT);
    	return returnValue;
    }
}
