package com.hsbank.util.java.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件相关_常量类 
 * @author Arthur.Xie
 * 2010-05-01
 */
public class EmailConstant {
	//----------------邮件发送状态----------------
    public static final String STATUS_S_OF_EMAIL_SEND = new StringBuffer()
    	.append("drafts|草稿箱,send|待发送,sending|发送中,paused|暂停中,sent|已发送,deleted|已删除")
    	.toString();
    /**待审核*/
    public static final String STATUS_OF_EMAIL_SEND_DRAFTS = "drafts";
    public static final String STATUS_NAME_OF_EMAIL_SEND_DRAFTS = "草稿箱";
    /**待发送*/    
    public static final String STATUS_OF_EMAIL_SEND_SEND = "send";
    public static final String STATUS_NAME_OF_EMAIL_SEND_SEND = "待发送";
    /**发送中*/    
    public static final String STATUS_OF_EMAIL_SEND_SENDTING = "sending";
    public static final String STATUS_NAME_OF_EMAIL_SEND_SENDTING = "发送中";
    /**暂停中*/    
    public static final String STATUS_OF_EMAIL_SEND_PAUSED = "paused";
    public static final String STATUS_NAME_OF_EMAIL_SEND_PAUSED = "暂停中";
    /**已发送*/
    public static final String STATUS_OF_EMAIL_SEND_SENT = "sent";
    public static final String STATUS_NAME_OF_EMAIL_SEND_SENT = "已发送";
    /**已删除*/
    public static final String STATUS_OF_EMAIL_SEND_DELETED = "deleted";
    public static final String STATUS_NAME_OF_EMAIL_SEND_DELETED = "已删除";
    //----------------邮件接收状态----------------
    public static final String STATUS_S_OF_EMAIL_RECEIVE = new StringBuffer()
		.append("received|已收到,read|已查看,dealed|已处理,deleted|已删除")
		.toString();
	/**已收到*/
	public static final String STATUS_OF_EMAIL_RECEIVE_RECEIVED = "received";
	public static final String STATUS_NAME_OF_EMAIL_RECEIVE_RECEIVED = "已收到";
	/**已查看*/
	public static final String STATUS_OF_EMAIL_RECEIVE_READ = "read";
	public static final String STATUS_NAME_OF_EMAIL_RECEIVE_READ = "已查看";
	/**已处理*/
	public static final String STATUS_OF_EMAIL_RECEIVE_DEALED = "dealed";
	public static final String STATUS_NAME_OF_EMAIL_RECEIVE_DEALED = "已处理";
	/**已删除*/    
	public static final String STATUS_OF_EMAIL_RECEIVE_DELETED = "deleted";
	public static final String STATUS_NAME_OF_EMAIL_RECEIVE_DELETED = "已删除";
    
    /**把上面定义的状态都放到一个集合里面去*/
    public static Map<String, String> getStatusMap() {
    	Map<String, String> returnValue = new HashMap<String, String>();
    	//status of email send 
    	returnValue.put(STATUS_OF_EMAIL_SEND_DRAFTS, STATUS_NAME_OF_EMAIL_SEND_DRAFTS);
    	returnValue.put(STATUS_OF_EMAIL_SEND_SEND, STATUS_NAME_OF_EMAIL_SEND_SEND);
    	returnValue.put(STATUS_OF_EMAIL_SEND_SENDTING, STATUS_NAME_OF_EMAIL_SEND_SENDTING);
    	returnValue.put(STATUS_OF_EMAIL_SEND_PAUSED, STATUS_NAME_OF_EMAIL_SEND_PAUSED);
    	returnValue.put(STATUS_OF_EMAIL_SEND_SENT, STATUS_NAME_OF_EMAIL_SEND_SENT);
    	returnValue.put(STATUS_OF_EMAIL_SEND_DELETED, STATUS_NAME_OF_EMAIL_SEND_DELETED);
    	//status of email receive
    	returnValue.put(STATUS_OF_EMAIL_RECEIVE_RECEIVED, STATUS_NAME_OF_EMAIL_RECEIVE_RECEIVED);
    	returnValue.put(STATUS_OF_EMAIL_RECEIVE_READ, STATUS_NAME_OF_EMAIL_RECEIVE_READ);
    	returnValue.put(STATUS_OF_EMAIL_RECEIVE_DEALED, STATUS_NAME_OF_EMAIL_RECEIVE_DEALED);
    	returnValue.put(STATUS_OF_EMAIL_RECEIVE_DELETED, STATUS_NAME_OF_EMAIL_RECEIVE_DELETED);
    	return returnValue;
    }
}
