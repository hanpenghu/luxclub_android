package com.hsbank.util.project.util;

import java.util.UUID;

/**
 * 工程_事件_基础类
 * Created by Administrator on 2016/1/13.
 */
public class BaseProjectEvent {
    /**登录系统事件*/
    public static final String BASE_LOGIN = UUID.randomUUID().toString();
    /**登出系统事件*/
    public static final String BASE_LOGOUT = UUID.randomUUID().toString();
    /**收到短信*/
    public static final String BASE_SMS_RECEIVED = UUID.randomUUID().toString();
    /**扫描二维码成功*/
    public static final String BASE_SCAN_SUCCESS = UUID.randomUUID().toString();
    /**扫描二维码失败*/
    public static final String BASE_SCAN_FAIL = UUID.randomUUID().toString();
    //---------------------------------------------------------------------------
    /**MainActivity：切换到【菜单01】*/
    public static final String BASE_MAIN_GOTO_MENU_01 = UUID.randomUUID().toString();
    /**MainActivity：切换到【菜单02】*/
    public static final String BASE_MAIN_GOTO_MENU_02 = UUID.randomUUID().toString();
    /**MainActivity：切换到【菜单03】*/
    public static final String BASE_MAIN_GOTO_MENU_03 = UUID.randomUUID().toString();
    /**MainActivity：切换到【菜单04】*/
    public static final String BASE_MAIN_GOTO_MENU_04 = UUID.randomUUID().toString();
    /**MainActivity：切换到【菜单05】*/
    public static final String BASE_MAIN_GOTO_MENU_05 = UUID.randomUUID().toString();
    //---------------------------------------------------------------------------
    /**GuideActivity：最后一项点击事件*/
    public static final String BASE_GUIDE_LAST_ITEM_CLICK = UUID.randomUUID().toString();
    //---------------------------------------------------------------------------
    /**消息命令*/
    private String command = null;
    /**消息对象*/
    private Object message = null;

    public BaseProjectEvent(String command, Object message) {
        this.command = command;
        this.message = message;
    }

    public BaseProjectEvent(String command) {
        this.command = command;
    }

    public String getCommand() {
        if (this.command == null) {
            this.command = "";
        }
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getMessage() {
        return this.message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
