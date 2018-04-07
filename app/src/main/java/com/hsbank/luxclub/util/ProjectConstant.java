package com.hsbank.luxclub.util;

import com.hsbank.util.project.util.BaseProjectConstant;

/**
 * 工程常量
 * 2015-09-11
 */
public interface ProjectConstant extends BaseProjectConstant {
    // 是否首次打开应用
    String IS_FIRST_ENTER = "is_first_enter";
    /**
     * 当前用户登录卡号
     */
    String CARDNO = "cardNo";
    /**
     * 当前用户登录手机号
     */
    String USER_MOBILE = "member_mobile";
    /**
     * 当前用户登录密码
     */
    String PASSWORD = "password";
    /**
     * 经理登录token
     */
    String MANAGER_TOKEN = "managerToken";
    /**
     * 经理登录userId
     */
    String MANAGER_USER_ID = "managerUserId";
    /**
     * 用户类型(5:管家(客服),6:移动管家)
     */
    String USER_TYPE = "user_type";
    /**
     * 预约时手机号码
     */
    String MOBILE = "MOBILE";
    /**
     * 专属的管家电话
     */
    String BUSINESS_MOBILE = "business_mobile";
    /**
     * 城市列表选择的城市
     */
    String CITY = "CITY";
    /**
     * 城市列表选择的城市id
     */
    String CITY_ID = "CITY_ID";
    //SP属性
    String MD_5_STRING = "stringMD5";
    // 是否开启通知消息推送
    String IS_PUSH_MESSAGE = "is_push_message";

}
